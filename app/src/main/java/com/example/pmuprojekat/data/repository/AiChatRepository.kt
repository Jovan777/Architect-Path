package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.ai.AiChatMessageContext
import com.example.pmuprojekat.ai.AiChatRole
import com.example.pmuprojekat.data.local.dao.AiChatDao
import com.example.pmuprojekat.data.local.entity.AiChatConversationEntity
import com.example.pmuprojekat.data.local.entity.AiChatMessageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

data class AiChatMessage(
    val id: String,
    val role: AiChatRole,
    val content: String,
    val createdAt: Long,
    val status: AiChatMessageStatus,
    val relatedTermIds: List<String>
)

enum class AiChatMessageStatus {
    SENT,
    FAILED
}

@Singleton
class AiChatRepository @Inject constructor(
    private val dao: AiChatDao
) {
    fun observeMessages(): Flow<List<AiChatMessage>> {
        return dao.observeMessages(DEFAULT_CONVERSATION_ID)
            .map { messages -> messages.map { it.toDomain() } }
    }

    suspend fun ensureDefaultConversation() {
        val now = System.currentTimeMillis()
        dao.upsertConversation(
            AiChatConversationEntity(
                id = DEFAULT_CONVERSATION_ID,
                title = "Pričaj sa AI",
                createdAt = now,
                updatedAt = now
            )
        )
    }

    suspend fun getRecentMessageContexts(limit: Int): List<AiChatMessageContext> {
        return dao.getMessages(DEFAULT_CONVERSATION_ID)
            .takeLast(limit)
            .map { entity ->
                AiChatMessageContext(
                    role = entity.role.toChatRole(),
                    content = entity.content
                )
            }
    }

    suspend fun addUserMessage(
        content: String,
        relatedTermIds: List<String>
    ) {
        insertMessage(
            role = AiChatRole.USER,
            content = content,
            status = AiChatMessageStatus.SENT,
            relatedTermIds = relatedTermIds
        )
    }

    suspend fun addAssistantMessage(
        content: String,
        relatedTermIds: List<String>,
        status: AiChatMessageStatus = AiChatMessageStatus.SENT
    ) {
        insertMessage(
            role = AiChatRole.ASSISTANT,
            content = content,
            status = status,
            relatedTermIds = relatedTermIds
        )
    }

    suspend fun clearConversation() {
        dao.clearConversation(DEFAULT_CONVERSATION_ID, System.currentTimeMillis())
    }

    private suspend fun insertMessage(
        role: AiChatRole,
        content: String,
        status: AiChatMessageStatus,
        relatedTermIds: List<String>
    ) {
        val now = System.currentTimeMillis()
        dao.insertMessage(
            AiChatMessageEntity(
                id = UUID.randomUUID().toString(),
                conversationId = DEFAULT_CONVERSATION_ID,
                role = role.name,
                content = content,
                createdAt = now,
                status = status.name,
                relatedTermIds = relatedTermIds.joinToString(",").ifBlank { null }
            )
        )
        dao.touchConversation(DEFAULT_CONVERSATION_ID, now)
    }

    private fun AiChatMessageEntity.toDomain(): AiChatMessage {
        return AiChatMessage(
            id = id,
            role = role.toChatRole(),
            content = content,
            createdAt = createdAt,
            status = runCatching { AiChatMessageStatus.valueOf(status) }
                .getOrDefault(AiChatMessageStatus.SENT),
            relatedTermIds = relatedTermIds
                ?.split(',')
                ?.filter { it.isNotBlank() }
                .orEmpty()
        )
    }

    private fun String.toChatRole(): AiChatRole {
        return runCatching { AiChatRole.valueOf(this) }
            .getOrDefault(AiChatRole.ASSISTANT)
    }

    companion object {
        const val DEFAULT_CONVERSATION_ID = "default_ai_chat"
    }
}
