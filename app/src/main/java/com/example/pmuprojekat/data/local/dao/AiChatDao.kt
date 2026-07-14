package com.example.pmuprojekat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pmuprojekat.data.local.entity.AiChatConversationEntity
import com.example.pmuprojekat.data.local.entity.AiChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertConversation(conversation: AiChatConversationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: AiChatMessageEntity)

    @Query(
        """
        SELECT * FROM ai_chat_messages
        WHERE conversationId = :conversationId
        ORDER BY createdAt ASC
        """
    )
    fun observeMessages(conversationId: String): Flow<List<AiChatMessageEntity>>

    @Query(
        """
        SELECT * FROM ai_chat_messages
        WHERE conversationId = :conversationId
        ORDER BY createdAt ASC
        """
    )
    suspend fun getMessages(conversationId: String): List<AiChatMessageEntity>

    @Query("DELETE FROM ai_chat_messages WHERE conversationId = :conversationId")
    suspend fun deleteMessages(conversationId: String)

    @Query("UPDATE ai_chat_conversations SET updatedAt = :updatedAt WHERE id = :conversationId")
    suspend fun touchConversation(conversationId: String, updatedAt: Long)

    @Transaction
    suspend fun clearConversation(conversationId: String, updatedAt: Long) {
        deleteMessages(conversationId)
        touchConversation(conversationId, updatedAt)
    }
}
