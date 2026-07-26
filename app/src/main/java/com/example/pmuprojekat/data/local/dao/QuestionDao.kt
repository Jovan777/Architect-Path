package com.example.pmuprojekat.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pmuprojekat.data.local.entity.CodeBlankEntity
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.QuestionStepEntity
import com.example.pmuprojekat.data.local.entity.StepOptionEntity
import com.example.pmuprojekat.data.local.entity.StepZoneEntity
import com.example.pmuprojekat.data.local.relation.QuestionWithSteps
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun countQuestions(): Int

    @Query("""
        SELECT * FROM questions
        WHERE isActive = 1 AND publicationMode != 'USER_TASKS'
        ORDER BY level ASC, wave ASC, orderIndex ASC
    """)
    fun observeAllQuestions(): Flow<List<QuestionEntity>>

    @Query("""
        SELECT * FROM questions
        WHERE level = :level AND isActive = 1 AND publicationMode != 'USER_TASKS'
        ORDER BY wave ASC, orderIndex ASC
    """)
    fun observeQuestionsByLevel(level: String): Flow<List<QuestionEntity>>

    @Query("""
        SELECT * FROM questions
        WHERE isActive = 1
          AND publicationMode = 'USER_TASKS'
          AND source = 'REMOTE_USER_APPROVED'
        ORDER BY level ASC, wave ASC, orderIndex ASC
    """)
    fun observeApprovedUserQuestions(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE questionId = :questionId LIMIT 1")
    suspend fun getQuestion(questionId: String): QuestionEntity?

    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<QuestionEntity>

    @Query("""
        SELECT * FROM questions
        WHERE isActive = 1 AND publicationMode != 'USER_TASKS'
    """)
    suspend fun getAllMainQuestions(): List<QuestionEntity>

    @Query("""
        SELECT * FROM questions
        WHERE remoteDocumentPath LIKE :pathPrefix || '%'
    """)
    suspend fun getRemoteQuestionsByPathPrefix(pathPrefix: String): List<QuestionEntity>

    @Query("DELETE FROM questions WHERE questionId IN (:questionIds)")
    suspend fun deleteQuestionsByIds(questionIds: List<String>)

    @Transaction
    @Query("SELECT * FROM questions WHERE questionId = :questionId LIMIT 1")
    fun observeQuestionWithSteps(questionId: String): Flow<QuestionWithSteps?>

    @Transaction
    @Query("SELECT * FROM questions WHERE questionId = :questionId LIMIT 1")
    suspend fun getQuestionWithSteps(questionId: String): QuestionWithSteps?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(steps: List<QuestionStepEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptions(options: List<StepOptionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZones(zones: List<StepZoneEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlanks(blanks: List<CodeBlankEntity>)

    @Query("DELETE FROM code_blanks")
    suspend fun clearBlanks()

    @Query("DELETE FROM step_options")
    suspend fun clearOptions()

    @Query("DELETE FROM step_zones")
    suspend fun clearZones()

    @Query("DELETE FROM question_steps")
    suspend fun clearSteps()

    @Query("DELETE FROM questions")
    suspend fun clearQuestions()

    @Transaction
    suspend fun clearAllQuestionData() {
        clearBlanks()
        clearOptions()
        clearZones()
        clearSteps()
        clearQuestions()
    }
}
