package com.example.pmuprojekat.ui.aichat

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class AiChatSuggestedQuestionsTest {

    @Test
    fun questionPoolContainsSeventyUniqueQuestions() {
        val questions = AiChatSuggestedQuestions.all

        assertEquals(70, questions.size)
        assertEquals(questions.size, questions.distinct().size)
        assertTrue(questions.all { it.isNotBlank() })
    }

    @Test
    fun randomSelectionReturnsSixUniqueQuestionsFromPool() {
        val selection = AiChatSuggestedQuestions.randomSelection(random = Random(7))

        assertEquals(6, selection.size)
        assertEquals(selection.size, selection.distinct().size)
        assertTrue(selection.all { it in AiChatSuggestedQuestions.all })
    }
}
