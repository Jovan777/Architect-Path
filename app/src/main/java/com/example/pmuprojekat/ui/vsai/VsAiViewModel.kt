package com.example.pmuprojekat.ui.vsai

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.ai.AiChatRelevantTerm
import com.example.pmuprojekat.ai.LearnerContextBuilder
import com.example.pmuprojekat.ai.VsAiCompletionReason
import com.example.pmuprojekat.ai.VsAiConversationRole
import com.example.pmuprojekat.ai.VsAiConversationTurn
import com.example.pmuprojekat.ai.VsAiEvaluationRequest
import com.example.pmuprojekat.ai.VsAiEvaluationResult
import com.example.pmuprojekat.ai.VsAiEvaluationPolicy
import com.example.pmuprojekat.ai.VsAiEvaluationSignal
import com.example.pmuprojekat.ai.VsAiFinalAnalysis
import com.example.pmuprojekat.ai.VsAiFinalAnalysisRequest
import com.example.pmuprojekat.ai.VsAiGeneratedChallenge
import com.example.pmuprojekat.ai.VsAiInputValidator
import com.example.pmuprojekat.ai.VsAiLearnerContext
import com.example.pmuprojekat.ai.VsAiLevelContext
import com.example.pmuprojekat.ai.VsAiLevelContextProvider
import com.example.pmuprojekat.ai.VsAiLocalAnalysisFactory
import com.example.pmuprojekat.ai.VsAiResponseParser
import com.example.pmuprojekat.ai.VsAiService
import com.example.pmuprojekat.ai.VsAiStartRequest
import com.example.pmuprojekat.data.encyclopedia.EncyclopediaTermRetriever
import com.example.pmuprojekat.data.repository.VsAiAttempt
import com.example.pmuprojekat.data.repository.VsAiAttemptStatus
import com.example.pmuprojekat.data.repository.VsAiMessage
import com.example.pmuprojekat.data.repository.VsAiMessageRole
import com.example.pmuprojekat.data.repository.VsAiPersistenceCodec
import com.example.pmuprojekat.data.repository.VsAiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class VsAiStage {
    IDLE,
    LOADING_FIRST_QUESTION,
    ACTIVE,
    EVALUATING,
    BUILDING_FINAL_ANALYSIS,
    FINAL_ANALYSIS,
    ERROR
}

data class VsAiUiState(
    val stage: VsAiStage = VsAiStage.IDLE,
    val levelContext: VsAiLevelContext? = null,
    val learnerContext: VsAiLearnerContext? = null,
    val relevantTerms: List<AiChatRelevantTerm> = emptyList(),
    val attemptId: String? = null,
    val challenge: VsAiGeneratedChallenge? = null,
    val messages: List<VsAiMessage> = emptyList(),
    val roundsAnswered: Int = 0,
    val inputText: String = "",
    val finalAnalysis: VsAiFinalAnalysis? = null,
    val completionReason: VsAiCompletionReason? = null,
    val errorMessage: String? = null,
    val canRetry: Boolean = false,
    val history: List<VsAiAttempt> = emptyList(),
    val isHistoryLoading: Boolean = true
) {
    val maximumRounds: Int
        get() = levelContext?.maximumRounds ?: 1

    val currentRound: Int
        get() = (roundsAnswered + 1).coerceIn(1, maximumRounds)

    val canSend: Boolean
        get() = stage == VsAiStage.ACTIVE && inputText.isNotBlank()

    val completedHistoryCount: Int
        get() = history.count { it.status != VsAiAttemptStatus.IN_PROGRESS }
}

@HiltViewModel
class VsAiViewModel @Inject constructor(
    private val service: VsAiService,
    private val levelContextProvider: VsAiLevelContextProvider,
    private val learnerContextBuilder: LearnerContextBuilder,
    private val termRetriever: EncyclopediaTermRetriever,
    private val repository: VsAiRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(VsAiUiState())
    val uiState: StateFlow<VsAiUiState> = _uiState.asStateFlow()

    private var challengeJob: Job? = null
    private var retryAction: RetryAction? = null

    init {
        viewModelScope.launch {
            repository.observeAttempts().collect { attempts ->
                _uiState.update { it.copy(history = attempts, isHistoryLoading = false) }
            }
        }
    }

    fun ensureChallenge(levelId: String) {
        val state = _uiState.value
        if (state.levelContext?.levelId == levelId && state.stage != VsAiStage.IDLE) return

        challengeJob?.cancel()
        challengeJob = viewModelScope.launch {
            val levelContext = runCatching { levelContextProvider.get(levelId) }.getOrNull()
                ?: return@launch showStartError("Izabrani nivo nije dostupan.")
            val existing = repository.loadLatestInProgress(levelId)
            if (existing == null) {
                startFreshChallenge(levelContext)
            } else {
                resumeChallenge(levelContext, existing)
            }
        }
    }

    fun startNewChallenge(levelId: String) {
        challengeJob?.cancel()
        challengeJob = viewModelScope.launch {
            val current = _uiState.value
            if (current.attemptId != null && current.stage in activeStages) {
                repository.markStoppedWithoutAnalysis(
                    current.attemptId,
                    "Izazov je prekinut pokretanjem novog izazova."
                )
            }
            val levelContext = runCatching { levelContextProvider.get(levelId) }.getOrNull()
                ?: return@launch showStartError("Izabrani nivo nije dostupan.")
            startFreshChallenge(levelContext)
        }
    }

    fun updateInput(text: String) {
        _uiState.update { state ->
            state.copy(inputText = text, errorMessage = null, canRetry = false)
        }
    }

    fun submitAnswer() {
        val answer = _uiState.value.inputText.trim()
        val validationError = VsAiInputValidator.validate(answer)
        if (validationError != null) {
            _uiState.update { it.copy(errorMessage = validationError, canRetry = false) }
            return
        }

        val state = _uiState.value
        val attemptId = state.attemptId ?: return
        val challenge = state.challenge ?: return
        val levelContext = state.levelContext ?: return
        val learnerContext = state.learnerContext ?: return
        if (state.stage != VsAiStage.ACTIVE) return

        challengeJob?.cancel()
        challengeJob = viewModelScope.launch {
            val round = state.currentRound
            val userMessage = repository.addMessage(
                attemptId = attemptId,
                role = VsAiMessageRole.USER_ANSWER,
                content = answer,
                roundNumber = round
            )
            repository.updateRounds(attemptId, round)
            val messages = state.messages + userMessage
            _uiState.update {
                it.copy(
                    stage = VsAiStage.EVALUATING,
                    messages = messages,
                    roundsAnswered = round,
                    inputText = "",
                    errorMessage = null,
                    canRetry = false
                )
            }

            evaluatePersistedAnswer(
                attemptId = attemptId,
                challenge = challenge,
                levelContext = levelContext,
                learnerContext = learnerContext,
                relevantTerms = state.relevantTerms,
                messages = messages,
                round = round
            )
        }
    }

    fun retry() {
        when (retryAction) {
            RetryAction.START -> {
                val levelId = _uiState.value.levelContext?.levelId ?: return
                startNewChallenge(levelId)
            }
            RetryAction.EVALUATE -> retryEvaluation()
            null -> Unit
        }
    }

    fun stopChallenge() {
        val state = _uiState.value
        val attemptId = state.attemptId ?: return
        val challenge = state.challenge ?: return
        val levelContext = state.levelContext ?: return
        val learnerContext = state.learnerContext ?: return
        if (state.stage !in activeStages) return

        challengeJob?.cancel()
        challengeJob = viewModelScope.launch {
            finishChallenge(
                attemptId = attemptId,
                levelContext = levelContext,
                learnerContext = learnerContext,
                challenge = challenge,
                messages = _uiState.value.messages,
                completionReason = VsAiCompletionReason.USER_STOPPED
            )
        }
    }

    private suspend fun startFreshChallenge(levelContext: VsAiLevelContext) {
        retryAction = null
        _uiState.update {
            it.copy(
                stage = VsAiStage.LOADING_FIRST_QUESTION,
                levelContext = levelContext,
                learnerContext = null,
                relevantTerms = emptyList(),
                attemptId = null,
                challenge = null,
                messages = emptyList(),
                roundsAnswered = 0,
                inputText = "",
                finalAnalysis = null,
                completionReason = null,
                errorMessage = null,
                canRetry = false
            )
        }

        try {
            val learnerContext = learnerContextBuilder.build(levelContext.levelId)
            val relevantTerms = retrieveTerms(levelContext, learnerContext)
            val attempt = repository.createAttempt(levelContext, learnerContext, relevantTerms)

            service.generateChallenge(
                VsAiStartRequest(
                    levelContext = levelContext,
                    learnerContext = learnerContext,
                    relevantTerms = relevantTerms
                )
            ).fold(
                onSuccess = { challenge ->
                    val firstMessage = repository.saveChallenge(attempt.id, challenge)
                    _uiState.update {
                        it.copy(
                            stage = VsAiStage.ACTIVE,
                            learnerContext = learnerContext,
                            relevantTerms = relevantTerms,
                            attemptId = attempt.id,
                            challenge = challenge,
                            messages = listOf(firstMessage),
                            roundsAnswered = 0,
                            errorMessage = null,
                            canRetry = false
                        )
                    }
                },
                onFailure = { error ->
                    Log.e(TAG, "VS AI challenge generation failed", error)
                    repository.markFailed(attempt.id, "Početno pitanje nije generisano.")
                    retryAction = RetryAction.START
                    _uiState.update {
                        it.copy(
                            stage = VsAiStage.ERROR,
                            learnerContext = learnerContext,
                            relevantTerms = relevantTerms,
                            attemptId = attempt.id,
                            errorMessage = GENERIC_ERROR,
                            canRetry = true
                        )
                    }
                }
            )
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Log.e(TAG, "VS AI challenge setup failed", error)
            showStartError(GENERIC_ERROR)
        }
    }

    private suspend fun resumeChallenge(
        levelContext: VsAiLevelContext,
        snapshot: com.example.pmuprojekat.data.repository.VsAiSessionSnapshot
    ) {
        val learnerContext = learnerContextBuilder.build(levelContext.levelId)
        val relevantTerms = retrieveTerms(levelContext, learnerContext)
        val lastRole = snapshot.messages.lastOrNull()?.role
        val pendingCompletionReason = snapshot.attempt.completionReason
            ?: terminalReasonFromMessages(snapshot.messages)
        val waitsForEvaluation = lastRole == VsAiMessageRole.USER_ANSWER
        retryAction = if (waitsForEvaluation && pendingCompletionReason == null) {
            RetryAction.EVALUATE
        } else {
            null
        }

        _uiState.update {
            it.copy(
                stage = when {
                    pendingCompletionReason != null -> VsAiStage.BUILDING_FINAL_ANALYSIS
                    waitsForEvaluation -> VsAiStage.ERROR
                    else -> VsAiStage.ACTIVE
                },
                levelContext = levelContext,
                learnerContext = learnerContext,
                relevantTerms = relevantTerms,
                attemptId = snapshot.attempt.id,
                challenge = snapshot.challenge,
                messages = snapshot.messages,
                roundsAnswered = snapshot.attempt.roundsCount,
                inputText = "",
                finalAnalysis = null,
                completionReason = null,
                errorMessage = if (waitsForEvaluation && pendingCompletionReason == null) {
                    "Odgovor je sačuvan, ali još nije procenjen. Pokušaj ponovo."
                } else null,
                canRetry = waitsForEvaluation && pendingCompletionReason == null
            )
        }

        if (pendingCompletionReason != null) {
            finishChallenge(
                attemptId = snapshot.attempt.id,
                levelContext = levelContext,
                learnerContext = learnerContext,
                challenge = snapshot.challenge,
                messages = snapshot.messages,
                completionReason = pendingCompletionReason
            )
        }
    }

    private fun retryEvaluation() {
        val state = _uiState.value
        val attemptId = state.attemptId ?: return
        val challenge = state.challenge ?: return
        val levelContext = state.levelContext ?: return
        val learnerContext = state.learnerContext ?: return
        val lastUserMessage = state.messages.lastOrNull { it.role == VsAiMessageRole.USER_ANSWER }
            ?: return

        challengeJob?.cancel()
        challengeJob = viewModelScope.launch {
            _uiState.update {
                it.copy(stage = VsAiStage.EVALUATING, errorMessage = null, canRetry = false)
            }
            evaluatePersistedAnswer(
                attemptId = attemptId,
                challenge = challenge,
                levelContext = levelContext,
                learnerContext = learnerContext,
                relevantTerms = state.relevantTerms,
                messages = state.messages,
                round = lastUserMessage.roundNumber
            )
        }
    }

    private suspend fun evaluatePersistedAnswer(
        attemptId: String,
        challenge: VsAiGeneratedChallenge,
        levelContext: VsAiLevelContext,
        learnerContext: VsAiLearnerContext,
        relevantTerms: List<AiChatRelevantTerm>,
        messages: List<VsAiMessage>,
        round: Int
    ) {
        val previousSignals = evaluationSignals(messages)
        val request = VsAiEvaluationRequest(
            levelContext = levelContext,
            learnerContext = learnerContext,
            challenge = challenge,
            relevantTerms = relevantTerms,
            conversation = conversation(messages),
            previousEvaluationSignals = previousSignals,
            currentRound = round
        )

        service.evaluateAnswer(request).fold(
            onSuccess = { result ->
                retryAction = null
                handleEvaluationResult(
                    attemptId = attemptId,
                    levelContext = levelContext,
                    learnerContext = learnerContext,
                    challenge = challenge,
                    messages = messages,
                    previousSignals = previousSignals,
                    result = result,
                    round = round
                )
            },
            onFailure = { error ->
                Log.e(TAG, "VS AI answer evaluation failed", error)
                retryAction = RetryAction.EVALUATE
                _uiState.update {
                    it.copy(stage = VsAiStage.ERROR, errorMessage = GENERIC_ERROR, canRetry = true)
                }
            }
        )
    }

    private suspend fun handleEvaluationResult(
        attemptId: String,
        levelContext: VsAiLevelContext,
        learnerContext: VsAiLearnerContext,
        challenge: VsAiGeneratedChallenge,
        messages: List<VsAiMessage>,
        previousSignals: List<VsAiEvaluationSignal>,
        result: VsAiEvaluationResult,
        round: Int
    ) {
        val effectiveResult = VsAiEvaluationPolicy.normalize(
            levelContext = levelContext,
            challenge = challenge,
            round = round,
            result = result
        )
        val shouldContinue = effectiveResult.shouldContinue
        val visibleContent = if (shouldContinue) {
            effectiveResult.visibleFollowUp()
        } else {
            effectiveResult.visibleMessage
        }.trim()
        val aiMessage = repository.addMessage(
            attemptId = attemptId,
            role = VsAiMessageRole.AI_FOLLOW_UP,
            content = visibleContent.ifBlank { "Procena runde je završena." },
            roundNumber = if (shouldContinue) round + 1 else round,
            hiddenEvaluationJson = VsAiPersistenceCodec.encodeEvaluation(effectiveResult)
        )
        val updatedMessages = messages + aiMessage

        if (shouldContinue) {
            _uiState.update {
                it.copy(
                    stage = VsAiStage.ACTIVE,
                    messages = updatedMessages,
                    errorMessage = null,
                    canRetry = false
                )
            }
            return
        }

        val completionReason = if (effectiveResult.isSatisfied) {
            VsAiCompletionReason.AI_SATISFIED
        } else {
            VsAiCompletionReason.MAX_ROUNDS
        }
        finishChallenge(
            attemptId = attemptId,
            levelContext = levelContext,
            learnerContext = learnerContext,
            challenge = challenge,
            messages = updatedMessages,
            completionReason = completionReason,
            knownSignals = previousSignals + effectiveResult.toSignal()
        )
    }

    private suspend fun finishChallenge(
        attemptId: String,
        levelContext: VsAiLevelContext,
        learnerContext: VsAiLearnerContext,
        challenge: VsAiGeneratedChallenge,
        messages: List<VsAiMessage>,
        completionReason: VsAiCompletionReason,
        knownSignals: List<VsAiEvaluationSignal> = evaluationSignals(messages)
    ) {
        retryAction = null
        repository.markFinishing(attemptId, completionReason)
        _uiState.update {
            it.copy(
                stage = VsAiStage.BUILDING_FINAL_ANALYSIS,
                messages = messages,
                errorMessage = null,
                canRetry = false
            )
        }

        val request = VsAiFinalAnalysisRequest(
            levelContext = levelContext,
            learnerContext = learnerContext,
            challenge = challenge,
            conversation = conversation(messages),
            evaluationSignals = knownSignals,
            completionReason = completionReason
        )
        val analysis = service.generateFinalAnalysis(request).getOrElse { error ->
            Log.e(TAG, "VS AI final analysis failed; using local summary", error)
            VsAiLocalAnalysisFactory.create(request)
        }
        val status = when (completionReason) {
            VsAiCompletionReason.USER_STOPPED -> VsAiAttemptStatus.STOPPED_BY_USER
            VsAiCompletionReason.ERROR -> VsAiAttemptStatus.FAILED
            else -> VsAiAttemptStatus.COMPLETED
        }
        val targetConcepts = buildList {
            add(challenge.targetConcept)
            knownSignals.flatMapTo(this, VsAiEvaluationSignal::targetConcepts)
        }.distinct()
        val finalMessage = repository.completeAttempt(
            attemptId = attemptId,
            status = status,
            completionReason = completionReason,
            analysis = analysis,
            targetConcepts = targetConcepts,
            roundNumber = _uiState.value.roundsAnswered.coerceAtLeast(1)
        )

        _uiState.update {
            it.copy(
                stage = VsAiStage.FINAL_ANALYSIS,
                messages = messages + finalMessage,
                finalAnalysis = analysis,
                completionReason = completionReason,
                errorMessage = null,
                canRetry = false
            )
        }
    }

    private fun retrieveTerms(
        levelContext: VsAiLevelContext,
        learnerContext: VsAiLearnerContext
    ): List<AiChatRelevantTerm> {
        val query = buildList {
            addAll(learnerContext.weakerTaskTypes)
            addAll(levelContext.typicalConcepts)
        }.joinToString(" ")
        return termRetriever.findRelevantTerms(query, MAX_RELEVANT_TERMS)
    }

    private fun conversation(messages: List<VsAiMessage>): List<VsAiConversationTurn> {
        return messages.mapNotNull { message ->
            val role = when (message.role) {
                VsAiMessageRole.AI_QUESTION,
                VsAiMessageRole.AI_FOLLOW_UP -> VsAiConversationRole.AI
                VsAiMessageRole.USER_ANSWER -> VsAiConversationRole.USER
                VsAiMessageRole.AI_FINAL_ANALYSIS,
                VsAiMessageRole.SYSTEM -> null
            }
            role?.let {
                VsAiConversationTurn(
                    role = it,
                    content = message.content,
                    roundNumber = message.roundNumber
                )
            }
        }
    }

    private fun evaluationSignals(messages: List<VsAiMessage>): List<VsAiEvaluationSignal> {
        return messages.mapNotNull { message ->
            if (message.role != VsAiMessageRole.AI_FOLLOW_UP) return@mapNotNull null
            val hidden = message.hiddenEvaluationJson ?: return@mapNotNull null
            runCatching { VsAiResponseParser.parseEvaluation(hidden).toSignal() }.getOrNull()
        }
    }

    private fun terminalReasonFromMessages(messages: List<VsAiMessage>): VsAiCompletionReason? {
        val lastMessage = messages.lastOrNull() ?: return null
        if (lastMessage.role != VsAiMessageRole.AI_FOLLOW_UP) return null
        val hiddenEvaluation = lastMessage.hiddenEvaluationJson ?: return null
        val result = runCatching {
            VsAiResponseParser.parseEvaluation(hiddenEvaluation)
        }.getOrNull() ?: return null
        if (result.shouldContinue) return null
        return if (result.isSatisfied) {
            VsAiCompletionReason.AI_SATISFIED
        } else {
            VsAiCompletionReason.MAX_ROUNDS
        }
    }

    private fun showStartError(message: String) {
        retryAction = RetryAction.START
        _uiState.update {
            it.copy(stage = VsAiStage.ERROR, errorMessage = message, canRetry = true)
        }
    }

    private enum class RetryAction {
        START,
        EVALUATE
    }

    private companion object {
        const val TAG = "VsAiViewModel"
        const val MAX_RELEVANT_TERMS = 5
        const val GENERIC_ERROR = "AI izazov trenutno nije dostupan. Pokušaj ponovo kasnije."
        val activeStages = setOf(
            VsAiStage.ACTIVE,
            VsAiStage.EVALUATING,
            VsAiStage.ERROR
        )
    }
}
