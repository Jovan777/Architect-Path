package com.example.pmuprojekat.ai

object AiChatInputValidator {
    const val MAX_MESSAGE_LENGTH = 1_600

    fun validate(message: String): String? {
        val trimmed = message.trim()
        return when {
            trimmed.isBlank() -> "Unesi pitanje pre slanja."
            trimmed.length > MAX_MESSAGE_LENGTH -> "Poruka je predugačka. Skrati pitanje i pokušaj ponovo."
            else -> null
        }
    }
}
