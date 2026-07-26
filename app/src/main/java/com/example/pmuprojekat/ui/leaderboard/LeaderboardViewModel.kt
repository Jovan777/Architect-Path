package com.example.pmuprojekat.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.data.repository.LeaderboardEntry
import com.example.pmuprojekat.data.repository.LeaderboardRepository
import com.example.pmuprojekat.data.repository.UserProgressSyncRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LeaderboardUiState(
    val isLoading: Boolean = false,
    val entries: List<LeaderboardEntry> = emptyList(),
    val currentUserId: String = "",
    val currentUserEntry: LeaderboardEntry? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val repository: LeaderboardRepository,
    private val progressSyncRepository: UserProgressSyncRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LeaderboardUiState())
    val uiState: StateFlow<LeaderboardUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    fun load() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            progressSyncRepository.syncPendingProgress()
            repository.fetchLeaderboard().fold(
                onSuccess = { snapshot ->
                    _uiState.value = LeaderboardUiState(
                        entries = snapshot.entries,
                        currentUserId = snapshot.currentUserId,
                        currentUserEntry = snapshot.currentUserEntry
                    )
                },
                onFailure = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Rang-lista trenutno nije dostupna."
                        )
                    }
                }
            )
        }
    }
}
