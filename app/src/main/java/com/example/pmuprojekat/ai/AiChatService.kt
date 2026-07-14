package com.example.pmuprojekat.ai

interface AiChatService {
    suspend fun sendMessage(request: AiChatRequest): Result<String>
}
