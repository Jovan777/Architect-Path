package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.data.local.entity.UserTaskSubmissionEntity
import kotlinx.coroutines.flow.Flow

interface TaskSubmissionRepository {
    fun observeLocalSubmissions(): Flow<List<UserTaskSubmissionEntity>>
    suspend fun getSubmissionById(submissionId: String): UserTaskSubmissionEntity?
    suspend fun saveDraft(submission: UserTaskSubmissionEntity)
    suspend fun submitForReview(submission: UserTaskSubmissionEntity): SubmissionSyncResult
}

sealed interface SubmissionSyncResult {
    data class Uploaded(val remoteSubmissionId: String) : SubmissionSyncResult
    data class AttachmentUploadFailed(val cause: Throwable?) : SubmissionSyncResult
    data class SavedLocally(val cause: Throwable?) : SubmissionSyncResult
}

class TaskAttachmentUploadException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)
