package com.example.pmuprojekat.data.remote

import android.util.Log
import com.example.pmuprojekat.data.repository.FirebaseAuthRepository
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseAnonymousAuthRepository @Inject constructor(
    private val clients: FirebaseClientProvider
) : FirebaseAuthRepository {

    override suspend fun ensureSignedInAnonymously(): Result<String> = runCatching {
        val auth = clients.auth().getOrThrow()
        val userId = auth.currentUser?.uid ?: auth.signInAnonymously()
            .awaitResult()
            .user
            ?.uid
            ?: error("Firebase anonimna prijava nije vratila korisnički ID.")
        Log.i(TAG, "Firebase anonimna autentifikacija je spremna.")
        userId
    }

    override fun currentUserId(): String? {
        return clients.auth().getOrNull()?.currentUser?.uid
    }

    private companion object {
        const val TAG = "FirebaseAuthRepository"
    }
}

internal suspend fun <T> Task<T>.awaitResult(): T = suspendCoroutine { continuation ->
    addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(task.result)
        } else {
            continuation.resumeWithException(
                task.exception ?: IllegalStateException("Firebase operacija nije uspela.")
            )
        }
    }
}
