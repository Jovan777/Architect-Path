package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.data.remote.RemoteTaskSyncState
import kotlinx.coroutines.flow.StateFlow

interface RemoteTaskRepository {
    val syncState: StateFlow<RemoteTaskSyncState>
    suspend fun refreshRemoteTasks(): Result<Unit>
}
