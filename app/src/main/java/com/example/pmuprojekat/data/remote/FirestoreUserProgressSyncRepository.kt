package com.example.pmuprojekat.data.remote

import android.util.Log
import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.dao.TaskAttemptSyncDao
import com.example.pmuprojekat.data.local.dao.UserAnswerDao
import com.example.pmuprojekat.data.local.dao.UserDao
import com.example.pmuprojekat.data.local.entity.TaskAttemptSyncEntity
import com.example.pmuprojekat.data.local.entity.UserQuestionProgressEntity
import com.example.pmuprojekat.data.repository.FirebaseAuthRepository
import com.example.pmuprojekat.data.repository.LearningRepository
import com.example.pmuprojekat.data.repository.UserProgressSyncRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreUserProgressSyncRepository @Inject constructor(
    private val clients: FirebaseClientProvider,
    private val authRepository: FirebaseAuthRepository,
    private val userDao: UserDao,
    private val userAnswerDao: UserAnswerDao,
    private val questionDao: QuestionDao,
    private val attemptSyncDao: TaskAttemptSyncDao
) : UserProgressSyncRepository {
    private val syncMutex = Mutex()

    override suspend fun syncPendingProgress(): Result<Unit> = syncMutex.withLock {
        runCatching {
            val uid = authRepository.ensureSignedInAnonymously().getOrThrow()
            val firestore = clients.firestore().getOrThrow()
            var firstAttemptFailure: Throwable? = null

            attemptSyncDao.getPendingForUser(uid).forEach { attempt ->
                attemptSyncDao.assignOwnerIfMissing(attempt.attemptId, uid)
                runCatching {
                    uploadAttemptIfMissing(
                        firestore = firestore,
                        uid = uid,
                        attempt = attempt
                    )
                    attemptSyncDao.markUploaded(attempt.attemptId)
                }.onFailure { error ->
                    Log.w(TAG, "Sinhronizacija pokušaja ${attempt.attemptId} nije uspela.", error)
                    attemptSyncDao.markFailed(
                        attemptId = attempt.attemptId,
                        error = error.javaClass.simpleName
                    )
                    if (firstAttemptFailure == null) {
                        firstAttemptFailure = error
                    }
                }
            }

            syncProfileAndLeaderboard(
                firestore = firestore,
                uid = uid
            )

            firstAttemptFailure?.let { throw it }
            Unit
        }.onFailure { error ->
            Log.w(TAG, "Sinhronizacija korisničkog progresa nije uspela.", error)
        }
    }

    private suspend fun uploadAttemptIfMissing(
        firestore: FirebaseFirestore,
        uid: String,
        attempt: TaskAttemptSyncEntity
    ) {
        val document = firestore
            .collection(USERS_COLLECTION)
            .document(uid)
            .collection(ATTEMPTS_COLLECTION)
            .document(attempt.attemptId)

        if (document.get().awaitResult().exists()) return

        document.set(
            mapOf(
                "attemptId" to attempt.attemptId,
                "taskId" to attempt.taskId,
                "taskTitle" to attempt.taskTitle,
                "level" to attempt.level,
                "taskType" to attempt.taskType,
                "taskSource" to attempt.taskSource,
                "percentage" to attempt.percentage,
                "pointsAwarded" to attempt.pointsAwarded,
                "attemptNumber" to attempt.attemptNumber,
                "completedAt" to Timestamp(Date(attempt.completedAt)),
                "schemaVersion" to SCHEMA_VERSION
            )
        ).awaitResult()
    }

    private suspend fun syncProfileAndLeaderboard(
        firestore: FirebaseFirestore,
        uid: String
    ) {
        val user = userDao.getActiveUser()
        val progress = userAnswerDao.getUserProgress(LearningRepository.LOCAL_USER_ID)
        val allQuestions = questionDao.getAllQuestions()
        val mainQuestionIds = questionDao.getAllMainQuestions()
            .mapTo(mutableSetOf()) { it.questionId }
        val questionById = allQuestions.associateBy { it.questionId }

        val displayName = user?.displayName
            ?.trim()
            ?.take(MAX_DISPLAY_NAME_LENGTH)
            ?.ifBlank { DEFAULT_DISPLAY_NAME }
            ?: DEFAULT_DISPLAY_NAME
        val totalPoints = progress
            .filter { it.questionId in mainQuestionIds }
            .sumOf(UserQuestionProgressEntity::bestEarnedXp)
            .coerceAtLeast(0)
        val totalAttempts = progress.sumOf { it.attempts.coerceAtLeast(0) }
        val uniqueTasksAttempted = progress.count { it.attempts > 0 }
        val completedTasks = progress.count { it.status == "completed" }
        val lastActiveAt = maxOf(
            user?.lastActiveAt ?: 0L,
            progress.maxOfOrNull { it.updatedAt } ?: 0L
        ).takeIf { it > 0L } ?: System.currentTimeMillis()

        val progressByLevel = LearningLevel.entries.associate { level ->
            val levelProgress = progress.filter { item ->
                questionById[item.questionId]?.level == level.id
            }
            val attempted = levelProgress.filter { it.attempts > 0 }
            level.id to mapOf(
                "attemptedTasks" to attempted.size,
                "totalAttempts" to levelProgress.sumOf { it.attempts.coerceAtLeast(0) },
                "completedTasks" to levelProgress.count { it.status == "completed" },
                "averageSuccessPercentage" to attempted
                    .map { it.bestScorePercent.coerceIn(0, 100) }
                    .takeIf { it.isNotEmpty() }
                    ?.average()
                    ?.toInt()
                    .orZero()
            )
        }

        val profile = mapOf(
            "uid" to uid,
            "displayName" to displayName,
            "totalPoints" to totalPoints,
            "totalAttempts" to totalAttempts,
            "uniqueTasksAttempted" to uniqueTasksAttempted,
            "completedTasksCount" to completedTasks,
            "lastActiveAt" to Timestamp(Date(lastActiveAt)),
            "progressByLevel" to progressByLevel,
            "schemaVersion" to SCHEMA_VERSION,
            "updatedAt" to FieldValue.serverTimestamp()
        )
        val leaderboard = mapOf(
            "uid" to uid,
            "displayName" to displayName,
            "totalPoints" to totalPoints,
            "updatedAt" to FieldValue.serverTimestamp()
        )

        firestore.batch().apply {
            set(
                firestore.collection(USERS_COLLECTION).document(uid),
                profile,
                SetOptions.merge()
            )
            set(
                firestore.collection(LEADERBOARD_COLLECTION).document(uid),
                leaderboard,
                SetOptions.merge()
            )
        }.commit().awaitResult()
    }

    private fun Int?.orZero(): Int = this ?: 0

    private companion object {
        const val TAG = "UserProgressSync"
        const val USERS_COLLECTION = "users"
        const val ATTEMPTS_COLLECTION = "task_attempts"
        const val LEADERBOARD_COLLECTION = "leaderboard"
        const val DEFAULT_DISPLAY_NAME = "Korisnik"
        const val MAX_DISPLAY_NAME_LENGTH = 80
        const val SCHEMA_VERSION = 1
    }
}
