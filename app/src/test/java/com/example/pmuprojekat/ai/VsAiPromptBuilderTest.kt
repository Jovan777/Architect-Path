package com.example.pmuprojekat.ai

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VsAiPromptBuilderTest {
    private val provider = VsAiLevelContextProvider()

    @Test
    fun `start prompt includes learner and encyclopedia context without exposing it as answer`() {
        val prompt = VsAiPromptBuilder.buildStart(
            VsAiStartRequest(
                levelContext = provider.get("medior"),
                learnerContext = learner("medior"),
                relevantTerms = listOf(
                    AiChatRelevantTerm(
                        id = "source_of_truth",
                        categoryId = "software_architecture",
                        categoryTitle = "Softverska arhitektura",
                        titleSr = "Izvor istine",
                        titleEn = "Source of truth",
                        shortExplanation = "Autoritativno stanje poslovnog podatka.",
                        score = 100
                    )
                )
            )
        )

        assertTrue(prompt.systemPrompt.contains("AI sparing mentor"))
        assertTrue(prompt.userPrompt.contains("RELEVANTNI POJMOVI IZ LOKALNE ENCIKLOPEDIJE"))
        assertTrue(prompt.userPrompt.contains("Izvor istine"))
        assertTrue(prompt.userPrompt.contains("expectedAnswerGuide"))
        assertTrue(prompt.userPrompt.contains("Postavi tačno jedno fokusirano pitanje"))
    }

    @Test
    fun `architect evaluation prompt contains level threshold and focused architecture rule`() {
        val context = provider.get("architect")
        val prompt = VsAiPromptBuilder.buildEvaluation(
            VsAiEvaluationRequest(
                levelContext = context,
                learnerContext = learner("architect"),
                challenge = challenge(),
                relevantTerms = emptyList(),
                conversation = listOf(
                    VsAiConversationTurn(VsAiConversationRole.AI, challenge().question, 1),
                    VsAiConversationTurn(VsAiConversationRole.USER, "Koristio bih cache.", 1)
                ),
                previousEvaluationSignals = emptyList(),
                currentRound = 1
            )
        )

        assertTrue(prompt.userPrompt.contains("Prag: 90/100"))
        assertTrue(prompt.userPrompt.contains("source of truth/read model/cache"))
        assertTrue(prompt.userPrompt.contains("Ne prihvataj neodređen Architect odgovor"))
        assertFalse(prompt.userPrompt.contains("shouldContinue mora biti false"))
    }

    @Test
    fun `final prompt requests all visible analysis sections and stopped reason`() {
        val prompt = VsAiPromptBuilder.buildFinalAnalysis(
            VsAiFinalAnalysisRequest(
                levelContext = provider.get("junior"),
                learnerContext = learner("junior"),
                challenge = challenge(),
                conversation = emptyList(),
                evaluationSignals = emptyList(),
                completionReason = VsAiCompletionReason.USER_STOPPED
            )
        )

        assertTrue(prompt.userPrompt.contains("USER_STOPPED"))
        assertTrue(prompt.userPrompt.contains("resultSummary"))
        assertTrue(prompt.userPrompt.contains("reasoningDevelopment"))
        assertTrue(prompt.userPrompt.contains("nextConcreteStep"))
    }

    private fun learner(levelId: String) = VsAiLearnerContext(
        selectedLevelId = levelId,
        selectedLevelName = levelId,
        solvedTasks = 0,
        totalTasks = 10,
        averageScorePercent = null,
        bestScorePercent = null,
        recentResults = emptyList(),
        weakerTaskTypes = emptyList(),
        strongerTaskTypes = emptyList(),
        repeatedMistakePatterns = emptyList(),
        status = VsAiLearnerStatus.NEW_TO_LEVEL,
        hasEnoughHistoryForPersonalization = false,
        personalizationNote = "Nema dovoljno istorije."
    )

    private fun challenge() = VsAiGeneratedChallenge(
        question = "Ko je izvor istine za status narudžbine?",
        targetConcept = "source of truth",
        expectedAnswerGuide = "Operativni servis poseduje status.",
        difficultyLabel = "fokusirano",
        reasonChosen = "Odgovara nivou."
    )
}
