package com.example.pmuprojekat.ai

import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class OpenAiCompatibleVsAiService @Inject constructor(
    private val client: OpenAiChatCompletionsClient,
    private val model: String
) : VsAiService {

    override suspend fun generateChallenge(
        request: VsAiStartRequest
    ): Result<VsAiGeneratedChallenge> = safeAiCall {
        val prompt = VsAiPromptBuilder.buildStart(request)
        val response = client.createCompletion(
            model = model,
            instruction = prompt.systemPrompt,
            userContent = prompt.userPrompt,
            maxCompletionTokens = 2_500,
            requestedTemperature = 0.2,
            requestedReasoningEffort = OpenAiReasoningEffort.LOW
        )
        VsAiResponseParser.parseChallenge(response)
    }

    override suspend fun evaluateAnswer(
        request: VsAiEvaluationRequest
    ): Result<VsAiEvaluationResult> = safeAiCall {
        val prompt = VsAiPromptBuilder.buildEvaluation(request)
        val response = client.createCompletion(
            model = model,
            instruction = prompt.systemPrompt,
            userContent = prompt.userPrompt,
            maxCompletionTokens = 2_800,
            requestedTemperature = 0.2,
            requestedReasoningEffort = OpenAiReasoningEffort.LOW
        )
        VsAiResponseParser.parseEvaluation(response)
    }

    override suspend fun generateFinalAnalysis(
        request: VsAiFinalAnalysisRequest
    ): Result<VsAiFinalAnalysis> = safeAiCall {
        val prompt = VsAiPromptBuilder.buildFinalAnalysis(request)
        val response = client.createCompletion(
            model = model,
            instruction = prompt.systemPrompt,
            userContent = prompt.userPrompt,
            maxCompletionTokens = 4_000,
            requestedTemperature = 0.2,
            requestedReasoningEffort = OpenAiReasoningEffort.LOW
        )
        VsAiResponseParser.parseFinalAnalysis(response)
    }

    private suspend fun <T> safeAiCall(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}
