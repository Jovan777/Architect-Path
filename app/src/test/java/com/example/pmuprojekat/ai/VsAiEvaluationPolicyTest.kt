package com.example.pmuprojekat.ai

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class VsAiEvaluationPolicyTest {
    private val contexts = VsAiLevelContextProvider()
    private val challenge = VsAiGeneratedChallenge(
        question = "Ko poseduje status narudžbine?",
        targetConcept = "source of truth",
        expectedAnswerGuide = "Operativni servis poseduje zapis.",
        difficultyLabel = "arhitektonski",
        reasonChosen = "Provera granice odgovornosti."
    )

    @Test
    fun `architect answer below threshold continues despite model satisfaction flag`() {
        val normalized = VsAiEvaluationPolicy.normalize(
            levelContext = contexts.get("architect"),
            challenge = challenge,
            round = 1,
            result = result(score = 75, isSatisfied = true, shouldContinue = false)
        )

        assertFalse(normalized.isSatisfied)
        assertTrue(normalized.shouldContinue)
        assertTrue(normalized.nextQuestion.orEmpty().contains("autoritativno stanje"))
    }

    @Test
    fun `answer at level threshold finishes when model is satisfied`() {
        val normalized = VsAiEvaluationPolicy.normalize(
            levelContext = contexts.get("junior"),
            challenge = challenge,
            round = 2,
            result = result(score = 75, isSatisfied = true, shouldContinue = false)
        )

        assertTrue(normalized.isSatisfied)
        assertFalse(normalized.shouldContinue)
        assertNull(normalized.nextQuestion)
    }

    @Test
    fun `maximum round stops even an insufficient answer`() {
        val context = contexts.get("beginner")
        val normalized = VsAiEvaluationPolicy.normalize(
            levelContext = context,
            challenge = challenge,
            round = context.maximumRounds,
            result = result(score = 20, isSatisfied = false, shouldContinue = true)
        )

        assertFalse(normalized.isSatisfied)
        assertFalse(normalized.shouldContinue)
        assertNull(normalized.nextQuestion)
    }

    private fun result(
        score: Int,
        isSatisfied: Boolean,
        shouldContinue: Boolean
    ) = VsAiEvaluationResult(
        visibleMessage = "Procena odgovora.",
        shouldContinue = shouldContinue,
        isSatisfied = isSatisfied,
        satisfactionScore = score,
        nextQuestion = null,
        targetConcepts = listOf("source of truth"),
        detectedStrengths = emptyList(),
        detectedWeaknesses = emptyList(),
        reasonForContinuingOrStopping = "Test odluka."
    )
}
