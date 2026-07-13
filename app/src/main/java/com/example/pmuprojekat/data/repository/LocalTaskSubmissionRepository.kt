package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.data.local.dao.UserTaskSubmissionDao
import com.example.pmuprojekat.data.local.entity.UserTaskSubmissionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalTaskSubmissionRepository @Inject constructor(
    private val userTaskSubmissionDao: UserTaskSubmissionDao
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

    override suspend fun submitForReview(submission: UserTaskSubmissionEntity) {
        userTaskSubmissionDao.upsertSubmission(
            submission.copy(
                reviewStatus = "PENDING",
                syncStatus = "READY_FOR_UPLOAD",
                localOnly = true,
                isPublic = false,
                updatedAt = System.currentTimeMillis()
            )
        )
    }
}
