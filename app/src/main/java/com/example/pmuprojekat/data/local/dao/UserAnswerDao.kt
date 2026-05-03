package com.example.pmuprojekat.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
        SELECT * FROM user_step_answers
        WHERE userId = :userId AND questionId = :questionId
    """)
    suspend fun getAnswersForQuestion(
        userId: String,
        questionId: String
    ): List<UserStepAnswerEntity>
}