package com.example.pmuprojekat.ai

import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.UserQuestionProgressEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LearnerProgressSummarizerTest {

    @Test
    fun `insufficient history is stated without invented weaknesses`() {
        val context = LearnerProgressSummarizer.summarize(
            levelId = "beginner",
            questions = listOf(question("P1.1", "P1")),
            allProgress = emptyList()
        )

        assertEquals(0, context.solvedTasks)
        assertFalse(context.hasEnoughHistoryForPersonalization)
        assertTrue(context.weakerTaskTypes.isEmpty())
        assertTrue(context.repeatedMistakePatterns.isEmpty())
        assertEquals(
            "Korisnik nema dovoljno istorije za preciznu personalizaciju. Generiši pitanje prema nivou.",
            context.personalizationNote
        )
    }

    @Test
    fun `summary derives scores and task type strengths only from saved progress`() {
        val questions = listOf(
            question("P1.1", "P1"),
            question("P1.2", "P1"),
            question("P2.1", "P2"),
            question("P3.1", "P3")
        )
        val progress = listOf(
            progress("P1.1", 90, 100L),
            progress("P1.2", 80, 200L),
            progress("P2.1", 50, 300L)
        )

        val context = LearnerProgressSummarizer.summarize("beginner", questions, progress)

        assertEquals(3, context.solvedTasks)
        assertEquals(4, context.totalTasks)
        assertEquals(73, context.averageScorePercent)
        assertEquals(90, context.bestScorePercent)
        assertEquals(listOf("P1"), context.strongerTaskTypes)
        assertEquals(listOf("P2"), context.weakerTaskTypes)
        assertEquals("P2.1", context.recentResults.first().questionId)
        assertTrue(context.hasEnoughHistoryForPersonalization)
        assertEquals(VsAiLearnerStatus.SOLID, context.status)
    }

    private fun question(id: String, type: String) = QuestionEntity(
        questionId = id,
        level = "beginner",
        type = type,
        title = id,
        prompt = "Prompt"
    )

    private fun progress(questionId: String, score: Int, updatedAt: Long) =
        UserQuestionProgressEntity(
            userId = "local-user",
            questionId = questionId,
            status = "completed",
            bestScorePercent = score,
            updatedAt = updatedAt
        )
}
