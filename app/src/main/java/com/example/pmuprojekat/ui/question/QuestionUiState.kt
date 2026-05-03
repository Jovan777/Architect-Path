package com.example.pmuprojekat.ui.question


data class QuestionUiState(
    val isLoading: Boolean = true,
    val questionId: String? = null,
    val title: String = "",
    val prompt: String = "",
    val level: String = "",
    val type: String = "",
    val difficulty: String = "",
    val aiFollowUp: String? = null,

    val steps: List<QuestionStepUi> = emptyList(),

    val currentStepIndex: Int = 0,
    val totalSteps: Int = 0,
    val currentStep: QuestionStepUi? = null,

    val draft: StepAnswerDraft = StepAnswerDraft(),
    val feedback: StepFeedbackUi? = null,

    val answeredStepIds: Set<String> = emptySet(),
    val correctStepIds: Set<String> = emptySet(),

    val isCompleted: Boolean = false,
    val scorePercent: Int = 0,
    val xpReward: Int = 0
) {
    val canGoPrevious: Boolean
        get() = currentStepIndex > 0 && !isCompleted

    val isLastStep: Boolean
        get() = currentStepIndex == totalSteps - 1
}

data class QuestionStepUi(
    val stepId: String,
    val type: String,
    val title: String,
    val instruction: String,
    val requiredCount: Int?,
    val codeBlock: String?,
    val explanation: String?,
    val isAutoEvaluated: Boolean,
    val points: Int,
    val options: List<StepOptionUi>,
    val zones: List<StepZoneUi>,
    val blanks: List<CodeBlankUi>
)

data class StepOptionUi(
    val optionId: String,
    val label: String?,
    val text: String,
    val optionOrder: Int,
    val isCorrect: Boolean,
    val correctOrder: Int?,
    val correctZoneId: String?,
    val isDistractor: Boolean,
    val metadata: String?
)

data class StepZoneUi(
    val zoneId: String,
    val title: String,
    val zoneOrder: Int
)

data class CodeBlankUi(
    val blankId: String,
    val blankOrder: Int,
    val placeholder: String,
    val correctValue: String
)

data class StepAnswerDraft(
    val selectedOptionIds: Set<String> = emptySet(),
    val orderedOptionIds: List<String> = emptyList(),
    val excludedOptionIds: Set<String> = emptySet(),
    val mappedZoneByOptionId: Map<String, String> = emptyMap(),
    val blankAnswersByBlankId: Map<String, String> = emptyMap(),
    val freeTextAnswer: String = ""
)

data class StepFeedbackUi(
    val isCorrect: Boolean,
    val title: String,
    val message: String
)