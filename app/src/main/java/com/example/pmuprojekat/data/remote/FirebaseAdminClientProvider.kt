package com.example.pmuprojekat.data.remote

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAdminClientProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun auth(): Result<FirebaseAuth> = runCatching {
        FirebaseAuth.getInstance(adminApp())
    }

    fun firestore(): Result<FirebaseFirestore> = runCatching {
        FirebaseFirestore.getInstance(adminApp())
    }

    fun storage(): Result<FirebaseStorage> = runCatching {
        FirebaseStorage.getInstance(adminApp())
    }

    private fun adminApp(): FirebaseApp = synchronized(appLock) {
        FirebaseApp.getApps(context)
            .firstOrNull { it.name == ADMIN_APP_NAME }
            ?: FirebaseApp.initializeApp(
                context,
                defaultApp().options,
                ADMIN_APP_NAME
            )
    }

    private fun defaultApp(): FirebaseApp {
        return FirebaseApp.getApps(context)
            .firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME }
            ?: FirebaseApp.initializeApp(context)
            ?: error(
                "Firebase nije konfigurisan. Proveri da li se google-services.json nalazi u app modulu."
            )
    }

    private companion object {
        const val ADMIN_APP_NAME = "adminAuthApp"
        val appLock = Any()
    }
}
