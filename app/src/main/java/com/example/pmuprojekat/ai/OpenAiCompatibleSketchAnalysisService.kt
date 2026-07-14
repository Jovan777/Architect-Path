package com.example.pmuprojekat.ai

import android.util.Base64
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class OpenAiCompatibleSketchAnalysisService @Inject constructor(
    private val client: OpenAiChatCompletionsClient,
    private val model: String
) : AiSketchAnalysisService {

    override suspend fun analyze(request: AiSketchAnalysisRequest): Result<String> = runCatching {
        val prompt = AiSketchAnalysisPromptBuilder.build(request)
        val imageData = Base64.encodeToString(request.imageBytes, Base64.NO_WRAP)
        val dataUrl = "data:${request.imageMimeType};base64,$imageData"

        val userContent = JSONArray()
            .put(
                JSONObject()
                    .put("type", "text")
                    .put("text", prompt.taskContextPrompt)
            )
            .put(
                JSONObject()
                    .put("type", "image_url")
                    .put(
                        "image_url",
                        JSONObject()
                            .put("url", dataUrl)
                            .put("detail", "auto")
                    )
            )

        client.createCompletion(
            model = model,
            instruction = prompt.systemPrompt,
            userContent = userContent,
            maxCompletionTokens = 3_000,
            requestedTemperature = 0.25,
            requestedReasoningEffort = OpenAiReasoningEffort.LOW
        )
    }
}
