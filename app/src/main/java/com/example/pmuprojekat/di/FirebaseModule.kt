package com.example.pmuprojekat.di

import com.example.pmuprojekat.data.remote.FirebaseAdminAuthRepository
import com.example.pmuprojekat.data.remote.FirebaseAnonymousAuthRepository
import com.example.pmuprojekat.data.remote.FirestoreAdminUsersRepository
import com.example.pmuprojekat.data.remote.FirestoreAdminTaskSubmissionsRepository
import com.example.pmuprojekat.data.remote.FirestoreLeaderboardRepository
import com.example.pmuprojekat.data.remote.FirestoreRemoteTaskRepository
import com.example.pmuprojekat.data.remote.FirestoreTaskSubmissionRemoteRepository
import com.example.pmuprojekat.data.remote.FirestoreUserProgressSyncRepository
import com.example.pmuprojekat.data.repository.AdminAuthRepository
import com.example.pmuprojekat.data.repository.AdminUsersRepository
import com.example.pmuprojekat.data.repository.AdminTaskSubmissionsRepository
import com.example.pmuprojekat.data.repository.FirebaseAuthRepository
import com.example.pmuprojekat.data.repository.LeaderboardRepository
import com.example.pmuprojekat.data.repository.RemoteTaskRepository
import com.example.pmuprojekat.data.repository.TaskSubmissionRemoteRepository
import com.example.pmuprojekat.data.repository.UserProgressSyncRepository
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
    abstract fun bindAdminAuthRepository(
        repository: FirebaseAdminAuthRepository
    ): AdminAuthRepository

    @Binds
    @Singleton
    abstract fun bindUserProgressSyncRepository(
        repository: FirestoreUserProgressSyncRepository
    ): UserProgressSyncRepository

    @Binds
    @Singleton
    abstract fun bindLeaderboardRepository(
        repository: FirestoreLeaderboardRepository
    ): LeaderboardRepository

    @Binds
    @Singleton
    abstract fun bindAdminUsersRepository(
        repository: FirestoreAdminUsersRepository
    ): AdminUsersRepository

    @Binds
    @Singleton
    abstract fun bindAdminTaskSubmissionsRepository(
        repository: FirestoreAdminTaskSubmissionsRepository
    ): AdminTaskSubmissionsRepository

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
