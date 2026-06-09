package com.example.pmuprojekat.di


import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pmuprojekat.data.local.PMUDatabase
import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.dao.SeedMetaDao
import com.example.pmuprojekat.data.local.dao.UserAnswerDao
import com.example.pmuprojekat.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE users ADD COLUMN hasCompletedOnboarding INTEGER NOT NULL DEFAULT 0"
            )
        }
    }

    private val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE questions ADD COLUMN diagramImageName TEXT"
            )
        }
    }

    private val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE user_question_progress ADD COLUMN bestEarnedXp INTEGER NOT NULL DEFAULT 0"
            )
            database.execSQL(
                """
                UPDATE user_question_progress
                SET bestEarnedXp =
                    CASE
                        WHEN bestScorePercent <= 0 THEN 0
                        WHEN bestScorePercent >= 100 THEN 5
                        ELSE CAST((bestScorePercent * 5.0 / 100.0) + 0.5 AS INTEGER)
                    END
                """.trimIndent()
            )
        }
    }

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
            .addMigrations(MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSeedMetaDao(database: PMUDatabase): SeedMetaDao {
        return database.seedMetaDao()
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
    fun provideUserAnswerDao(database: PMUDatabase): UserAnswerDao {
        return database.userAnswerDao()
    }
}
