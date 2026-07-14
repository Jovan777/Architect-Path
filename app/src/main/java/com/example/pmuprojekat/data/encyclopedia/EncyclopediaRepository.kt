package com.example.pmuprojekat.data.encyclopedia

import javax.inject.Inject
import javax.inject.Singleton

interface EncyclopediaRepository {
    fun getCategories(): List<EncyclopediaCategory>
    fun getTermsForCategory(categoryId: String): List<EncyclopediaTerm>
    fun getTermById(termId: String): EncyclopediaTerm?
}

@Singleton
class LocalEncyclopediaRepository @Inject constructor() : EncyclopediaRepository {
    private val categories = EncyclopediaSeed.categories
    private val termsById = categories
        .flatMap(EncyclopediaCategory::terms)
        .associateBy(EncyclopediaTerm::id)

    override fun getCategories(): List<EncyclopediaCategory> = categories

    override fun getTermsForCategory(categoryId: String): List<EncyclopediaTerm> {
        return categories.firstOrNull { it.id == categoryId }?.terms.orEmpty()
    }

    override fun getTermById(termId: String): EncyclopediaTerm? = termsById[termId]
}
