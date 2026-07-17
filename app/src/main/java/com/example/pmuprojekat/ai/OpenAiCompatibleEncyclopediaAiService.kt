package com.example.pmuprojekat.ai

import javax.inject.Inject

class OpenAiCompatibleEncyclopediaAiService @Inject constructor(
    private val client: OpenAiChatCompletionsClient,
    private val model: String
) : EncyclopediaAiService {

    override suspend fun explain(request: EncyclopediaAiRequest): Result<String> = runCatching {
        val prompt = EncyclopediaPromptBuilder.build(request)
        client.createCompletion(
            model = model,
            instruction = prompt.systemPrompt,
            userContent = prompt.userPrompt,
            maxCompletionTokens = 700,
            requestedTemperature = 0.3,
            requestedReasoningEffort = OpenAiReasoningEffort.NONE
        )
    }

    override suspend fun answerCustomQuestion(
        request: EncyclopediaCustomQuestionRequest
    ): Result<String> = runCatching {
        val prompt = EncyclopediaPromptBuilder.buildCustomQuestion(request)
        client.createCompletion(
            model = model,
            instruction = prompt.systemPrompt,
            userContent = prompt.userPrompt,
            maxCompletionTokens = 650,
            requestedTemperature = 0.3,
            requestedReasoningEffort = OpenAiReasoningEffort.NONE
        )
    }
}
