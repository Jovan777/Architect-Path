package com.example.pmuprojekat.ai

interface AiSketchAnalysisService {
    suspend fun analyze(request: AiSketchAnalysisRequest): Result<String>
}
