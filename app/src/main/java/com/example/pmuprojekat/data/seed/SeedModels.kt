package com.example.pmuprojekat.data.seed

data class SeedQuestion(
    val questionId: String,
    val level: String,
    val type: String,
    val title: String,
    val prompt: String,
    val diagramImageName: String? = null,
    val aiFollowUp: String? = null,
    val wave: Int,
    val difficulty: String = "easy",
    val orderIndex: Int = 0,
    val estimatedMinutes: Int = 3,
    val steps: List<SeedStep> = emptyList()
)

data class SeedStep(
    val stepId: String,
    val type: String,
    val title: String,
    val instruction: String,
    val requiredCount: Int? = null,
    val codeBlock: String? = null,
    val explanation: String? = null,
    val options: List<SeedOption> = emptyList(),
    val zones: List<SeedZone> = emptyList(),
    val blanks: List<SeedBlank> = emptyList()
)

data class SeedOption(
    val optionId: String,
    val label: String? = null,
    val text: String,
    val optionOrder: Int = 0,
    val isCorrect: Boolean = false,
    val correctOrder: Int? = null,
    val correctZoneId: String? = null,
    val isDistractor: Boolean = false,
    val metadata: String? = null
)

data class SeedZone(
    val zoneId: String,
    val title: String,
    val zoneOrder: Int = 0
)

data class SeedBlank(
    val blankId: String,
    val blankOrder: Int,
    val placeholder: String = "__________",
    val correctValue: String
)
