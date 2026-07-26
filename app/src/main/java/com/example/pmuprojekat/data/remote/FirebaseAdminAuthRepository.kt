package com.example.pmuprojekat.data.remote

import android.util.Log
import com.example.pmuprojekat.data.repository.AdminAuthRepository
import com.example.pmuprojekat.data.repository.AdminAuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAdminAuthRepository @Inject constructor(
    private val clients: FirebaseAdminClientProvider
) : AdminAuthRepository {

    override suspend fun verifyCurrentSession(): AdminAuthResult {
        val auth = clients.auth().getOrElse {
            Log.e(TAG, "Admin FirebaseAuth nije dostupan.", it)
            return AdminAuthResult.Unavailable
        }
        val user = auth.currentUser ?: return AdminAuthResult.SignedOut
        return verifyAdminClaim(user)
    }

    override suspend fun signIn(email: String, password: String): AdminAuthResult {
        val auth = clients.auth().getOrElse {
            Log.e(TAG, "Admin FirebaseAuth nije dostupan.", it)
            return AdminAuthResult.Unavailable
        }

        return try {
            val user = auth.signInWithEmailAndPassword(email, password)
                .awaitResult()
                .user
                ?: return AdminAuthResult.InvalidCredentials
            verifyAdminClaim(user)
        } catch (error: FirebaseAuthInvalidCredentialsException) {
            Log.w(TAG, "Admin prijava je odbijena zbog neispravnih kredencijala.")
            AdminAuthResult.InvalidCredentials
        } catch (error: FirebaseAuthInvalidUserException) {
            Log.w(TAG, "Admin prijava je odbijena jer nalog nije dostupan.")
            AdminAuthResult.InvalidCredentials
        } catch (error: Exception) {
            Log.e(TAG, "Admin prijava nije uspela.", error)
            AdminAuthResult.Unavailable
        }
    }

    override fun signOut() {
        clients.auth().getOrNull()?.signOut()
    }

    private suspend fun verifyAdminClaim(user: FirebaseUser): AdminAuthResult {
        return try {
            val token = user.getIdToken(true).awaitResult()
            if (token.claims[ADMIN_CLAIM] == true) {
                AdminAuthResult.Authorized(
                    email = user.email.orEmpty()
                )
            } else {
                clients.auth().getOrNull()?.signOut()
                AdminAuthResult.MissingAdminClaim
            }
        } catch (error: Exception) {
            Log.e(TAG, "Provera admin claim-a nije uspela.", error)
            AdminAuthResult.Unavailable
        }
    }

    private companion object {
        const val TAG = "AdminAuthRepository"
        const val ADMIN_CLAIM = "admin"
    }
}
