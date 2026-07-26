package com.example.pmuprojekat.core.learning

import java.time.DayOfWeek
import java.time.LocalDate

enum class LearningGoalKind {
    ONE_TASK_DAILY,
    ONE_TO_TWO_TASKS_DAILY,
    ONE_WAVE_WEEKLY,
    FAST_PROGRESS,
    EXAM_PREPARATION;

    companion object {
        fun fromStoredValue(value: String): LearningGoalKind {
            val normalized = value.trim().lowercase()
            return when {
                "talas" in normalized -> ONE_WAVE_WEEKLY
                "brzi" in normalized -> FAST_PROGRESS
                "ispit" in normalized -> EXAM_PREPARATION
                "1–2" in normalized || "1-2" in normalized -> ONE_TO_TWO_TASKS_DAILY
                else -> ONE_TASK_DAILY
            }
        }
    }
}

data class StudyReminderContext(
    val today: LocalDate,
    val currentStreak: Int,
    val tasksCompletedToday: Int,
    val waveCompletedThisWeek: Boolean,
    val lastNotificationDate: String?
)

data class StudyReminderContent(
    val title: String,
    val text: String
)

object StudyReminderPolicy {

    fun reminderFor(
        learningGoal: String,
        context: StudyReminderContext
    ): StudyReminderContent? {
        if (context.lastNotificationDate == context.today.toString()) return null

        val goal = LearningGoalKind.fromStoredValue(learningGoal)
        val streakNeedsReminder =
            context.currentStreak > 0 && context.tasksCompletedToday == 0
        val goalNeedsReminder = when (goal) {
            LearningGoalKind.ONE_TASK_DAILY -> context.tasksCompletedToday < 1
            LearningGoalKind.ONE_TO_TWO_TASKS_DAILY -> context.tasksCompletedToday < 2
            LearningGoalKind.ONE_WAVE_WEEKLY ->
                context.today.dayOfWeek in WEEK_END_DAYS &&
                    !context.waveCompletedThisWeek
            LearningGoalKind.FAST_PROGRESS,
            LearningGoalKind.EXAM_PREPARATION -> context.tasksCompletedToday == 0
        }

        if (!streakNeedsReminder && !goalNeedsReminder) return null

        if (streakNeedsReminder) {
            val text = when {
                goal == LearningGoalKind.ONE_WAVE_WEEKLY && goalNeedsReminder ->
                    "Uradi zadatak danas i nastavi ka završetku talasa. Sačuvaj niz od ${context.currentStreak} dana."
                goal == LearningGoalKind.EXAM_PREPARATION ->
                    "Odvoji nekoliko minuta za zadatak danas i sačuvaj niz od ${context.currentStreak} dana."
                else ->
                    "Uradi jedan zadatak danas i sačuvaj svoj niz od ${context.currentStreak} dana."
            }
            return StudyReminderContent(
                title = "Ne prekidaj niz 🔥",
                text = text
            )
        }

        return when (goal) {
            LearningGoalKind.ONE_TASK_DAILY -> StudyReminderContent(
                title = "Vreme je za jedan zadatak",
                text = "Uradi jedan zadatak danas i ispuni svoj cilj učenja."
            )
            LearningGoalKind.ONE_TO_TWO_TASKS_DAILY -> StudyReminderContent(
                title = "Nastavi današnji cilj",
                text = if (context.tasksCompletedToday == 1) {
                    "Uradi još jedan zadatak danas i zaokruži svoj dnevni cilj."
                } else {
                    "Započni današnje učenje jednim kratkim zadatkom."
                }
            )
            LearningGoalKind.ONE_WAVE_WEEKLY -> StudyReminderContent(
                title = "Završi talas ove nedelje",
                text = "Nastavi sa zadacima kako bi završio jedan talas ove nedelje."
            )
            LearningGoalKind.FAST_PROGRESS -> StudyReminderContent(
                title = "Nastavi sa učenjem",
                text = "Uradi zadatak danas i održi ritam brzog napretka."
            )
            LearningGoalKind.EXAM_PREPARATION -> StudyReminderContent(
                title = "Vreme je za pripremu",
                text = "Odvoji nekoliko minuta za jedan zadatak i nastavi pripremu za ispit."
            )
        }
    }

    private val WEEK_END_DAYS = setOf(
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY
    )
}
