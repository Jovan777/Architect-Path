package com.example.pmuprojekat.data.remote

import android.util.Log
import androidx.room.withTransaction
import com.example.pmuprojekat.data.local.PMUDatabase
import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.relation.QuestionWithSteps
import com.example.pmuprojekat.data.repository.FirebaseAuthRepository
import com.example.pmuprojekat.data.repository.RemoteTaskRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRemoteTaskRepository @Inject constructor(
    private val database: PMUDatabase,
    private val questionDao: QuestionDao,
    private val clients: FirebaseClientProvider,
    private val authRepository: FirebaseAuthRepository,
    private val mapper: RemoteTaskMapper
) : RemoteTaskRepository {

    private val _syncState = MutableStateFlow(RemoteTaskSyncState())
    override val syncState: StateFlow<RemoteTaskSyncState> = _syncState.asStateFlow()

    override suspend fun refreshRemoteTasks(): Result<Unit> {
        _syncState.value = _syncState.value.copy(isLoading = true, errorMessage = null)

        val authResult = authRepository.ensureSignedInAnonymously()
        if (authResult.isFailure) {
            val message = authResult.exceptionOrNull().toOnlineErrorMessage()
            _syncState.value = RemoteTaskSyncState(isLoading = false, errorMessage = message)
            return Result.failure(authResult.exceptionOrNull() ?: IllegalStateException(message))
        }

        val firestoreResult = clients.firestore()
        if (firestoreResult.isFailure) {
            val message = firestoreResult.exceptionOrNull().toOnlineErrorMessage()
            _syncState.value = RemoteTaskSyncState(isLoading = false, errorMessage = message)
            return Result.failure(firestoreResult.exceptionOrNull() ?: IllegalStateException(message))
        }

        val firestore = firestoreResult.getOrThrow()
        val errors = mutableListOf<String>()
        var skippedDocuments = 0

        runCatching {
            val snapshot = firestore.collection(REMOTE_TASKS_COLLECTION)
                .whereEqualTo("status", "ACTIVE")
                .get()
                .awaitResult()
            val mapped = snapshot.documents.mapNotNull { document ->
                val result = mapper.toPlayableQuestion(document.toRemoteTaskDocument())
                result.exceptionOrNull()?.let { error ->
                    skippedDocuments += 1
                    Log.w(TAG, "Preskačem ${document.reference.path}: ${error.message}")
                }
                result.getOrNull()
            }
            syncCollection(REMOTE_TASKS_COLLECTION, mapped)
        }.onFailure { error ->
            errors += "Udaljeni zadaci nisu osveženi: ${error.message.orEmpty()}"
            Log.w(TAG, "Neuspešno čitanje remote_tasks.", error)
        }

        runCatching {
            val snapshot = firestore.collection(TASK_SUBMISSIONS_COLLECTION)
                .whereEqualTo("reviewStatus", "APPROVED")
                .whereEqualTo("isPublic", true)
                .get()
                .awaitResult()
            val mapped = snapshot.documents.mapNotNull { document ->
                val result = mapper.toPlayableQuestion(document.toApprovedSubmissionDocument())
                result.exceptionOrNull()?.let { error ->
                    skippedDocuments += 1
                    Log.w(TAG, "Preskačem ${document.reference.path}: ${error.message}")
                }
                result.getOrNull()
            }
            syncCollection(TASK_SUBMISSIONS_COLLECTION, mapped)
        }.onFailure { error ->
            errors += "Odobreni korisnički zadaci nisu osveženi: ${error.message.orEmpty()}"
            Log.w(TAG, "Neuspešno čitanje task_submissions.", error)
        }

        val message = errors.takeIf(List<String>::isNotEmpty)?.joinToString(" ")
        _syncState.value = RemoteTaskSyncState(
            isLoading = false,
            errorMessage = message,
            lastUpdatedAt = System.currentTimeMillis(),
            skippedDocuments = skippedDocuments
        )

        if (errors.isEmpty()) {
            Log.i(TAG, "Online zadaci su osveženi; preskočeno dokumenata: $skippedDocuments.")
        }

        return if (errors.isEmpty()) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalStateException(message))
        }
    }

    private suspend fun syncCollection(
        collection: String,
        questions: List<QuestionWithSteps>
    ) {
        database.withTransaction {
            val pathPrefix = "$collection/"
            val existing = questionDao.getRemoteQuestionsByPathPrefix(pathPrefix)
            val incomingIds = questions.map { it.question.questionId }.toSet()
            val staleIds = existing.map { it.questionId }.filterNot(incomingIds::contains)
            if (staleIds.isNotEmpty()) {
                questionDao.deleteQuestionsByIds(staleIds)
            }

            val existingById = existing.associateBy { it.questionId }
            val changed = questions.filter { incoming ->
                val cached = existingById[incoming.question.questionId]
                cached == null ||
                    cached.remoteUpdatedAt != incoming.question.remoteUpdatedAt ||
                    cached.title != incoming.question.title ||
                    cached.prompt != incoming.question.prompt
            }
            if (changed.isEmpty()) return@withTransaction

            questionDao.insertQuestions(changed.map(QuestionWithSteps::question))
            questionDao.insertSteps(changed.flatMap { question ->
                question.steps.map { step -> step.step }
            })
            questionDao.insertZones(changed.flatMap { question ->
                question.steps.flatMap { step -> step.zones }
            })
            questionDao.insertOptions(changed.flatMap { question ->
                question.steps.flatMap { step -> step.options }
            })
            questionDao.insertBlanks(changed.flatMap { question ->
                question.steps.flatMap { step -> step.blanks }
            })
        }
    }

    private fun DocumentSnapshot.toRemoteTaskDocument(): RemoteTaskDocument {
        val data = data.orEmpty()
        return RemoteTaskDocument(
            documentId = id,
            collection = REMOTE_TASKS_COLLECTION,
            title = data["title"] as? String ?: id,
            level = data["level"] as? String ?: "",
            taskType = data["taskType"] as? String ?: "",
            source = data["source"] as? String ?: "ADMIN",
            status = data["status"] as? String ?: "HIDDEN",
            publicationMode = data["publicationMode"] as? String ?: "MAIN_TASK_LIST",
            schemaVersion = (data["schemaVersion"] as? Number)?.toInt() ?: 1,
            updatedAt = data["updatedAt"].toEpochMillis(),
            payload = data["payload"].asStringMap()
                ?: (data["payloadJson"] as? String)?.toJsonMap()
                ?: emptyMap()
        )
    }

    private fun DocumentSnapshot.toApprovedSubmissionDocument(): RemoteTaskDocument {
        val data = data.orEmpty()
        return RemoteTaskDocument(
            documentId = id,
            collection = TASK_SUBMISSIONS_COLLECTION,
            title = data["title"] as? String ?: id,
            level = data["level"] as? String ?: "",
            taskType = data["taskType"] as? String ?: "",
            source = "USER_APPROVED",
            status = "ACTIVE",
            publicationMode = "USER_TASKS",
            schemaVersion = (data["schemaVersion"] as? Number)?.toInt() ?: 1,
            updatedAt = data["updatedAt"].toEpochMillis(),
            payload = data["payload"].asStringMap()
                ?: (data["payloadJson"] as? String)?.toJsonMap()
                ?: emptyMap()
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun Any?.asStringMap(): Map<String, Any?>? = this as? Map<String, Any?>

    private fun Any?.toEpochMillis(): Long? = when (this) {
        is Timestamp -> toDate().time
        is Number -> toLong()
        else -> null
    }

    private fun Throwable?.toOnlineErrorMessage(): String {
        return this?.message?.takeIf(String::isNotBlank)
            ?: "Povezivanje sa internet bazom nije uspelo. Lokalni zadaci su i dalje dostupni."
    }

    companion object {
        private const val TAG = "RemoteTaskRepository"
        private const val REMOTE_TASKS_COLLECTION = "remote_tasks"
        private const val TASK_SUBMISSIONS_COLLECTION = "task_submissions"
    }
}
