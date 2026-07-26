package com.example.pmuprojekat.data.remote

import android.util.Log
import com.example.pmuprojekat.data.repository.AdminAuthRepository
import com.example.pmuprojekat.data.repository.AdminAuthResult
import com.example.pmuprojekat.data.repository.AdminAuthorizationException
import com.example.pmuprojekat.data.repository.AdminModerationAction
import com.example.pmuprojekat.data.repository.AdminModerationOutcome
import com.example.pmuprojekat.data.repository.AdminTaskImageUnavailableException
import com.example.pmuprojekat.data.repository.AdminTaskPromotionConflictException
import com.example.pmuprojekat.data.repository.AdminTaskSubmissionDetail
import com.example.pmuprojekat.data.repository.AdminTaskSubmissionSummary
import com.example.pmuprojekat.data.repository.AdminTaskSubmissionsRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreAdminTaskSubmissionsRepository @Inject constructor(
    private val clients: FirebaseAdminClientProvider,
    private val adminAuthRepository: AdminAuthRepository,
    private val payloadParser: AdminTaskSubmissionPayloadParser,
    private val remoteTaskMapper: RemoteTaskMapper
) : AdminTaskSubmissionsRepository {

    override suspend fun fetchSubmissions(): Result<List<AdminTaskSubmissionSummary>> = runCatching {
        ensureAuthorized()
        val firestore = clients.firestore().getOrThrow()
        val submitterNames = firestore.collection(USERS_COLLECTION)
            .get()
            .awaitResult()
            .documents
            .associate { document ->
                document.id to document.getString("displayName")
                    .normalizedText()
                    .orDefaultUserName()
            }

        firestore.collection(SUBMISSIONS_COLLECTION)
            .get()
            .awaitResult()
            .documents
            .mapNotNull { document ->
                mapSummary(
                    document = document,
                    submitterDisplayName = submitterNames[
                        document.getString("createdByUserId").orEmpty()
                    ].orDefaultUserName()
                )
            }
            .sortedWith(
                compareByDescending<AdminTaskSubmissionSummary> { it.createdAt ?: 0L }
                    .thenByDescending(AdminTaskSubmissionSummary::updatedAt)
            )
    }.onFailure { error ->
        Log.w(TAG, "Admin lista korisničkih zadataka nije dostupna.", error)
    }

    override suspend fun fetchSubmission(
        submissionId: String
    ): Result<AdminTaskSubmissionDetail> = runCatching {
        ensureAuthorized()
        val firestore = clients.firestore().getOrThrow()
        val document = firestore.collection(SUBMISSIONS_COLLECTION)
            .document(submissionId)
            .get()
            .awaitResult()
        require(document.exists()) {
            "Predlog zadatka više ne postoji."
        }

        val submitterName = document.getString("createdByUserId")
            ?.takeIf(String::isNotBlank)
            ?.let { uid ->
                firestore.collection(USERS_COLLECTION)
                    .document(uid)
                    .get()
                    .awaitResult()
                    .getString("displayName")
            }
            .normalizedText()
            .orDefaultUserName()
        val summary = mapSummary(document, submitterName)
            ?: error("Predlog zadatka nema obavezne podatke.")
        val payload = document.readPayload()
        val resolvedPayload = resolveDiagramDownloadUrl(payload)
        val contentResult = payloadParser.parse(resolvedPayload)
        val playableResult = remoteTaskMapper.toPlayableQuestion(
            RemoteTaskDocument(
                documentId = document.id,
                collection = SUBMISSIONS_COLLECTION,
                title = summary.title,
                level = summary.level,
                taskType = summary.taskType,
                source = "USER_APPROVED",
                status = "ACTIVE",
                publicationMode = "USER_TASKS",
                schemaVersion = document.intValue("schemaVersion").coerceAtLeast(1),
                updatedAt = summary.updatedAt,
                payload = resolvedPayload
            )
        )

        AdminTaskSubmissionDetail(
            summary = summary,
            question = contentResult.getOrNull(),
            playableQuestion = playableResult.getOrNull(),
            contentError = when {
                contentResult.isFailure ->
                    "Sadržaj zadatka je nepotpun ili neispravan."
                playableResult.isFailure ->
                    "Zadatak nije moguće otvoriti u postojećem renderer-u. Proveri obavezna polja šablona."
                else -> null
            }
        )
    }.onFailure { error ->
        Log.w(TAG, "Admin detalji korisničkog zadatka nisu dostupni.", error)
    }

    override suspend fun approveAsUserTask(
        submissionId: String
    ): Result<AdminModerationOutcome> = runCatching {
        val actor = ensureAuthorized()
        val firestore = clients.firestore().getOrThrow()
        val reference = firestore.collection(SUBMISSIONS_COLLECTION).document(submissionId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(reference)
            require(snapshot.exists()) {
                "Predlog zadatka više ne postoji."
            }

            val promotedId = snapshot.getString("promotedToRemoteTaskId")
                .normalizedText()
            if (promotedId != null) {
                return@runTransaction AdminModerationOutcome(
                    action = AdminModerationAction.PROMOTE,
                    submissionId = submissionId,
                    remoteTaskId = promotedId,
                    alreadyApplied = true
                )
            }

            val alreadyApproved = snapshot.getString("reviewStatus") == "APPROVED" &&
                snapshot.getBoolean("isPublic") == true
            if (!alreadyApproved) {
                transaction.update(
                    reference,
                    mapOf(
                        "reviewStatus" to "APPROVED",
                        "isPublic" to true,
                        "approvedBy" to actor.uid,
                        "approvedAt" to FieldValue.serverTimestamp(),
                        "updatedAt" to FieldValue.serverTimestamp(),
                        "rejectedBy" to null,
                        "rejectedAt" to null,
                        "rejectionReason" to null
                    )
                )
            }

            AdminModerationOutcome(
                action = AdminModerationAction.APPROVE,
                submissionId = submissionId,
                alreadyApplied = alreadyApproved
            )
        }.awaitResult()
    }.onFailure { error ->
        Log.w(TAG, "Odobravanje korisničkog zadatka nije uspelo.", error)
    }

    override suspend fun promoteToOfficialTask(
        submissionId: String
    ): Result<AdminModerationOutcome> = runCatching {
        val actor = ensureAuthorized()
        val firestore = clients.firestore().getOrThrow()
        val submissionReference = firestore
            .collection(SUBMISSIONS_COLLECTION)
            .document(submissionId)

        firestore.runTransaction { transaction ->
            val submission = transaction.get(submissionReference)
            require(submission.exists()) {
                "Predlog zadatka više ne postoji."
            }

            val data = submission.data.orEmpty()
            val payload = submission.readPayload()
            requirePortableDiagramIfNeeded(data, payload)

            val remoteTaskId = submission.getString("promotedToRemoteTaskId")
                .normalizedText()
                ?: officialTaskIdFor(submissionId)
            val remoteReference = firestore
                .collection(REMOTE_TASKS_COLLECTION)
                .document(remoteTaskId)
            val existingRemote = transaction.get(remoteReference)

            if (existingRemote.exists()) {
                val originalSubmissionId = existingRemote
                    .getString("originalSubmissionId")
                if (originalSubmissionId != submissionId) {
                    throw AdminTaskPromotionConflictException()
                }
                ensureSubmissionMarkedPromoted(
                    transaction = transaction,
                    submissionReference = submissionReference,
                    actorUid = actor.uid,
                    remoteTaskId = remoteTaskId
                )
                return@runTransaction AdminModerationOutcome(
                    action = AdminModerationAction.PROMOTE,
                    submissionId = submissionId,
                    remoteTaskId = remoteTaskId,
                    alreadyApplied = true
                )
            }

            val title = submission.getString("title").normalizedText()
                ?: error("Nedostaje naslov zadatka.")
            val level = submission.getString("level").normalizedText()
                ?: error("Nedostaje nivo zadatka.")
            val taskType = submission.getString("taskType").normalizedText()
                ?: error("Nedostaje tip zadatka.")
            require(payload.isNotEmpty()) {
                "Nedostaje sadržaj zadatka."
            }

            val remoteDocument = mutableMapOf<String, Any?>(
                "id" to remoteTaskId,
                "title" to title,
                "level" to level,
                "taskType" to taskType,
                "templateId" to submission.getString("templateId"),
                "source" to "ADMIN",
                "createdByRole" to "ADMIN",
                "status" to "ACTIVE",
                "publicationMode" to "MAIN_TASK_LIST",
                "isPublic" to true,
                "schemaVersion" to submission.intValue("schemaVersion").coerceAtLeast(1),
                "payload" to payload,
                "originalSubmissionId" to submissionId,
                "createdAt" to (
                    submission.get("createdAt") ?: FieldValue.serverTimestamp()
                ),
                "updatedAt" to FieldValue.serverTimestamp(),
                "promotedAt" to FieldValue.serverTimestamp(),
                "promotedBy" to actor.uid
            )
            submission.getString("payloadJson")?.let { payloadJson ->
                remoteDocument["payloadJson"] = payloadJson
            }

            transaction.set(remoteReference, remoteDocument)
            ensureSubmissionMarkedPromoted(
                transaction = transaction,
                submissionReference = submissionReference,
                actorUid = actor.uid,
                remoteTaskId = remoteTaskId
            )

            AdminModerationOutcome(
                action = AdminModerationAction.PROMOTE,
                submissionId = submissionId,
                remoteTaskId = remoteTaskId
            )
        }.awaitResult()
    }.onFailure { error ->
        Log.w(TAG, "Promocija korisničkog zadatka nije uspela.", error)
    }

    override suspend fun rejectSubmission(
        submissionId: String,
        rejectionReason: String?
    ): Result<AdminModerationOutcome> = runCatching {
        val actor = ensureAuthorized()
        val firestore = clients.firestore().getOrThrow()
        val reference = firestore.collection(SUBMISSIONS_COLLECTION).document(submissionId)
        val normalizedReason = rejectionReason
            ?.trim()
            ?.take(MAX_REJECTION_REASON_LENGTH)
            ?.takeIf(String::isNotBlank)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(reference)
            require(snapshot.exists()) {
                "Predlog zadatka više ne postoji."
            }
            check(snapshot.getString("promotedToRemoteTaskId").isNullOrBlank()) {
                "Promovisan zadatak se ne može odbiti bez uklanjanja zvanične kopije."
            }

            val alreadyRejected = snapshot.getString("reviewStatus") == "REJECTED" &&
                snapshot.getBoolean("isPublic") == false &&
                snapshot.getString("rejectionReason").normalizedText() == normalizedReason
            if (!alreadyRejected) {
                transaction.update(
                    reference,
                    mapOf(
                        "reviewStatus" to "REJECTED",
                        "isPublic" to false,
                        "rejectedBy" to actor.uid,
                        "rejectedAt" to FieldValue.serverTimestamp(),
                        "rejectionReason" to normalizedReason,
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                )
            }

            AdminModerationOutcome(
                action = AdminModerationAction.REJECT,
                submissionId = submissionId,
                alreadyApplied = alreadyRejected
            )
        }.awaitResult()
    }.onFailure { error ->
        Log.w(TAG, "Odbijanje korisničkog zadatka nije uspelo.", error)
    }

    private fun ensureSubmissionMarkedPromoted(
        transaction: com.google.firebase.firestore.Transaction,
        submissionReference: com.google.firebase.firestore.DocumentReference,
        actorUid: String,
        remoteTaskId: String
    ) {
        transaction.update(
            submissionReference,
            mapOf(
                "reviewStatus" to "APPROVED",
                "isPublic" to false,
                "promotedToRemoteTaskId" to remoteTaskId,
                "promotedBy" to actorUid,
                "promotedAt" to FieldValue.serverTimestamp(),
                "approvedBy" to actorUid,
                "approvedAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp(),
                "rejectedBy" to null,
                "rejectedAt" to null,
                "rejectionReason" to null
            )
        )
    }

    private fun requirePortableDiagramIfNeeded(
        document: Map<String, Any?>,
        payload: Map<String, Any?>
    ) {
        val question = payload.mapValue("question") ?: payload
        val isA3 = (document["templateId"] as? String) == A3_TEMPLATE_ID ||
            question.nonBlankString("questionIdPattern")?.startsWith("A3.") == true
        if (!isA3) return

        val diagram = question.mapValue("diagramImage")
        val hasPortableImage = diagram?.nonBlankString("downloadUrl") != null ||
            diagram?.nonBlankString("remoteStoragePath") != null
        if (!hasPortableImage) {
            throw AdminTaskImageUnavailableException()
        }
    }

    private suspend fun resolveDiagramDownloadUrl(
        payload: Map<String, Any?>
    ): Map<String, Any?> {
        val hasQuestionWrapper = payload["question"] is Map<*, *>
        val question = payload.mapValue("question") ?: payload
        val diagram = question.mapValue("diagramImage") ?: return payload
        if (diagram.nonBlankString("downloadUrl") != null) return payload
        val storagePath = diagram.nonBlankString("remoteStoragePath") ?: return payload

        val downloadUrl = runCatching {
            clients.storage().getOrThrow()
                .reference
                .child(storagePath)
                .downloadUrl
                .awaitResult()
                .toString()
        }.onFailure { error ->
            Log.w(TAG, "Admin pregled ne može da razreši A3 Storage URL.", error)
        }.getOrNull() ?: return payload

        val updatedQuestion = question + (
            "diagramImage" to (diagram + ("downloadUrl" to downloadUrl))
        )
        return if (hasQuestionWrapper) {
            payload + ("question" to updatedQuestion)
        } else {
            updatedQuestion
        }
    }

    private suspend fun ensureAuthorized(): AdminActor {
        return when (adminAuthRepository.verifyCurrentSession()) {
            is AdminAuthResult.Authorized -> {
                val user = clients.auth().getOrThrow().currentUser
                    ?: throw AdminAuthorizationException()
                AdminActor(uid = user.uid)
            }
            AdminAuthResult.SignedOut,
            AdminAuthResult.InvalidCredentials,
            AdminAuthResult.MissingAdminClaim -> throw AdminAuthorizationException()
            AdminAuthResult.Unavailable ->
                error("Admin autorizacija trenutno nije dostupna.")
        }
    }

    private fun mapSummary(
        document: DocumentSnapshot,
        submitterDisplayName: String
    ): AdminTaskSubmissionSummary? {
        if (!document.exists()) return null
        return AdminTaskSubmissionSummary(
            submissionId = document.id,
            title = document.getString("title").normalizedText() ?: "Zadatak bez naslova",
            level = document.getString("level").orEmpty(),
            taskType = document.getString("taskType").orEmpty(),
            templateId = document.getString("templateId").normalizedText(),
            submitterDisplayName = submitterDisplayName,
            createdAt = document.get("createdAt").asEpochMillis(),
            updatedAt = document.get("updatedAt").asEpochMillis(),
            reviewStatus = document.getString("reviewStatus").orEmpty(),
            isPublic = document.getBoolean("isPublic") ?: false,
            promotedToRemoteTaskId = document
                .getString("promotedToRemoteTaskId")
                .normalizedText()
        )
    }

    private fun DocumentSnapshot.readPayload(): Map<String, Any?> {
        return get("payload").asStringMap()
            ?: getString("payloadJson")?.toJsonMap()
            ?: emptyMap()
    }

    private fun officialTaskIdFor(submissionId: String): String {
        val safeId = submissionId
            .lowercase()
            .replace(Regex("[^a-z0-9_-]+"), "_")
            .trim('_')
            .ifBlank { "submission" }
        return "admin_$safeId"
    }

    private fun String?.normalizedText(): String? {
        return this?.trim()?.takeIf(String::isNotBlank)
    }

    private fun String?.orDefaultUserName(): String {
        return normalizedText() ?: DEFAULT_USER_NAME
    }

    private fun DocumentSnapshot.intValue(field: String): Int {
        return (get(field) as? Number)?.toInt() ?: 0
    }

    private fun Any?.asEpochMillis(): Long? {
        return when (this) {
            is Timestamp -> toDate().time
            is Number -> toLong()
            else -> null
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun Any?.asStringMap(): Map<String, Any?>? {
        return this as? Map<String, Any?>
    }

    @Suppress("UNCHECKED_CAST")
    private fun Map<String, Any?>.mapValue(key: String): Map<String, Any?>? {
        return this[key] as? Map<String, Any?>
    }

    private fun Map<String, Any?>.nonBlankString(key: String): String? {
        return (this[key] as? String)?.trim()?.takeIf(String::isNotBlank)
    }

    private data class AdminActor(val uid: String)

    private companion object {
        const val TAG = "AdminTaskModeration"
        const val USERS_COLLECTION = "users"
        const val SUBMISSIONS_COLLECTION = "task_submissions"
        const val REMOTE_TASKS_COLLECTION = "remote_tasks"
        const val A3_TEMPLATE_ID = "architect_a3_review"
        const val DEFAULT_USER_NAME = "Korisnik"
        const val MAX_REJECTION_REASON_LENGTH = 500
    }
}

