package com.example.pmuprojekat.di


import android.content.Context
import androidx.room.Room
import com.example.pmuprojekat.data.local.PMUDatabase
import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.dao.SeedMetaDao
import com.example.pmuprojekat.data.local.dao.UserAnswerDao
import com.example.pmuprojekat.data.local.dao.UserDao
import com.example.pmuprojekat.data.seed.SeedInserter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PMUDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = PMUDatabase::class.java,
            name = "pmu_learning.db"
        )
            /*
             * Dok si u razvoju, ovo je praktično.
             * Kasnije, kada se model stabilizuje, zameni pravim migracijama.
             */
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSeedInserter(
        userDao: UserDao,
        questionDao: QuestionDao
    ): SeedInserter {
        return SeedInserter(
            userDao = userDao,
            questionDao = questionDao
        )
    }

    @Provides
    fun provideUserDao(database: PMUDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideQuestionDao(database: PMUDatabase): QuestionDao {
        return database.questionDao()
    }

    @Provides
    fun provideSeedMetaDao(database: PMUDatabase): SeedMetaDao {
        return database.seedMetaDao()
    }

    @Provides
    fun provideUserAnswerDao(database: PMUDatabase): UserAnswerDao {
        return database.userAnswerDao()
    }
}