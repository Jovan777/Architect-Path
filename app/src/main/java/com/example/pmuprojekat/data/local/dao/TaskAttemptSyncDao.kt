package com.example.pmuprojekat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pmuprojekat.data.local.entity.TaskAttemptSyncEntity

@Dao
interface TaskAttemptSyncDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(attempt: TaskAttemptSyncEntity)

    @Query(
        """
        SELECT * FROM task_attempt_sync
        WHERE syncStatus = 'PENDING'
          AND (ownerUid IS NULL OR ownerUid = :uid)
        ORDER BY completedAt ASC
        """
    )
    suspend fun getPendingForUser(uid: String): List<TaskAttemptSyncEntity>

    @Query(
        """
        UPDATE task_attempt_sync
        SET ownerUid = :uid
        WHERE attemptId = :attemptId AND ownerUid IS NULL
        """
    )
    suspend fun assignOwnerIfMissing(attemptId: String, uid: String)

    @Query(
        """
        UPDATE task_attempt_sync
        SET syncStatus = 'UPLOADED',
            lastSyncAttemptAt = :syncedAt,
            syncError = NULL
        WHERE attemptId = :attemptId
        """
    )
    suspend fun markUploaded(
        attemptId: String,
        syncedAt: Long = System.currentTimeMillis()
    )

    @Query(
        """
        UPDATE task_attempt_sync
        SET lastSyncAttemptAt = :attemptedAt,
            syncError = :error
        WHERE attemptId = :attemptId AND syncStatus = 'PENDING'
        """
    )
    suspend fun markFailed(
        attemptId: String,
        error: String,
        attemptedAt: Long = System.currentTimeMillis()
    )
}
