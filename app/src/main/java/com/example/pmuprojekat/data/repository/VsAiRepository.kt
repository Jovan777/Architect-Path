package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.ai.AiChatRelevantTerm
import com.example.pmuprojekat.ai.VsAiCompletionReason
import com.example.pmuprojekat.ai.VsAiEvaluationResult
import com.example.pmuprojekat.ai.VsAiFinalAnalysis
import com.example.pmuprojekat.ai.VsAiGeneratedChallenge
import com.example.pmuprojekat.ai.VsAiLearnerContext
import com.example.pmuprojekat.ai.VsAiLevelContext
import com.example.pmuprojekat.ai.VsAiResponseParser
import com.example.pmuprojekat.data.local.dao.VsAiDao
import com.example.pmuprojekat.data.local.entity.VsAiAttemptEntity
import com.example.pmuprojekat.data.local.entity.VsAiMessageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

enum class VsAiAttemptStatus {
    IN_PROGRESS,
    COMPLETED,
    STOPPED_BY_USER,
    FAILED
}

enum class VsAiMessageRole {
    AI_QUESTION,
    USER_ANSWER,
    AI_FOLLOW_UP,
    AI_FINAL_ANALYSIS,
    SYSTEM
}

data class VsAiAttempt(
    val id: String,
    val level: String,
    val status: VsAiAttemptStatus,
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
    val completionReason: VsAiCompletionReason?
)

data class VsAiMessage(
    val id: String,
    val attemptId: String,
    val role: VsAiMessageRole,
    val content: String,
    val createdAt: Long,
    val roundNumber: Int,
    val hiddenEvaluationJson: String?
)

data class VsAiSessionSnapshot(
    val attempt: VsAiAttempt,
    val messages: List<VsAiMessage>,
    val challenge: VsAiGeneratedChallenge
)

@Singleton
class VsAiRepository @Inject constructor(
    private val dao: VsAiDao
) {
    fun observeAttempts(): Flow<List<VsAiAttempt>> {
        return dao.observeAttempts().map { attempts -> attempts.map { it.toDomain() } }
    }

    suspend fun createAttempt(
        levelContext: VsAiLevelContext,
        learnerContext: VsAiLearnerContext,
        relevantTerms: List<AiChatRelevantTerm>
    ): VsAiAttempt {
        val now = System.currentTimeMillis()
        val entity = VsAiAttemptEntity(
            id = UUID.randomUUID().toString(),
            level = levelContext.levelId,
            status = VsAiAttemptStatus.IN_PROGRESS.name,
            createdAt = now,
            updatedAt = now,
            completedAt = null,
            finalScore = null,
            finalSummary = null,
            targetConceptsJson = "[]",
            levelContextSnapshotJson = VsAiPersistenceCodec.encodeLevelContext(levelContext),
            learnerContextSnapshotJson = VsAiPersistenceCodec.encodeLearnerContext(learnerContext),
            relevantTermIdsJson = VsAiPersistenceCodec.encodeStringList(relevantTerms.map { it.id }),
            challengeJson = null,
            roundsCount = 0,
            completionReason = null
        )
        dao.upsertAttempt(entity)
        return entity.toDomain()
    }

    suspend fun saveChallenge(attemptId: String, challenge: VsAiGeneratedChallenge): VsAiMessage {
        val now = System.currentTimeMillis()
        val message = newMessageEntity(
            attemptId = attemptId,
            role = VsAiMessageRole.AI_QUESTION,
            content = challenge.question,
            roundNumber = 1,
            hiddenEvaluationJson = VsAiPersistenceCodec.encodeChallenge(challenge),
            createdAt = now
        )
        dao.saveChallengeWithMessage(
            attemptId = attemptId,
            challengeJson = VsAiPersistenceCodec.encodeChallenge(challenge),
            targetConceptsJson = VsAiPersistenceCodec.encodeStringList(listOf(challenge.targetConcept)),
            updatedAt = now,
            message = message
        )
        return message.toDomain()
    }

    suspend fun addMessage(
        attemptId: String,
        role: VsAiMessageRole,
        content: String,
        roundNumber: Int,
        hiddenEvaluationJson: String? = null
    ): VsAiMessage {
        val message = newMessageEntity(
            attemptId = attemptId,
            role = role,
            content = content,
            roundNumber = roundNumber,
            hiddenEvaluationJson = hiddenEvaluationJson,
            createdAt = System.currentTimeMillis()
        )
        dao.insertMessage(message)
        return message.toDomain()
    }

    suspend fun updateRounds(attemptId: String, roundsCount: Int) {
        dao.updateRounds(attemptId, roundsCount, System.currentTimeMillis())
    }

    suspend fun markFinishing(attemptId: String, completionReason: VsAiCompletionReason) {
        dao.markFinishing(
            attemptId = attemptId,
            completionReason = completionReason.name,
            updatedAt = System.currentTimeMillis()
        )
    }

    suspend fun completeAttempt(
        attemptId: String,
        status: VsAiAttemptStatus,
        completionReason: VsAiCompletionReason,
        analysis: VsAiFinalAnalysis,
        targetConcepts: List<String>,
        roundNumber: Int
    ): VsAiMessage {
        val now = System.currentTimeMillis()
        val message = newMessageEntity(
            attemptId = attemptId,
            role = VsAiMessageRole.AI_FINAL_ANALYSIS,
            content = analysis.toPlainText(),
            roundNumber = roundNumber,
            hiddenEvaluationJson = null,
            createdAt = now
        )
        dao.completeAttemptWithMessage(
            attemptId = attemptId,
            status = status.name,
            completedAt = now,
            finalScore = analysis.score,
            finalSummary = analysis.resultSummary,
            targetConceptsJson = VsAiPersistenceCodec.encodeStringList(targetConcepts),
            completionReason = completionReason.name,
            message = message
        )
        return message.toDomain()
    }

    suspend fun markFailed(attemptId: String, summary: String) {
        dao.completeAttempt(
            attemptId = attemptId,
            status = VsAiAttemptStatus.FAILED.name,
            completedAt = System.currentTimeMillis(),
            finalScore = null,
            finalSummary = summary,
            targetConceptsJson = "[]",
            completionReason = VsAiCompletionReason.ERROR.name
        )
    }

    suspend fun markStoppedWithoutAnalysis(attemptId: String, summary: String) {
        dao.completeAttempt(
            attemptId = attemptId,
            status = VsAiAttemptStatus.STOPPED_BY_USER.name,
            completedAt = System.currentTimeMillis(),
            finalScore = null,
            finalSummary = summary,
            targetConceptsJson = "[]",
            completionReason = VsAiCompletionReason.USER_STOPPED.name
        )
    }

    suspend fun loadLatestInProgress(levelId: String): VsAiSessionSnapshot? {
        val attemptEntity = dao.getLatestInProgressAttempt(levelId) ?: return null
        val challengeJson = attemptEntity.challengeJson ?: return null
        val challenge = runCatching { VsAiResponseParser.parseChallenge(challengeJson) }.getOrNull()
            ?: return null
        return VsAiSessionSnapshot(
            attempt = attemptEntity.toDomain(),
            messages = dao.getMessages(attemptEntity.id).map { it.toDomain() },
            challenge = challenge
        )
    }

    suspend fun getMessages(attemptId: String): List<VsAiMessage> {
        return dao.getMessages(attemptId).map { it.toDomain() }
    }

    private fun newMessageEntity(
        attemptId: String,
        role: VsAiMessageRole,
        content: String,
        roundNumber: Int,
        hiddenEvaluationJson: String?,
        createdAt: Long
    ): VsAiMessageEntity {
        return VsAiMessageEntity(
            id = UUID.randomUUID().toString(),
            attemptId = attemptId,
            role = role.name,
            content = content,
            createdAt = createdAt,
            roundNumber = roundNumber,
            hiddenEvaluationJson = hiddenEvaluationJson
        )
    }

    private fun VsAiAttemptEntity.toDomain(): VsAiAttempt {
        return VsAiAttempt(
            id = id,
            level = level,
            status = enumValueOrDefault(status, VsAiAttemptStatus.IN_PROGRESS),
            createdAt = createdAt,
            updatedAt = updatedAt,
            completedAt = completedAt,
            finalScore = finalScore,
            finalSummary = finalSummary,
            targetConceptsJson = targetConceptsJson,
            levelContextSnapshotJson = levelContextSnapshotJson,
            learnerContextSnapshotJson = learnerContextSnapshotJson,
            relevantTermIdsJson = relevantTermIdsJson,
            challengeJson = challengeJson,
            roundsCount = roundsCount,
            completionReason = completionReason?.let {
                enumValueOrDefault(it, VsAiCompletionReason.ERROR)
            }
        )
    }

    private fun VsAiMessageEntity.toDomain(): VsAiMessage {
        return VsAiMessage(
            id = id,
            attemptId = attemptId,
            role = enumValueOrDefault(role, VsAiMessageRole.SYSTEM),
            content = content,
            createdAt = createdAt,
            roundNumber = roundNumber,
            hiddenEvaluationJson = hiddenEvaluationJson
        )
    }

    private inline fun <reified T : Enum<T>> enumValueOrDefault(value: String, default: T): T {
        return runCatching { enumValueOf<T>(value) }.getOrDefault(default)
    }
}
