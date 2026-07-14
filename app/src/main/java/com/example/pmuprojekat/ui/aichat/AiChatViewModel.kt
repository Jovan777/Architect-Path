package com.example.pmuprojekat.ui.aichat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.ai.AiChatInputValidator
import com.example.pmuprojekat.ai.AiChatRelevantTerm
import com.example.pmuprojekat.ai.AiChatRequest
import com.example.pmuprojekat.ai.AiChatService
import com.example.pmuprojekat.data.encyclopedia.EncyclopediaTermRetriever
import com.example.pmuprojekat.data.repository.AiChatMessage
import com.example.pmuprojekat.data.repository.AiChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AiChatUiState(
    val messages: List<AiChatMessage> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val canRetry: Boolean = false,
    val relatedTermsForLastQuestion: List<AiChatRelevantTerm> = emptyList(),
    val suggestedPrompts: List<String> = DEFAULT_SUGGESTED_PROMPTS
) {
    val canSend: Boolean
        get() = inputText.trim().isNotBlank() && !isLoading
}

@HiltViewModel
class AiChatViewModel @Inject constructor(
    private val chatRepository: AiChatRepository,
    private val chatService: AiChatService,
    private val termRetriever: EncyclopediaTermRetriever
) : ViewModel() {
    private val _uiState = MutableStateFlow(AiChatUiState())
    val uiState: StateFlow<AiChatUiState> = _uiState.asStateFlow()

    private var sendJob: Job? = null
    private var lastFailedRequest: PendingAiChatRequest? = null

    init {
        viewModelScope.launch {
            chatRepository.ensureDefaultConversation()
            chatRepository.observeMessages().collect { messages ->
                _uiState.update { it.copy(messages = messages) }
            }
        }
    }

    fun updateInput(text: String) {
        _uiState.update {
            it.copy(
                inputText = text,
                errorMessage = null,
                canRetry = false
            )
        }
    }

    fun sendCurrentMessage() {
        val message = uiState.value.inputText.trim()
        sendMessage(message, clearInput = true)
    }

    fun sendSuggestion(prompt: String) {
        sendMessage(prompt.trim(), clearInput = true)
    }

    fun retryLastFailed() {
        val pending = lastFailedRequest ?: return
        sendAiRequest(
            userMessage = pending.userMessage,
            history = pending.history,
            relevantTerms = pending.relevantTerms,
            persistUserMessage = false
        )
    }

    fun clearConversation() {
        sendJob?.cancel()
        lastFailedRequest = null
        viewModelScope.launch {
            chatRepository.clearConversation()
            _uiState.update {
                it.copy(
                    inputText = "",
                    isLoading = false,
                    errorMessage = null,
                    canRetry = false,
                    relatedTermsForLastQuestion = emptyList()
                )
            }
        }
    }

    private fun sendMessage(
        message: String,
        clearInput: Boolean
    ) {
        val validationError = AiChatInputValidator.validate(message)
        if (validationError != null) {
            _uiState.update {
                it.copy(
                    errorMessage = validationError,
                    canRetry = false
                )
            }
            return
        }

        val relevantTerms = termRetriever.findRelevantTerms(message)
        viewModelScope.launch {
            val history = chatRepository.getRecentMessageContexts(
                AiChatPromptHistoryLimit
            )
            sendAiRequest(
                userMessage = message,
                history = history,
                relevantTerms = relevantTerms,
                persistUserMessage = true
            )
        }

        if (clearInput) {
            _uiState.update {
                it.copy(
                    inputText = "",
                    errorMessage = null,
                    canRetry = false,
                    relatedTermsForLastQuestion = relevantTerms
                )
            }
        }
    }

    private fun sendAiRequest(
        userMessage: String,
        history: List<com.example.pmuprojekat.ai.AiChatMessageContext>,
        relevantTerms: List<AiChatRelevantTerm>,
        persistUserMessage: Boolean
    ) {
        if (uiState.value.isLoading) return

        sendJob?.cancel()
        sendJob = viewModelScope.launch {
            if (persistUserMessage) {
                chatRepository.addUserMessage(
                    content = userMessage,
                    relatedTermIds = relevantTerms.map { it.id }
                )
            }

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    canRetry = false,
                    relatedTermsForLastQuestion = relevantTerms
                )
            }

            chatService.sendMessage(
                AiChatRequest(
                    userMessage = userMessage,
                    recentMessages = history,
                    relevantTerms = relevantTerms
                )
            ).fold(
                onSuccess = { response ->
                    lastFailedRequest = null
                    chatRepository.addAssistantMessage(
                        content = response,
                        relatedTermIds = relevantTerms.map { it.id }
                    )
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            canRetry = false
                        )
                    }
                },
                onFailure = {
                    lastFailedRequest = PendingAiChatRequest(
                        userMessage = userMessage,
                        history = history,
                        relevantTerms = relevantTerms
                    )
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = "AI trenutno nije dostupan. Pokušaj ponovo kasnije.",
                            canRetry = true
                        )
                    }
                }
            )
        }
    }

    private data class PendingAiChatRequest(
        val userMessage: String,
        val history: List<com.example.pmuprojekat.ai.AiChatMessageContext>,
        val relevantTerms: List<AiChatRelevantTerm>
    )
}

private const val AiChatPromptHistoryLimit = 8

private val DEFAULT_SUGGESTED_PROMPTS = listOf(
    "Objasni mi Strategy obrazac",
    "Šta znači source of truth?",
    "Koja je razlika između cache-a i read modela?",
    "Kako da razmišljam o mikroservisima?",
    "Šta je RAG?",
    "Objasni mi arhitektonski kompromis",
    "Kako da nacrtam osnovnu arhitekturu sistema?"
)
