package com.example.pmuprojekat.ai

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AiAnalysisPromptBuilderTest {

    @Test
    fun systemPrompt_requiresEvidenceDirectAddressAndThreeLineLearningAction() {
        val systemPrompt = AiAnalysisPromptBuilder.systemPrompt

        assertTrue(systemPrompt.contains("Address the learner directly"))
        assertTrue(systemPrompt.contains("Base every positive or critical claim on supplied evidence"))
        assertTrue(systemPrompt.contains("Do not always recommend drawing a diagram"))
        assertTrue(systemPrompt.contains("2-5 minutes"))
        assertTrue(systemPrompt.contains("Sledeće uradi:"))
        assertTrue(systemPrompt.contains("Cilj je da učvrstiš:"))
        assertTrue(systemPrompt.contains("Proveri sebe tako što ćeš moći da objasniš:"))
        assertTrue(systemPrompt.contains("what remains authoritative"))
        assertTrue(systemPrompt.contains("Use the narrowest relevant concept"))
    }

    @Test
    fun beginnerWrongChoice_getsComparisonActionInsteadOfDiagram() {
        val prompt = AiAnalysisPromptBuilder.build(
            request(
                level = "BEGINNER",
                type = "pattern_recognition",
                score = 25,
                stepType = "single_choice",
                userAnswer = "B. Singleton",
                correctAnswer = "A. Strategy"
            )
        ).taskContextPrompt

        assertTrue(prompt.contains("Score band: LOW"))
        assertTrue(prompt.contains("Compare the exact selected and expected option"))
        assertTrue(prompt.contains("B. Singleton"))
        assertTrue(prompt.contains("A. Strategy"))
        assertFalse(prompt.contains("draw a system diagram"))
    }

    @Test
    fun juniorCodeCompletion_getsMissingLineAction() {
        val prompt = AiAnalysisPromptBuilder.build(
            request(
                level = "JUNIOR",
                type = "code_completion",
                score = 55,
                stepType = "code_completion",
                userAnswer = "Blanks: method: execute",
                correctAnswer = "Correct blanks: method: calculate"
            )
        ).taskContextPrompt

        assertTrue(prompt.contains("Score band: MEDIUM"))
        assertTrue(prompt.contains("rewrite only the missing or incorrect pseudo-code line", ignoreCase = true))
        assertTrue(prompt.contains("Do not ask for a system diagram"))
    }

    @Test
    fun mediorOrdering_getsDependencyAction() {
        val prompt = AiAnalysisPromptBuilder.build(
            request(
                level = "MEDIOR",
                type = "sequence_logic",
                score = 60,
                stepType = "ordered_cards",
                userAnswer = "Order: 1. Objavi događaj; 2. Sačuvaj podatak",
                correctAnswer = "Expected order: 1. Sačuvaj podatak; 2. Objavi događaj"
            )
        ).taskContextPrompt

        assertTrue(prompt.contains("identify two specifically misplaced steps"))
        assertTrue(prompt.contains("expected order"))
    }

    @Test
    fun seniorTradeOff_getsDecisionBenefitRiskAndChangeCondition() {
        val prompt = AiAnalysisPromptBuilder.build(
            request(
                level = "SENIOR",
                type = "trade_off",
                score = 65,
                stepType = "single_choice",
                userAnswer = "Sinhrona obrada svega",
                correctAnswer = "Kritični tok sinhrono, analitika asinhrono"
            )
        ).taskContextPrompt

        assertTrue(prompt.contains("decision, benefit, risk and the condition that would change the decision"))
    }

    @Test
    fun architectDerivedViewMistake_getsAuthorityDistinctionAction() {
        val prompt = AiAnalysisPromptBuilder.build(
            request(
                level = "ARCHITECT",
                type = "architecture_extension",
                score = 50,
                stepType = "single_choice",
                prompt = "Availability snapshot u read modelu može kasniti. Rezervacija kod apoteke mora biti autoritativna.",
                userAnswer = "Read model potvrđuje rezervaciju",
                correctAnswer = "Apoteka potvrđuje rezervaciju; snapshot je informativan"
            )
        ).taskContextPrompt

        assertTrue(prompt.contains("short, exact comparison of the informative/derived element"))
        assertTrue(prompt.contains("authoritative action"))
        assertTrue(prompt.contains("Availability snapshot"))
        assertTrue(prompt.contains("Rezervacija kod apoteke"))
    }

    @Test
    fun highScore_getsRefinementChallengeInsteadOfRemedialBasics() {
        val prompt = AiAnalysisPromptBuilder.build(
            request(
                level = "BEGINNER",
                type = "pattern_recognition",
                score = 92,
                stepType = "single_choice",
                wasCorrect = true,
                userAnswer = "A. Strategy",
                correctAnswer = "A. Strategy"
            )
        ).taskContextPrompt

        assertTrue(prompt.contains("Score band: HIGH"))
        assertTrue(prompt.contains("closest plausible alternative"))
        assertTrue(prompt.contains("changed constraint"))
    }

    @Test
    fun fullContext_keepsFeedbackAndFollowUpEvidence() {
        val prompt = AiAnalysisPromptBuilder.build(
            request(
                level = "MEDIOR",
                type = "sequence_logic",
                score = 60,
                stepType = "ordered_cards",
                userAnswer = "Prvo čitanje, zatim upis",
                correctAnswer = "Prvo upis, zatim čitanje",
                followUpAnswer = "Redosled utiče na konzistentnost."
            )
        ).taskContextPrompt

        assertTrue(prompt.contains("Feedback iz aplikacije:"))
        assertTrue(prompt.contains("Pokušaj ponovo uz proveru zavisnosti."))
        assertTrue(prompt.contains("TVOJ ODGOVOR NA AI FOLLOW-UP"))
        assertTrue(prompt.contains("Redosled utiče na konzistentnost."))
    }

    @Test
    fun mockAnalysis_usesSpecificThreeLineActionWithoutDefaultDiagramAdvice() = runBlocking {
        val output = MockAiAnalysisService().analyze(
            request(
                level = "JUNIOR",
                type = "code_completion",
                score = 35,
                stepType = "code_completion",
                userAnswer = "Blanks: method: execute",
                correctAnswer = "Correct blanks: method: calculate"
            )
        ).getOrThrow()

        assertTrue(output.contains("Sledeće uradi: prepiši samo pogrešno ili prazno mesto"))
        assertTrue(output.contains("Cilj je da učvrstiš:"))
        assertTrue(output.contains("Proveri sebe tako što ćeš moći da objasniš:"))
        assertFalse(output.contains("Nacrtaj tok", ignoreCase = true))
    }

    private fun request(
        level: String,
        type: String,
        score: Int,
        stepType: String,
        prompt: String = "Scenario zadatka sa konkretnim ograničenjima.",
        userAnswer: String,
        correctAnswer: String,
        wasCorrect: Boolean = false,
        followUpAnswer: String = ""
    ): AiAnalysisRequest {
        return AiAnalysisRequest(
            questionId = "TEST.1",
            title = "Test zadatak",
            level = level,
            type = type,
            difficulty = "Srednje",
            prompt = prompt,
            steps = listOf(
                AiAnalysisStepContext(
                    stepId = "TEST.1.1",
                    type = stepType,
                    title = "Ključni korak",
                    instruction = "Izaberi ili unesi odgovor na osnovu scenarija.",
                    codeBlock = if (stepType == "code_completion") "return strategy.___(price)" else null,
                    options = emptyList(),
                    zones = emptyList(),
                    userAnswer = userAnswer,
                    correctAnswer = correctAnswer,
                    feedback = "Pokušaj ponovo uz proveru zavisnosti.",
                    architecturalRelevance = "Proverava konkretan odnos iz scenarija.",
                    wasAnswered = true,
                    wasCorrect = wasCorrect
                )
            ),
            scorePercent = score,
            aiFollowUpQuestion = "Zašto je očekivani odgovor bolji?",
            aiFollowUpAnswer = followUpAnswer
        )
    }
}
