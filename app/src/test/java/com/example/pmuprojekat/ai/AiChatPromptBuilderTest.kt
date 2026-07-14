package com.example.pmuprojekat.ai

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AiChatPromptBuilderTest {
    @Test
    fun prompt_includesDomainGuardrailAndRelevantTerms() {
        val prompt = AiChatPromptBuilder.build(
            AiChatRequest(
                userMessage = "Šta je RAG?",
                recentMessages = emptyList(),
                relevantTerms = listOf(
                    AiChatRelevantTerm(
                        id = "rag",
                        categoryId = "ai_ml_rag",
                        categoryTitle = "AI, ML i RAG",
                        titleSr = "RAG",
                        titleEn = "Retrieval-Augmented Generation",
                        shortExplanation = "RAG prvo pronalazi relevantne informacije, pa ih prosleđuje modelu.",
                        score = 120
                    )
                )
            )
        )

        assertTrue(prompt.systemPrompt.contains("Ovaj chat je namenjen pitanjima"))
        assertTrue(prompt.systemPrompt.contains("srpskom jeziku"))
        assertTrue(prompt.userPrompt.contains("Relevantni pojmovi iz enciklopedije"))
        assertTrue(prompt.userPrompt.contains("RAG — Retrieval-Augmented Generation"))
    }

    @Test
    fun prompt_handlesNoRelevantTerms() {
        val prompt = AiChatPromptBuilder.build(
            AiChatRequest(
                userMessage = "Kako da razmišljam o servisima?",
                recentMessages = emptyList(),
                relevantTerms = emptyList()
            )
        )

        assertTrue(prompt.userPrompt.contains("Nema posebno pronađenih pojmova"))
    }

    @Test
    fun prompt_limitsRecentHistory() {
        val history = (1..10).map { index ->
            AiChatMessageContext(
                role = if (index % 2 == 0) AiChatRole.ASSISTANT else AiChatRole.USER,
                content = "chat_history_${index.toString().padStart(2, '0')}"
            )
        }

        val prompt = AiChatPromptBuilder.build(
            AiChatRequest(
                userMessage = "Objasni cache.",
                recentMessages = history,
                relevantTerms = emptyList()
            )
        )

        assertFalse(prompt.userPrompt.contains("chat_history_01"))
        assertFalse(prompt.userPrompt.contains("chat_history_02"))
        assertTrue(prompt.userPrompt.contains("chat_history_03"))
        assertTrue(prompt.userPrompt.contains("chat_history_10"))
    }
}
