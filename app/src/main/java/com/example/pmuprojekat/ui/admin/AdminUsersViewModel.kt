package com.example.pmuprojekat.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.data.repository.AdminAuthorizationException
import com.example.pmuprojekat.data.repository.AdminUserDetails
import com.example.pmuprojekat.data.repository.AdminUserProgress
import com.example.pmuprojekat.data.repository.AdminUsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdminUsersUiState(
    val isUsersLoading: Boolean = false,
    val users: List<AdminUserProgress> = emptyList(),
    val usersError: String? = null,
    val isDetailsLoading: Boolean = false,
    val details: AdminUserDetails? = null,
    val detailsError: String? = null,
    val requiresAdminLogin: Boolean = false
)

@HiltViewModel
class AdminUsersViewModel @Inject constructor(
    private val repository: AdminUsersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdminUsersUiState())
    val uiState: StateFlow<AdminUsersUiState> = _uiState.asStateFlow()

    private var usersJob: Job? = null
    private var detailsJob: Job? = null

    fun loadUsers() {
        usersJob?.cancel()
        usersJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isUsersLoading = true,
                    usersError = null,
                    requiresAdminLogin = false
                )
            }
            repository.fetchUsers().fold(
                onSuccess = { users ->
                    _uiState.update {
                        it.copy(
                            isUsersLoading = false,
                            users = users,
                            usersError = null
                        )
                    }
                },
                onFailure = { error ->
                    handleUsersFailure(error)
                }
            )
        }
    }

    fun loadUserDetails(uid: String) {
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isDetailsLoading = true,
                    details = null,
                    detailsError = null,
                    requiresAdminLogin = false
                )
            }
            repository.fetchUserDetails(uid).fold(
                onSuccess = { details ->
                    _uiState.update {
                        it.copy(
                            isDetailsLoading = false,
                            details = details,
                            detailsError = null
                        )
                    }
                },
                onFailure = { error ->
                    handleDetailsFailure(error)
                }
            )
        }
    }

    fun consumeAuthorizationRedirect() {
        _uiState.update { it.copy(requiresAdminLogin = false) }
    }

    fun clearProtectedData() {
        usersJob?.cancel()
        detailsJob?.cancel()
        _uiState.value = AdminUsersUiState()
    }

    private fun handleUsersFailure(error: Throwable) {
        val authorizationFailed = error is AdminAuthorizationException
        _uiState.update {
            it.copy(
                isUsersLoading = false,
                users = if (authorizationFailed) emptyList() else it.users,
                usersError = if (error is AdminAuthorizationException) {
                    null
                } else {
                    "Podaci o korisnicima trenutno nisu dostupni."
                },
                details = if (authorizationFailed) null else it.details,
                requiresAdminLogin = authorizationFailed
            )
        }
    }

    private fun handleDetailsFailure(error: Throwable) {
        val authorizationFailed = error is AdminAuthorizationException
        _uiState.update {
            it.copy(
                isDetailsLoading = false,
                details = null,
                detailsError = if (error is AdminAuthorizationException) {
                    null
                } else {
                    "Podaci o korisniku trenutno nisu dostupni."
                },
                users = if (authorizationFailed) emptyList() else it.users,
                requiresAdminLogin = authorizationFailed
            )
        }
    }
}
