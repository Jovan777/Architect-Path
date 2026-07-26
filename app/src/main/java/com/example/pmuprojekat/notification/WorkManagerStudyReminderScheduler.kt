package com.example.pmuprojekat.notification

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.pmuprojekat.core.learning.LearningTimeProvider
import com.example.pmuprojekat.data.repository.StudyReminderRepository
import com.example.pmuprojekat.data.repository.StudyReminderScheduler
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkManagerStudyReminderScheduler @Inject constructor(
    @ApplicationContext context: Context,
    private val timeProvider: LearningTimeProvider
) : StudyReminderScheduler {
    private val workManager = WorkManager.getInstance(context)

    override fun scheduleAfterQualifyingCompletion(completedAtMillis: Long) {
        enqueue(
            expectedCompletionAtMillis = completedAtMillis,
            initialDelayMillis = REMINDER_DELAY_MILLIS,
            policy = ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE
        )
    }

    override fun rescheduleForLearningGoal(lastCompletionAtMillis: Long?) {
        val now = timeProvider.nowMillis()
        val initialDelay = lastCompletionAtMillis
            ?.let { completion ->
                (completion + REMINDER_DELAY_MILLIS - now)
                    .coerceAtLeast(MINIMUM_RESCHEDULE_DELAY_MILLIS)
            }
            ?: REMINDER_DELAY_MILLIS

        enqueue(
            expectedCompletionAtMillis =
                lastCompletionAtMillis ?: StudyReminderRepository.NO_COMPLETION,
            initialDelayMillis = initialDelay,
            policy = ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE
        )
    }

    override fun ensureScheduled(lastCompletionAtMillis: Long?) {
        val now = timeProvider.nowMillis()
        val initialDelay = lastCompletionAtMillis
            ?.let { completion ->
                (completion + REMINDER_DELAY_MILLIS - now)
                    .coerceAtLeast(MINIMUM_RESCHEDULE_DELAY_MILLIS)
            }
            ?: REMINDER_DELAY_MILLIS

        enqueue(
            expectedCompletionAtMillis =
                lastCompletionAtMillis ?: StudyReminderRepository.NO_COMPLETION,
            initialDelayMillis = initialDelay,
            policy = ExistingPeriodicWorkPolicy.KEEP
        )
    }

    private fun enqueue(
        expectedCompletionAtMillis: Long,
        initialDelayMillis: Long,
        policy: ExistingPeriodicWorkPolicy
    ) {
        val request = PeriodicWorkRequestBuilder<StudyReminderWorker>(
            REPEAT_INTERVAL_HOURS,
            TimeUnit.HOURS
        )
            .setInitialDelay(initialDelayMillis, TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    StudyReminderWorker.EXPECTED_COMPLETION_AT_KEY to
                        expectedCompletionAtMillis
                )
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            policy,
            request
        )
    }

    companion object {
        const val UNIQUE_WORK_NAME = "study_streak_reminder"
        private const val REPEAT_INTERVAL_HOURS = 24L
        private val REMINDER_DELAY_MILLIS = TimeUnit.HOURS.toMillis(20)
        private val MINIMUM_RESCHEDULE_DELAY_MILLIS = TimeUnit.MINUTES.toMillis(15)
    }
}
