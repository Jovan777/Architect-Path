package com.example.pmuprojekat.ai

import com.example.pmuprojekat.data.encyclopedia.EncyclopediaTerm

enum class EncyclopediaExplanationMode(
    val actionLabel: String,
    val responseTitle: String
) {
    DETAILED(
        actionLabel = "Objasni detaljnije",
        responseTitle = "Detaljnije objašnjenje"
    ),
    SIMPLE(
        actionLabel = "Objasni jednostavnije",
        responseTitle = "Jednostavnije objašnjenje"
    ),
    PRACTICAL_EXAMPLE(
        actionLabel = "Daj primer iz prakse",
        responseTitle = "Primer iz prakse"
    )
}

data class EncyclopediaAiRequest(
    val categoryTitle: String,
    val term: EncyclopediaTerm,
    val mode: EncyclopediaExplanationMode
)

data class EncyclopediaAiPrompt(
    val systemPrompt: String,
    val userPrompt: String
)
