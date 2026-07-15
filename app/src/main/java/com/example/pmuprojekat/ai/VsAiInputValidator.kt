package com.example.pmuprojekat.ai

object VsAiInputValidator {
    const val MAX_ANSWER_LENGTH = 2_000

    fun validate(answer: String): String? {
        return when {
            answer.isBlank() -> "Unesi odgovor pre slanja."
            answer.length > MAX_ANSWER_LENGTH ->
                "Odgovor je predugačak. Skrati ga i pokušaj ponovo."
            else -> null
        }
    }
}
