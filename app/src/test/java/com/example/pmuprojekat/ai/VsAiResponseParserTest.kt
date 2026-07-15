package com.example.pmuprojekat.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class VsAiResponseParserTest {

    @Test
    fun `evaluation parser accepts fenced JSON and clamps score`() {
        val parsed = VsAiResponseParser.parseEvaluation(
            """
            Evo procene:
            ```json
            {
              "visibleMessage": "Dopuni posledicu kvara.",
              "shouldContinue": true,
              "isSatisfied": false,
              "satisfactionScore": 140,
              "nextQuestion": "Šta se dešava kada cache nije dostupan?",
              "targetConcepts": ["cache", "fallback"],
              "detectedStrengths": ["Prepoznao je cache."],
              "detectedWeaknesses": ["Nedostaje fallback."],
              "reasonForContinuingOrStopping": "Odgovor nije potpun."
            }
            ```
            """.trimIndent()
        )

        assertEquals(100, parsed.satisfactionScore)
        assertTrue(parsed.shouldContinue)
        assertEquals("Šta se dešava kada cache nije dostupan?", parsed.nextQuestion)
        assertEquals(listOf("cache", "fallback"), parsed.targetConcepts)
    }

    @Test
    fun `satisfied evaluation cannot continue`() {
        val parsed = VsAiResponseParser.parseEvaluation(
            """
            {
              "visibleMessage": "Dovoljno precizno.",
              "shouldContinue": true,
              "isSatisfied": true,
              "satisfactionScore": 90,
              "nextQuestion": "Ovo se neće koristiti.",
              "targetConcepts": ["Strategy"],
              "detectedStrengths": ["Jasna odgovornost."],
              "detectedWeaknesses": [],
              "reasonForContinuingOrStopping": "Prag je dostignut."
            }
            """.trimIndent()
        )

        assertFalse(parsed.shouldContinue)
        assertNull(parsed.nextQuestion)
    }
}
