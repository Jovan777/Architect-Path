package com.example.pmuprojekat.core.learning

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class StudyReminderPolicyTest {

    private val monday = LocalDate.of(2026, 7, 27)

    @Test
    fun `one daily task suppresses daily reminder`() {
        val reminder = reminder(
            goal = "1 zadatak dnevno",
            date = monday,
            tasksToday = 1
        )

        assertNull(reminder)
    }

    @Test
    fun `one to two goal reminds after only one task`() {
        val reminder = reminder(
            goal = "1–2 zadatka dnevno",
            date = monday,
            tasksToday = 1
        )

        assertNotNull(reminder)
        assertTrue(reminder!!.text.contains("još jedan zadatak"))
    }

    @Test
    fun `weekly goal waits until end of week`() {
        val thursday = monday.plusDays(3)

        assertNull(
            reminder(
                goal = "1 talas nedeljno",
                date = thursday,
                tasksToday = 0,
                waveCompleted = false
            )
        )
    }

    @Test
    fun `weekly goal reminds on Friday when no wave is complete`() {
        val friday = monday.plusDays(4)
        val reminder = reminder(
            goal = "1 talas nedeljno",
            date = friday,
            tasksToday = 0,
            waveCompleted = false
        )

        assertNotNull(reminder)
        assertEquals("Završi talas ove nedelje", reminder!!.title)
    }

    @Test
    fun `completed wave suppresses weekly reminder`() {
        val friday = monday.plusDays(4)

        assertNull(
            reminder(
                goal = "1 talas nedeljno",
                date = friday,
                tasksToday = 0,
                waveCompleted = true
            )
        )
    }

    @Test
    fun `active streak creates continuity reminder even before weekly reminder window`() {
        val reminder = reminder(
            goal = "1 talas nedeljno",
            date = monday,
            currentStreak = 3,
            tasksToday = 0,
            waveCompleted = false
        )

        assertNotNull(reminder)
        assertEquals("Ne prekidaj niz 🔥", reminder!!.title)
        assertTrue(reminder.text.contains("3 dana"))
    }

    @Test
    fun `same day notification is never duplicated`() {
        val reminder = StudyReminderPolicy.reminderFor(
            learningGoal = "Brzi napredak",
            context = StudyReminderContext(
                today = monday,
                currentStreak = 2,
                tasksCompletedToday = 0,
                waveCompletedThisWeek = false,
                lastNotificationDate = monday.toString()
            )
        )

        assertNull(reminder)
    }

    @Test
    fun `exam goal uses study focused reminder`() {
        val reminder = reminder(
            goal = "Priprema za ispit",
            date = monday,
            tasksToday = 0
        )

        assertEquals("Vreme je za pripremu", reminder?.title)
    }

    private fun reminder(
        goal: String,
        date: LocalDate,
        currentStreak: Int = 0,
        tasksToday: Int,
        waveCompleted: Boolean = false
    ): StudyReminderContent? {
        return StudyReminderPolicy.reminderFor(
            learningGoal = goal,
            context = StudyReminderContext(
                today = date,
                currentStreak = currentStreak,
                tasksCompletedToday = tasksToday,
                waveCompletedThisWeek = waveCompleted,
                lastNotificationDate = null
            )
        )
    }
}
