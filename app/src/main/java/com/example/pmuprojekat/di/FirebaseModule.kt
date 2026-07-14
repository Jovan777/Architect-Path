package com.example.pmuprojekat.di

import com.example.pmuprojekat.data.remote.FirebaseAnonymousAuthRepository
import com.example.pmuprojekat.data.remote.FirestoreRemoteTaskRepository
import com.example.pmuprojekat.data.remote.FirestoreTaskSubmissionRemoteRepository
import com.example.pmuprojekat.data.repository.FirebaseAuthRepository
import com.example.pmuprojekat.data.repository.RemoteTaskRepository
import com.example.pmuprojekat.data.repository.TaskSubmissionRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {

    @Binds
    @Singleton
    abstract fun bindFirebaseAuthRepository(
        repository: FirebaseAnonymousAuthRepository
    ): FirebaseAuthRepository

    @Binds
    @Singleton
    abstract fun bindRemoteTaskRepository(
        repository: FirestoreRemoteTaskRepository
    ): RemoteTaskRepository

    @Binds
    @Singleton
    abstract fun bindTaskSubmissionRemoteRepository(
        repository: FirestoreTaskSubmissionRemoteRepository
    ): TaskSubmissionRemoteRepository
}
