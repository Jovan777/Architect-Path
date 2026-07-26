package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.data.local.relation.QuestionWithSteps

enum class AdminSubmissionStatus {
    PENDING,
    APPROVED,
    PROMOTED,
    REJECTED,
    UNKNOWN;

    companion object {
        fun from(reviewStatus: String, promotedToRemoteTaskId: String?): AdminSubmissionStatus {
            if (!promotedToRemoteTaskId.isNullOrBlank()) return PROMOTED
            return when (reviewStatus.uppercase()) {
                "PENDING" -> PENDING
                "APPROVED" -> APPROVED
                "REJECTED" -> REJECTED
                else -> UNKNOWN
            }
        }
    }
}

enum class AdminSubmissionFilter(val label: String) {
    PENDING("Na čekanju"),
    APPROVED("Odobreni"),
    PROMOTED("Promovisani"),
    REJECTED("Odbijeni"),
    ALL("Svi");

    fun accepts(status: AdminSubmissionStatus): Boolean {
        return this == ALL || name == status.name
    }
}

data class AdminTaskSubmissionSummary(
    val submissionId: String,
    val title: String,
    val level: String,
    val taskType: String,
    val templateId: String?,
    val submitterDisplayName: String,
    val createdAt: Long?,
    val updatedAt: Long?,
    val reviewStatus: String,
    val isPublic: Boolean,
    val promotedToRemoteTaskId: String?
) {
    val moderationStatus: AdminSubmissionStatus
        get() = AdminSubmissionStatus.from(reviewStatus, promotedToRemoteTaskId)
}

data class AdminDiagramImageContent(
    val type: String?,
    val originalFileName: String?,
    val mimeType: String?,
    val sizeBytes: Long?,
    val localFileName: String?,
    val localUri: String?,
    val localPath: String?,
    val remoteStoragePath: String?,
    val downloadUrl: String?
) {
    val hasPortableSource: Boolean
        get() = !downloadUrl.isNullOrBlank() || !remoteStoragePath.isNullOrBlank()
}

data class AdminTaskZoneContent(
    val zoneId: String,
    val title: String,
    val order: Int?
)

data class AdminTaskOptionContent(
    val optionId: String,
    val label: String?,
    val text: String,
    val order: Int?,
    val isCorrect: Boolean,
    val correctOrder: Int?,
    val correctZoneId: String?,
    val isDistractor: Boolean,
    val metadata: String?
)

data class AdminTaskBlankContent(
    val blankId: String,
    val order: Int?,
    val placeholder: String,
    val correctValue: String
)

data class AdminTaskStepContent(
    val stepId: String,
    val blueprintKey: String?,
    val type: String,
    val title: String,
    val instruction: String,
    val requiredCount: Int?,
    val codeBlock: String?,
    val explanation: String?,
    val rendererHint: String?,
    val correctAnswerMode: String?,
    val correctOptionIds: List<String>,
    val zones: List<AdminTaskZoneContent>,
    val options: List<AdminTaskOptionContent>,
    val blanks: List<AdminTaskBlankContent>,
    val rubricPoints: List<String>
)

data class AdminTaskQuestionContent(
    val questionIdPattern: String?,
    val title: String,
    val prompt: String,
    val diagramImageName: String?,
    val diagramImage: AdminDiagramImageContent?,
    val aiFollowUp: String?,
    val internalAiRubric: String?,
    val drawingChecklist: List<String>,
    val steps: List<AdminTaskStepContent>
)

data class AdminTaskSubmissionDetail(
    val summary: AdminTaskSubmissionSummary,
    val question: AdminTaskQuestionContent?,
    val playableQuestion: QuestionWithSteps?,
    val contentError: String?
) {
    val requiresPortableImage: Boolean
        get() = summary.templateId == "architect_a3_review" ||
            question?.questionIdPattern?.startsWith("A3.") == true

    val hasPortableImage: Boolean
        get() = question?.diagramImage?.hasPortableSource == true

    val portableImageWarning: String?
        get() = if (requiresPortableImage && !hasPortableImage) {
            "Slika zadatka nije dostupna drugim uređajima."
        } else {
            null
        }
}

enum class AdminModerationAction {
    APPROVE,
    PROMOTE,
    REJECT
}

data class AdminModerationOutcome(
    val action: AdminModerationAction,
    val submissionId: String,
    val remoteTaskId: String? = null,
    val alreadyApplied: Boolean = false
)

class AdminTaskImageUnavailableException :
    Exception("Slika zadatka nije dostupna drugim uređajima.")

class AdminTaskPromotionConflictException :
    Exception("Ciljni zvanični zadatak već postoji i pripada drugom predlogu.")

