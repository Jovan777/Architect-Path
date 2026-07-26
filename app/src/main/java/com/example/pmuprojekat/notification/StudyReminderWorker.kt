package com.example.pmuprojekat.notification

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pmuprojekat.data.repository.StudyReminderRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class StudyReminderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val reminderRepository: StudyReminderRepository
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        val expectedCompletionAt = inputData.getLong(
            EXPECTED_COMPLETION_AT_KEY,
            StudyReminderRepository.NO_COMPLETION
        )

        return runCatching {
            reminderRepository.showReminderIfNeeded(expectedCompletionAt)
            Result.success()
        }.getOrElse { error ->
            Log.w(TAG, "Study reminder check failed", error)
            Result.retry()
        }
    }

    companion object {
        const val EXPECTED_COMPLETION_AT_KEY = "expected_completion_at"
        private const val TAG = "StudyReminderWorker"
    }
}
