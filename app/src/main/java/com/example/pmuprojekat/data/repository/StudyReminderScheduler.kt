package com.example.pmuprojekat.data.repository

interface StudyReminderScheduler {
    fun scheduleAfterQualifyingCompletion(completedAtMillis: Long)
    fun rescheduleForLearningGoal(lastCompletionAtMillis: Long?)
    fun ensureScheduled(lastCompletionAtMillis: Long?)
}
