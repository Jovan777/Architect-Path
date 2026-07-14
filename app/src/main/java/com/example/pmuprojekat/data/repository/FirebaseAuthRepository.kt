package com.example.pmuprojekat.data.repository

interface FirebaseAuthRepository {
    suspend fun ensureSignedInAnonymously(): Result<String>
    fun currentUserId(): String?
}
