package com.example.pmuprojekat.core.learning

import java.time.LocalDate

data class StoredLearningContinuity(
    val currentStreak: Int,
    val longestStreak: Int,
    val lastQualifyingTaskDate: String?,
    val dailyCompletionDate: String?,
    val tasksCompletedToday: Int
)

data class LearningContinuityUpdate(
    val currentStreak: Int,
    val longestStreak: Int,
    val lastQualifyingTaskDate: String,
    val dailyCompletionDate: String,
    val tasksCompletedToday: Int
)

object StreakCalculator {

    fun recordQualifyingCompletion(
        state: StoredLearningContinuity,
        today: LocalDate
    ): LearningContinuityUpdate {
        val previousDate = state.lastQualifyingTaskDate.toLocalDateOrNull()

        val updatedCurrentStreak = when {
            previousDate == null -> 1
            previousDate == today -> state.currentStreak.coerceAtLeast(1)
            previousDate == today.minusDays(1) ->
                state.currentStreak.coerceAtLeast(1) + 1
            previousDate.isAfter(today) -> state.currentStreak.coerceAtLeast(1)
            else -> 1
        }

        val updatedDailyCount = if (state.dailyCompletionDate.toLocalDateOrNull() == today) {
            state.tasksCompletedToday.coerceAtLeast(0) + 1
        } else {
            1
        }

        return LearningContinuityUpdate(
            currentStreak = updatedCurrentStreak,
            longestStreak = maxOf(state.longestStreak, updatedCurrentStreak),
            lastQualifyingTaskDate = today.toString(),
            dailyCompletionDate = today.toString(),
            tasksCompletedToday = updatedDailyCount
        )
    }

    fun activeStreak(
        currentStreak: Int,
        lastQualifyingTaskDate: String?,
        today: LocalDate
    ): Int {
        val lastDate = lastQualifyingTaskDate.toLocalDateOrNull() ?: return 0
        return if (!lastDate.isBefore(today.minusDays(1))) {
            currentStreak.coerceAtLeast(0)
        } else {
            0
        }
    }

    fun tasksCompletedOnDate(
        dailyCompletionDate: String?,
        storedCount: Int,
        date: LocalDate
    ): Int {
        return if (dailyCompletionDate.toLocalDateOrNull() == date) {
            storedCount.coerceAtLeast(0)
        } else {
            0
        }
    }

    private fun String?.toLocalDateOrNull(): LocalDate? {
        if (this.isNullOrBlank()) return null
        return runCatching { LocalDate.parse(this) }.getOrNull()
    }
}
