package com.example.pmuprojekat.ui.question


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.ai.AiAnalysisOptionContext
import com.example.pmuprojekat.ai.AiAnalysisRequest
import com.example.pmuprojekat.ai.AiAnalysisService
import com.example.pmuprojekat.ai.AiAnalysisStepContext
import com.example.pmuprojekat.ai.AiAnalysisZoneContext
import com.example.pmuprojekat.ai.AiSketchAnalysisRequest
import com.example.pmuprojekat.ai.AiSketchAnalysisService
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.core.model.XpCalculator
import com.example.pmuprojekat.data.local.entity.UserStepAnswerEntity
import com.example.pmuprojekat.data.local.relation.QuestionWithSteps
import com.example.pmuprojekat.data.repository.LearningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val repository: LearningRepository,
    private val aiAnalysisService: AiAnalysisService,
    private val aiSketchAnalysisService: AiSketchAnalysisService
) : ViewModel() {

    private val selectedQuestionId = MutableStateFlow<String?>(null)
    private val currentStepIndex = MutableStateFlow(0)
    private val draftsByStepId = MutableStateFlow<Map<String, StepAnswerDraft>>(emptyMap())
    private val feedbackByStepId = MutableStateFlow<Map<String, StepFeedbackUi>>(emptyMap())
    private val answeredStepIds = MutableStateFlow<Set<String>>(emptySet())
    private val correctStepIds = MutableStateFlow<Set<String>>(emptySet())
    private val completedQuestionIds = MutableStateFlow<Set<String>>(emptySet())
    private val shuffledOptionIdsByStepId = MutableStateFlow<Map<String, List<String>>>(emptyMap())
    private val newlyAwardedXpByQuestionId = MutableStateFlow<Map<String, Int>>(emptyMap())
    private val aiFollowUpAnswer = MutableStateFlow("")
    private val isAiAnalysisLoading = MutableStateFlow(false)
    private val aiAnalysisText = MutableStateFlow<String?>(null)
    private val aiAnalysisError = MutableStateFlow<String?>(null)
    private val isSketchAnalysisLoading = MutableStateFlow(false)
    private val sketchAnalysisText = MutableStateFlow<String?>(null)
    private val sketchAnalysisError = MutableStateFlow<String?>(null)

    private data class QuestionRuntimeState(
        val questionWithSteps: QuestionWithSteps?,
        val stepIndex: Int,
        val drafts: Map<String, StepAnswerDraft>,
        val feedbacks: Map<String, StepFeedbackUi>,
        val answered: Set<String>
    )

    private data class AiAnalysisRuntimeState(
        val followUpAnswer: String,
        val isLoading: Boolean,
        val text: String?,
        val error: String?,
        val isSketchLoading: Boolean,
        val sketchText: String?,
        val sketchError: String?
    )

    private data class TextAiRuntimeState(
        val followUpAnswer: String,
        val isLoading: Boolean,
        val text: String?,
        val error: String?
    )

    private data class SketchAiRuntimeState(
        val isLoading: Boolean,
        val text: String?,
        val error: String?
    )

    private val questionFlow = selectedQuestionId.flatMapLatest { questionId ->
        if (questionId == null) {
            flowOf(null)
        } else {
            repository.observeQuestionWithSteps(questionId)
        }
    }

    private val runtimeState = combine(
        questionFlow,
        currentStepIndex,
        draftsByStepId,
        feedbackByStepId,
        answeredStepIds
    ) { questionWithSteps,
        stepIndex,
        drafts,
        feedbacks,
        answered ->

        QuestionRuntimeState(
            questionWithSteps = questionWithSteps,
            stepIndex = stepIndex,
            drafts = drafts,
            feedbacks = feedbacks,
            answered = answered
        )
    }

    private val textAiRuntimeState = combine(
        aiFollowUpAnswer,
        isAiAnalysisLoading,
        aiAnalysisText,
        aiAnalysisError
    ) { answer, isLoading, text, error ->
        TextAiRuntimeState(
            followUpAnswer = answer,
            isLoading = isLoading,
            text = text,
            error = error
        )
    }

    private val sketchAiRuntimeState = combine(
        isSketchAnalysisLoading,
        sketchAnalysisText,
        sketchAnalysisError
    ) { isLoading, text, error ->
        SketchAiRuntimeState(
            isLoading = isLoading,
            text = text,
            error = error
        )
    }

    private val aiAnalysisRuntimeState = combine(
        textAiRuntimeState,
        sketchAiRuntimeState
    ) { textAi, sketchAi ->
        AiAnalysisRuntimeState(
            followUpAnswer = textAi.followUpAnswer,
            isLoading = textAi.isLoading,
            text = textAi.text,
            error = textAi.error,
            isSketchLoading = sketchAi.isLoading,
            sketchText = sketchAi.text,
            sketchError = sketchAi.error
        )
    }

    val uiState = combine(
        runtimeState,
        correctStepIds,
        completedQuestionIds,
        newlyAwardedXpByQuestionId,
        aiAnalysisRuntimeState
    ) { runtime,
        correct,
        completed,
        newlyAwardedXp,
        aiAnalysis ->

        val questionWithSteps = runtime.questionWithSteps

        if (questionWithSteps == null) {
            QuestionUiState()
        } else {
            questionWithSteps.toQuestionUiState(
                currentStepIndex = runtime.stepIndex,
                drafts = runtime.drafts,
                feedbacks = runtime.feedbacks,
                answeredStepIds = runtime.answered,
                correctStepIds = correct,
                completedQuestionIds = completed,
                newlyAwardedXpByQuestionId = newlyAwardedXp,
                aiAnalysis = aiAnalysis
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = QuestionUiState()
    )

    fun startQuestion(questionId: String) {
        loadQuestion(questionId = questionId, restartAttempt = true)
    }

    fun ensureQuestionLoaded(questionId: String) {
        loadQuestion(questionId = questionId, restartAttempt = false)
    }

    private fun loadQuestion(questionId: String, restartAttempt: Boolean) {
        if (!restartAttempt && selectedQuestionId.value == questionId) return

        selectedQuestionId.value = questionId
        currentStepIndex.value = 0
        draftsByStepId.value = emptyMap()
        feedbackByStepId.value = emptyMap()
        answeredStepIds.value = emptySet()
        correctStepIds.value = emptySet()
        shuffledOptionIdsByStepId.value = emptyMap()
        aiFollowUpAnswer.value = ""
        isAiAnalysisLoading.value = false
        aiAnalysisText.value = null
        aiAnalysisError.value = null
        isSketchAnalysisLoading.value = false
        sketchAnalysisText.value = null
        sketchAnalysisError.value = null

        if (restartAttempt) {
            completedQuestionIds.value = completedQuestionIds.value - questionId
        }
    }

    fun updateAiFollowUpAnswer(value: String) {
        aiFollowUpAnswer.value = value
        aiAnalysisError.value = null
    }

    fun requestAiAnalysis() {
        val state = uiState.value
        if (!state.isCompleted || isAiAnalysisLoading.value) return

        val request = buildAiAnalysisRequest(state)

        viewModelScope.launch {
            isAiAnalysisLoading.value = true
            aiAnalysisError.value = null

            aiAnalysisService.analyze(request)
                .onSuccess { analysis ->
                    aiAnalysisText.value = analysis
                }
                .onFailure { error ->
                    Log.e(AI_LOG_TAG, "Completed-task AI analysis failed", error)
                    aiAnalysisError.value =
                        "AI analiza trenutno nije dostupna. Proveri podešavanja modela ili pokušaj ponovo kasnije."
                }

            isAiAnalysisLoading.value = false
        }
    }

    fun clearSketchAnalysis() {
        sketchAnalysisText.value = null
        sketchAnalysisError.value = null
    }

    fun requestSketchAnalysis(imageBytes: ByteArray, imageMimeType: String) {
        val state = uiState.value
        val questionId = state.questionId ?: return
        val step = state.steps.firstOrNull() ?: return

        if (isSketchAnalysisLoading.value) return

        val request = AiSketchAnalysisRequest(
            questionId = questionId,
            title = state.title,
            systemDescription = state.prompt,
            drawingInstruction = step.instruction,
            drawingChecklist = step.codeBlock.orEmpty(),
            internalRubric = step.explanation.orEmpty(),
            imageBytes = imageBytes,
            imageMimeType = imageMimeType
        )

        viewModelScope.launch {
            isSketchAnalysisLoading.value = true
            sketchAnalysisError.value = null

            aiSketchAnalysisService.analyze(request)
                .onSuccess { analysis ->
                    sketchAnalysisText.value = analysis
                    answeredStepIds.value = answeredStepIds.value + step.stepId
                    correctStepIds.value = correctStepIds.value + step.stepId
                    feedbackByStepId.value = feedbackByStepId.value + (
                        step.stepId to StepFeedbackUi(
                            isCorrect = true,
                            title = "AI analiza je završena",
                            message = "Fotografija skice je poslata na mentorsku analizu."
                        )
                    )

                    repository.saveStepAnswer(
                        UserStepAnswerEntity(
                            userId = LearningRepository.LOCAL_USER_ID,
                            questionId = questionId,
                            stepId = step.stepId,
                            freeTextAnswer = "Fotografija skice je poslata na AI analizu.",
                            isCorrect = true
                        )
                    )

                    if (!state.isCompleted) {
                        val reward = repository.completeQuestion(
                            questionId = questionId,
                            scorePercent = 100
                        )

                        newlyAwardedXpByQuestionId.value =
                            newlyAwardedXpByQuestionId.value + (questionId to reward.newlyAwardedXp)
                        completedQuestionIds.value = completedQuestionIds.value + questionId
                    }
                }
                .onFailure { error ->
                    Log.e(AI_LOG_TAG, "AI sketch analysis failed", error)
                    sketchAnalysisError.value =
                        "AI analiza skice trenutno nije dostupna. Proveri podešavanja modela ili pokušaj ponovo kasnije."
                }

            isSketchAnalysisLoading.value = false
        }
    }

    fun toggleOption(step: QuestionStepUi, optionId: String) {
        if (isStepLocked(step.stepId)) return

        val draft = getOrCreateDraft(step)

        val isCodeDecisionChoice =
            step.type == StepType.SINGLE_CHOICE.id && !step.codeBlock.isNullOrBlank()

        if (
            isCodeDecisionChoice &&
            draft.selectedOptionIds.isNotEmpty() &&
            !isDraftReselectionAllowedForCodeChoice(step)
        ) {
            return
        }

        val updatedSelected = when (step.type) {
            StepType.SINGLE_CHOICE.id,
            StepType.VISUAL_MAPPING.id -> {
                setOf(optionId)
            }

            StepType.MULTI_CHOICE.id,
            StepType.HOTSPOT.id -> {
                if (draft.selectedOptionIds.contains(optionId)) {
                    draft.selectedOptionIds - optionId
                } else {
                    draft.selectedOptionIds + optionId
                }
            }

            else -> draft.selectedOptionIds
        }

        updateDraft(
            stepId = step.stepId,
            draft = draft.copy(selectedOptionIds = updatedSelected)
        )
    }

    private fun isDraftReselectionAllowedForCodeChoice(step: QuestionStepUi): Boolean {
        return step.stepId.startsWith("J3.") || step.stepId.startsWith("M2.")
    }



    fun moveOrderedOption(step: QuestionStepUi, optionId: String, direction: Int) {
        if (isStepLocked(step.stepId)) return


        val draft = getOrCreateDraft(step)
        val current = draft.orderedOptionIds.toMutableList()



        val index = current.indexOf(optionId)
        if (index == -1) return

        val targetIndex = (index + direction).coerceIn(0, current.lastIndex)
        if (index == targetIndex) return

        current.removeAt(index)
        current.add(targetIndex, optionId)

        updateDraft(
            stepId = step.stepId,
            draft = draft.copy(orderedOptionIds = current)
        )
    }

    fun updateOrderedOptions(step: QuestionStepUi, orderedOptionIds: List<String>) {
        if (isStepLocked(step.stepId)) return

        val draft = getOrCreateDraft(step)
        val currentIds = draft.orderedOptionIds
        val currentIdSet = currentIds.toSet()
        val reorderedIds = orderedOptionIds
            .filter { it in currentIdSet }
            .distinct()
        val missingIds = currentIds.filterNot { it in reorderedIds }
        val updatedIds = reorderedIds + missingIds

        if (updatedIds == currentIds) return

        updateDraft(
            stepId = step.stepId,
            draft = draft.copy(orderedOptionIds = updatedIds)
        )
    }

    fun excludeOrderedOption(step: QuestionStepUi, optionId: String) {

        if (isStepLocked(step.stepId)) return

        val draft = getOrCreateDraft(step)

        updateDraft(
            stepId = step.stepId,
            draft = draft.copy(
                orderedOptionIds = draft.orderedOptionIds.filterNot { it == optionId },
                excludedOptionIds = draft.excludedOptionIds + optionId
            )
        )
    }

    fun restoreOrderedOption(step: QuestionStepUi, optionId: String) {
        if (isStepLocked(step.stepId)) return

        val draft = getOrCreateDraft(step)

        updateDraft(
            stepId = step.stepId,
            draft = draft.copy(
                orderedOptionIds = draft.orderedOptionIds + optionId,
                excludedOptionIds = draft.excludedOptionIds - optionId
            )
        )
    }

    fun mapOptionToZone(step: QuestionStepUi, optionId: String, zoneId: String) {

        if (isStepLocked(step.stepId)) return

        val draft = getOrCreateDraft(step)

        updateDraft(
            stepId = step.stepId,
            draft = draft.copy(
                mappedZoneByOptionId = draft.mappedZoneByOptionId + (optionId to zoneId)
            )
        )
    }

    fun removeOptionZone(step: QuestionStepUi, optionId: String) {
        if (isStepLocked(step.stepId)) return


        val draft = getOrCreateDraft(step)

        updateDraft(
            stepId = step.stepId,
            draft = draft.copy(
                mappedZoneByOptionId = draft.mappedZoneByOptionId - optionId
            )
        )
    }

    fun updateBlankAnswer(step: QuestionStepUi, blankId: String, value: String) {

        if (isStepLocked(step.stepId)) return


        val draft = getOrCreateDraft(step)

        updateDraft(
            stepId = step.stepId,
            draft = draft.copy(
                blankAnswersByBlankId = draft.blankAnswersByBlankId + (blankId to value)
            )
        )
    }

    fun updateFreeText(step: QuestionStepUi, value: String) {
        if (isStepLocked(step.stepId)) return

        val draft = getOrCreateDraft(step)

        updateDraft(
            stepId = step.stepId,
            draft = draft.copy(freeTextAnswer = value)
        )
    }

    fun checkCurrentStep() {
        val state = uiState.value
        val step = state.currentStep ?: return
        val questionId = state.questionId ?: return

        if (isStepLocked(step.stepId)) return

        val draft = getOrCreateDraft(step)

        val result = evaluateStep(step, draft)

        feedbackByStepId.value = feedbackByStepId.value + (step.stepId to result)
        answeredStepIds.value = answeredStepIds.value + step.stepId

        if (result.isCorrect) {
            correctStepIds.value = correctStepIds.value + step.stepId
        } else {
            correctStepIds.value = correctStepIds.value - step.stepId
        }

        viewModelScope.launch {
            repository.saveStepAnswer(
                UserStepAnswerEntity(
                    userId = LearningRepository.LOCAL_USER_ID,
                    questionId = questionId,
                    stepId = step.stepId,
                    selectedOptionIds = draft.selectedOptionIds.toList(),
                    orderedOptionIds = draft.orderedOptionIds,
                    mappedZoneByOptionId = draft.mappedZoneByOptionId,
                    blankAnswersByBlankId = draft.blankAnswersByBlankId,
                    freeTextAnswer = draft.freeTextAnswer,
                    isCorrect = result.isCorrect
                )
            )
        }
    }

    fun goToNextStep() {
        val state = uiState.value
        if (state.currentStepIndex < state.totalSteps - 1) {
            currentStepIndex.value = state.currentStepIndex + 1
        }
    }

    fun goToPreviousStep() {
        if (currentStepIndex.value > 0) {
            currentStepIndex.value = currentStepIndex.value - 1
        }
    }

    fun finishQuestion() {
        val state = uiState.value
        val questionId = state.questionId ?: return

        val autoEvaluatedSteps = collectAutoEvaluatedSteps(state)

        val correctCount = autoEvaluatedSteps.count { step ->
            correctStepIds.value.contains(step.stepId)
        }

        val scorePercent = if (autoEvaluatedSteps.isEmpty()) {
            100
        } else {
            ((correctCount.toFloat() / autoEvaluatedSteps.size.toFloat()) * 100)
                .roundToInt()
                .coerceIn(0, 100)
        }

        viewModelScope.launch {
            val reward = repository.completeQuestion(
                questionId = questionId,
                scorePercent = scorePercent
            )

            newlyAwardedXpByQuestionId.value =
                newlyAwardedXpByQuestionId.value + (questionId to reward.newlyAwardedXp)
            completedQuestionIds.value = completedQuestionIds.value + questionId
        }
    }

    private fun getOrCreateDraft(step: QuestionStepUi): StepAnswerDraft {
        val current = draftsByStepId.value[step.stepId]
        if (current != null) return current

        val initialDraft = when (step.type) {
            StepType.ORDERED_CARDS.id -> {
                StepAnswerDraft(
                    orderedOptionIds = step.options.map { it.optionId }
                )
            }

            else -> StepAnswerDraft()
        }

        updateDraft(step.stepId, initialDraft)
        return initialDraft
    }

    private fun updateDraft(stepId: String, draft: StepAnswerDraft) {
        if (isStepLocked(stepId)) return

        draftsByStepId.value = draftsByStepId.value + (stepId to draft)
        feedbackByStepId.value = feedbackByStepId.value - stepId
    }

    private fun isStepLocked(stepId: String): Boolean {
        val questionId = selectedQuestionId.value

        return answeredStepIds.value.contains(stepId) ||
                questionId != null && completedQuestionIds.value.contains(questionId)
    }

    private fun evaluateStep(
        step: QuestionStepUi,
        draft: StepAnswerDraft
    ): StepFeedbackUi {
        if (!step.isAutoEvaluated) {
            return StepFeedbackUi(
                isCorrect = true,
                title = "Odgovor je zabeležen",
                message = "Ovaj korak se ne ocenjuje automatski. Odgovor je sačuvan za kasniju ručnu analizu."
            )
        }

        return when (step.type) {
            StepType.SINGLE_CHOICE.id,
            StepType.VISUAL_MAPPING.id -> evaluateSingleChoice(step, draft)

            StepType.MULTI_CHOICE.id,
            StepType.HOTSPOT.id -> evaluateMultiChoice(step, draft)

            StepType.ORDERED_CARDS.id -> {
                if (isArchitectExtensionOrderedStep(step)) {
                    evaluateArchitectOrderedCards(step, draft)
                } else {
                    evaluateOrderedCards(step, draft)
                }
            }

            StepType.CATEGORIZATION.id -> {
                if (isArchitectType6DefenseStep(step)) {
                    evaluateArchitectType6DefenseBoard(step, draft)
                } else {
                    evaluateMapping(step, draft)
                }
            }

            StepType.ROLE_MAPPING.id -> evaluateMapping(step, draft)

            StepType.CODE_COMPLETION.id -> evaluateCodeCompletion(step, draft)

            else -> StepFeedbackUi(
                isCorrect = false,
                title = "Nepodržan tip koraka",
                message = "Ovaj tip koraka još nije povezan sa evaluatorom."
            )
        }
    }

    private fun evaluateSingleChoice(
        step: QuestionStepUi,
        draft: StepAnswerDraft
    ): StepFeedbackUi {
        val selectedId = draft.selectedOptionIds.firstOrNull()
        val selected = step.options.firstOrNull { it.optionId == selectedId }

        val isCorrect = selected?.isCorrect == true

        return StepFeedbackUi(
            isCorrect = isCorrect,
            title = if (isCorrect) "Tačno" else "Nije tačno",
            message = if (isCorrect) {
                step.explanation ?: "Izabrao si tačan odgovor."
            } else {
                step.explanation ?: "Pogledaj ponovo opis zadatka i razmisli šta je ključni signal."
            }
        )
    }

    private fun evaluateMultiChoice(
        step: QuestionStepUi,
        draft: StepAnswerDraft
    ): StepFeedbackUi {
        val correctIds = step.options
            .filter { it.isCorrect }
            .map { it.optionId }
            .toSet()

        val selectedIds = draft.selectedOptionIds

        val isCorrect = selectedIds == correctIds

        return StepFeedbackUi(
            isCorrect = isCorrect,
            title = if (isCorrect) "Tačno" else "Još nije potpuno tačno",
            message = if (isCorrect) {
                step.explanation ?: "Izabrao si sve tačne odgovore."
            } else {
                "Potrebno je izabrati baš sve tačne tvrdnje, bez dodatnih netačnih izbora."
            }
        )
    }

    private fun evaluateOrderedCards(
        step: QuestionStepUi,
        draft: StepAnswerDraft
    ): StepFeedbackUi {
        val expected = step.options
            .filter { it.correctOrder != null }
            .sortedBy { it.correctOrder }
            .map { it.optionId }

        val actual = draft.orderedOptionIds
            .filter { optionId ->
                step.options.firstOrNull { it.optionId == optionId }?.isDistractor != true
            }

        val includedDistractors = draft.orderedOptionIds.any { optionId ->
            step.options.firstOrNull { it.optionId == optionId }?.isDistractor == true
        }

        val correctPositions = actual.withIndex().count { (index, optionId) ->
            expected.getOrNull(index) == optionId
        }

        val isCorrect = actual == expected && !includedDistractors

        return StepFeedbackUi(
            isCorrect = isCorrect,
            title = if (isCorrect) "Redosled je tačan" else "Redosled nije tačan",
            message = if (isCorrect) {
                step.explanation ?: "Kartice su poređane pravilnim redosledom."
            } else {
                "Tačno poređano: $correctPositions/${expected.size}. Proveri redosled kartica."
            }
        )
    }

    private fun evaluateArchitectOrderedCards(
        step: QuestionStepUi,
        draft: StepAnswerDraft
    ): StepFeedbackUi {
        val expected = step.options
            .filter { it.correctOrder != null }
            .sortedBy { it.correctOrder }
            .map { it.optionId }

        val actualNonDistractors = draft.orderedOptionIds.filter { optionId ->
            step.options.firstOrNull { it.optionId == optionId }?.isDistractor != true
        }

        val includedDistractors = draft.orderedOptionIds.count { optionId ->
            step.options.firstOrNull { it.optionId == optionId }?.isDistractor == true
        }

        val correctlyExcludedDistractors = step.options.count { option ->
            option.isDistractor && draft.excludedOptionIds.contains(option.optionId)
        }

        val wronglyExcludedRequired = step.options.count { option ->
            !option.isDistractor && draft.excludedOptionIds.contains(option.optionId)
        }

        val correctPositions = actualNonDistractors.withIndex().count { (index, optionId) ->
            expected.getOrNull(index) == optionId
        }

        val totalDistractors = step.options.count { it.isDistractor }
        val isCorrect = actualNonDistractors == expected &&
                includedDistractors == 0 &&
                correctlyExcludedDistractors == totalDistractors &&
                wronglyExcludedRequired == 0

        return StepFeedbackUi(
            isCorrect = isCorrect,
            title = if (isCorrect) {
                "Arhitektonski tok je tačan"
            } else {
                "Tok još ima problem"
            },
            message = if (isCorrect) {
                step.explanation ?: "Potrebni koraci su poređani, a zamke su izbačene."
            } else {
                "Tačne pozicije: $correctPositions/${expected.size}. Izbačene zamke: $correctlyExcludedDistractors/$totalDistractors. Zamke u glavnom toku: $includedDistractors. Pogrešno izbačeni potrebni koraci: $wronglyExcludedRequired."
            }
        )
    }

    private fun isArchitectExtensionOrderedStep(step: QuestionStepUi): Boolean {
        return step.type == StepType.ORDERED_CARDS.id &&
                (
                        step.stepId.startsWith("A1.") && step.stepId.endsWith("_s2") ||
                                step.stepId.startsWith("A4.") && step.stepId.endsWith("_s2") ||
                                step.stepId.startsWith("A5.") && step.stepId.endsWith("_s1")
                        )
    }

    private fun evaluateMapping(
        step: QuestionStepUi,
        draft: StepAnswerDraft
    ): StepFeedbackUi {
        val relevantOptions = step.options.filter { !it.isDistractor }

        val correctCount = relevantOptions.count { option ->
            val expectedZoneId = option.correctZoneId
            val actualZoneId = draft.mappedZoneByOptionId[option.optionId]
            expectedZoneId != null && expectedZoneId == actualZoneId
        }

        val answeredCount = relevantOptions.count { option ->
            draft.mappedZoneByOptionId[option.optionId] != null
        }

        val totalCount = relevantOptions.size

        val isCorrect = totalCount > 0 && correctCount == totalCount

        return StepFeedbackUi(
            isCorrect = isCorrect,
            title = if (isCorrect) {
                "Mapiranje je tačno"
            } else {
                "Mapiranje nije potpuno tačno"
            },
            message = if (isCorrect) {
                step.explanation ?: "Sve kartice su povezane sa odgovarajućim zonama."
            } else {
                "Tačno raspoređeno: $correctCount/$totalCount. Raspoređeno ukupno: $answeredCount/$totalCount."
            }
        )
    }

    private fun evaluateArchitectType6DefenseBoard(
        step: QuestionStepUi,
        draft: StepAnswerDraft
    ): StepFeedbackUi {
        val requiredDefenses = step.options.filter { !it.isDistractor && it.correctZoneId != null }
        val assignedDistractors = step.options.count { option ->
            option.isDistractor && draft.mappedZoneByOptionId[option.optionId] != null
        }
        val correctCount = requiredDefenses.count { option ->
            draft.mappedZoneByOptionId[option.optionId] == option.correctZoneId
        }
        val assignedRequiredCount = requiredDefenses.count { option ->
            draft.mappedZoneByOptionId[option.optionId] != null
        }
        val missingRequired = requiredDefenses.size - assignedRequiredCount
        val isCorrect = requiredDefenses.isNotEmpty() &&
                correctCount == requiredDefenses.size &&
                assignedDistractors == 0

        return StepFeedbackUi(
            isCorrect = isCorrect,
            title = if (isCorrect) {
                "Odluka je odbranjena"
            } else {
                "Odbrana još nije stabilna"
            },
            message = if (isCorrect) {
                step.explanation ?: "Svaki pritisak ima odgovarajuću odbranu, bez pogrešnih argumenata."
            } else {
                "Tačno povezane odbrane: $correctCount/${requiredDefenses.size}. Nedostaje: $missingRequired. Pogrešne odbrane koje su prikačene: $assignedDistractors."
            }
        )
    }

    private fun isArchitectType6DefenseStep(step: QuestionStepUi): Boolean {
        return step.type == StepType.CATEGORIZATION.id &&
                step.stepId.startsWith("A6.") &&
                step.stepId.endsWith("_s2")
    }

    private fun evaluateCodeCompletion(
        step: QuestionStepUi,
        draft: StepAnswerDraft
    ): StepFeedbackUi {
        val isCorrect = step.blanks.all { blank ->
            val userAnswer = draft.blankAnswersByBlankId[blank.blankId]
                ?.trim()
                .orEmpty()

            val expectedAnswer = blank.correctValue.trim()

            userAnswer == expectedAnswer
        }

        return StepFeedbackUi(
            isCorrect = isCorrect,
            title = if (isCorrect) "Kod je pravilno dopunjen" else "Kod nije potpuno tačan",
            message = if (isCorrect) {
                step.explanation ?: "Sva prazna mesta su pravilno popunjena."
            } else {
                "Proveri redosled i tačan naziv dopuna. Kod ovakvih zadataka velika i mala slova su važna."
            }
        )
    }

    private fun buildAiAnalysisRequest(state: QuestionUiState): AiAnalysisRequest {
        val drafts = draftsByStepId.value
        val feedbacks = feedbackByStepId.value
        val answered = answeredStepIds.value
        val correct = correctStepIds.value

        return AiAnalysisRequest(
            questionId = state.questionId.orEmpty(),
            title = state.title,
            level = state.level,
            type = state.type,
            difficulty = state.difficulty,
            prompt = state.prompt,
            scorePercent = state.scorePercent,
            aiFollowUpQuestion = state.aiFollowUp,
            aiFollowUpAnswer = aiFollowUpAnswer.value,
            steps = state.steps.map { step ->
                val draft = drafts[step.stepId] ?: defaultDraftForStep(step)
                val feedback = feedbacks[step.stepId]
                AiAnalysisStepContext(
                    stepId = step.stepId,
                    type = step.type,
                    title = step.title,
                    instruction = step.instruction,
                    codeBlock = step.codeBlock,
                    options = step.options.map { option ->
                        AiAnalysisOptionContext(
                            optionId = option.optionId,
                            label = option.label,
                            text = option.text,
                            isCorrect = option.isCorrect,
                            correctOrder = option.correctOrder,
                            correctZoneId = option.correctZoneId,
                            isDistractor = option.isDistractor
                        )
                    },
                    zones = step.zones.map { zone ->
                        AiAnalysisZoneContext(
                            zoneId = zone.zoneId,
                            title = zone.title
                        )
                    },
                    userAnswer = describeUserAnswer(step, draft),
                    correctAnswer = describeCorrectAnswer(step),
                    feedback = feedback?.let { "${it.title}: ${it.message}" }
                        ?: if (answered.contains(step.stepId)) {
                            "Korak je oznacen kao proveren, ali detaljan feedback nije dostupan."
                        } else {
                            "Korak nije proveren."
                        },
                    architecturalRelevance = describeArchitecturalRelevance(step),
                    wasAnswered = answered.contains(step.stepId),
                    wasCorrect = correct.contains(step.stepId)
                )
            }
        )
    }

    private fun describeUserAnswer(step: QuestionStepUi, draft: StepAnswerDraft): String {
        val selected = draft.selectedOptionIds.mapNotNull { selectedId ->
            step.options.firstOrNull { it.optionId == selectedId }?.optionSummary()
        }

        val ordered = draft.orderedOptionIds.mapIndexedNotNull { index, optionId ->
            step.options.firstOrNull { it.optionId == optionId }?.let { option ->
                "${index + 1}. ${option.optionSummary()}"
            }
        }

        val excluded = draft.excludedOptionIds.mapNotNull { optionId ->
            step.options.firstOrNull { it.optionId == optionId }?.optionSummary()
        }

        val mapped = draft.mappedZoneByOptionId.mapNotNull { (optionId, zoneId) ->
            val option = step.options.firstOrNull { it.optionId == optionId }
            val zone = step.zones.firstOrNull { it.zoneId == zoneId }
            if (option != null && zone != null) {
                "${option.optionSummary()} -> ${zone.title}"
            } else {
                null
            }
        }

        val blanks = draft.blankAnswersByBlankId.mapNotNull { (blankId, value) ->
            val blank = step.blanks.firstOrNull { it.blankId == blankId }
            blank?.let { "${it.placeholder}: $value" }
        }

        return listOf(
            selected.takeIf { it.isNotEmpty() }?.joinToString(prefix = "Selected: "),
            ordered.takeIf { it.isNotEmpty() }?.joinToString(separator = "; ", prefix = "Order: "),
            excluded.takeIf { it.isNotEmpty() }?.joinToString(prefix = "Excluded: "),
            mapped.takeIf { it.isNotEmpty() }?.joinToString(separator = "; ", prefix = "Mapped: "),
            blanks.takeIf { it.isNotEmpty() }?.joinToString(separator = "; ", prefix = "Blanks: "),
            draft.freeTextAnswer.takeIf { it.isNotBlank() }?.let { "Free text: $it" }
        )
            .filterNotNull()
            .joinToString(separator = " | ")
            .ifBlank { "No answer recorded." }
    }

    private fun describeCorrectAnswer(step: QuestionStepUi): String {
        val correctOptions = step.options
            .filter { it.isCorrect }
            .map { it.optionSummary() }

        val expectedOrder = step.options
            .filter { it.correctOrder != null && !it.isDistractor }
            .sortedBy { it.correctOrder }
            .mapIndexed { index, option -> "${index + 1}. ${option.optionSummary()}" }

        val expectedMappings = step.options
            .filter { it.correctZoneId != null && !it.isDistractor }
            .mapNotNull { option ->
                val zone = step.zones.firstOrNull { it.zoneId == option.correctZoneId }
                zone?.let { "${option.optionSummary()} -> ${it.title}" }
            }

        val distractors = step.options
            .filter { it.isDistractor }
            .map { it.optionSummary() }

        val blanks = step.blanks.map { blank ->
            "${blank.placeholder}: ${blank.correctValue}"
        }

        return listOf(
            correctOptions.takeIf { it.isNotEmpty() }?.joinToString(prefix = "Correct option(s): "),
            expectedOrder.takeIf { it.isNotEmpty() }?.joinToString(separator = "; ", prefix = "Expected order: "),
            expectedMappings.takeIf { it.isNotEmpty() }?.joinToString(separator = "; ", prefix = "Expected mapping: "),
            distractors.takeIf { it.isNotEmpty() }?.joinToString(prefix = "Distractors/traps: "),
            blanks.takeIf { it.isNotEmpty() }?.joinToString(separator = "; ", prefix = "Correct blanks: "),
            step.explanation?.takeIf { it.isNotBlank() }?.let { "Explanation: $it" }
        )
            .filterNotNull()
            .joinToString(separator = " | ")
            .ifBlank { "No explicit correct answer metadata available." }
    }

    private fun describeArchitecturalRelevance(step: QuestionStepUi): String {
        return step.explanation
            ?.takeIf { it.isNotBlank() }
            ?: buildString {
                append("Use this step's full instruction, options, zones and expected answer as evidence. ")
                append("Connect feedback only to visible task concepts; do not infer a misconception without support.")
            }
    }

    private fun StepOptionUi.optionSummary(): String {
        val labelPrefix = label?.takeIf { it.isNotBlank() }?.let { "$it. " }.orEmpty()
        return "$labelPrefix$text"
    }

    private fun collectAutoEvaluatedSteps(state: QuestionUiState): List<QuestionStepUi> {
        return state.steps.filter { it.isAutoEvaluated }
    }

    private fun QuestionWithSteps.toQuestionUiState(
        currentStepIndex: Int,
        drafts: Map<String, StepAnswerDraft>,
        feedbacks: Map<String, StepFeedbackUi>,
        answeredStepIds: Set<String>,
        correctStepIds: Set<String>,
        completedQuestionIds: Set<String>,
        newlyAwardedXpByQuestionId: Map<String, Int>,
        aiAnalysis: AiAnalysisRuntimeState
    ): QuestionUiState {
        val sortedSteps = steps
            .sortedBy { it.step.stepOrder }
            .map { relation ->
                QuestionStepUi(
                    stepId = relation.step.stepId,
                    type = relation.step.type,
                    title = relation.step.title,
                    instruction = relation.step.instruction,
                    requiredCount = relation.step.requiredCount,
                    codeBlock = relation.step.codeBlock,
                    explanation = relation.step.explanation,
                    isAutoEvaluated = relation.step.isAutoEvaluated,
                    points = relation.step.points,
                    options = relation.options
                        .sortedBy { it.optionOrder }
                        .map { option ->
                            StepOptionUi(
                                optionId = option.optionId,
                                label = option.label,
                                text = option.text,
                                optionOrder = option.optionOrder,
                                isCorrect = option.isCorrect,
                                correctOrder = option.correctOrder,
                                correctZoneId = option.correctZoneId,
                                isDistractor = option.isDistractor,
                                metadata = option.metadata
                            )
                        }
                        .shuffledForAttempt(relation.step.stepId),
                    zones = relation.zones
                        .sortedBy { it.zoneOrder }
                        .map { zone ->
                            StepZoneUi(
                                zoneId = zone.zoneId,
                                title = zone.title,
                                zoneOrder = zone.zoneOrder
                            )
                        },
                    blanks = relation.blanks
                        .sortedBy { it.blankOrder }
                        .map { blank ->
                            CodeBlankUi(
                                blankId = blank.blankId,
                                blankOrder = blank.blankOrder,
                                placeholder = blank.placeholder,
                                correctValue = blank.correctValue
                            )
                        }
                )
            }

        val safeIndex = currentStepIndex.coerceIn(
            minimumValue = 0,
            maximumValue = (sortedSteps.size - 1).coerceAtLeast(0)
        )

        val currentStep = sortedSteps.getOrNull(safeIndex)
        val questionId = question.questionId

        val autoEvaluatedSteps = sortedSteps.filter { it.isAutoEvaluated }
        val correctAutoSteps = autoEvaluatedSteps.count { correctStepIds.contains(it.stepId) }

        val score = if (autoEvaluatedSteps.isEmpty()) {
            100
        } else {
            ((correctAutoSteps.toFloat() / autoEvaluatedSteps.size.toFloat()) * 100)
                .roundToInt()
                .coerceIn(0, 100)
        }

        val xpReward = if (completedQuestionIds.contains(questionId)) {
            newlyAwardedXpByQuestionId[questionId] ?: XpCalculator.xpForScore(score)
        } else {
            XpCalculator.xpForScore(score)
        }

        return QuestionUiState(
            isLoading = false,
            questionId = questionId,
            title = question.title,
            prompt = question.prompt,
            diagramImageName = question.diagramImageName,
            level = question.level,
            type = question.type,
            difficulty = question.difficulty,
            aiFollowUp = question.aiFollowUp,
            steps = sortedSteps,
            currentStepIndex = safeIndex,
            totalSteps = sortedSteps.size,
            currentStep = currentStep,
            draft = currentStep?.let { drafts[it.stepId] ?: defaultDraftForStep(it) }
                ?: StepAnswerDraft(),
            feedback = currentStep?.let { feedbacks[it.stepId] },
            answeredStepIds = answeredStepIds,
            correctStepIds = correctStepIds,
            isCompleted = completedQuestionIds.contains(questionId),
            scorePercent = score,
            xpReward = xpReward,
            aiFollowUpAnswer = aiAnalysis.followUpAnswer,
            isAiAnalysisLoading = aiAnalysis.isLoading,
            aiAnalysisText = aiAnalysis.text,
            aiAnalysisError = aiAnalysis.error,
            isSketchAnalysisLoading = aiAnalysis.isSketchLoading,
            sketchAnalysisText = aiAnalysis.sketchText,
            sketchAnalysisError = aiAnalysis.sketchError
        )
    }

    private fun defaultDraftForStep(step: QuestionStepUi): StepAnswerDraft {
        return when (step.type) {
            StepType.ORDERED_CARDS.id -> StepAnswerDraft(
                orderedOptionIds = step.options.map { it.optionId }
            )

            else -> StepAnswerDraft()
        }
    }

    private fun List<StepOptionUi>.shuffledForAttempt(stepId: String): List<StepOptionUi> {
        if (size <= 1) return this

        val optionById = associateBy { it.optionId }
        val currentOrder = shuffledOptionIdsByStepId.value[stepId]

        if (currentOrder != null && currentOrder.toSet() == optionById.keys) {
            return currentOrder.mapNotNull { optionById[it] }
        }

        val shuffledIds = shuffled().map { it.optionId }
        shuffledOptionIdsByStepId.value = shuffledOptionIdsByStepId.value + (stepId to shuffledIds)

        return shuffledIds.mapNotNull { optionById[it] }
    }
}

private const val AI_LOG_TAG = "QuestionViewModel"
