package com.example.pmuprojekat.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vs_ai_messages",
    foreignKeys = [
        ForeignKey(
            entity = VsAiAttemptEntity::class,
            parentColumns = ["id"],
            childColumns = ["attemptId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["attemptId", "createdAt"])]
)
data class VsAiMessageEntity(
    @PrimaryKey val id: String,
    val attemptId: String,
    val role: String,
    val content: String,
    val createdAt: Long,
    val roundNumber: Int,
    val hiddenEvaluationJson: String?
)
