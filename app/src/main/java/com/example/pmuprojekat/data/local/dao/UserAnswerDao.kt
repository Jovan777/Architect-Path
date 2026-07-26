package com.example.pmuprojekat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pmuprojekat.data.local.entity.UserQuestionProgressEntity
import com.example.pmuprojekat.data.local.entity.UserStepAnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStepAnswer(answer: UserStepAnswerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQuestionProgress(progress: UserQuestionProgressEntity)

    @Query("""
        SELECT * FROM user_question_progress
        WHERE userId = :userId
        ORDER BY updatedAt DESC
    """)
    fun observeUserProgress(userId: String): Flow<List<UserQuestionProgressEntity>>

    @Query("""
        SELECT * FROM user_question_progress
        WHERE userId = :userId
        ORDER BY updatedAt DESC
    """)
    suspend fun getUserProgress(userId: String): List<UserQuestionProgressEntity>

    @Query("""
        SELECT * FROM user_question_progress
        WHERE userId = :userId AND questionId = :questionId
        LIMIT 1
    """)
    suspend fun getQuestionProgress(
        userId: String,
        questionId: String
    ): UserQuestionProgressEntity?

    @Query("""
        SELECT COUNT(*)
        FROM questions AS question
        LEFT JOIN user_question_progress AS progress
          ON progress.questionId = question.questionId
          AND progress.userId = :userId
        WHERE question.level = :level
          AND question.wave = :wave
          AND question.isActive = 1
          AND question.publicationMode != 'USER_TASKS'
          AND (progress.status IS NULL OR progress.status != 'completed')
    """)
    suspend fun countIncompleteQuestionsInWave(
        userId: String,
        level: String,
        wave: Int
    ): Int

    @Query("""
        SELECT * FROM user_step_answers
        WHERE userId = :userId AND questionId = :questionId
    """)
    suspend fun getAnswersForQuestion(
        userId: String,
        questionId: String
    ): List<UserStepAnswerEntity>

    @Query("DELETE FROM user_step_answers WHERE userId = :userId")
    suspend fun clearStepAnswersForUser(userId: String)

    @Query("DELETE FROM user_question_progress WHERE userId = :userId")
    suspend fun clearQuestionProgressForUser(userId: String)

    @Transaction
    suspend fun clearUserProgress(userId: String) {
        clearStepAnswersForUser(userId)
        clearQuestionProgressForUser(userId)
    }
}
