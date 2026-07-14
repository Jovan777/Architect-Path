package com.example.pmuprojekat.data.encyclopedia

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class EncyclopediaTermRetrieverTest {
    private val retriever = EncyclopediaTermRetriever(LocalEncyclopediaRepository())

    @Test
    fun findRelevantTerms_matchesSerbianTitle() {
        val results = retriever.findRelevantTerms("Šta znači cache?", limit = 5)

        assertTrue(results.any { it.titleSr.contains("Cache", ignoreCase = true) })
    }

    @Test
    fun findRelevantTerms_matchesEnglishTitle() {
        val results = retriever.findRelevantTerms("Explain RAG", limit = 5)

        assertEquals("RAG", results.first().titleSr)
    }

    @Test
    fun findRelevantTerms_matchesCategoryTitle() {
        val results = retriever.findRelevantTerms("bezbednost", limit = 5)

        assertTrue(results.isNotEmpty())
        assertTrue(results.first().categoryTitle.contains("Bezbednost", ignoreCase = true))
    }
}
