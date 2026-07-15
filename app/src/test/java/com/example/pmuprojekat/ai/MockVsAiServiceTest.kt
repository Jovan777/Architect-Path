package com.example.pmuprojekat.ai

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MockVsAiServiceTest {
    private val service = MockVsAiService()
    private val contexts = VsAiLevelContextProvider()

    @Test
    fun `beginner mock accepts a clear basic explanation quickly`() = runBlocking {
        val context = contexts.get("beginner")
        val challenge = service.generateChallenge(startRequest(context)).getOrThrow()
        val result = service.evaluateAnswer(
            evaluationRequest(
                context = context,
                challenge = challenge,
                answer = "Interfejs skriva konkretnu klasu i njenu implementaciju jer objekat zavisi od jasne odgovornosti.",
                round = 1
            )
        ).getOrThrow()

        assertTrue(result.isSatisfied)
        assertFalse(result.shouldContinue)
    }

    @Test
    fun `architect mock challenges a vague answer`() = runBlocking {
        val context = contexts.get("architect")
        val challenge = service.generateChallenge(startRequest(context)).getOrThrow()
        val result = service.evaluateAnswer(
            evaluationRequest(
                context = context,
                challenge = challenge,
                answer = "Koristio bih mikroservise.",
                round = 1
            )
        ).getOrThrow()

        assertFalse(result.isSatisfied)
        assertTrue(result.shouldContinue)
        assertTrue(result.nextQuestion.orEmpty().contains("izvor istine"))
    }

    private fun startRequest(context: VsAiLevelContext) = VsAiStartRequest(
        levelContext = context,
        learnerContext = learner(context),
        relevantTerms = emptyList()
    )

    private fun evaluationRequest(
        context: VsAiLevelContext,
        challenge: VsAiGeneratedChallenge,
        answer: String,
        round: Int
    ) = VsAiEvaluationRequest(
        levelContext = context,
        learnerContext = learner(context),
        challenge = challenge,
        relevantTerms = emptyList(),
        conversation = listOf(
            VsAiConversationTurn(VsAiConversationRole.AI, challenge.question, 1),
            VsAiConversationTurn(VsAiConversationRole.USER, answer, round)
        ),
        previousEvaluationSignals = emptyList(),
        currentRound = round
    )

    private fun learner(context: VsAiLevelContext) = VsAiLearnerContext(
        selectedLevelId = context.levelId,
        selectedLevelName = context.displayName,
        solvedTasks = 0,
        totalTasks = 0,
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
}
