package com.example.pmuprojekat.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.data.repository.AdminAuthRepository
import com.example.pmuprojekat.data.repository.AdminAuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AdminAccessStatus {
    IDLE,
    CHECKING_SESSION,
    SIGNED_OUT,
    SIGNING_IN,
    AUTHORIZED
}

data class AdminAuthUiState(
    val status: AdminAccessStatus = AdminAccessStatus.IDLE,
    val email: String = "",
    val password: String = "",
    val adminEmail: String = "",
    val errorMessage: String? = null
) {
    val isLoading: Boolean
        get() = status == AdminAccessStatus.CHECKING_SESSION ||
            status == AdminAccessStatus.SIGNING_IN
}

@HiltViewModel
class AdminAuthViewModel @Inject constructor(
    private val repository: AdminAuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdminAuthUiState())
    val uiState: StateFlow<AdminAuthUiState> = _uiState.asStateFlow()

    private var authJob: Job? = null

    fun verifySession() {
        if (_uiState.value.status == AdminAccessStatus.CHECKING_SESSION) return

        authJob?.cancel()
        authJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    status = AdminAccessStatus.CHECKING_SESSION,
                    errorMessage = null
                )
            }
            applyResult(repository.verifyCurrentSession(), fromLogin = false)
        }
    }

    fun updateEmail(value: String) {
        _uiState.update {
            it.copy(email = value, errorMessage = null)
        }
    }

    fun updatePassword(value: String) {
        _uiState.update {
            it.copy(password = value, errorMessage = null)
        }
    }

    fun signIn() {
        val current = _uiState.value
        if (current.isLoading) return

        val email = current.email.trim()
        if (email.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Unesite e-mail adresu.") }
            return
        }
        if (current.password.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Unesite lozinku.") }
            return
        }

        authJob?.cancel()
        authJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    status = AdminAccessStatus.SIGNING_IN,
                    email = email,
                    errorMessage = null
                )
            }
            applyResult(
                result = repository.signIn(email, current.password),
                fromLogin = true
            )
        }
    }

    fun signOut() {
        authJob?.cancel()
        repository.signOut()
        _uiState.value = AdminAuthUiState(status = AdminAccessStatus.SIGNED_OUT)
    }

    private fun applyResult(result: AdminAuthResult, fromLogin: Boolean) {
        _uiState.update { current ->
            when (result) {
                is AdminAuthResult.Authorized -> current.copy(
                    status = AdminAccessStatus.AUTHORIZED,
                    adminEmail = result.email,
                    password = "",
                    errorMessage = null
                )

                AdminAuthResult.SignedOut -> current.copy(
                    status = AdminAccessStatus.SIGNED_OUT,
                    adminEmail = "",
                    password = "",
                    errorMessage = null
                )

                AdminAuthResult.InvalidCredentials -> current.copy(
                    status = AdminAccessStatus.SIGNED_OUT,
                    adminEmail = "",
                    password = "",
                    errorMessage = "E-mail ili lozinka nisu ispravni."
                )

                AdminAuthResult.MissingAdminClaim -> current.copy(
                    status = AdminAccessStatus.SIGNED_OUT,
                    adminEmail = "",
                    password = "",
                    errorMessage = "Nalog nema administratorska prava."
                )

                AdminAuthResult.Unavailable -> current.copy(
                    status = AdminAccessStatus.SIGNED_OUT,
                    adminEmail = "",
                    password = if (fromLogin) "" else current.password,
                    errorMessage = "Prijava trenutno nije moguća. Pokušajte ponovo."
                )
            }
        }
    }
}
