package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.data.local.dao.UserTaskSubmissionDao
import com.example.pmuprojekat.data.local.entity.UserTaskSubmissionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalTaskSubmissionRepository @Inject constructor(
    private val userTaskSubmissionDao: UserTaskSubmissionDao,
    private val remoteRepository: TaskSubmissionRemoteRepository
) : TaskSubmissionRepository {

    override fun observeLocalSubmissions(): Flow<List<UserTaskSubmissionEntity>> {
        return userTaskSubmissionDao.observeSubmissions()
    }

    override suspend fun getSubmissionById(submissionId: String): UserTaskSubmissionEntity? {
        return userTaskSubmissionDao.getSubmissionById(submissionId)
    }

    override suspend fun saveDraft(submission: UserTaskSubmissionEntity) {
        userTaskSubmissionDao.upsertSubmission(
            submission.copy(
                reviewStatus = "DRAFT",
                syncStatus = "LOCAL_ONLY",
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun submitForReview(
        submission: UserTaskSubmissionEntity
    ): SubmissionSyncResult {
        val pending = submission.copy(
            reviewStatus = "PENDING",
            syncStatus = "READY_FOR_UPLOAD",
            localOnly = true,
            isPublic = false,
            updatedAt = System.currentTimeMillis()
        )
        userTaskSubmissionDao.upsertSubmission(pending)

        val remoteResult = remoteRepository.submitForReview(pending)
        return remoteResult.fold(
            onSuccess = { remoteId ->
                userTaskSubmissionDao.upsertSubmission(
                    pending.copy(
                        syncStatus = "UPLOADED",
                        localOnly = false,
                        remoteSubmissionId = remoteId,
                        updatedAt = System.currentTimeMillis()
                    )
                )
                SubmissionSyncResult.Uploaded(remoteId)
            },
            onFailure = { error ->
                userTaskSubmissionDao.upsertSubmission(
                    pending.copy(
                        syncStatus = "FAILED",
                        localOnly = true,
                        updatedAt = System.currentTimeMillis()
                    )
                )
                if (error is TaskAttachmentUploadException) {
                    SubmissionSyncResult.AttachmentUploadFailed(error)
                } else {
                    SubmissionSyncResult.SavedLocally(error)
                }
            }
        )
    }
}
