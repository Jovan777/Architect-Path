package com.example.pmuprojekat.data.encyclopedia

data class EncyclopediaCategory(
    val id: String,
    val title: String,
    val badgeText: String,
    val description: String? = null,
    val terms: List<EncyclopediaTerm>
)

data class EncyclopediaTerm(
    val id: String,
    val categoryId: String,
    val titleSr: String,
    val titleEn: String,
    val shortExplanation: String
)
