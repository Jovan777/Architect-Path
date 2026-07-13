package com.example.pmuprojekat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pmuprojekat.data.local.entity.UserTaskSubmissionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserTaskSubmissionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSubmission(submission: UserTaskSubmissionEntity)

    @Query("SELECT * FROM user_task_submissions ORDER BY createdAt DESC")
    fun observeSubmissions(): Flow<List<UserTaskSubmissionEntity>>

    @Query("SELECT * FROM user_task_submissions WHERE id = :submissionId")
    suspend fun getSubmissionById(submissionId: String): UserTaskSubmissionEntity?
}
