package com.example.pmuprojekat.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AiChatInputValidatorTest {
    @Test
    fun validate_rejectsBlankMessage() {
        assertEquals(
            "Unesi pitanje pre slanja.",
            AiChatInputValidator.validate("   ")
        )
    }

    @Test
    fun validate_rejectsTooLongMessage() {
        val longMessage = "a".repeat(AiChatInputValidator.MAX_MESSAGE_LENGTH + 1)

        assertEquals(
            "Poruka je predugačka. Skrati pitanje i pokušaj ponovo.",
            AiChatInputValidator.validate(longMessage)
        )
    }

    @Test
    fun validate_acceptsNormalMessage() {
        assertNull(AiChatInputValidator.validate("Šta je read model?"))
    }
}
