package com.example.pmuprojekat.data.repository

interface UserProgressSyncRepository {
    suspend fun syncPendingProgress(): Result<Unit>
}

interface LeaderboardRepository {
    suspend fun fetchLeaderboard(limit: Long = 50): Result<LeaderboardSnapshot>
}

interface AdminUsersRepository {
    suspend fun fetchUsers(): Result<List<AdminUserProgress>>

    suspend fun fetchUserDetails(uid: String): Result<AdminUserDetails>
}
