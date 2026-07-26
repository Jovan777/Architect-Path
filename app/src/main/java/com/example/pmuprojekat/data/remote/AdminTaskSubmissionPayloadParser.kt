package com.example.pmuprojekat.data.remote

import com.example.pmuprojekat.data.repository.AdminDiagramImageContent
import com.example.pmuprojekat.data.repository.AdminTaskBlankContent
import com.example.pmuprojekat.data.repository.AdminTaskOptionContent
import com.example.pmuprojekat.data.repository.AdminTaskQuestionContent
import com.example.pmuprojekat.data.repository.AdminTaskStepContent
import com.example.pmuprojekat.data.repository.AdminTaskZoneContent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminTaskSubmissionPayloadParser @Inject constructor() {

    fun parse(payload: Map<String, Any?>): Result<AdminTaskQuestionContent> = runCatching {
        val question = payload.mapValue("question") ?: payload
        val steps = question.mapList("steps").mapIndexed { index, step ->
            parseStep(step, index)
        }
        require(steps.isNotEmpty()) {
            "Zadatak nema nijedan korak."
        }

        AdminTaskQuestionContent(
            questionIdPattern = question.nonBlankString("questionIdPattern")
                ?: payload.nonBlankString("questionIdPattern"),
            title = question.nonBlankString("title").orEmpty(),
            prompt = question.nonBlankString("prompt").orEmpty(),
            diagramImageName = question.nonBlankString("diagramImageName"),
            diagramImage = question.mapValue("diagramImage")?.let(::parseDiagramImage),
            aiFollowUp = question.nonBlankString("aiFollowUp"),
            internalAiRubric = question.nonBlankString("internalAiRubric"),
            drawingChecklist = question.stringList("drawingChecklist"),
            steps = steps
        )
    }

    private fun parseDiagramImage(value: Map<String, Any?>): AdminDiagramImageContent {
        return AdminDiagramImageContent(
            type = value.nonBlankString("type"),
            originalFileName = value.nonBlankString("originalFileName"),
            mimeType = value.nonBlankString("mimeType"),
            sizeBytes = value.longValue("sizeBytes"),
            localFileName = value.nonBlankString("localFileName"),
            localUri = value.nonBlankString("localUri"),
            localPath = value.nonBlankString("localPath"),
            remoteStoragePath = value.nonBlankString("remoteStoragePath"),
            downloadUrl = value.nonBlankString("downloadUrl")
        )
    }

    private fun parseStep(value: Map<String, Any?>, index: Int): AdminTaskStepContent {
        return AdminTaskStepContent(
            stepId = value.nonBlankString("stepId") ?: "korak_${index + 1}",
            blueprintKey = value.nonBlankString("blueprintKey"),
            type = value.nonBlankString("type").orEmpty(),
            title = value.nonBlankString("title") ?: "Korak ${index + 1}",
            instruction = value.nonBlankString("instruction").orEmpty(),
            requiredCount = value.intValue("requiredCount"),
            codeBlock = value.nonBlankString("codeBlock"),
            explanation = value.nonBlankString("explanation"),
            rendererHint = value.nonBlankString("rendererHint"),
            correctAnswerMode = value.nonBlankString("correctAnswerMode"),
            correctOptionIds = value.stringList("correctOptionIds"),
            zones = value.mapList("zones").mapIndexed { zoneIndex, zone ->
                AdminTaskZoneContent(
                    zoneId = zone.nonBlankString("zoneId") ?: "zona_${zoneIndex + 1}",
                    title = zone.nonBlankString("title") ?: "Zona ${zoneIndex + 1}",
                    order = zone.intValue("zoneOrder")
                )
            },
            options = value.mapList("options").mapIndexed { optionIndex, option ->
                AdminTaskOptionContent(
                    optionId = option.nonBlankString("optionId") ?: "opcija_${optionIndex + 1}",
                    label = option.nonBlankString("label"),
                    text = option.nonBlankString("text").orEmpty(),
                    order = option.intValue("optionOrder"),
                    isCorrect = option.booleanValue("isCorrect") ?: false,
                    correctOrder = option.intValue("correctOrder"),
                    correctZoneId = option.nonBlankString("correctZoneId"),
                    isDistractor = option.booleanValue("isDistractor") ?: false,
                    metadata = option["metadata"].toReadableMetadata()
                )
            },
            blanks = value.mapList("blanks").mapIndexed { blankIndex, blank ->
                AdminTaskBlankContent(
                    blankId = blank.nonBlankString("blankId") ?: "prazno_${blankIndex + 1}",
                    order = blank.intValue("blankOrder"),
                    placeholder = blank.nonBlankString("placeholder").orEmpty(),
                    correctValue = blank.nonBlankString("correctValue")
                        ?: blank.nonBlankString("expectedAnswer")
                        ?: ""
                )
            },
            rubricPoints = value.stringList("rubricPoints")
        )
    }

    private fun Any?.toReadableMetadata(): String? {
        return when (this) {
            null -> null
            is String -> trim().takeIf(String::isNotBlank)
            is Map<*, *> -> entries.joinToString(separator = ", ") { (key, value) ->
                "$key: $value"
            }.takeIf(String::isNotBlank)
            is List<*> -> joinToString(separator = ", ").takeIf(String::isNotBlank)
            else -> toString().takeIf(String::isNotBlank)
        }
    }

    private fun Map<String, Any?>.nonBlankString(key: String): String? {
        return (this[key] as? String)?.trim()?.takeIf(String::isNotBlank)
    }

    private fun Map<String, Any?>.intValue(key: String): Int? {
        return (this[key] as? Number)?.toInt()
    }

    private fun Map<String, Any?>.longValue(key: String): Long? {
        return (this[key] as? Number)?.toLong()
    }

    private fun Map<String, Any?>.booleanValue(key: String): Boolean? {
        return this[key] as? Boolean
    }

    @Suppress("UNCHECKED_CAST")
    private fun Map<String, Any?>.mapValue(key: String): Map<String, Any?>? {
        return this[key] as? Map<String, Any?>
    }

    @Suppress("UNCHECKED_CAST")
    private fun Map<String, Any?>.mapList(key: String): List<Map<String, Any?>> {
        return (this[key] as? List<*>)
            ?.mapNotNull { it as? Map<String, Any?> }
            .orEmpty()
    }

    private fun Map<String, Any?>.stringList(key: String): List<String> {
        return (this[key] as? List<*>)
            ?.mapNotNull { (it as? String)?.trim()?.takeIf(String::isNotBlank) }
            .orEmpty()
    }
}

