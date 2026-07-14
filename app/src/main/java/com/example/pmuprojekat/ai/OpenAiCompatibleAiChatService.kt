package com.example.pmuprojekat.ai

import javax.inject.Inject

class OpenAiCompatibleAiChatService @Inject constructor(
    private val client: OpenAiChatCompletionsClient,
    private val model: String
) : AiChatService {

    override suspend fun sendMessage(request: AiChatRequest): Result<String> = runCatching {
        val prompt = AiChatPromptBuilder.build(request)
        client.createCompletion(
            model = model,
            instruction = prompt.systemPrompt,
            userContent = prompt.userPrompt,
            maxCompletionTokens = 900,
            requestedTemperature = 0.35,
            requestedReasoningEffort = OpenAiReasoningEffort.NONE
        )
    }
}
