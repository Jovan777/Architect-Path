package com.example.pmuprojekat.ai

interface AiAnalysisService {
    suspend fun analyze(request: AiAnalysisRequest): Result<String>
}
