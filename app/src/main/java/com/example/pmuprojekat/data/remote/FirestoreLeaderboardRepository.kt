package com.example.pmuprojekat.data.remote

import android.util.Log
import com.example.pmuprojekat.data.repository.FirebaseAuthRepository
import com.example.pmuprojekat.data.repository.LeaderboardEntry
import com.example.pmuprojekat.data.repository.LeaderboardRepository
import com.example.pmuprojekat.data.repository.LeaderboardSnapshot
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreLeaderboardRepository @Inject constructor(
    private val clients: FirebaseClientProvider,
    private val authRepository: FirebaseAuthRepository
) : LeaderboardRepository {
    override suspend fun fetchLeaderboard(limit: Long): Result<LeaderboardSnapshot> = runCatching {
        val uid = authRepository.ensureSignedInAnonymously().getOrThrow()
        val collection = clients.firestore().getOrThrow().collection(LEADERBOARD_COLLECTION)
        val entries = collection
            .orderBy("totalPoints", Query.Direction.DESCENDING)
            .limit(limit.coerceIn(1, MAX_LEADERBOARD_SIZE))
            .get()
            .awaitResult()
            .documents
            .mapNotNull { document ->
                val documentUid = document.getString("uid") ?: document.id
                LeaderboardEntry(
                    uid = documentUid,
                    displayName = document.getString("displayName")
                        ?.trim()
                        ?.ifBlank { DEFAULT_DISPLAY_NAME }
                        ?: DEFAULT_DISPLAY_NAME,
                    totalPoints = document.getLong("totalPoints")
                        ?.toInt()
                        ?.coerceAtLeast(0)
                        ?: 0,
                    updatedAt = document.getTimestamp("updatedAt")?.toDate()?.time
                )
            }

        val currentInList = entries.firstOrNull { it.uid == uid }
        val currentUserEntry = currentInList ?: collection
            .document(uid)
            .get()
            .awaitResult()
            .takeIf { it.exists() }
            ?.let { document ->
                LeaderboardEntry(
                    uid = uid,
                    displayName = document.getString("displayName")
                        ?.trim()
                        ?.ifBlank { DEFAULT_DISPLAY_NAME }
                        ?: DEFAULT_DISPLAY_NAME,
                    totalPoints = document.getLong("totalPoints")
                        ?.toInt()
                        ?.coerceAtLeast(0)
                        ?: 0,
                    updatedAt = document.get("updatedAt").asEpochMillis()
                )
            }

        LeaderboardSnapshot(
            entries = entries,
            currentUserId = uid,
            currentUserEntry = currentUserEntry
        )
    }.onFailure { error ->
        Log.w(TAG, "Rang-lista nije dostupna.", error)
    }

    private fun Any?.asEpochMillis(): Long? {
        return when (this) {
            is Timestamp -> toDate().time
            is Number -> toLong()
            else -> null
        }
    }

    private companion object {
        const val TAG = "LeaderboardRepository"
        const val LEADERBOARD_COLLECTION = "leaderboard"
        const val DEFAULT_DISPLAY_NAME = "Korisnik"
        const val MAX_LEADERBOARD_SIZE = 100L
    }
}
