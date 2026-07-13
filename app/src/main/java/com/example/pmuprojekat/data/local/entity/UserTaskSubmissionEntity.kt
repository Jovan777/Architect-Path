package com.example.pmuprojekat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_task_submissions")
data class UserTaskSubmissionEntity(
    @PrimaryKey
    val id: String,
    val createdAt: Long,
    val updatedAt: Long,
    val level: String,
    val taskType: String,
    val templateId: String,
    val title: String,
    val authorType: String,
    val source: String,
    val reviewStatus: String,
    val publicationTarget: String,
    val schemaVersion: Int,
    val payloadJson: String,
    val localOnly: Boolean,
    val remoteSubmissionId: String?,
    val syncStatus: String,
    val createdByRole: String,
    val publicationMode: String,
    val isPublic: Boolean,
    val approvedAt: Long?,
    val approvedBy: String?,
    val rejectionReason: String?
)
