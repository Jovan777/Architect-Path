package com.example.pmuprojekat.data.repository

sealed interface AdminAuthResult {
    data class Authorized(val email: String) : AdminAuthResult
    data object SignedOut : AdminAuthResult
    data object InvalidCredentials : AdminAuthResult
    data object MissingAdminClaim : AdminAuthResult
    data object Unavailable : AdminAuthResult
}

interface AdminAuthRepository {
    suspend fun verifyCurrentSession(): AdminAuthResult

    suspend fun signIn(email: String, password: String): AdminAuthResult

    fun signOut()
}
