package com.example.pmuprojekat.data.remote

data class RemoteTaskDocument(
    val documentId: String,
    val collection: String,
    val title: String,
    val level: String,
    val taskType: String,
    val source: String,
    val status: String,
    val publicationMode: String,
    val schemaVersion: Int,
    val updatedAt: Long?,
    val payload: Map<String, Any?>
)

data class RemoteTaskSyncState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val lastUpdatedAt: Long? = null,
    val skippedDocuments: Int = 0
)
