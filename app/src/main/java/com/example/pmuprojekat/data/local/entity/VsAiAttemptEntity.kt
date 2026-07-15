package com.example.pmuprojekat.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vs_ai_attempts",
    indices = [
        Index(value = ["level"]),
        Index(value = ["status"]),
        Index(value = ["createdAt"])
    ]
)
data class VsAiAttemptEntity(
    @PrimaryKey val id: String,
    val level: String,
    val status: String,
    val createdAt: Long,
    val updatedAt: Long,
    val completedAt: Long?,
    val finalScore: Int?,
    val finalSummary: String?,
    val targetConceptsJson: String,
    val levelContextSnapshotJson: String,
    val learnerContextSnapshotJson: String,
    val relevantTermIdsJson: String,
    val challengeJson: String?,
    val roundsCount: Int,
    val completionReason: String?
)
