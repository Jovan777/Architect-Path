package com.example.pmuprojekat.di


import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pmuprojekat.data.local.PMUDatabase
import com.example.pmuprojekat.data.local.dao.AiChatDao
import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.dao.SeedMetaDao
import com.example.pmuprojekat.data.local.dao.TaskAttemptSyncDao
import com.example.pmuprojekat.data.local.dao.UserAnswerDao
import com.example.pmuprojekat.data.local.dao.UserDao
import com.example.pmuprojekat.data.local.dao.UserTaskSubmissionDao
import com.example.pmuprojekat.data.local.dao.VsAiDao
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

    private val MIGRATION_5_6 = object : Migration(5, 6) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS user_task_submissions (
                    id TEXT NOT NULL PRIMARY KEY,
                    createdAt INTEGER NOT NULL,
                    updatedAt INTEGER NOT NULL,
                    level TEXT NOT NULL,
                    taskType TEXT NOT NULL,
                    templateId TEXT NOT NULL,
                    title TEXT NOT NULL,
                    authorType TEXT NOT NULL,
                    source TEXT NOT NULL,
                    reviewStatus TEXT NOT NULL,
                    publicationTarget TEXT NOT NULL,
                    schemaVersion INTEGER NOT NULL,
                    payloadJson TEXT NOT NULL,
                    localOnly INTEGER NOT NULL,
                    remoteSubmissionId TEXT,
                    syncStatus TEXT NOT NULL,
                    createdByRole TEXT NOT NULL,
                    publicationMode TEXT NOT NULL,
                    isPublic INTEGER NOT NULL,
                    approvedAt INTEGER,
                    approvedBy TEXT,
                    rejectionReason TEXT
                )
                """.trimIndent()
            )
        }
    }

    private val MIGRATION_6_7 = object : Migration(6, 7) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE questions ADD COLUMN source TEXT NOT NULL DEFAULT 'LOCAL_SEED'"
            )
            database.execSQL(
                "ALTER TABLE questions ADD COLUMN publicationMode TEXT NOT NULL DEFAULT 'MAIN_TASK_LIST'"
            )
            database.execSQL(
                "ALTER TABLE questions ADD COLUMN remoteDocumentPath TEXT"
            )
            database.execSQL(
                "ALTER TABLE questions ADD COLUMN remoteUpdatedAt INTEGER"
            )
        }
    }

    private val MIGRATION_7_8 = object : Migration(7, 8) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS ai_chat_conversations (
                    id TEXT NOT NULL PRIMARY KEY,
                    title TEXT,
                    createdAt INTEGER NOT NULL,
                    updatedAt INTEGER NOT NULL
                )
                """.trimIndent()
            )
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS ai_chat_messages (
                    id TEXT NOT NULL PRIMARY KEY,
                    conversationId TEXT NOT NULL,
                    role TEXT NOT NULL,
                    content TEXT NOT NULL,
                    createdAt INTEGER NOT NULL,
                    status TEXT NOT NULL,
                    relatedTermIds TEXT
                )
                """.trimIndent()
            )
            database.execSQL(
                """
                CREATE INDEX IF NOT EXISTS index_ai_chat_messages_conversationId_createdAt
                ON ai_chat_messages(conversationId, createdAt)
                """.trimIndent()
            )
        }
    }

    private val MIGRATION_8_9 = object : Migration(8, 9) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS vs_ai_attempts (
                    id TEXT NOT NULL PRIMARY KEY,
                    level TEXT NOT NULL,
                    status TEXT NOT NULL,
                    createdAt INTEGER NOT NULL,
                    updatedAt INTEGER NOT NULL,
                    completedAt INTEGER,
                    finalScore INTEGER,
                    finalSummary TEXT,
                    targetConceptsJson TEXT NOT NULL,
                    levelContextSnapshotJson TEXT NOT NULL,
                    learnerContextSnapshotJson TEXT NOT NULL,
                    relevantTermIdsJson TEXT NOT NULL,
                    challengeJson TEXT,
                    roundsCount INTEGER NOT NULL,
                    completionReason TEXT
                )
                """.trimIndent()
            )
            database.execSQL(
                "CREATE INDEX IF NOT EXISTS index_vs_ai_attempts_level ON vs_ai_attempts(level)"
            )
            database.execSQL(
                "CREATE INDEX IF NOT EXISTS index_vs_ai_attempts_status ON vs_ai_attempts(status)"
            )
            database.execSQL(
                "CREATE INDEX IF NOT EXISTS index_vs_ai_attempts_createdAt ON vs_ai_attempts(createdAt)"
            )
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS vs_ai_messages (
                    id TEXT NOT NULL PRIMARY KEY,
                    attemptId TEXT NOT NULL,
                    role TEXT NOT NULL,
                    content TEXT NOT NULL,
                    createdAt INTEGER NOT NULL,
                    roundNumber INTEGER NOT NULL,
                    hiddenEvaluationJson TEXT,
                    FOREIGN KEY(attemptId) REFERENCES vs_ai_attempts(id) ON UPDATE NO ACTION ON DELETE CASCADE
                )
                """.trimIndent()
            )
            database.execSQL(
                """
                CREATE INDEX IF NOT EXISTS index_vs_ai_messages_attemptId_createdAt
                ON vs_ai_messages(attemptId, createdAt)
                """.trimIndent()
            )
        }
    }

    private val MIGRATION_9_10 = object : Migration(9, 10) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE questions ADD COLUMN diagramImageLocalUri TEXT")
            database.execSQL("ALTER TABLE questions ADD COLUMN diagramImageLocalPath TEXT")
            database.execSQL("ALTER TABLE questions ADD COLUMN diagramImageRemoteStoragePath TEXT")
            database.execSQL("ALTER TABLE questions ADD COLUMN diagramImageDownloadUrl TEXT")
        }
    }

    private val MIGRATION_10_11 = object : Migration(10, 11) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS task_attempt_sync (
                    attemptId TEXT NOT NULL PRIMARY KEY,
                    taskId TEXT NOT NULL,
                    taskTitle TEXT NOT NULL,
                    level TEXT NOT NULL,
                    taskType TEXT NOT NULL,
                    taskSource TEXT NOT NULL,
                    percentage INTEGER NOT NULL,
                    pointsAwarded INTEGER NOT NULL,
                    attemptNumber INTEGER NOT NULL,
                    completedAt INTEGER NOT NULL,
                    ownerUid TEXT,
                    syncStatus TEXT NOT NULL,
                    lastSyncAttemptAt INTEGER,
                    syncError TEXT
                )
                """.trimIndent()
            )
            database.execSQL(
                "CREATE INDEX IF NOT EXISTS index_task_attempt_sync_syncStatus ON task_attempt_sync(syncStatus)"
            )
            database.execSQL(
                "CREATE INDEX IF NOT EXISTS index_task_attempt_sync_ownerUid ON task_attempt_sync(ownerUid)"
            )
            database.execSQL(
                "CREATE INDEX IF NOT EXISTS index_task_attempt_sync_completedAt ON task_attempt_sync(completedAt)"
            )
        }
    }

    private val MIGRATION_11_12 = object : Migration(11, 12) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE users ADD COLUMN longestStreak INTEGER NOT NULL DEFAULT 0"
            )
            database.execSQL(
                "ALTER TABLE users ADD COLUMN lastQualifyingTaskDate TEXT"
            )
            database.execSQL(
                "ALTER TABLE users ADD COLUMN lastQualifyingTaskCompletedAt INTEGER"
            )
            database.execSQL(
                "ALTER TABLE users ADD COLUMN dailyCompletionDate TEXT"
            )
            database.execSQL(
                "ALTER TABLE users ADD COLUMN tasksCompletedToday INTEGER NOT NULL DEFAULT 0"
            )
            database.execSQL(
                "ALTER TABLE users ADD COLUMN lastCompletedWaveDate TEXT"
            )
            database.execSQL(
                "ALTER TABLE users ADD COLUMN lastReminderNotificationDate TEXT"
            )
            database.execSQL(
                "ALTER TABLE users ADD COLUMN notificationPermissionAsked INTEGER NOT NULL DEFAULT 0"
            )
            database.execSQL(
                "UPDATE users SET longestStreak = streakDays WHERE streakDays > longestStreak"
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
            .addMigrations(
                MIGRATION_2_3,
                MIGRATION_3_4,
                MIGRATION_4_5,
                MIGRATION_5_6,
                MIGRATION_6_7,
                MIGRATION_7_8,
                MIGRATION_8_9,
                MIGRATION_9_10,
                MIGRATION_10_11,
                MIGRATION_11_12
            )
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

    @Provides
    fun provideUserTaskSubmissionDao(database: PMUDatabase): UserTaskSubmissionDao {
        return database.userTaskSubmissionDao()
    }

    @Provides
    fun provideAiChatDao(database: PMUDatabase): AiChatDao {
        return database.aiChatDao()
    }

    @Provides
    fun provideVsAiDao(database: PMUDatabase): VsAiDao {
        return database.vsAiDao()
    }

    @Provides
    fun provideTaskAttemptSyncDao(database: PMUDatabase): TaskAttemptSyncDao {
        return database.taskAttemptSyncDao()
    }
}
