package com.example.pmuprojekat.ai

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class OpenAiRequestPolicyTest {

    @Test
    fun gpt56Luna_omitsCustomTemperature() {
        assertNull(OpenAiRequestPolicy.temperatureForModel("gpt-5.6-luna", 0.25))
    }

    @Test
    fun anyGpt56Model_omitsCustomTemperature() {
        assertNull(OpenAiRequestPolicy.temperatureForModel(" GPT-5.6-preview ", 0.35))
    }

    @Test
    fun supportedModel_keepsRequestedTemperature() {
        assertEquals(
            0.25,
            OpenAiRequestPolicy.temperatureForModel("gpt-4o", 0.25)!!,
            0.0
        )
    }

    @Test
    fun gpt56_usesModernTokenParameterAndDeveloperRole() {
        assertEquals(
            "max_completion_tokens",
            OpenAiRequestPolicy.maxTokenParameterForModel("gpt-5.6-luna")
        )
        assertEquals(
            "developer",
            OpenAiRequestPolicy.instructionRoleForModel("gpt-5.6-luna")
        )
        assertEquals(
            "low",
            OpenAiRequestPolicy.reasoningEffortForModel(
                "gpt-5.6-luna",
                OpenAiReasoningEffort.LOW
            )
        )
    }

    @Test
    fun legacyModel_keepsLegacyTokenParameterAndSystemRole() {
        assertEquals("max_tokens", OpenAiRequestPolicy.maxTokenParameterForModel("gpt-4o"))
        assertEquals("system", OpenAiRequestPolicy.instructionRoleForModel("gpt-4o"))
        assertNull(
            OpenAiRequestPolicy.reasoningEffortForModel(
                "gpt-4o",
                OpenAiReasoningEffort.NONE
            )
        )
    }
}
