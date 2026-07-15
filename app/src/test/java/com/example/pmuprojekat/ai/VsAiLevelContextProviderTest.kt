package com.example.pmuprojekat.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VsAiLevelContextProviderTest {
    private val provider = VsAiLevelContextProvider()

    @Test
    fun `all levels use their required round and satisfaction limits`() {
        val expected = mapOf(
            "beginner" to (3 to 70),
            "junior" to (4 to 75),
            "medior" to (5 to 80),
            "senior" to (6 to 85),
            "architect" to (7 to 90)
        )

        assertEquals(expected.keys, provider.getAll().map { it.levelId }.toSet())
        expected.forEach { (levelId, limits) ->
            val context = provider.get(levelId)
            assertEquals(limits.first, context.maximumRounds)
            assertEquals(limits.second, context.minimumExpectedAnswerQuality)
            assertTrue(context.satisfactionCriteria.isNotEmpty())
            assertTrue(context.suitableQuestionExamples.isNotEmpty())
        }
    }
}
