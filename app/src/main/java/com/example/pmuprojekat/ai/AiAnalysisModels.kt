package com.example.pmuprojekat.ai

data class AiAnalysisRequest(
    val questionId: String,
    val title: String,
    val level: String,
    val type: String,
    val difficulty: String,
    val prompt: String,
    val steps: List<AiAnalysisStepContext>,
    val scorePercent: Int,
    val aiFollowUpQuestion: String?,
    val aiFollowUpAnswer: String
)

data class AiAnalysisStepContext(
    val stepId: String,
    val type: String,
    val title: String,
    val instruction: String,
    val codeBlock: String?,
    val options: List<AiAnalysisOptionContext>,
    val zones: List<AiAnalysisZoneContext>,
    val userAnswer: String,
    val correctAnswer: String,
    val feedback: String,
    val architecturalRelevance: String,
    val wasAnswered: Boolean,
    val wasCorrect: Boolean
)

data class AiAnalysisOptionContext(
    val optionId: String,
    val label: String?,
    val text: String,
    val isCorrect: Boolean,
    val correctOrder: Int?,
    val correctZoneId: String?,
    val isDistractor: Boolean
)

data class AiAnalysisZoneContext(
    val zoneId: String,
    val title: String
)

data class AiAnalysisPrompt(
    val systemPrompt: String,
    val taskContextPrompt: String
)
