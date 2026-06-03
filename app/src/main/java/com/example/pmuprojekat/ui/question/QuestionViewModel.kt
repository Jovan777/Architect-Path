package com.example.pmuprojekat.ui.question


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.core.model.StepType
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
    private val repository: LearningRepository
) : ViewModel() {

    private val selectedQuestionId = MutableStateFlow<String?>(null)
    private val currentStepIndex = MutableStateFlow(0)
    private val draftsByStepId = MutableStateFlow<Map<String, StepAnswerDraft>>(emptyMap())
    private val feedbackByStepId = MutableStateFlow<Map<String, StepFeedbackUi>>(emptyMap())
    private val answeredStepIds = MutableStateFlow<Set<String>>(emptySet())
    private val correctStepIds = MutableStateFlow<Set<String>>(emptySet())
    private val completedQuestionIds = MutableStateFlow<Set<String>>(emptySet())
    private val shuffledOptionIdsByStepId = MutableStateFlow<Map<String, List<String>>>(emptyMap())

    private data class QuestionRuntimeState(
        val questionWithSteps: QuestionWithSteps?,
        val stepIndex: Int,
        val drafts: Map<String, StepAnswerDraft>,
        val feedbacks: Map<String, StepFeedbackUi>,
        val answered: Set<String>
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

    val uiState = combine(
        runtimeState,
        correctStepIds,
        completedQuestionIds
    ) { runtime,
        correct,
        completed ->

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
                completedQuestionIds = completed
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

        if (restartAttempt) {
            completedQuestionIds.value = completedQuestionIds.value - questionId
        }
    }

    fun toggleOption(step: QuestionStepUi, optionId: String) {
        if (isStepLocked(step.stepId)) return

        val draft = getOrCreateDraft(step)

        val isCodeDecisionChoice =
            step.type == StepType.SINGLE_CHOICE.id && !step.codeBlock.isNullOrBlank()

        if (isCodeDecisionChoice && draft.selectedOptionIds.isNotEmpty()) {
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

        val xpReward = when {
            scorePercent >= 90 -> 40
            scorePercent >= 70 -> 25
            scorePercent >= 50 -> 15
            else -> 5
        }

        completedQuestionIds.value = completedQuestionIds.value + questionId

        viewModelScope.launch {
            repository.completeQuestion(
                questionId = questionId,
                scorePercent = scorePercent,
                xpReward = xpReward
            )
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

        val isCorrect = actual == expected && !includedDistractors

        return StepFeedbackUi(
            isCorrect = isCorrect,
            title = if (isCorrect) "Redosled je tačan" else "Redosled nije tačan",
            message = if (isCorrect) {
                step.explanation ?: "Kartice su poređane pravilnim redosledom."
            } else {
                "Proveri redosled kartica i izbaci kartice koje ne pripadaju rešenju."
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
                "Arhitektonski tok je taÄan"
            } else {
                "Tok joÅ¡ ima problem"
            },
            message = if (isCorrect) {
                step.explanation ?: "Potrebni koraci su poreÄ‘ani, a zamke su izbaÄene."
            } else {
                "TaÄne pozicije: $correctPositions/${expected.size}. IzbaÄene zamke: $correctlyExcludedDistractors/$totalDistractors. Zamke u glavnom toku: $includedDistractors. PogreÅ¡no izbaÄeni potrebni koraci: $wronglyExcludedRequired."
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
                "Odbrana joÅ¡ nije stabilna"
            },
            message = if (isCorrect) {
                step.explanation ?: "Svaki pritisak ima odgovarajuÄ‡u odbranu, bez pogreÅ¡nih argumenata."
            } else {
                "TaÄno povezane odbrane: $correctCount/${requiredDefenses.size}. Nedostaje: $missingRequired. PogreÅ¡ne odbrane koje su prikaÄene: $assignedDistractors."
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

    private fun collectAutoEvaluatedSteps(state: QuestionUiState): List<QuestionStepUi> {
        return state.steps.filter { it.isAutoEvaluated }
    }

    private fun QuestionWithSteps.toQuestionUiState(
        currentStepIndex: Int,
        drafts: Map<String, StepAnswerDraft>,
        feedbacks: Map<String, StepFeedbackUi>,
        answeredStepIds: Set<String>,
        correctStepIds: Set<String>,
        completedQuestionIds: Set<String>
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

        val xpReward = when {
            score >= 90 -> 40
            score >= 70 -> 25
            score >= 50 -> 15
            else -> 5
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
            xpReward = xpReward
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
