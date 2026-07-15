package com.example.pmuprojekat.ai

data class VsAiLevelContext(
    val levelId: String,
    val displayName: String,
    val difficultyDescription: String,
    val expectedReasoningDepth: String,
    val typicalConcepts: List<String>,
    val allowedQuestionStyles: List<String>,
    val forbiddenQuestionStyles: List<String>,
    val satisfactionCriteria: List<String>,
    val maximumRounds: Int,
    val minimumExpectedAnswerQuality: Int,
    val suitableQuestionExamples: List<String>
)

enum class VsAiLearnerStatus(val displayName: String) {
    NEW_TO_LEVEL("nov na ovom nivou"),
    DEVELOPING("u razvoju"),
    SOLID("stabilan"),
    STRONG("jak")
}

data class VsAiRecentResult(
    val questionId: String,
    val taskType: String,
    val bestScorePercent: Int,
    val updatedAt: Long
)

data class VsAiLearnerContext(
    val selectedLevelId: String,
    val selectedLevelName: String,
    val solvedTasks: Int,
    val totalTasks: Int,
    val averageScorePercent: Int?,
    val bestScorePercent: Int?,
    val recentResults: List<VsAiRecentResult>,
    val weakerTaskTypes: List<String>,
    val strongerTaskTypes: List<String>,
    val repeatedMistakePatterns: List<String>,
    val status: VsAiLearnerStatus,
    val hasEnoughHistoryForPersonalization: Boolean,
    val personalizationNote: String
)

data class VsAiGeneratedChallenge(
    val question: String,
    val targetConcept: String,
    val expectedAnswerGuide: String,
    val difficultyLabel: String,
    val reasonChosen: String
)

data class VsAiConversationTurn(
    val role: VsAiConversationRole,
    val content: String,
    val roundNumber: Int
)

enum class VsAiConversationRole {
    AI,
    USER
}

data class VsAiEvaluationSignal(
    val satisfactionScore: Int,
    val targetConcepts: List<String>,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val reason: String
)

data class VsAiStartRequest(
    val levelContext: VsAiLevelContext,
    val learnerContext: VsAiLearnerContext,
    val relevantTerms: List<AiChatRelevantTerm>
)

data class VsAiEvaluationRequest(
    val levelContext: VsAiLevelContext,
    val learnerContext: VsAiLearnerContext,
    val challenge: VsAiGeneratedChallenge,
    val relevantTerms: List<AiChatRelevantTerm>,
    val conversation: List<VsAiConversationTurn>,
    val previousEvaluationSignals: List<VsAiEvaluationSignal>,
    val currentRound: Int
)

data class VsAiEvaluationResult(
    val visibleMessage: String,
    val shouldContinue: Boolean,
    val isSatisfied: Boolean,
    val satisfactionScore: Int,
    val nextQuestion: String?,
    val targetConcepts: List<String>,
    val detectedStrengths: List<String>,
    val detectedWeaknesses: List<String>,
    val reasonForContinuingOrStopping: String
) {
    fun toSignal(): VsAiEvaluationSignal {
        return VsAiEvaluationSignal(
            satisfactionScore = satisfactionScore,
            targetConcepts = targetConcepts,
            strengths = detectedStrengths,
            weaknesses = detectedWeaknesses,
            reason = reasonForContinuingOrStopping
        )
    }

    fun visibleFollowUp(): String {
        return listOfNotNull(
            visibleMessage.trim().takeIf(String::isNotBlank),
            nextQuestion?.trim()?.takeIf(String::isNotBlank)
        ).joinToString("\n\n")
    }
}

enum class VsAiCompletionReason {
    AI_SATISFIED,
    USER_STOPPED,
    MAX_ROUNDS,
    ERROR
}

data class VsAiFinalAnalysisRequest(
    val levelContext: VsAiLevelContext,
    val learnerContext: VsAiLearnerContext,
    val challenge: VsAiGeneratedChallenge,
    val conversation: List<VsAiConversationTurn>,
    val evaluationSignals: List<VsAiEvaluationSignal>,
    val completionReason: VsAiCompletionReason
)

data class VsAiFinalAnalysis(
    val score: Int,
    val resultSummary: String,
    val strengths: String,
    val weakerPoints: String,
    val reasoningDevelopment: String,
    val keyConcepts: List<String>,
    val nextConcreteStep: String
) {
    fun toPlainText(): String {
        return buildString {
            appendLine("Rezultat izazova")
            appendLine()
            appendLine("$score/100. $resultSummary")
            appendLine()
            appendLine("Šta si dobro uradio")
            appendLine()
            appendLine(strengths)
            appendLine()
            appendLine("Gde je odgovor bio slabiji")
            appendLine()
            appendLine(weakerPoints)
            appendLine()
            appendLine("Kako se tvoje razmišljanje razvijalo tokom izazova")
            appendLine()
            appendLine(reasoningDevelopment)
            appendLine()
            appendLine("Ključni koncepti za ponavljanje")
            appendLine()
            appendLine(keyConcepts.joinToString(", ").ifBlank { "Nisu izdvojeni." })
            appendLine()
            appendLine("Sledeći konkretan korak")
            appendLine()
            append(nextConcreteStep)
        }
    }
}

data class VsAiPrompt(
    val systemPrompt: String,
    val userPrompt: String
)
