package com.example.pmuprojekat.ai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class OpenAiCompatibleEncyclopediaAiService @Inject constructor(
    private val apiKey: String,
    private val endpoint: String,
    private val model: String
) : EncyclopediaAiService {

    override suspend fun explain(request: EncyclopediaAiRequest): Result<String> =
        withContext(Dispatchers.IO) {
            runCatching {
                val prompt = EncyclopediaPromptBuilder.build(request)
                val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    connectTimeout = 20_000
                    readTimeout = 45_000
                    doOutput = true
                    setRequestProperty("Authorization", "Bearer $apiKey")
                    setRequestProperty("Content-Type", "application/json")
                }

                val body = JSONObject()
                    .put("model", model)
                    .put("temperature", 0.3)
                    .put("max_tokens", 700)
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
                                    .put("content", prompt.userPrompt)
                            )
                    )
                    .toString()

                connection.outputStream.use { output ->
                    output.write(body.toByteArray(Charsets.UTF_8))
                }

                val responseCode = connection.responseCode
                val responseBody = readResponse(connection, responseCode)

                if (responseCode !in 200..299) {
                    throw IllegalStateException(
                        "Encyclopedia AI API error $responseCode: ${responseBody.take(500)}"
                    )
                }

                JSONObject(responseBody)
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim()
                    .ifBlank {
                        throw IllegalStateException("Encyclopedia AI API returned an empty response.")
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
