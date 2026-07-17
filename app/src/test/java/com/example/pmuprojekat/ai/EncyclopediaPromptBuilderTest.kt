package com.example.pmuprojekat.ai

import com.example.pmuprojekat.data.encyclopedia.EncyclopediaTerm
import org.junit.Assert.assertTrue
import org.junit.Test

class EncyclopediaPromptBuilderTest {

    @Test
    fun customQuestionPrompt_includesTermContextAndUserQuestion() {
        val term = EncyclopediaTerm(
            id = "rag",
            categoryId = "ai_ml_rag",
            titleSr = "RAG",
            titleEn = "Retrieval-Augmented Generation",
            shortExplanation = "RAG prvo pronalazi relevantne informacije, a zatim ih prosleđuje modelu."
        )

        val prompt = EncyclopediaPromptBuilder.buildCustomQuestion(
            EncyclopediaCustomQuestionRequest(
                categoryTitle = "AI, ML i RAG",
                term = term,
                question = "Kada mi zapravo treba RAG, a kada je dovoljan običan prompt?"
            )
        )

        assertTrue(prompt.userPrompt.contains("AI, ML i RAG"))
        assertTrue(prompt.userPrompt.contains("RAG"))
        assertTrue(prompt.userPrompt.contains("Retrieval-Augmented Generation"))
        assertTrue(prompt.userPrompt.contains("Kada mi zapravo treba RAG"))
        assertTrue(prompt.userPrompt.contains("Ovo pitanje nije direktno vezano"))
        assertTrue(prompt.systemPrompt.contains("srpskom"))
    }
}
