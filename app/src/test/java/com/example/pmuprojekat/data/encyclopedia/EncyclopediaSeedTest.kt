package com.example.pmuprojekat.data.encyclopedia

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class EncyclopediaSeedTest {
    @Test
    fun categories_haveCompleteStableSeed() {
        val categories = EncyclopediaSeed.categories
        val aiMlRagCategory = categories.single { it.id == "ai_ml_rag" }
        val securityCategory = categories.single { it.id == "security_access_audit" }
        val integrationsCategory = categories.single { it.id == "integrations_business_domains" }
        val communicationCategory = categories.single { it.id == "communication_events_processing" }
        val consistencyCategory = categories.single { it.id == "consistency_concurrency" }
        val designPatternsCategory = categories.single { it.id == "design_patterns" }
        val observabilityCategory = categories.single { it.id == "observability_devops_incidents" }
        val programmingOopCategory = categories.single { it.id == "programming_oop_basics" }
        val performanceCategory = categories.single { it.id == "performance_scaling_resilience" }
        val dataCategory = categories.single { it.id == "data_storage_search" }
        val architectureCategory = categories.single { it.id == "software_architecture" }
        val patternRolesCategory = categories.single { it.id == "pattern_roles" }
        val allTerms = categories.flatMap { it.terms }

        assertEquals(12, categories.size)
        assertEquals("AI, ML i RAG", aiMlRagCategory.title)
        assertEquals("Bezbednost, pristup i audit", securityCategory.title)
        assertEquals("Integracije i poslovni domeni", integrationsCategory.title)
        assertEquals("Komunikacija, događaji i obrada", communicationCategory.title)
        assertEquals("Konzistentnost i konkurentnost", consistencyCategory.title)
        assertEquals("Obrasci projektovanja", designPatternsCategory.title)
        assertEquals("Observability, DevOps i incidenti", observabilityCategory.title)
        assertEquals("Osnove programiranja i OOP", programmingOopCategory.title)
        assertEquals("Performanse, skaliranje i otpornost", performanceCategory.title)
        assertEquals("Podaci, skladištenje i pretraga", dataCategory.title)
        assertEquals("Softverska arhitektura", architectureCategory.title)
        assertEquals("Uloge u obrascima", patternRolesCategory.title)
        assertEquals("AI", aiMlRagCategory.badgeText)
        assertEquals("SEC", securityCategory.badgeText)
        assertEquals("INT", integrationsCategory.badgeText)
        assertEquals("EVT", communicationCategory.badgeText)
        assertEquals("CONS", consistencyCategory.badgeText)
        assertEquals("PAT", designPatternsCategory.badgeText)
        assertEquals("OPS", observabilityCategory.badgeText)
        assertEquals("OOP", programmingOopCategory.badgeText)
        assertEquals("PERF", performanceCategory.badgeText)
        assertEquals("DATA", dataCategory.badgeText)
        assertEquals("ARCH", architectureCategory.badgeText)
        assertEquals("ROLE", patternRolesCategory.badgeText)
        assertEquals(63, aiMlRagCategory.terms.size)
        assertEquals(35, securityCategory.terms.size)
        assertEquals(104, integrationsCategory.terms.size)
        assertEquals(56, communicationCategory.terms.size)
        assertEquals(32, consistencyCategory.terms.size)
        assertEquals(31, designPatternsCategory.terms.size)
        assertEquals(28, observabilityCategory.terms.size)
        assertEquals(69, programmingOopCategory.terms.size)
        assertEquals(41, performanceCategory.terms.size)
        assertEquals(56, dataCategory.terms.size)
        assertEquals(66, architectureCategory.terms.size)
        assertEquals(73, patternRolesCategory.terms.size)
        assertEquals(654, allTerms.size)
        assertEquals(654, allTerms.map { it.id }.distinct().size)
        assertTrue(categories.all { category -> category.terms.all { it.categoryId == category.id } })
        assertTrue(
            allTerms.all {
                it.titleSr.isNotBlank() &&
                    it.titleEn.isNotBlank() &&
                    it.shortExplanation.isNotBlank()
            }
        )
    }
}
