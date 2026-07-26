package com.example.pmuprojekat.data.remote

import android.util.Log
import com.example.pmuprojekat.data.repository.AdminAuthRepository
import com.example.pmuprojekat.data.repository.AdminAuthResult
import com.example.pmuprojekat.data.repository.AdminAuthorizationException
import com.example.pmuprojekat.data.repository.AdminUserDetails
import com.example.pmuprojekat.data.repository.AdminUserProgress
import com.example.pmuprojekat.data.repository.AdminUsersRepository
import com.example.pmuprojekat.data.repository.LevelProgressSnapshot
import com.example.pmuprojekat.data.repository.SyncedTaskAttempt
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreAdminUsersRepository @Inject constructor(
    private val clients: FirebaseAdminClientProvider,
    private val adminAuthRepository: AdminAuthRepository
) : AdminUsersRepository {
    override suspend fun fetchUsers(): Result<List<AdminUserProgress>> = runCatching {
        ensureAuthorized()
        clients.firestore()
            .getOrThrow()
            .collection(USERS_COLLECTION)
            .get()
            .awaitResult()
            .documents
            .mapNotNull(::mapUser)
            .sortedByDescending { it.lastActiveAt ?: 0L }
    }.onFailure { error ->
        Log.w(TAG, "Admin lista korisnika nije dostupna.", error)
    }

    override suspend fun fetchUserDetails(uid: String): Result<AdminUserDetails> = runCatching {
        ensureAuthorized()
        val userReference = clients.firestore()
            .getOrThrow()
            .collection(USERS_COLLECTION)
            .document(uid)
        val profile = mapUser(userReference.get().awaitResult())
            ?: error("Korisnički profil ne postoji.")
        val attempts = userReference
            .collection(ATTEMPTS_COLLECTION)
            .orderBy("completedAt", Query.Direction.DESCENDING)
            .get()
            .awaitResult()
            .documents
            .mapNotNull(::mapAttempt)

        AdminUserDetails(
            profile = profile,
            attempts = attempts
        )
    }.onFailure { error ->
        Log.w(TAG, "Admin detalji korisnika nisu dostupni.", error)
    }

    private suspend fun ensureAuthorized() {
        when (adminAuthRepository.verifyCurrentSession()) {
            is AdminAuthResult.Authorized -> Unit
            AdminAuthResult.SignedOut,
            AdminAuthResult.InvalidCredentials,
            AdminAuthResult.MissingAdminClaim -> throw AdminAuthorizationException()

            AdminAuthResult.Unavailable -> error("Admin autorizacija trenutno nije dostupna.")
        }
    }

    private fun mapUser(document: DocumentSnapshot): AdminUserProgress? {
        if (!document.exists()) return null
        val uid = document.getString("uid") ?: document.id
        val progressByLevel = (document.get("progressByLevel") as? Map<*, *>)
            .orEmpty()
            .mapNotNull { (level, rawValue) ->
                val levelId = level as? String ?: return@mapNotNull null
                val values = rawValue as? Map<*, *> ?: return@mapNotNull null
                levelId to LevelProgressSnapshot(
                    attemptedTasks = values.intValue("attemptedTasks"),
                    totalAttempts = values.intValue("totalAttempts"),
                    completedTasks = values.intValue("completedTasks"),
                    averageSuccessPercentage = values
                        .intValue("averageSuccessPercentage")
                        .coerceIn(0, 100)
                )
            }
            .toMap()

        return AdminUserProgress(
            uid = uid,
            displayName = document.getString("displayName")
                ?.trim()
                ?.ifBlank { DEFAULT_DISPLAY_NAME }
                ?: DEFAULT_DISPLAY_NAME,
            totalPoints = document.nonNegativeInt("totalPoints"),
            totalAttempts = document.nonNegativeInt("totalAttempts"),
            uniqueTasksAttempted = document.nonNegativeInt("uniqueTasksAttempted"),
            completedTasksCount = document.nonNegativeInt("completedTasksCount"),
            lastActiveAt = document.get("lastActiveAt").asEpochMillis(),
            progressByLevel = progressByLevel
        )
    }

    private fun mapAttempt(document: DocumentSnapshot): SyncedTaskAttempt? {
        if (!document.exists()) return null
        val taskId = document.getString("taskId") ?: return null
        return SyncedTaskAttempt(
            attemptId = document.getString("attemptId") ?: document.id,
            taskId = taskId,
            taskTitle = document.getString("taskTitle")
                ?.trim()
                ?.ifBlank { taskId }
                ?: taskId,
            level = document.getString("level").orEmpty(),
            taskType = document.getString("taskType").orEmpty(),
            taskSource = document.getString("taskSource").orEmpty(),
            percentage = document.getLong("percentage")
                ?.toInt()
                ?.coerceIn(0, 100)
                ?: 0,
            pointsAwarded = document.nonNegativeInt("pointsAwarded"),
            attemptNumber = document.nonNegativeInt("attemptNumber"),
            completedAt = document.get("completedAt").asEpochMillis() ?: 0L
        )
    }

    private fun DocumentSnapshot.nonNegativeInt(field: String): Int {
        return getLong(field)?.toInt()?.coerceAtLeast(0) ?: 0
    }

    private fun Map<*, *>.intValue(field: String): Int {
        return (this[field] as? Number)?.toInt()?.coerceAtLeast(0) ?: 0
    }

    private fun Any?.asEpochMillis(): Long? {
        return when (this) {
            is Timestamp -> toDate().time
            is Number -> toLong()
            else -> null
        }
    }

    private companion object {
        const val TAG = "AdminUsersRepository"
        const val USERS_COLLECTION = "users"
        const val ATTEMPTS_COLLECTION = "task_attempts"
        const val DEFAULT_DISPLAY_NAME = "Korisnik"
    }
}
