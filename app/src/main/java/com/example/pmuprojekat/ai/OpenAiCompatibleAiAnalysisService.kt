package com.example.pmuprojekat.ai

import javax.inject.Inject

class OpenAiCompatibleAiAnalysisService @Inject constructor(
    private val client: OpenAiChatCompletionsClient,
    private val model: String
) : AiAnalysisService {

    override suspend fun analyze(request: AiAnalysisRequest): Result<String> = runCatching {
        val prompt = AiAnalysisPromptBuilder.build(request)
        client.createCompletion(
            model = model,
            instruction = prompt.systemPrompt,
            userContent = prompt.taskContextPrompt,
            maxCompletionTokens = 4_000,
            requestedTemperature = 0.25,
            requestedReasoningEffort = OpenAiReasoningEffort.LOW
        )
    }
}
