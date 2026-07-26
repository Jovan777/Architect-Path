package com.example.pmuprojekat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pmuprojekat.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE isActive = 1 LIMIT 1")
    fun observeActiveUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveUser(): UserEntity?

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    suspend fun getUserById(userId: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: UserEntity)

    @Query("""
        UPDATE users
        SET currentLevel = :level,
            lastActiveAt = :updatedAt
        WHERE userId = :userId
    """)
    suspend fun updateCurrentLevel(
        userId: String,
        level: String,
        updatedAt: Long = System.currentTimeMillis()
    )

    @Query("""
        UPDATE users
        SET completedQuestions = completedQuestions + :completedDelta,
            xp = xp + :xpDelta,
            lastActiveAt = :updatedAt
        WHERE userId = :userId
    """)
    suspend fun increaseLearningStats(
        userId: String,
        completedDelta: Int,
        xpDelta: Int,
        updatedAt: Long = System.currentTimeMillis()
    )

    @Query("""
        UPDATE users
        SET streakDays = :currentStreak,
            longestStreak = :longestStreak,
            lastQualifyingTaskDate = :lastQualifyingTaskDate,
            lastQualifyingTaskCompletedAt = :lastQualifyingTaskCompletedAt,
            dailyCompletionDate = :dailyCompletionDate,
            tasksCompletedToday = :tasksCompletedToday,
            lastActiveAt = :updatedAt
        WHERE userId = :userId
    """)
    suspend fun updateLearningContinuity(
        userId: String,
        currentStreak: Int,
        longestStreak: Int,
        lastQualifyingTaskDate: String,
        lastQualifyingTaskCompletedAt: Long,
        dailyCompletionDate: String,
        tasksCompletedToday: Int,
        updatedAt: Long
    )

    @Query("""
        UPDATE users
        SET streakDays = :currentStreak,
            dailyCompletionDate = :dailyCompletionDate,
            tasksCompletedToday = :tasksCompletedToday
        WHERE userId = :userId
    """)
    suspend fun normalizeLearningContinuity(
        userId: String,
        currentStreak: Int,
        dailyCompletionDate: String?,
        tasksCompletedToday: Int
    )

    @Query("""
        UPDATE users
        SET lastReminderNotificationDate = :notificationDate
        WHERE userId = :userId
          AND (
            lastReminderNotificationDate IS NULL
            OR lastReminderNotificationDate != :notificationDate
          )
    """)
    suspend fun claimReminderNotificationDate(
        userId: String,
        notificationDate: String
    ): Int

    @Query("""
        UPDATE users
        SET notificationPermissionAsked = 1
        WHERE userId = :userId
    """)
    suspend fun markNotificationPermissionAsked(userId: String)

    @Query("""
        UPDATE users
        SET lastCompletedWaveDate = :completionDate
        WHERE userId = :userId
    """)
    suspend fun updateLastCompletedWaveDate(
        userId: String,
        completionDate: String
    )

    @Query("""
        UPDATE users
        SET lastReminderNotificationDate = NULL
        WHERE userId = :userId
          AND lastReminderNotificationDate = :notificationDate
    """)
    suspend fun releaseReminderNotificationDate(
        userId: String,
        notificationDate: String
    )

    @Query("""
        UPDATE users
        SET displayName = :displayName,
            currentLevel = :currentLevel,
            learningGoal = :learningGoal,
            preferredTaskFormat = :preferredTaskFormat,
            learningFocus = :learningFocus,
            aiFollowUpEnabled = :aiFollowUpEnabled,
            hasCompletedOnboarding = 1,
            lastActiveAt = :updatedAt
        WHERE userId = :userId
    """)
    suspend fun updateProfileSettings(
        userId: String,
        displayName: String,
        currentLevel: String,
        learningGoal: String,
        preferredTaskFormat: String,
        learningFocus: String,
        aiFollowUpEnabled: Boolean,
        updatedAt: Long = System.currentTimeMillis()
    )

    @Query("""
        UPDATE users
        SET displayName = :displayName,
            currentLevel = :currentLevel,
            preferredTaskFormat = :preferredTaskFormat,
            learningFocus = :learningFocus,
            hasCompletedOnboarding = 1,
            lastActiveAt = :updatedAt
        WHERE userId = :userId
    """)
    suspend fun completeOnboarding(
        userId: String,
        displayName: String,
        currentLevel: String,
        preferredTaskFormat: String,
        learningFocus: String,
        updatedAt: Long = System.currentTimeMillis()
    )

    @Query("""
        UPDATE users
        SET xp = 0,
            streakDays = 0,
            longestStreak = 0,
            lastQualifyingTaskDate = NULL,
            lastQualifyingTaskCompletedAt = NULL,
            dailyCompletionDate = NULL,
            tasksCompletedToday = 0,
            lastCompletedWaveDate = NULL,
            lastReminderNotificationDate = NULL,
            completedQuestions = 0,
            lastActiveAt = :updatedAt
        WHERE userId = :userId
    """)
    suspend fun resetLearningStats(
        userId: String,
        updatedAt: Long = System.currentTimeMillis()
    )
}
