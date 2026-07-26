package com.example.pmuprojekat.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "task_attempt_sync",
    indices = [
        Index(value = ["syncStatus"]),
        Index(value = ["ownerUid"]),
        Index(value = ["completedAt"])
    ]
)
data class TaskAttemptSyncEntity(
    @PrimaryKey
    val attemptId: String,
    val taskId: String,
    val taskTitle: String,
    val level: String,
    val taskType: String,
    val taskSource: String,
    val percentage: Int,
    val pointsAwarded: Int,
    val attemptNumber: Int,
    val completedAt: Long,
    val ownerUid: String? = null,
    val syncStatus: String = STATUS_PENDING,
    val lastSyncAttemptAt: Long? = null,
    val syncError: String? = null
) {
    companion object {
        const val STATUS_PENDING = "PENDING"
        const val STATUS_UPLOADED = "UPLOADED"
    }
}
