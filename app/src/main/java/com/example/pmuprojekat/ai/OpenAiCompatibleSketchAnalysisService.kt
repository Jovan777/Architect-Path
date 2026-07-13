package com.example.pmuprojekat.ai

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class OpenAiCompatibleSketchAnalysisService @Inject constructor(
    private val apiKey: String,
    private val endpoint: String,
    private val model: String
) : AiSketchAnalysisService {

    override suspend fun analyze(request: AiSketchAnalysisRequest): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val prompt = AiSketchAnalysisPromptBuilder.build(request)
            val imageData = Base64.encodeToString(request.imageBytes, Base64.NO_WRAP)
            val dataUrl = "data:${request.imageMimeType};base64,$imageData"

            val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                connectTimeout = 20_000
                readTimeout = 60_000
                doOutput = true
                setRequestProperty("Authorization", "Bearer $apiKey")
                setRequestProperty("Content-Type", "application/json")
            }

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

            val body = JSONObject()
                .put("model", model)
                .put("temperature", 0.25)
                .put("max_tokens", 750)
                .put(
                    "messages",
                    JSONArray()
                        .put(
                            JSONObject()
                                .put("role", "system")
                                .put("content", prompt.systemPrompt)
                        )
                        .put(
                            JSONObject()
                                .put("role", "user")
                                .put("content", userContent)
                        )
                )
                .toString()

            connection.outputStream.use { output ->
                output.write(body.toByteArray(Charsets.UTF_8))
            }

            val responseCode = connection.responseCode
            val responseBody = readResponse(connection, responseCode)

            if (responseCode !in 200..299) {
                throw IllegalStateException("AI vision API error $responseCode: ${responseBody.take(500)}")
            }

            JSONObject(responseBody)
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
                .trim()
                .ifBlank {
                    throw IllegalStateException("AI vision API returned an empty analysis.")
                }
        }
    }

    private fun readResponse(connection: HttpURLConnection, responseCode: Int): String {
        val stream = if (responseCode in 200..299) {
            connection.inputStream
        } else {
            connection.errorStream ?: connection.inputStream
        }

        return stream.use { input ->
            BufferedReader(InputStreamReader(input, Charsets.UTF_8)).use { reader ->
                reader.readText()
            }
        }
    }
}
