package com.example.pmuprojekat.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.data.repository.AdminAuthorizationException
import com.example.pmuprojekat.data.repository.AdminModerationAction
import com.example.pmuprojekat.data.repository.AdminModerationOutcome
import com.example.pmuprojekat.data.repository.AdminSubmissionFilter
import com.example.pmuprojekat.data.repository.AdminTaskImageUnavailableException
import com.example.pmuprojekat.data.repository.AdminTaskPromotionConflictException
import com.example.pmuprojekat.data.repository.AdminTaskSubmissionDetail
import com.example.pmuprojekat.data.repository.AdminTaskSubmissionSummary
import com.example.pmuprojekat.data.repository.AdminTaskSubmissionsRepository
import com.example.pmuprojekat.data.repository.RemoteTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdminTaskSubmissionsUiState(
    val isListLoading: Boolean = false,
    val submissions: List<AdminTaskSubmissionSummary> = emptyList(),
    val selectedFilter: AdminSubmissionFilter = AdminSubmissionFilter.PENDING,
    val listError: String? = null,
    val isDetailLoading: Boolean = false,
    val detail: AdminTaskSubmissionDetail? = null,
    val detailError: String? = null,
    val confirmationAction: AdminModerationAction? = null,
    val rejectionReason: String = "",
    val runningAction: AdminModerationAction? = null,
    val actionMessage: String? = null,
    val actionError: String? = null,
    val requiresAdminLogin: Boolean = false
) {
    val filteredSubmissions: List<AdminTaskSubmissionSummary>
        get() = submissions.filter {
            selectedFilter.accepts(it.moderationStatus)
        }

    val isActionRunning: Boolean
        get() = runningAction != null
}

@HiltViewModel
class AdminTaskSubmissionsViewModel @Inject constructor(
    private val repository: AdminTaskSubmissionsRepository,
    private val remoteTaskRepository: RemoteTaskRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdminTaskSubmissionsUiState())
    val uiState: StateFlow<AdminTaskSubmissionsUiState> = _uiState.asStateFlow()

    private var listJob: Job? = null
    private var detailJob: Job? = null
    private var actionJob: Job? = null

    fun selectFilter(filter: AdminSubmissionFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    fun loadSubmissions() {
        listJob?.cancel()
        listJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isListLoading = true,
                    listError = null,
                    requiresAdminLogin = false
                )
            }
            repository.fetchSubmissions().fold(
                onSuccess = { submissions ->
                    _uiState.update {
                        it.copy(
                            isListLoading = false,
                            submissions = submissions,
                            listError = null
                        )
                    }
                },
                onFailure = ::handleListFailure
            )
        }
    }

    fun loadSubmission(submissionId: String) {
        detailJob?.cancel()
        detailJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isDetailLoading = true,
                    detail = null,
                    detailError = null,
                    actionMessage = null,
                    actionError = null,
                    confirmationAction = null,
                    requiresAdminLogin = false
                )
            }
            repository.fetchSubmission(submissionId).fold(
                onSuccess = { detail ->
                    _uiState.update {
                        it.copy(
                            isDetailLoading = false,
                            detail = detail,
                            detailError = null
                        )
                    }
                },
                onFailure = ::handleDetailFailure
            )
        }
    }

    fun requestAction(action: AdminModerationAction) {
        val current = _uiState.value
        val detail = current.detail ?: return
        if (current.isActionRunning) return

        if (
            action == AdminModerationAction.REJECT &&
            !detail.summary.promotedToRemoteTaskId.isNullOrBlank()
        ) {
            _uiState.update {
                it.copy(
                    actionError =
                        "Promovisan zadatak se ne može odbiti dok postoji zvanična kopija."
                )
            }
            return
        }

        if (
            action == AdminModerationAction.PROMOTE &&
            detail.requiresPortableImage &&
            !detail.hasPortableImage
        ) {
            _uiState.update {
                it.copy(actionError = "Slika zadatka nije dostupna drugim uređajima.")
            }
            return
        }

        _uiState.update {
            it.copy(
                confirmationAction = action,
                rejectionReason = "",
                actionMessage = null,
                actionError = null
            )
        }
    }

    fun dismissConfirmation() {
        if (_uiState.value.isActionRunning) return
        _uiState.update {
            it.copy(
                confirmationAction = null,
                rejectionReason = ""
            )
        }
    }

    fun updateRejectionReason(value: String) {
        if (_uiState.value.isActionRunning) return
        _uiState.update {
            it.copy(
                rejectionReason = value.take(MAX_REJECTION_REASON_LENGTH),
                actionError = null
            )
        }
    }

    fun confirmAction() {
        val current = _uiState.value
        val detail = current.detail ?: return
        val action = current.confirmationAction ?: return
        if (current.isActionRunning) return

        actionJob?.cancel()
        actionJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    confirmationAction = null,
                    runningAction = action,
                    actionMessage = null,
                    actionError = null
                )
            }

            val result = when (action) {
                AdminModerationAction.APPROVE ->
                    repository.approveAsUserTask(detail.summary.submissionId)
                AdminModerationAction.PROMOTE ->
                    repository.promoteToOfficialTask(detail.summary.submissionId)
                AdminModerationAction.REJECT ->
                    repository.rejectSubmission(
                        submissionId = detail.summary.submissionId,
                        rejectionReason = current.rejectionReason
                    )
            }

            result.fold(
                onSuccess = { outcome ->
                    applySuccessfulAction(outcome)
                    if (
                        action == AdminModerationAction.APPROVE ||
                        action == AdminModerationAction.PROMOTE
                    ) {
                        remoteTaskRepository.refreshRemoteTasks()
                    }
                    reloadAfterAction(detail.summary.submissionId)
                },
                onFailure = ::handleActionFailure
            )
        }
    }

    fun clearActionMessage() {
        _uiState.update {
            it.copy(actionMessage = null, actionError = null)
        }
    }

    fun consumeAuthorizationRedirect() {
        _uiState.update { it.copy(requiresAdminLogin = false) }
    }

    fun clearProtectedData() {
        listJob?.cancel()
        detailJob?.cancel()
        actionJob?.cancel()
        _uiState.value = AdminTaskSubmissionsUiState()
    }

    private suspend fun reloadAfterAction(submissionId: String) {
        val listResult = repository.fetchSubmissions()
        val detailResult = repository.fetchSubmission(submissionId)

        _uiState.update { current ->
            current.copy(
                isListLoading = false,
                submissions = listResult.getOrNull() ?: current.submissions,
                listError = null,
                isDetailLoading = false,
                detail = detailResult.getOrNull() ?: current.detail,
                detailError = null,
                runningAction = null
            )
        }
    }

    private fun applySuccessfulAction(outcome: AdminModerationOutcome) {
        val message = when (outcome.action) {
            AdminModerationAction.APPROVE -> {
                if (outcome.alreadyApplied) {
                    "Zadatak je već odobren kao korisnički zadatak."
                } else {
                    "Zadatak je odobren i dostupan u odeljku „Korisnički zadaci“."
                }
            }
            AdminModerationAction.PROMOTE -> {
                if (outcome.alreadyApplied) {
                    "Zadatak je već promovisan kao ${outcome.remoteTaskId.orEmpty()}."
                } else {
                    "Zadatak je promovisan u zvanične zadatke."
                }
            }
            AdminModerationAction.REJECT -> {
                if (outcome.alreadyApplied) {
                    "Zadatak je već odbijen."
                } else {
                    "Zadatak je odbijen i ostaje sačuvan u evidenciji."
                }
            }
        }
        _uiState.update {
            it.copy(
                actionMessage = message,
                actionError = null
            )
        }
    }

    private fun handleListFailure(error: Throwable) {
        val authorizationFailed = error is AdminAuthorizationException
        _uiState.update {
            it.copy(
                isListLoading = false,
                submissions = if (authorizationFailed) emptyList() else it.submissions,
                listError = if (authorizationFailed) {
                    null
                } else {
                    "Korisnički zadaci trenutno nisu dostupni."
                },
                detail = if (authorizationFailed) null else it.detail,
                requiresAdminLogin = authorizationFailed
            )
        }
    }

    private fun handleDetailFailure(error: Throwable) {
        val authorizationFailed = error is AdminAuthorizationException
        _uiState.update {
            it.copy(
                isDetailLoading = false,
                detail = null,
                detailError = if (authorizationFailed) {
                    null
                } else {
                    "Detalji zadatka trenutno nisu dostupni."
                },
                submissions = if (authorizationFailed) emptyList() else it.submissions,
                requiresAdminLogin = authorizationFailed
            )
        }
    }

    private fun handleActionFailure(error: Throwable) {
        val authorizationFailed = error is AdminAuthorizationException
        val message = when (error) {
            is AdminAuthorizationException -> null
            is AdminTaskImageUnavailableException ->
                "Slika zadatka nije dostupna drugim uređajima."
            is AdminTaskPromotionConflictException ->
                "Zvanični zadatak sa ovim identifikatorom već pripada drugom predlogu."
            else -> "Moderacija trenutno nije uspela. Pokušaj ponovo."
        }
        _uiState.update {
            it.copy(
                runningAction = null,
                actionError = message,
                submissions = if (authorizationFailed) emptyList() else it.submissions,
                detail = if (authorizationFailed) null else it.detail,
                requiresAdminLogin = authorizationFailed
            )
        }
    }

    private companion object {
        const val MAX_REJECTION_REASON_LENGTH = 500
    }
}

