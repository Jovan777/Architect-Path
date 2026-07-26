package com.example.pmuprojekat.data.remote

import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.taskcreation.TaskCreationStepBlueprint
import com.example.pmuprojekat.taskcreation.TaskCreationTemplateRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AdminTaskSubmissionPayloadParserTest {
    private val parser = AdminTaskSubmissionPayloadParser()
    private val mapper = RemoteTaskMapper()

    @Test
    fun all25TaskTemplates_parseAndMapToExistingRendererModel() {
        val templates = TaskCreationTemplateRegistry.templates

        assertEquals(25, templates.size)

        templates.forEachIndexed { index, template ->
            val payload = payloadForTemplate(template.templateId)
            val parsed = parser.parse(payload)
            val mapped = mapper.toPlayableQuestion(
                RemoteTaskDocument(
                    documentId = "submission_$index",
                    collection = "task_submissions",
                    title = template.displayName,
                    level = template.level,
                    taskType = template.taskType,
                    source = "USER_APPROVED",
                    status = "ACTIVE",
                    publicationMode = "USER_TASKS",
                    schemaVersion = 2,
                    updatedAt = 1L,
                    payload = payload
                )
            )

            assertTrue("${template.templateId} nije parsiran", parsed.isSuccess)
            assertTrue("${template.templateId} nije mapiran", mapped.isSuccess)
            assertEquals(template.steps.size, parsed.getOrThrow().steps.size)
            assertEquals(template.steps.size, mapped.getOrThrow().steps.size)

            val expectedRendererPrefix = template.questionIdPattern.substringBefore('x')
            assertTrue(
                "${template.templateId} nema renderer prefiks $expectedRendererPrefix",
                mapped.getOrThrow().steps.all {
                    it.step.stepId.startsWith(expectedRendererPrefix)
                }
            )
        }
    }

    @Test
    fun a3DiagramMetadata_isPreservedForAdminPreviewAndPlayableTask() {
        val payload = payloadForTemplate("architect_a3_review")
        val parsed = parser.parse(payload).getOrThrow()
        val mapped = mapper.toPlayableQuestion(
            RemoteTaskDocument(
                documentId = "a3_submission",
                collection = "task_submissions",
                title = "A3 pregled",
                level = "architect",
                taskType = "architecture_review",
                source = "USER_APPROVED",
                status = "ACTIVE",
                publicationMode = "USER_TASKS",
                schemaVersion = 2,
                updatedAt = 1L,
                payload = payload
            )
        ).getOrThrow()

        assertEquals(
            "task_submission_attachments/a3/diagram.png",
            parsed.diagramImage?.remoteStoragePath
        )
        assertEquals(
            "https://example.test/diagram.png",
            mapped.question.diagramImageDownloadUrl
        )
        assertNotNull(parsed.diagramImage)
    }

    private fun payloadForTemplate(templateId: String): Map<String, Any?> {
        val template = requireNotNull(
            TaskCreationTemplateRegistry.templateById(templateId)
        )
        val question = mutableMapOf<String, Any?>(
            "questionIdPattern" to template.questionIdPattern,
            "level" to template.level,
            "type" to template.taskType,
            "title" to template.displayName,
            "prompt" to "Scenario za proveru šablona.",
            "aiFollowUp" to "Objasni odluku.",
            "internalAiRubric" to "Proveri granice odgovornosti.",
            "drawingChecklist" to listOf("Komponente", "Tok podataka"),
            "steps" to template.steps.mapIndexed(::stepPayload)
        )

        if (template.templateId == "architect_a3_review") {
            question["diagramImage"] = mapOf(
                "type" to "USER_SELECTED_IMAGE",
                "originalFileName" to "diagram.png",
                "mimeType" to "image/png",
                "sizeBytes" to 1024L,
                "remoteStoragePath" to
                    "task_submission_attachments/a3/diagram.png",
                "downloadUrl" to "https://example.test/diagram.png"
            )
        }

        return mapOf(
            "schemaVersion" to 2,
            "templateId" to template.templateId,
            "questionIdPattern" to template.questionIdPattern,
            "question" to question
        )
    }

    private fun stepPayload(
        index: Int,
        blueprint: TaskCreationStepBlueprint
    ): Map<String, Any?> {
        val zoneCount = blueprint.minZones.coerceAtLeast(2)
        val zones = if (
            blueprint.type == StepType.CATEGORIZATION ||
            blueprint.type == StepType.ROLE_MAPPING
        ) {
            List(zoneCount) { zoneIndex ->
                mapOf(
                    "zoneId" to "z${zoneIndex + 1}",
                    "title" to "Zona ${zoneIndex + 1}",
                    "zoneOrder" to zoneIndex + 1
                )
            }
        } else {
            emptyList()
        }

        val options = when (blueprint.type) {
            StepType.SINGLE_CHOICE,
            StepType.VISUAL_MAPPING,
            StepType.MULTI_CHOICE,
            StepType.HOTSPOT -> List(blueprint.minOptions.coerceAtLeast(2)) {
                optionIndex ->
                mapOf(
                    "optionId" to "o${optionIndex + 1}",
                    "label" to ('A'.code + optionIndex).toChar().toString(),
                    "text" to "Opcija ${optionIndex + 1}",
                    "optionOrder" to optionIndex + 1,
                    "isCorrect" to (optionIndex == 0),
                    "isDistractor" to false
                )
            }
            StepType.ORDERED_CARDS -> List(3) { optionIndex ->
                mapOf(
                    "optionId" to "o${optionIndex + 1}",
                    "text" to "Korak ${optionIndex + 1}",
                    "optionOrder" to optionIndex + 1,
                    "correctOrder" to optionIndex + 1,
                    "isCorrect" to false,
                    "isDistractor" to false
                )
            }
            StepType.CATEGORIZATION,
            StepType.ROLE_MAPPING -> List(blueprint.minOptions.coerceAtLeast(2)) {
                optionIndex ->
                mapOf(
                    "optionId" to "o${optionIndex + 1}",
                    "text" to "Stavka ${optionIndex + 1}",
                    "optionOrder" to optionIndex + 1,
                    "correctZoneId" to "z${(optionIndex % zoneCount) + 1}",
                    "isCorrect" to false,
                    "isDistractor" to false
                )
            }
            else -> emptyList()
        }

        val blanks = if (blueprint.type == StepType.CODE_COMPLETION) {
            listOf(
                mapOf(
                    "blankId" to "b1",
                    "blankOrder" to 1,
                    "placeholder" to "dopuna",
                    "correctValue" to "ispravna_dopuna"
                )
            )
        } else {
            emptyList()
        }

        return mapOf(
            "stepId" to "user_step_${index + 1}",
            "blueprintKey" to blueprint.key,
            "type" to blueprint.type.id,
            "title" to blueprint.title,
            "instruction" to blueprint.instruction,
            "requiredCount" to 1,
            "codeBlock" to if (blueprint.requiresCodeBlock) {
                "if (uslov) { ____ }"
            } else {
                ""
            },
            "explanation" to "Objašnjenje koraka.",
            "rendererHint" to blueprint.rendererHint.name,
            "correctAnswerMode" to blueprint.defaultCorrectAnswerMode?.name,
            "correctOptionIds" to listOf("o1"),
            "zones" to zones,
            "options" to options,
            "blanks" to blanks,
            "rubricPoints" to listOf("Jasno obrazloženje")
        )
    }
}
