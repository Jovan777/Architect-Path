package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.core.learning.LearningTimeProvider
import com.example.pmuprojekat.core.learning.StreakCalculator
import com.example.pmuprojekat.core.learning.StudyReminderContext
import com.example.pmuprojekat.core.learning.StudyReminderPolicy
import com.example.pmuprojekat.data.local.dao.UserDao
import com.example.pmuprojekat.notification.StudyNotificationManager
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudyReminderRepository @Inject constructor(
    private val userDao: UserDao,
    private val timeProvider: LearningTimeProvider,
    private val notificationManager: StudyNotificationManager
) {

    suspend fun showReminderIfNeeded(expectedCompletionAtMillis: Long): Boolean {
        val user = userDao.getActiveUser() ?: return false
        val actualCompletionAt = user.lastQualifyingTaskCompletedAt ?: NO_COMPLETION
        if (actualCompletionAt != expectedCompletionAtMillis) return false

        val today = timeProvider.localDate()
        val currentStreak = StreakCalculator.activeStreak(
            currentStreak = user.streakDays,
            lastQualifyingTaskDate = user.lastQualifyingTaskDate,
            today = today
        )
        val tasksCompletedToday = StreakCalculator.tasksCompletedOnDate(
            dailyCompletionDate = user.dailyCompletionDate,
            storedCount = user.tasksCompletedToday,
            date = today
        )

        if (
            currentStreak != user.streakDays ||
            tasksCompletedToday != user.tasksCompletedToday
        ) {
            userDao.normalizeLearningContinuity(
                userId = LearningRepository.LOCAL_USER_ID,
                currentStreak = currentStreak,
                dailyCompletionDate = user.dailyCompletionDate.takeIf {
                    tasksCompletedToday > 0
                },
                tasksCompletedToday = tasksCompletedToday
            )
        }

        val reminder = StudyReminderPolicy.reminderFor(
            learningGoal = user.learningGoal,
            context = StudyReminderContext(
                today = today,
                currentStreak = currentStreak,
                tasksCompletedToday = tasksCompletedToday,
                waveCompletedThisWeek = wasWaveCompletedThisWeek(
                    lastCompletedWaveDate = user.lastCompletedWaveDate,
                    today = today
                ),
                lastNotificationDate = user.lastReminderNotificationDate
            )
        ) ?: return false

        if (!notificationManager.canPostNotifications()) return false

        val todayKey = today.toString()
        val claimed = userDao.claimReminderNotificationDate(
            userId = LearningRepository.LOCAL_USER_ID,
            notificationDate = todayKey
        )
        if (claimed != 1) return false

        return runCatching {
            notificationManager.showReminder(reminder)
            true
        }.getOrElse { error ->
            userDao.releaseReminderNotificationDate(
                userId = LearningRepository.LOCAL_USER_ID,
                notificationDate = todayKey
            )
            throw error
        }
    }

    private fun wasWaveCompletedThisWeek(
        lastCompletedWaveDate: String?,
        today: LocalDate
    ): Boolean {
        val completionDate = lastCompletedWaveDate
            ?.takeIf(String::isNotBlank)
            ?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
            ?: return false
        val weekStart = today.with(
            TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
        )
        return !completionDate.isBefore(weekStart) && !completionDate.isAfter(today)
    }

    companion object {
        const val NO_COMPLETION = -1L
    }
}
