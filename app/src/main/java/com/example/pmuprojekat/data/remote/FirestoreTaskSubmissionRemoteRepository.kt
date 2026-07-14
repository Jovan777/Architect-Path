package com.example.pmuprojekat.data.remote

import com.example.pmuprojekat.data.local.entity.UserTaskSubmissionEntity
import com.example.pmuprojekat.data.repository.FirebaseAuthRepository
import com.example.pmuprojekat.data.repository.TaskSubmissionRemoteRepository
import com.google.firebase.firestore.FieldValue
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
            "payload" to submission.payloadJson.toJsonMap(),
            "payloadJson" to submission.payloadJson,
            "syncStatus" to "UPLOADED"
        )

        firestore.collection(TASK_SUBMISSIONS_COLLECTION)
            .document(documentId)
            .set(document)
            .awaitResult()

        documentId
    }

    companion object {
        const val TASK_SUBMISSIONS_COLLECTION = "task_submissions"
    }
}
