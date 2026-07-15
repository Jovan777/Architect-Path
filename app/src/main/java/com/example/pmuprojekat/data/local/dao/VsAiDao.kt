package com.example.pmuprojekat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pmuprojekat.data.local.entity.VsAiAttemptEntity
import com.example.pmuprojekat.data.local.entity.VsAiMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VsAiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAttempt(attempt: VsAiAttemptEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: VsAiMessageEntity)

    @Query("SELECT * FROM vs_ai_attempts ORDER BY createdAt DESC")
    fun observeAttempts(): Flow<List<VsAiAttemptEntity>>

    @Query("SELECT * FROM vs_ai_attempts WHERE id = :attemptId LIMIT 1")
    suspend fun getAttempt(attemptId: String): VsAiAttemptEntity?

    @Query(
        """
        SELECT * FROM vs_ai_attempts
        WHERE level = :level AND status = 'IN_PROGRESS'
        ORDER BY updatedAt DESC
        LIMIT 1
        """
    )
    suspend fun getLatestInProgressAttempt(level: String): VsAiAttemptEntity?

    @Query(
        """
        SELECT * FROM vs_ai_messages
        WHERE attemptId = :attemptId
        ORDER BY createdAt ASC, id ASC
        """
    )
    suspend fun getMessages(attemptId: String): List<VsAiMessageEntity>

    @Query(
        """
        UPDATE vs_ai_attempts
        SET challengeJson = :challengeJson,
            targetConceptsJson = :targetConceptsJson,
            updatedAt = :updatedAt
        WHERE id = :attemptId
        """
    )
    suspend fun updateChallenge(
        attemptId: String,
        challengeJson: String,
        targetConceptsJson: String,
        updatedAt: Long
    )

    @Query(
        """
        UPDATE vs_ai_attempts
        SET roundsCount = :roundsCount,
            updatedAt = :updatedAt
        WHERE id = :attemptId
        """
    )
    suspend fun updateRounds(attemptId: String, roundsCount: Int, updatedAt: Long)

    @Query(
        """
        UPDATE vs_ai_attempts
        SET completionReason = :completionReason,
            updatedAt = :updatedAt
        WHERE id = :attemptId AND status = 'IN_PROGRESS'
        """
    )
    suspend fun markFinishing(
        attemptId: String,
        completionReason: String,
        updatedAt: Long
    )

    @Query(
        """
        UPDATE vs_ai_attempts
        SET status = :status,
            completedAt = :completedAt,
            updatedAt = :completedAt,
            finalScore = :finalScore,
            finalSummary = :finalSummary,
            targetConceptsJson = :targetConceptsJson,
            completionReason = :completionReason
        WHERE id = :attemptId
        """
    )
    suspend fun completeAttempt(
        attemptId: String,
        status: String,
        completedAt: Long,
        finalScore: Int?,
        finalSummary: String?,
        targetConceptsJson: String,
        completionReason: String
    )

    @Transaction
    suspend fun saveChallengeWithMessage(
        attemptId: String,
        challengeJson: String,
        targetConceptsJson: String,
        updatedAt: Long,
        message: VsAiMessageEntity
    ) {
        updateChallenge(attemptId, challengeJson, targetConceptsJson, updatedAt)
        insertMessage(message)
    }

    @Transaction
    suspend fun completeAttemptWithMessage(
        attemptId: String,
        status: String,
        completedAt: Long,
        finalScore: Int?,
        finalSummary: String?,
        targetConceptsJson: String,
        completionReason: String,
        message: VsAiMessageEntity
    ) {
        completeAttempt(
            attemptId = attemptId,
            status = status,
            completedAt = completedAt,
            finalScore = finalScore,
            finalSummary = finalSummary,
            targetConceptsJson = targetConceptsJson,
            completionReason = completionReason
        )
        insertMessage(message)
    }
}
