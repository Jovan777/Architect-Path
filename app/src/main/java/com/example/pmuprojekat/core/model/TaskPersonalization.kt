package com.example.pmuprojekat.core.model

data class TaskMetadata(
    val format: TaskFormatPreference,
    val focus: TaskFocusPreference
)

data class TaskScoringWeights(
    val formatMatch: Int = 2,
    val focusMatch: Int = 3
)

enum class TaskFormatPreference(
    val displayName: String,
    private val aliases: Set<String> = emptySet()
) {
    INTERACTIVE_STEPS(
        displayName = "Interaktivni koraci",
        aliases = setOf("Interactive Steps")
    ),
    QUIZ_QUESTIONS(
        displayName = "Kviz pitanja",
        aliases = setOf("Quiz Questions")
    ),
    CODE_PSEUDOCODE(
        displayName = "Kod i pseudo-kod",
        aliases = setOf("Code & Pseudocode", "Code and Pseudocode")
    ),
    MAPPING_CARDS(
        displayName = "Mapiranje i kartice",
        aliases = setOf("Mapping & Cards", "Mapping and Cards")
    ),
    ARCHITECTURAL_SCENARIOS(
        displayName = "Arhitektonski scenariji",
        aliases = setOf("Architectural Scenarios")
    );

    fun matches(value: String): Boolean {
        val normalized = value.trim()
        return displayName.equals(normalized, ignoreCase = true) ||
                aliases.any { it.equals(normalized, ignoreCase = true) }
    }
}

enum class TaskFocusPreference(
    val displayName: String,
    private val aliases: Set<String> = emptySet()
) {
    BALANCED_LEARNING(
        displayName = "Balansirano učenje",
        aliases = setOf("Balanced Learning")
    ),
    DESIGN_PATTERNS(
        displayName = "Obrasci projektovanja",
        aliases = setOf("Design Patterns")
    ),
    REFACTORING(
        displayName = "Refaktorisanje",
        aliases = setOf("Refactoring")
    ),
    PRODUCTION_THINKING(
        displayName = "Produkcijsko razmišljanje",
        aliases = setOf("Production Thinking")
    ),
    ARCHITECTURAL_DECISION_MAKING(
        displayName = "Arhitektonsko odlučivanje",
        aliases = setOf("Architectural Decision-Making", "Architectural Decision Making")
    );

    fun matches(value: String): Boolean {
        val normalized = value.trim()
        return displayName.equals(normalized, ignoreCase = true) ||
                aliases.any { it.equals(normalized, ignoreCase = true) }
    }
}

object TaskPersonalizer {
    val defaultWeights = TaskScoringWeights()

    fun encodePreferredFormats(formats: Set<TaskFormatPreference>): String {
        return TaskFormatPreference.entries
            .filter { formats.contains(it) }
            .ifEmpty { listOf(TaskFormatPreference.INTERACTIVE_STEPS) }
            .joinToString(separator = ", ") { it.displayName }
    }

    fun parsePreferredFormats(value: String): Set<TaskFormatPreference> {
        val selected = value
            .split(",", "|")
            .mapNotNull { raw ->
                TaskFormatPreference.entries.firstOrNull { it.matches(raw) }
            }
            .toSet()

        return selected.ifEmpty { setOf(TaskFormatPreference.INTERACTIVE_STEPS) }
    }

    fun parseLearningFocus(value: String): TaskFocusPreference {
        return TaskFocusPreference.entries.firstOrNull { it.matches(value) }
            ?: TaskFocusPreference.BALANCED_LEARNING
    }

    fun metadataFor(
        questionType: String,
        level: String
    ): TaskMetadata {
        return TaskMetadata(
            format = formatFor(questionType),
            focus = focusFor(
                questionType = questionType,
                level = level
            )
        )
    }

    fun score(
        metadata: TaskMetadata,
        preferredFormats: Set<TaskFormatPreference>,
        learningFocus: TaskFocusPreference,
        weights: TaskScoringWeights = defaultWeights
    ): Int {
        val formatScore = if (preferredFormats.contains(metadata.format)) {
            weights.formatMatch
        } else {
            0
        }

        val focusScore = if (
            learningFocus != TaskFocusPreference.BALANCED_LEARNING &&
            metadata.focus == learningFocus
        ) {
            weights.focusMatch
        } else {
            0
        }

        return formatScore + focusScore
    }

    private fun formatFor(questionType: String): TaskFormatPreference {
        return when (questionType) {
            QuestionType.MULTI_STEP_UNDERSTANDING.id,
            QuestionType.SEQUENCE_LOGIC.id,
            QuestionType.CONSEQUENCE_ANALYSIS.id -> TaskFormatPreference.INTERACTIVE_STEPS

            QuestionType.PATTERN_RECOGNITION.id,
            QuestionType.PATTERN_COMPARISON.id,
            QuestionType.REASONING.id,
            QuestionType.CONSTRAINT_DECISION.id,
            QuestionType.TRADE_OFF.id,
            QuestionType.PRIORITIZATION.id -> TaskFormatPreference.QUIZ_QUESTIONS

            QuestionType.CODE_COMPLETION.id,
            QuestionType.REFACTORING.id,
            QuestionType.ERROR_DETECTION.id -> TaskFormatPreference.CODE_PSEUDOCODE

            QuestionType.VISUAL_MAPPING.id,
            QuestionType.ROLE_MAPPING.id,
            QuestionType.SYSTEM_REQUIREMENT_MAPPING.id,
            QuestionType.ARCHITECTURE_COMPOSITION.id -> TaskFormatPreference.MAPPING_CARDS

            QuestionType.PRODUCTION_DIAGNOSIS.id,
            QuestionType.OPTIMIZATION_STRATEGY.id,
            QuestionType.INCIDENT_ANALYSIS.id,
            QuestionType.ARCHITECTURE_EXTENSION.id,
            QuestionType.ARCHITECTURE_STYLE.id,
            QuestionType.ARCHITECTURE_REVIEW.id,
            QuestionType.SCALING_ASSESSMENT.id,
            QuestionType.ARCHITECTURAL_COMPROMISE.id -> TaskFormatPreference.ARCHITECTURAL_SCENARIOS

            else -> TaskFormatPreference.INTERACTIVE_STEPS
        }
    }

    private fun focusFor(
        questionType: String,
        level: String
    ): TaskFocusPreference {
        return when {
            level == LearningLevel.ARCHITECT.id -> TaskFocusPreference.ARCHITECTURAL_DECISION_MAKING
            level == LearningLevel.SENIOR.id -> TaskFocusPreference.PRODUCTION_THINKING

            questionType == QuestionType.REFACTORING.id ||
                    questionType == QuestionType.ERROR_DETECTION.id -> TaskFocusPreference.REFACTORING

            questionType == QuestionType.PRODUCTION_DIAGNOSIS.id ||
                    questionType == QuestionType.OPTIMIZATION_STRATEGY.id ||
                    questionType == QuestionType.INCIDENT_ANALYSIS.id ||
                    questionType == QuestionType.PRIORITIZATION.id -> TaskFocusPreference.PRODUCTION_THINKING

            questionType == QuestionType.CONSTRAINT_DECISION.id ||
                    questionType == QuestionType.CONSEQUENCE_ANALYSIS.id ||
                    questionType == QuestionType.TRADE_OFF.id -> TaskFocusPreference.ARCHITECTURAL_DECISION_MAKING

            else -> TaskFocusPreference.DESIGN_PATTERNS
        }
    }
}
