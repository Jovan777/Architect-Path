package com.example.pmuprojekat.data.encyclopedia

import com.example.pmuprojekat.ai.AiChatRelevantTerm
import java.util.Locale
import javax.inject.Inject

class EncyclopediaTermRetriever @Inject constructor(
    private val repository: EncyclopediaRepository
) {
    fun findRelevantTerms(
        query: String,
        limit: Int = DEFAULT_LIMIT
    ): List<AiChatRelevantTerm> {
        val normalizedQuery = query.normalized()
        if (normalizedQuery.isBlank()) return emptyList()

        val queryTokens = normalizedQuery
            .split(Regex("[^\\p{L}\\p{N}]+"))
            .filter { it.length >= 2 }

        return repository.getCategories()
            .flatMap { category ->
                category.terms.mapNotNull { term ->
                    val score = scoreTerm(
                        query = normalizedQuery,
                        queryTokens = queryTokens,
                        category = category,
                        term = term
                    )

                    if (score <= 0) {
                        null
                    } else {
                        AiChatRelevantTerm(
                            id = term.id,
                            categoryId = category.id,
                            categoryTitle = category.title,
                            titleSr = term.titleSr,
                            titleEn = term.titleEn,
                            shortExplanation = term.shortExplanation,
                            score = score
                        )
                    }
                }
            }
            .sortedWith(
                compareByDescending<AiChatRelevantTerm> { it.score }
                    .thenBy { it.titleSr }
            )
            .take(limit)
    }

    private fun scoreTerm(
        query: String,
        queryTokens: List<String>,
        category: EncyclopediaCategory,
        term: EncyclopediaTerm
    ): Int {
        val titleSr = term.titleSr.normalized()
        val titleEn = term.titleEn.normalized()
        val explanation = term.shortExplanation.normalized()
        val categoryTitle = category.title.normalized()

        var score = 0

        if (query == titleSr || query == titleEn) score += 120
        if (titleSr.contains(query) || titleEn.contains(query)) score += 85
        if (query.contains(titleSr) || query.contains(titleEn)) score += 75
        if (categoryTitle.contains(query) || query.contains(categoryTitle)) score += 20

        queryTokens.forEach { token ->
            if (titleSr.contains(token)) score += 28
            if (titleEn.contains(token)) score += 28
            if (categoryTitle.contains(token)) score += 7
            if (explanation.contains(token)) score += 3
        }

        return score
    }

    private fun String.normalized(): String {
        return trim().lowercase(Locale.ROOT)
    }

    companion object {
        const val DEFAULT_LIMIT = 5
    }
}
