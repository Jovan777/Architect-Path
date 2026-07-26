package com.example.pmuprojekat.di

import com.example.pmuprojekat.data.repository.StudyReminderScheduler
import com.example.pmuprojekat.notification.WorkManagerStudyReminderScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StudyReminderModule {

    @Binds
    @Singleton
    abstract fun bindStudyReminderScheduler(
        implementation: WorkManagerStudyReminderScheduler
    ): StudyReminderScheduler
}
