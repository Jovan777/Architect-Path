package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.data.local.entity.UserTaskSubmissionEntity

interface TaskSubmissionRemoteRepository {
    suspend fun submitForReview(submission: UserTaskSubmissionEntity): Result<String>
}
