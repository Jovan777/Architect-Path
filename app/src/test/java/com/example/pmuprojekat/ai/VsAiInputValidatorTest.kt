package com.example.pmuprojekat.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class VsAiInputValidatorTest {
    @Test
    fun `blank and oversized answers are rejected`() {
        assertEquals("Unesi odgovor pre slanja.", VsAiInputValidator.validate("   "))
        assertEquals(
            "Odgovor je predugačak. Skrati ga i pokušaj ponovo.",
            VsAiInputValidator.validate("a".repeat(2001))
        )
        assertNull(VsAiInputValidator.validate("Kratak i smislen odgovor."))
    }
}
