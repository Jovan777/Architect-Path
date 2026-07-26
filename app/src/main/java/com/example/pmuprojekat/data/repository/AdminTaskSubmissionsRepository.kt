package com.example.pmuprojekat.data.repository

interface AdminTaskSubmissionsRepository {
    suspend fun fetchSubmissions(): Result<List<AdminTaskSubmissionSummary>>

    suspend fun fetchSubmission(submissionId: String): Result<AdminTaskSubmissionDetail>

    suspend fun approveAsUserTask(submissionId: String): Result<AdminModerationOutcome>

    suspend fun promoteToOfficialTask(submissionId: String): Result<AdminModerationOutcome>

    suspend fun rejectSubmission(
        submissionId: String,
        rejectionReason: String?
    ): Result<AdminModerationOutcome>
}

