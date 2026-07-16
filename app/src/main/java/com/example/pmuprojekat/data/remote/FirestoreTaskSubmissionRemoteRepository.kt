package com.example.pmuprojekat.data.remote

import android.net.Uri
import android.util.Log
import com.example.pmuprojekat.data.local.entity.UserTaskSubmissionEntity
import com.example.pmuprojekat.data.repository.FirebaseAuthRepository
import com.example.pmuprojekat.data.repository.TaskAttachmentUploadException
import com.example.pmuprojekat.data.repository.TaskSubmissionRemoteRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.storage.StorageMetadata
import org.json.JSONObject
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreTaskSubmissionRemoteRepository @Inject constructor(
    private val clients: FirebaseClientProvider,
    private val authRepository: FirebaseAuthRepository
) : TaskSubmissionRemoteRepository {

    override suspend fun submitForReview(
        submission: UserTaskSubmissionEntity
    ): Result<String> = runCatching {
        val userId = authRepository.ensureSignedInAnonymously().getOrThrow()
        val firestore = clients.firestore().getOrThrow()
        val documentId = submission.id
        val payloadJson = uploadRequiredDiagramImage(
            submission = submission,
            userId = userId
        ).toString()
        val document = mapOf(
            "id" to documentId,
            "title" to submission.title,
            "level" to submission.level.uppercase(),
            "taskType" to submission.taskType,
            "templateId" to submission.templateId,
            "createdByRole" to "USER",
            "createdByUserId" to userId,
            "reviewStatus" to "PENDING",
            "isPublic" to false,
            "publicationMode" to "USER_TASKS",
            "schemaVersion" to submission.schemaVersion,
            "createdAt" to FieldValue.serverTimestamp(),
            "updatedAt" to FieldValue.serverTimestamp(),
            "approvedAt" to null,
            "approvedBy" to null,
            "rejectionReason" to null,
            "payload" to payloadJson.toJsonMap(),
            "payloadJson" to payloadJson,
            "syncStatus" to "UPLOADED"
        )

        firestore.collection(TASK_SUBMISSIONS_COLLECTION)
            .document(documentId)
            .set(document)
            .awaitResult()

        documentId
    }

    private suspend fun uploadRequiredDiagramImage(
        submission: UserTaskSubmissionEntity,
        userId: String
    ): JSONObject {
        val payload = JSONObject(submission.payloadJson)
        val question = payload.optJSONObject("question")
        val requiresDiagram = submission.templateId == A3_TEMPLATE_ID ||
            question?.nonBlankString("questionIdPattern")?.startsWith("A3.") == true
        if (!requiresDiagram) return payload

        val diagramImage = question?.optJSONObject("diagramImage")
            ?: throw TaskAttachmentUploadException(IMAGE_UPLOAD_ERROR)
        if (diagramImage.nonBlankString("downloadUrl") != null) return payload

        val localFile = diagramImage.resolveLocalFile()
            ?.takeIf(File::isFile)
            ?: throw TaskAttachmentUploadException(IMAGE_UPLOAD_ERROR)
        if (localFile.length() <= 0L || localFile.length() > MAX_IMAGE_SIZE_BYTES) {
            throw TaskAttachmentUploadException(IMAGE_UPLOAD_ERROR)
        }

        val mimeType = diagramImage.nonBlankString("mimeType")
            ?.takeIf(SUPPORTED_IMAGE_TYPES::contains)
            ?: throw TaskAttachmentUploadException(IMAGE_UPLOAD_ERROR)
        val safeFileName = diagramImage.nonBlankString("localFileName")
            ?.substringAfterLast('/')
            ?.replace(Regex("[^A-Za-z0-9._-]"), "_")
            ?.takeIf(String::isNotBlank)
            ?: localFile.name.replace(Regex("[^A-Za-z0-9._-]"), "_")
        val storagePath = "task_submission_attachments/${submission.id}/diagram/$safeFileName"

        val storageReference = runCatching {
            clients.storage().getOrThrow().reference.child(storagePath)
        }.getOrElse { error ->
            Log.w(TAG, "Firebase Storage nije dostupan za A3 prilog.", error)
            throw TaskAttachmentUploadException(IMAGE_UPLOAD_ERROR, error)
        }

        val metadata = StorageMetadata.Builder()
            .setContentType(mimeType)
            .setCustomMetadata("ownerUid", userId)
            .setCustomMetadata("submissionId", submission.id)
            .build()

        val downloadUrl = runCatching {
            storageReference.putFile(Uri.fromFile(localFile), metadata).awaitResult()
            storageReference.downloadUrl.awaitResult().toString()
        }.getOrElse { error ->
            Log.w(TAG, "Otpremanje A3 dijagrama nije uspelo.", error)
            throw TaskAttachmentUploadException(IMAGE_UPLOAD_ERROR, error)
        }

        diagramImage
            .put("remoteStoragePath", storagePath)
            .put("downloadUrl", downloadUrl)
        question.put("diagramImage", diagramImage)
        payload.put("question", question)
        return payload
    }

    private fun JSONObject.resolveLocalFile(): File? {
        nonBlankString("localPath")?.let { path ->
            File(path).takeIf(File::exists)?.let { return it }
        }
        val uri = nonBlankString("localUri")?.let(Uri::parse) ?: return null
        return uri.path?.let(::File)?.takeIf(File::exists)
    }

    private fun JSONObject.nonBlankString(key: String): String? {
        return opt(key)
            ?.takeUnless { it == JSONObject.NULL }
            ?.let { it as? String }
            ?.trim()
            ?.takeIf(String::isNotBlank)
    }

    companion object {
        const val TASK_SUBMISSIONS_COLLECTION = "task_submissions"
        private const val TAG = "TaskSubmissionRemote"
        private const val A3_TEMPLATE_ID = "architect_a3_review"
        private const val MAX_IMAGE_SIZE_BYTES = 10L * 1024L * 1024L
        private const val IMAGE_UPLOAD_ERROR =
            "Slika dijagrama nije uspešno otpremljena. Proveri internet vezu i pokušaj ponovo."
        private val SUPPORTED_IMAGE_TYPES = setOf("image/png", "image/jpeg", "image/webp")
    }
}
