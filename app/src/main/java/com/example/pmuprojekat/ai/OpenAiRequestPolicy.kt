package com.example.pmuprojekat.ai

/** Keeps optional generation parameters compatible with the configured model. */
object OpenAiRequestPolicy {
    fun temperatureForModel(model: String, requestedTemperature: Double): Double? {
        return requestedTemperature.takeUnless { isGpt56(model) }
    }

    fun maxTokenParameterForModel(model: String): String {
        return if (usesModernChatParameters(model)) {
            "max_completion_tokens"
        } else {
            "max_tokens"
        }
    }

    fun instructionRoleForModel(model: String): String {
        return if (usesModernChatParameters(model)) "developer" else "system"
    }

    fun reasoningEffortForModel(
        model: String,
        requestedEffort: OpenAiReasoningEffort
    ): String? {
        return requestedEffort.apiValue.takeIf { isGpt56(model) }
    }

    private fun isGpt56(model: String): Boolean {
        return normalized(model).startsWith("gpt-5.6")
    }

    private fun usesModernChatParameters(model: String): Boolean {
        val normalized = normalized(model)
        return normalized.startsWith("gpt-5") ||
            normalized.startsWith("o1") ||
            normalized.startsWith("o3") ||
            normalized.startsWith("o4")
    }

    private fun normalized(model: String): String {
        return model.trim().lowercase()
    }
}

enum class OpenAiReasoningEffort(val apiValue: String) {
    NONE("none"),
    LOW("low")
}
