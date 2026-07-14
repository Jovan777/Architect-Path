package com.example.pmuprojekat.ai

enum class AiChatRole {
    USER,
    ASSISTANT
}

data class AiChatMessageContext(
    val role: AiChatRole,
    val content: String
)

data class AiChatRelevantTerm(
    val id: String,
    val categoryId: String,
    val categoryTitle: String,
    val titleSr: String,
    val titleEn: String,
    val shortExplanation: String,
    val score: Int
)

data class AiChatRequest(
    val userMessage: String,
    val recentMessages: List<AiChatMessageContext>,
    val relevantTerms: List<AiChatRelevantTerm>
)

data class AiChatPrompt(
    val systemPrompt: String,
    val userPrompt: String
)
