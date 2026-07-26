package com.example.pmuprojekat.data.repository

data class LevelProgressSnapshot(
    val attemptedTasks: Int = 0,
    val totalAttempts: Int = 0,
    val completedTasks: Int = 0,
    val averageSuccessPercentage: Int = 0
)

data class AdminUserProgress(
    val uid: String,
    val displayName: String,
    val totalPoints: Int,
    val totalAttempts: Int,
    val uniqueTasksAttempted: Int,
    val completedTasksCount: Int,
    val lastActiveAt: Long?,
    val progressByLevel: Map<String, LevelProgressSnapshot>
)

data class SyncedTaskAttempt(
    val attemptId: String,
    val taskId: String,
    val taskTitle: String,
    val level: String,
    val taskType: String,
    val taskSource: String,
    val percentage: Int,
    val pointsAwarded: Int,
    val attemptNumber: Int,
    val completedAt: Long
)

data class AdminUserDetails(
    val profile: AdminUserProgress,
    val attempts: List<SyncedTaskAttempt>
)

data class LeaderboardEntry(
    val uid: String,
    val displayName: String,
    val totalPoints: Int,
    val updatedAt: Long?
)

data class LeaderboardSnapshot(
    val entries: List<LeaderboardEntry>,
    val currentUserId: String,
    val currentUserEntry: LeaderboardEntry?
)

class AdminAuthorizationException : IllegalStateException()
