package com.example.pmuprojekat.data.remote

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.data.local.entity.CodeBlankEntity
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.QuestionStepEntity
import com.example.pmuprojekat.data.local.entity.StepOptionEntity
import com.example.pmuprojekat.data.local.entity.StepZoneEntity
import com.example.pmuprojekat.data.local.relation.QuestionWithSteps
import com.example.pmuprojekat.data.local.relation.StepWithContent
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteTaskMapper @Inject constructor() {

    fun toPlayableQuestion(document: RemoteTaskDocument): Result<QuestionWithSteps> = runCatching {
        require(document.schemaVersion in 1..2) {
            "Nepodržana verzija udaljenog zadatka: ${document.schemaVersion}."
        }

        val questionPayload = document.payload.mapValue("question") ?: document.payload
        val level = normalizeLevel(
            questionPayload.stringValue("level") ?: document.level
        )
        val type = normalizeQuestionType(
            questionPayload.stringValue("type") ?: document.taskType
        )
        val requestedPublicationMode = normalizePublicationMode(document.publicationMode)
        val isApprovedUserTask =
            requestedPublicationMode == PUBLICATION_USER_TASKS ||
                document.source.equals("USER_APPROVED", ignoreCase = true) ||
                document.source.equals(SOURCE_REMOTE_USER_APPROVED, ignoreCase = true)
        val source = if (isApprovedUserTask) {
            SOURCE_REMOTE_USER_APPROVED
        } else {
            SOURCE_REMOTE_ADMIN
        }
        val publicationMode = if (isApprovedUserTask) {
            PUBLICATION_USER_TASKS
        } else {
            PUBLICATION_MAIN_TASK_LIST
        }
        val questionId = remoteQuestionId(document, source)
        val stepPayloads = questionPayload.mapList("steps")
        val diagramImagePayload = questionPayload.mapValue("diagramImage")
        val rendererStepPrefix = rendererStepPrefix(
            questionPayload.stringValue("questionIdPattern")
                ?: document.payload.stringValue("questionIdPattern")
        )

        require(stepPayloads.isNotEmpty()) {
            "Udaljeni zadatak nema nijedan korak."
        }

        val stepRelations = stepPayloads.mapIndexed { stepIndex, stepPayload ->
            mapStep(
                questionId = questionId,
                stepIndex = stepIndex,
                payload = stepPayload,
                rendererStepPrefix = rendererStepPrefix
            )
        }

        QuestionWithSteps(
            question = QuestionEntity(
                questionId = questionId,
                level = level,
                type = type,
                title = questionPayload.stringValue("title")
                    ?.takeIf(String::isNotBlank)
                    ?: document.title,
                prompt = questionPayload.stringValue("prompt").orEmpty(),
                diagramImageName = questionPayload.stringValue("diagramImageName")
                    ?.takeIf(String::isNotBlank),
                diagramImageLocalUri = diagramImagePayload?.stringValue("localUri")
                    ?.takeIf(String::isNotBlank),
                diagramImageLocalPath = diagramImagePayload?.stringValue("localPath")
                    ?.takeIf(String::isNotBlank),
                diagramImageRemoteStoragePath = diagramImagePayload?.stringValue("remoteStoragePath")
                    ?.takeIf(String::isNotBlank),
                diagramImageDownloadUrl = diagramImagePayload?.stringValue("downloadUrl")
                    ?.takeIf(String::isNotBlank),
                aiFollowUp = questionPayload.stringValue("aiFollowUp")
                    ?.takeIf(String::isNotBlank),
                wave = questionPayload.intValue("wave"),
                difficulty = questionPayload.stringValue("difficulty") ?: "easy",
                orderIndex = questionPayload.intValue("orderIndex") ?: REMOTE_ORDER_INDEX,
                estimatedMinutes = questionPayload.intValue("estimatedMinutes") ?: 3,
                isActive = true,
                topic = questionPayload.stringValue("topic"),
                patternName = questionPayload.stringValue("patternName"),
                source = source,
                publicationMode = publicationMode,
                remoteDocumentPath = "${document.collection}/${document.documentId}",
                remoteUpdatedAt = document.updatedAt ?: 0L
            ),
            steps = stepRelations
        )
    }

    private fun mapStep(
        questionId: String,
        stepIndex: Int,
        payload: Map<String, Any?>,
        rendererStepPrefix: String?
    ): StepWithContent {
        val stepType = normalizeStepType(payload.stringValue("type"))
        val stepId = if (rendererStepPrefix == null) {
            "${questionId}_s${stepIndex + 1}"
        } else {
            "$rendererStepPrefix${questionId}_s${stepIndex + 1}"
        }
        val zonePayloads = payload.mapList("zones")
        val zoneIdsByRemoteId = mutableMapOf<String, String>()
        val zones = zonePayloads.mapIndexed { zoneIndex, zonePayload ->
            val remoteZoneId = zonePayload.stringValue("zoneId") ?: "zone_${zoneIndex + 1}"
            val zoneId = "${stepId}_z${zoneIndex + 1}"
            zoneIdsByRemoteId[remoteZoneId] = zoneId
            StepZoneEntity(
                zoneId = zoneId,
                stepId = stepId,
                title = zonePayload.stringValue("title")
                    ?.takeIf(String::isNotBlank)
                    ?: "Zona ${zoneIndex + 1}",
                zoneOrder = zonePayload.intValue("zoneOrder") ?: zoneIndex + 1
            )
        }

        val options = payload.mapList("options").mapIndexed { optionIndex, optionPayload ->
            val remoteCorrectZoneId = optionPayload.stringValue("correctZoneId")
            StepOptionEntity(
                optionId = "${stepId}_o${optionIndex + 1}",
                stepId = stepId,
                label = optionPayload.stringValue("label")?.takeIf(String::isNotBlank),
                text = optionPayload.stringValue("text").orEmpty(),
                optionOrder = optionPayload.intValue("optionOrder") ?: optionIndex + 1,
                isCorrect = optionPayload.booleanValue("isCorrect") ?: false,
                correctOrder = optionPayload.intValue("correctOrder"),
                correctZoneId = remoteCorrectZoneId?.let(zoneIdsByRemoteId::get),
                isDistractor = optionPayload.booleanValue("isDistractor") ?: false,
                metadata = optionPayload["metadata"].toMetadataString()
            )
        }

        val blanks = payload.mapList("blanks").mapIndexed { blankIndex, blankPayload ->
            CodeBlankEntity(
                blankId = "${stepId}_b${blankIndex + 1}",
                stepId = stepId,
                blankOrder = blankPayload.intValue("blankOrder") ?: blankIndex + 1,
                placeholder = blankPayload.stringValue("placeholder") ?: "__________",
                correctValue = blankPayload.stringValue("correctValue")
                    ?: blankPayload.stringValue("expectedAnswer")
                    ?: ""
            )
        }

        validateStep(stepType, options, zones, blanks)

        val isAutoEvaluated = payload.booleanValue("isAutoEvaluated")
            ?: (stepType != StepType.FREE_TEXT.id && stepType != StepType.MINI_ADR.id)

        return StepWithContent(
            step = QuestionStepEntity(
                stepId = stepId,
                questionId = questionId,
                stepOrder = payload.intValue("stepOrder") ?: stepIndex + 1,
                type = stepType,
                title = payload.stringValue("title")
                    ?.takeIf(String::isNotBlank)
                    ?: "Korak ${stepIndex + 1}",
                instruction = payload.stringValue("instruction").orEmpty(),
                requiredCount = payload.intValue("requiredCount"),
                codeBlock = payload.stringValue("codeBlock")?.takeIf(String::isNotBlank),
                explanation = payload.stringValue("explanation")?.takeIf(String::isNotBlank),
                isRequired = payload.booleanValue("isRequired") ?: true,
                isAutoEvaluated = isAutoEvaluated,
                points = payload.intValue("points") ?: if (isAutoEvaluated) 1 else 0
            ),
            options = options,
            zones = zones,
            blanks = blanks
        )
    }

    private fun validateStep(
        type: String,
        options: List<StepOptionEntity>,
        zones: List<StepZoneEntity>,
        blanks: List<CodeBlankEntity>
    ) {
        when (type) {
            StepType.SINGLE_CHOICE.id,
            StepType.VISUAL_MAPPING.id -> {
                require(options.size >= 2) { "Korak izbora mora imati najmanje dve opcije." }
                require(options.count(StepOptionEntity::isCorrect) == 1) {
                    "Korak sa jednim izborom mora imati tačno jedan odgovor."
                }
            }

            StepType.MULTI_CHOICE.id -> {
                require(options.size >= 2 && options.any(StepOptionEntity::isCorrect)) {
                    "Korak sa više izbora mora imati opcije i najmanje jedan tačan odgovor."
                }
            }

            StepType.ORDERED_CARDS.id -> require(
                options.count { it.correctOrder != null } >= 2
            ) { "Korak redosleda mora imati najmanje dve uređene kartice." }

            StepType.CATEGORIZATION.id,
            StepType.ROLE_MAPPING.id -> {
                require(zones.size >= 2) { "Mapiranje mora imati najmanje dve zone." }
                require(options.isNotEmpty() && options.all { it.isDistractor || it.correctZoneId != null }) {
                    "Svaka kartica mapiranja mora imati ciljnu zonu ili biti zamka."
                }
            }

            StepType.CODE_COMPLETION.id -> require(
                blanks.isNotEmpty() && blanks.all { it.correctValue.isNotBlank() }
            ) { "Dopuna koda mora imati prazna mesta sa očekivanim odgovorima." }
        }
    }

    private fun normalizeLevel(value: String): String {
        return LearningLevel.entries.firstOrNull {
            it.id.equals(value, ignoreCase = true) || it.name.equals(value, ignoreCase = true)
        }?.id ?: error("Nepodržan nivo udaljenog zadatka: $value.")
    }

    private fun normalizeQuestionType(value: String): String {
        return QuestionType.entries.firstOrNull {
            it.id.equals(value, ignoreCase = true) || it.name.equals(value, ignoreCase = true)
        }?.id ?: error("Nepodržan tip udaljenog zadatka: $value.")
    }

    private fun normalizeStepType(value: String?): String {
        return StepType.entries.firstOrNull {
            it.id.equals(value, ignoreCase = true) || it.name.equals(value, ignoreCase = true)
        }?.id ?: error("Nepodržan tip koraka udaljenog zadatka: $value.")
    }

    private fun normalizePublicationMode(value: String): String {
        return when (value.uppercase()) {
            PUBLICATION_MAIN_TASK_LIST -> PUBLICATION_MAIN_TASK_LIST
            PUBLICATION_USER_TASKS -> PUBLICATION_USER_TASKS
            else -> error("Nepodržan način objavljivanja: $value.")
        }
    }

    private fun remoteQuestionId(document: RemoteTaskDocument, source: String): String {
        val safeDocumentId = document.documentId
            .lowercase()
            .replace(Regex("[^a-z0-9_]+"), "_")
            .trim('_')
        return if (source == SOURCE_REMOTE_USER_APPROVED) {
            "remote_user_$safeDocumentId"
        } else {
            "remote_$safeDocumentId"
        }
    }

    private fun rendererStepPrefix(questionIdPattern: String?): String? {
        val match = RENDERER_PREFIX.find(questionIdPattern.orEmpty().trim().uppercase())
            ?: return null
        return "${match.groupValues[1]}."
    }

    private fun Any?.toMetadataString(): String? = when (this) {
        null -> null
        is String -> takeIf(String::isNotBlank)
        is Map<*, *> -> JSONObject(this).toString()
        else -> toString()
    }

    private fun Map<String, Any?>.stringValue(key: String): String? = this[key] as? String
    private fun Map<String, Any?>.intValue(key: String): Int? = (this[key] as? Number)?.toInt()
    private fun Map<String, Any?>.booleanValue(key: String): Boolean? = this[key] as? Boolean

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

    companion object {
        const val SOURCE_REMOTE_ADMIN = "REMOTE_ADMIN"
        const val SOURCE_REMOTE_USER_APPROVED = "REMOTE_USER_APPROVED"
        const val PUBLICATION_MAIN_TASK_LIST = "MAIN_TASK_LIST"
        const val PUBLICATION_USER_TASKS = "USER_TASKS"
        private const val REMOTE_ORDER_INDEX = 10_000
        private val RENDERER_PREFIX = Regex("^([PJMSA][1-7])\\.")
    }
}
