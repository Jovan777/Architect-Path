package com.example.pmuprojekat.data.repository

import androidx.room.withTransaction
import com.example.pmuprojekat.core.learning.LearningTimeProvider
import com.example.pmuprojekat.core.learning.StoredLearningContinuity
import com.example.pmuprojekat.core.learning.StreakCalculator
import com.example.pmuprojekat.data.local.PMUDatabase
import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.dao.TaskAttemptSyncDao
import com.example.pmuprojekat.data.local.dao.UserAnswerDao
import com.example.pmuprojekat.data.local.dao.UserDao
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.TaskAttemptSyncEntity
import com.example.pmuprojekat.data.local.entity.UserEntity
import com.example.pmuprojekat.data.local.entity.UserQuestionProgressEntity
import com.example.pmuprojekat.data.local.entity.UserStepAnswerEntity
import com.example.pmuprojekat.data.local.relation.QuestionWithSteps
import com.example.pmuprojekat.data.seed.SeedInserter
import com.example.pmuprojekat.core.model.XpCalculator
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningRepository @Inject constructor(
    private val database: PMUDatabase,
    private val userDao: UserDao,
    private val questionDao: QuestionDao,
    private val userAnswerDao: UserAnswerDao,
    private val taskAttemptSyncDao: TaskAttemptSyncDao,
    private val seedInserter: SeedInserter,
    private val timeProvider: LearningTimeProvider,
    private val studyReminderScheduler: StudyReminderScheduler
) {

    fun observeActiveUser(): Flow<UserEntity?> {
        return userDao.observeActiveUser()
    }

    fun activeStreakForDisplay(user: UserEntity?): Int {
        user ?: return 0
        return StreakCalculator.activeStreak(
            currentStreak = user.streakDays,
            lastQualifyingTaskDate = user.lastQualifyingTaskDate,
            today = timeProvider.localDate()
        )
    }

    fun observeAllQuestions(): Flow<List<QuestionEntity>> {
        return questionDao.observeAllQuestions()
    }

    fun observeQuestionsByLevel(level: String): Flow<List<QuestionEntity>> {
        return questionDao.observeQuestionsByLevel(level)
    }

    fun observeApprovedUserQuestions(): Flow<List<QuestionEntity>> {
        return questionDao.observeApprovedUserQuestions()
    }

    fun observeQuestionWithSteps(questionId: String): Flow<QuestionWithSteps?> {
        return questionDao.observeQuestionWithSteps(questionId)
    }

    suspend fun initializeDatabaseIfNeeded() {
        seedInserter.seedIfNeeded()
    }

    fun observeLocalUserProgress() =
        userAnswerDao.observeUserProgress(LOCAL_USER_ID)



    suspend fun updateCurrentLevel(level: String) {
        userDao.updateCurrentLevel(
            userId = LOCAL_USER_ID,
            level = level
        )
    }

    suspend fun updateProfileSettings(
        displayName: String,
        currentLevel: String,
        learningGoal: String,
        preferredTaskFormat: String,
        learningFocus: String,
        aiFollowUpEnabled: Boolean
    ) {
        val previousUser = userDao.getUserById(LOCAL_USER_ID)
        userDao.updateProfileSettings(
            userId = LOCAL_USER_ID,
            displayName = displayName,
            currentLevel = currentLevel,
            learningGoal = learningGoal,
            preferredTaskFormat = preferredTaskFormat,
            learningFocus = learningFocus,
            aiFollowUpEnabled = aiFollowUpEnabled
        )

        if (previousUser?.learningGoal != learningGoal) {
            val updatedUser = userDao.getUserById(LOCAL_USER_ID)
            studyReminderScheduler.rescheduleForLearningGoal(
                updatedUser?.lastQualifyingTaskCompletedAt
            )
        }
    }

    suspend fun completeOnboarding(
        displayName: String,
        currentLevel: String,
        preferredTaskFormat: String,
        learningFocus: String
    ) {
        val existingUser = userDao.getUserById(LOCAL_USER_ID)

        if (existingUser == null) {
            userDao.upsertUser(
                UserEntity(
                    userId = LOCAL_USER_ID,
                    displayName = displayName,
                    currentLevel = currentLevel,
                    preferredTaskFormat = preferredTaskFormat,
                    learningFocus = learningFocus,
                    hasCompletedOnboarding = true
                )
            )
        } else {
            userDao.completeOnboarding(
                userId = LOCAL_USER_ID,
                displayName = displayName,
                currentLevel = currentLevel,
                preferredTaskFormat = preferredTaskFormat,
                learningFocus = learningFocus
            )
        }
    }

    suspend fun resetProgress() {
        userAnswerDao.clearUserProgress(LOCAL_USER_ID)
        userDao.resetLearningStats(LOCAL_USER_ID)
        studyReminderScheduler.rescheduleForLearningGoal(null)
    }

    suspend fun validateLearningContinuity() {
        val user = userDao.getUserById(LOCAL_USER_ID) ?: return
        val today = timeProvider.localDate()
        val activeStreak = StreakCalculator.activeStreak(
            currentStreak = user.streakDays,
            lastQualifyingTaskDate = user.lastQualifyingTaskDate,
            today = today
        )
        val tasksToday = StreakCalculator.tasksCompletedOnDate(
            dailyCompletionDate = user.dailyCompletionDate,
            storedCount = user.tasksCompletedToday,
            date = today
        )

        if (activeStreak != user.streakDays || tasksToday != user.tasksCompletedToday) {
            userDao.normalizeLearningContinuity(
                userId = LOCAL_USER_ID,
                currentStreak = activeStreak,
                dailyCompletionDate = user.dailyCompletionDate.takeIf { tasksToday > 0 },
                tasksCompletedToday = tasksToday
            )
        }
    }

    suspend fun ensureStudyReminderScheduled() {
        val user = userDao.getUserById(LOCAL_USER_ID)
        studyReminderScheduler.ensureScheduled(user?.lastQualifyingTaskCompletedAt)
    }

    suspend fun markNotificationPermissionAsked() {
        userDao.markNotificationPermissionAsked(LOCAL_USER_ID)
    }

    suspend fun saveStepAnswer(answer: UserStepAnswerEntity) {
        userAnswerDao.upsertStepAnswer(answer)
    }

    suspend fun completeQuestion(
        questionId: String,
        scorePercent: Int
    ): QuestionCompletionReward {
        val now = timeProvider.nowMillis()
        val today = timeProvider.localDate(now)
        val attemptId = "attempt_${UUID.randomUUID()}"

        val reward = database.withTransaction {
            val existingProgress = userAnswerDao.getQuestionProgress(
                userId = LOCAL_USER_ID,
                questionId = questionId
            )

            val alreadyCompleted = existingProgress?.status == "completed"
            val earnedXpForAttempt = XpCalculator.xpForScore(scorePercent)
            val previousBestXp = existingProgress?.bestEarnedXp ?: 0
            val bestEarnedXp = maxOf(previousBestXp, earnedXpForAttempt)
            val newlyAwardedXp = bestEarnedXp - previousBestXp
            val attemptNumber = (existingProgress?.attempts ?: 0) + 1
            val user = userDao.getUserById(LOCAL_USER_ID)

            userAnswerDao.upsertQuestionProgress(
                UserQuestionProgressEntity(
                    userId = LOCAL_USER_ID,
                    questionId = questionId,
                    status = "completed",
                    attempts = attemptNumber,
                    bestScorePercent = maxOf(
                        existingProgress?.bestScorePercent ?: 0,
                        scorePercent
                    ),
                    bestEarnedXp = bestEarnedXp,
                    startedAt = existingProgress?.startedAt ?: now,
                    completedAt = now,
                    updatedAt = now
                )
            )

            if (!alreadyCompleted || newlyAwardedXp > 0) {
                userDao.increaseLearningStats(
                    userId = LOCAL_USER_ID,
                    completedDelta = if (alreadyCompleted) 0 else 1,
                    xpDelta = newlyAwardedXp,
                    updatedAt = now
                )
            }

            val question = questionDao.getQuestion(questionId)
            if (user != null) {
                val continuity = StreakCalculator.recordQualifyingCompletion(
                    state = StoredLearningContinuity(
                        currentStreak = user.streakDays,
                        longestStreak = user.longestStreak,
                        lastQualifyingTaskDate = user.lastQualifyingTaskDate,
                        dailyCompletionDate = user.dailyCompletionDate,
                        tasksCompletedToday = user.tasksCompletedToday
                    ),
                    today = today
                )
                userDao.updateLearningContinuity(
                    userId = LOCAL_USER_ID,
                    currentStreak = continuity.currentStreak,
                    longestStreak = continuity.longestStreak,
                    lastQualifyingTaskDate = continuity.lastQualifyingTaskDate,
                    lastQualifyingTaskCompletedAt = now,
                    dailyCompletionDate = continuity.dailyCompletionDate,
                    tasksCompletedToday = continuity.tasksCompletedToday,
                    updatedAt = now
                )
            }

            if (!alreadyCompleted && question?.wave != null) {
                val incompleteInWave = userAnswerDao.countIncompleteQuestionsInWave(
                    userId = LOCAL_USER_ID,
                    level = question.level,
                    wave = question.wave
                )
                if (incompleteInWave == 0) {
                    userDao.updateLastCompletedWaveDate(
                        userId = LOCAL_USER_ID,
                        completionDate = today.toString()
                    )
                }
            }

            taskAttemptSyncDao.insert(
                TaskAttemptSyncEntity(
                    attemptId = attemptId,
                    taskId = questionId,
                    taskTitle = question?.title?.take(MAX_SYNC_TITLE_LENGTH)
                        ?: questionId.take(MAX_SYNC_TITLE_LENGTH),
                    level = question?.level.orEmpty(),
                    taskType = question?.type.orEmpty(),
                    taskSource = question?.source ?: "LOCAL_SEED",
                    percentage = scorePercent.coerceIn(0, 100),
                    pointsAwarded = newlyAwardedXp.coerceAtLeast(0),
                    attemptNumber = attemptNumber,
                    completedAt = now
                )
            )

            QuestionCompletionReward(
                attemptId = attemptId,
                attemptNumber = attemptNumber,
                earnedXpForAttempt = earnedXpForAttempt,
                newlyAwardedXp = newlyAwardedXp,
                bestEarnedXp = bestEarnedXp,
                shouldRequestNotificationPermission =
                    user?.notificationPermissionAsked == false
            )
        }

        runCatching {
            studyReminderScheduler.scheduleAfterQualifyingCompletion(now)
        }

        return reward
    }

    companion object {
        const val LOCAL_USER_ID = "local_user"
        private const val MAX_SYNC_TITLE_LENGTH = 240
    }
}

data class QuestionCompletionReward(
    val attemptId: String,
    val attemptNumber: Int,
    val earnedXpForAttempt: Int,
    val newlyAwardedXp: Int,
    val bestEarnedXp: Int,
    val shouldRequestNotificationPermission: Boolean
)
