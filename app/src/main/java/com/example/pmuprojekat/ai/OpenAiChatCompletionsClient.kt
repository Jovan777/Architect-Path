package com.example.pmuprojekat.ai

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class OpenAiChatCompletionsClient(
    private val apiKey: String,
    private val endpoint: String
) {
    suspend fun createCompletion(
        model: String,
        instruction: String,
        userContent: Any,
        maxCompletionTokens: Int,
        requestedTemperature: Double?,
        requestedReasoningEffort: OpenAiReasoningEffort? = null
    ): String = withContext(Dispatchers.IO) {
        require(model.isNotBlank()) { "OpenAI model must not be blank." }
        require(maxCompletionTokens > 0) { "Completion token limit must be positive." }

        val normalizedModel = model.trim()
        val body = JSONObject()
            .put("model", normalizedModel)
            .put(
                OpenAiRequestPolicy.maxTokenParameterForModel(normalizedModel),
                maxCompletionTokens
            )
            .put(
                "messages",
                JSONArray()
                    .put(
                        JSONObject()
                            .put("role", OpenAiRequestPolicy.instructionRoleForModel(normalizedModel))
                            .put("content", instruction)
                    )
                    .put(
                        JSONObject()
                            .put("role", "user")
                            .put("content", userContent)
                    )
            )

        requestedTemperature?.let { temperature ->
            OpenAiRequestPolicy.temperatureForModel(normalizedModel, temperature)?.let {
                body.put("temperature", it)
            }
        }

        requestedReasoningEffort?.let { effort ->
            OpenAiRequestPolicy.reasoningEffortForModel(normalizedModel, effort)?.let {
                body.put("reasoning_effort", it)
            }
        }

        executeRequest(normalizedModel, body.toString())
    }

    private fun executeRequest(model: String, body: String): String {
        val connection = (URL(endpoint.trim()).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = CONNECT_TIMEOUT_MS
            readTimeout = READ_TIMEOUT_MS
            doOutput = true
            setRequestProperty("Authorization", "Bearer ${apiKey.trim()}")
            setRequestProperty("Content-Type", "application/json")
        }

        try {
            connection.outputStream.use { output ->
                output.write(body.toByteArray(Charsets.UTF_8))
            }

            val responseCode = connection.responseCode
            val responseBody = readResponse(connection, responseCode)

            if (responseCode !in 200..299) {
                throwApiError(responseCode, model, responseBody)
            }

            return parseAssistantContent(responseBody)
        } catch (error: OpenAiRequestException) {
            throw error
        } catch (error: Exception) {
            Log.e(TAG, "OpenAI request failed before a valid response was parsed. model=$model", error)
            throw OpenAiRequestException(cause = error)
        } finally {
            connection.disconnect()
        }
    }

    private fun throwApiError(responseCode: Int, model: String, responseBody: String): Nothing {
        val errorObject = runCatching {
            JSONObject(responseBody).optJSONObject("error")
        }.getOrNull()
        val type = errorObject?.optString("type").orEmpty()
        val code = errorObject?.optString("code").orEmpty()
        val parameter = errorObject?.optString("param").orEmpty()

        Log.e(
            TAG,
            "OpenAI API rejected request: http=$responseCode model=$model type=$type code=$code param=$parameter"
        )
        throw OpenAiRequestException()
    }

    private fun parseAssistantContent(responseBody: String): String {
        val root = JSONObject(responseBody)
        val message = root.optJSONArray("choices")
            ?.optJSONObject(0)
            ?.optJSONObject("message")
            ?: throw OpenAiRequestException()

        val content = when (val rawContent = message.opt("content")) {
            is String -> rawContent
            is JSONArray -> buildString {
                for (index in 0 until rawContent.length()) {
                    val text = rawContent.optJSONObject(index)?.optString("text").orEmpty()
                    if (text.isNotBlank()) {
                        if (isNotEmpty()) appendLine()
                        append(text)
                    }
                }
            }
            else -> ""
        }

        return content.trim()
            .ifBlank { message.optString("refusal", "").trim() }
            .ifBlank { throw OpenAiRequestException() }
    }

    private fun readResponse(connection: HttpURLConnection, responseCode: Int): String {
        val stream = if (responseCode in 200..299) {
            connection.inputStream
        } else {
            connection.errorStream ?: return ""
        }

        return stream.use { input ->
            BufferedReader(InputStreamReader(input, Charsets.UTF_8)).use { reader ->
                reader.readText()
            }
        }
    }

    private companion object {
        const val TAG = "OpenAiClient"
        const val CONNECT_TIMEOUT_MS = 20_000
        const val READ_TIMEOUT_MS = 60_000
    }
}

class OpenAiRequestException(
    cause: Throwable? = null
) : IOException("AI service request failed.", cause)
