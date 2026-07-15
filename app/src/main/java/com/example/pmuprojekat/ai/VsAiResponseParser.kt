package com.example.pmuprojekat.ai

import org.json.JSONArray
import org.json.JSONObject

object VsAiResponseParser {

    fun parseChallenge(rawResponse: String): VsAiGeneratedChallenge {
        val json = parseObject(rawResponse)
        return VsAiGeneratedChallenge(
            question = json.requiredText("question"),
            targetConcept = json.requiredText("targetConcept"),
            expectedAnswerGuide = json.requiredText("expectedAnswerGuide"),
            difficultyLabel = json.requiredText("difficultyLabel"),
            reasonChosen = json.requiredText("reasonChosen")
        )
    }

    fun parseEvaluation(rawResponse: String): VsAiEvaluationResult {
        val json = parseObject(rawResponse)
        val isSatisfied = json.booleanValue("isSatisfied")
        val requestedContinuation = json.booleanValue("shouldContinue")
        val shouldContinue = requestedContinuation && !isSatisfied
        val requestedNextQuestion = json.nullableText("nextQuestion")

        if (shouldContinue && requestedNextQuestion.isNullOrBlank()) {
            throw VsAiInvalidResponseException("Nastavak nema sledeće pitanje.")
        }
        val nextQuestion = requestedNextQuestion.takeIf { shouldContinue }

        return VsAiEvaluationResult(
            visibleMessage = json.requiredText("visibleMessage"),
            shouldContinue = shouldContinue,
            isSatisfied = isSatisfied,
            satisfactionScore = json.intValue("satisfactionScore").coerceIn(0, 100),
            nextQuestion = nextQuestion,
            targetConcepts = json.stringList("targetConcepts"),
            detectedStrengths = json.stringList("detectedStrengths"),
            detectedWeaknesses = json.stringList("detectedWeaknesses"),
            reasonForContinuingOrStopping = json.requiredText("reasonForContinuingOrStopping")
        )
    }

    fun parseFinalAnalysis(rawResponse: String): VsAiFinalAnalysis {
        val json = parseObject(rawResponse)
        val keyConcepts = json.stringList("keyConcepts")
        if (keyConcepts.isEmpty()) {
            throw VsAiInvalidResponseException("Završna analiza nema ključne koncepte.")
        }

        return VsAiFinalAnalysis(
            score = json.intValue("score").coerceIn(0, 100),
            resultSummary = json.requiredText("resultSummary"),
            strengths = json.requiredText("strengths"),
            weakerPoints = json.requiredText("weakerPoints"),
            reasoningDevelopment = json.requiredText("reasoningDevelopment"),
            keyConcepts = keyConcepts,
            nextConcreteStep = json.requiredText("nextConcreteStep")
        )
    }

    private fun parseObject(rawResponse: String): JSONObject {
        val jsonText = extractFirstJsonObject(rawResponse)
        return runCatching { JSONObject(jsonText) }
            .getOrElse { throw VsAiInvalidResponseException("AI odgovor nije validan JSON.", it) }
    }

    internal fun extractFirstJsonObject(rawResponse: String): String {
        val start = rawResponse.indexOf('{')
        if (start < 0) throw VsAiInvalidResponseException("AI odgovor nema JSON objekat.")

        var depth = 0
        var inString = false
        var escaped = false

        for (index in start until rawResponse.length) {
            val character = rawResponse[index]
            if (inString) {
                when {
                    escaped -> escaped = false
                    character == '\\' -> escaped = true
                    character == '"' -> inString = false
                }
                continue
            }

            when (character) {
                '"' -> inString = true
                '{' -> depth += 1
                '}' -> {
                    depth -= 1
                    if (depth == 0) return rawResponse.substring(start, index + 1)
                }
            }
        }

        throw VsAiInvalidResponseException("AI JSON objekat nije zatvoren.")
    }

    private fun JSONObject.requiredText(key: String): String {
        return nullableText(key)
            ?: throw VsAiInvalidResponseException("Nedostaje polje: $key")
    }

    private fun JSONObject.nullableText(key: String): String? {
        if (!has(key) || isNull(key)) return null
        return optString(key).trim().takeIf(String::isNotBlank)
    }

    private fun JSONObject.booleanValue(key: String): Boolean {
        if (!has(key)) throw VsAiInvalidResponseException("Nedostaje polje: $key")
        return when (val value = opt(key)) {
            is Boolean -> value
            is String -> value.equals("true", ignoreCase = true)
            is Number -> value.toInt() != 0
            else -> throw VsAiInvalidResponseException("Polje $key nije boolean.")
        }
    }

    private fun JSONObject.intValue(key: String): Int {
        if (!has(key)) throw VsAiInvalidResponseException("Nedostaje polje: $key")
        return when (val value = opt(key)) {
            is Number -> value.toInt()
            is String -> value.toIntOrNull()
            else -> null
        } ?: throw VsAiInvalidResponseException("Polje $key nije broj.")
    }

    private fun JSONObject.stringList(key: String): List<String> {
        if (!has(key) || isNull(key)) return emptyList()
        return when (val value = opt(key)) {
            is JSONArray -> buildList {
                for (index in 0 until value.length()) {
                    value.optString(index).trim().takeIf(String::isNotBlank)?.let(::add)
                }
            }
            is String -> value.split(',').map(String::trim).filter(String::isNotBlank)
            else -> emptyList()
        }.distinct()
    }
}

class VsAiInvalidResponseException(
    message: String,
    cause: Throwable? = null
) : IllegalStateException(message, cause)
