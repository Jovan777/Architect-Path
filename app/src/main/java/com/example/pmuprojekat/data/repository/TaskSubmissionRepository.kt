package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.data.local.entity.UserTaskSubmissionEntity
import kotlinx.coroutines.flow.Flow

interface TaskSubmissionRepository {
    fun observeLocalSubmissions(): Flow<List<UserTaskSubmissionEntity>>
    suspend fun getSubmissionById(submissionId: String): UserTaskSubmissionEntity?
    suspend fun saveDraft(submission: UserTaskSubmissionEntity)
    suspend fun submitForReview(submission: UserTaskSubmissionEntity)
}
