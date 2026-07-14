package com.example.pmuprojekat.data.remote

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClientProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun auth(): Result<FirebaseAuth> = firebaseApp().map(FirebaseAuth::getInstance)

    fun firestore(): Result<FirebaseFirestore> = firebaseApp().map(FirebaseFirestore::getInstance)

    private fun firebaseApp(): Result<FirebaseApp> = runCatching {
        FirebaseApp.getApps(context).firstOrNull()
            ?: FirebaseApp.initializeApp(context)
            ?: error(
                "Firebase nije konfigurisan. Proveri da li se google-services.json nalazi u app modulu."
            )
    }
}
