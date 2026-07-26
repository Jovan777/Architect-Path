package com.example.pmuprojekat.core.learning

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class StreakCalculatorTest {

    private val today = LocalDate.of(2026, 7, 27)

    @Test
    fun `first completion starts streak at one`() {
        val result = StreakCalculator.recordQualifyingCompletion(
            state = emptyState(),
            today = today
        )

        assertEquals(1, result.currentStreak)
        assertEquals(1, result.longestStreak)
        assertEquals(today.toString(), result.lastQualifyingTaskDate)
        assertEquals(1, result.tasksCompletedToday)
    }

    @Test
    fun `additional completion on same day does not increase streak`() {
        val result = StreakCalculator.recordQualifyingCompletion(
            state = StoredLearningContinuity(
                currentStreak = 4,
                longestStreak = 6,
                lastQualifyingTaskDate = today.toString(),
                dailyCompletionDate = today.toString(),
                tasksCompletedToday = 1
            ),
            today = today
        )

        assertEquals(4, result.currentStreak)
        assertEquals(6, result.longestStreak)
        assertEquals(2, result.tasksCompletedToday)
    }

    @Test
    fun `completion on next day increases streak`() {
        val result = StreakCalculator.recordQualifyingCompletion(
            state = StoredLearningContinuity(
                currentStreak = 4,
                longestStreak = 4,
                lastQualifyingTaskDate = today.minusDays(1).toString(),
                dailyCompletionDate = today.minusDays(1).toString(),
                tasksCompletedToday = 3
            ),
            today = today
        )

        assertEquals(5, result.currentStreak)
        assertEquals(5, result.longestStreak)
        assertEquals(1, result.tasksCompletedToday)
    }

    @Test
    fun `missed full day breaks streak and next completion starts at one`() {
        val result = StreakCalculator.recordQualifyingCompletion(
            state = StoredLearningContinuity(
                currentStreak = 5,
                longestStreak = 8,
                lastQualifyingTaskDate = today.minusDays(2).toString(),
                dailyCompletionDate = today.minusDays(2).toString(),
                tasksCompletedToday = 2
            ),
            today = today
        )

        assertEquals(1, result.currentStreak)
        assertEquals(8, result.longestStreak)
        assertEquals(1, result.tasksCompletedToday)
    }

    @Test
    fun `stale streak is displayed as zero before next completion`() {
        val active = StreakCalculator.activeStreak(
            currentStreak = 7,
            lastQualifyingTaskDate = today.minusDays(3).toString(),
            today = today
        )

        assertEquals(0, active)
    }

    @Test
    fun `yesterday streak remains active`() {
        val active = StreakCalculator.activeStreak(
            currentStreak = 7,
            lastQualifyingTaskDate = today.minusDays(1).toString(),
            today = today
        )

        assertEquals(7, active)
    }

    @Test
    fun `daily task count is zero when stored date is not today`() {
        val count = StreakCalculator.tasksCompletedOnDate(
            dailyCompletionDate = today.minusDays(1).toString(),
            storedCount = 4,
            date = today
        )

        assertEquals(0, count)
    }

    private fun emptyState() = StoredLearningContinuity(
        currentStreak = 0,
        longestStreak = 0,
        lastQualifyingTaskDate = null,
        dailyCompletionDate = null,
        tasksCompletedToday = 0
    )
}
