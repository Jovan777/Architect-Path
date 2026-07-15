package com.example.pmuprojekat.ai

interface VsAiService {
    suspend fun generateChallenge(request: VsAiStartRequest): Result<VsAiGeneratedChallenge>
    suspend fun evaluateAnswer(request: VsAiEvaluationRequest): Result<VsAiEvaluationResult>
    suspend fun generateFinalAnalysis(request: VsAiFinalAnalysisRequest): Result<VsAiFinalAnalysis>
}
