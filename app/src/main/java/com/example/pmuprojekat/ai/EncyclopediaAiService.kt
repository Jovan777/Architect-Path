package com.example.pmuprojekat.ai

interface EncyclopediaAiService {
    suspend fun explain(request: EncyclopediaAiRequest): Result<String>
    suspend fun answerCustomQuestion(request: EncyclopediaCustomQuestionRequest): Result<String>
}
