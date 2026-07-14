package com.example.pmuprojekat.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_chat_messages",
    indices = [Index(value = ["conversationId", "createdAt"])]
)
data class AiChatMessageEntity(
    @PrimaryKey val id: String,
    val conversationId: String,
    val role: String,
    val content: String,
    val createdAt: Long,
    val status: String,
    val relatedTermIds: String?
)
