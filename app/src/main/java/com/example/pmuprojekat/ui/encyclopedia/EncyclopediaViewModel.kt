package com.example.pmuprojekat.ui.encyclopedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.ai.EncyclopediaAiRequest
import com.example.pmuprojekat.ai.EncyclopediaAiService
import com.example.pmuprojekat.ai.EncyclopediaExplanationMode
import com.example.pmuprojekat.data.encyclopedia.EncyclopediaCategory
import com.example.pmuprojekat.data.encyclopedia.EncyclopediaRepository
import com.example.pmuprojekat.data.encyclopedia.EncyclopediaTerm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EncyclopediaUiState(
    val categories: List<EncyclopediaCategory> = emptyList(),
    val isAiLoading: Boolean = false,
    val activeMode: EncyclopediaExplanationMode? = null,
    val aiResponseTitle: String? = null,
    val aiResponseText: String? = null,
    val aiError: String? = null
)

@HiltViewModel
class EncyclopediaViewModel @Inject constructor(
    private val repository: EncyclopediaRepository,
    private val aiService: EncyclopediaAiService
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        EncyclopediaUiState(categories = repository.getCategories())
    )
    val uiState: StateFlow<EncyclopediaUiState> = _uiState.asStateFlow()

    private var explanationJob: Job? = null

    fun explain(termId: String, mode: EncyclopediaExplanationMode) {
        val term = repository.getTermById(termId) ?: return
        val category = repository.getCategories().firstOrNull { it.id == term.categoryId } ?: return

        explanationJob?.cancel()
        explanationJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isAiLoading = true,
                    activeMode = mode,
                    aiResponseTitle = null,
                    aiResponseText = null,
                    aiError = null
                )
            }

            aiService.explain(
                EncyclopediaAiRequest(
                    categoryTitle = category.title,
                    term = term,
                    mode = mode
                )
            ).fold(
                onSuccess = { response ->
                    _uiState.update {
                        it.copy(
                            isAiLoading = false,
                            aiResponseTitle = mode.responseTitle,
                            aiResponseText = response,
                            aiError = null
                        )
                    }
                },
                onFailure = {
                    _uiState.update { state ->
                        state.copy(
                            isAiLoading = false,
                            aiResponseTitle = mode.responseTitle,
                            aiResponseText = localFallback(term, mode),
                            aiError = "AI servis trenutno nije dostupan. Prikazano je lokalno objašnjenje."
                        )
                    }
                }
            )
        }
    }

    fun clearExplanation() {
        explanationJob?.cancel()
        _uiState.update {
            it.copy(
                isAiLoading = false,
                activeMode = null,
                aiResponseTitle = null,
                aiResponseText = null,
                aiError = null
            )
        }
    }

    private fun localFallback(
        term: EncyclopediaTerm,
        mode: EncyclopediaExplanationMode
    ): String {
        return when (mode) {
            EncyclopediaExplanationMode.DETAILED -> {
                "${term.shortExplanation}\n\nZa dublje razumevanje obrati pažnju na to gde se ovaj pojam nalazi u toku obrade, koje podatke koristi i kakav rezultat daje ostatku sistema."
            }

            EncyclopediaExplanationMode.SIMPLE -> {
                "Najjednostavnije rečeno: ${term.shortExplanation.substringBefore('.')} ."
                    .replace(" .", ".")
            }

            EncyclopediaExplanationMode.PRACTICAL_EXAMPLE -> {
                "Na platformi za učenje, pojam „${term.titleSr}” može da se pojavi u AI funkciji koja obrađuje sadržaj lekcija, preporuke ili ponašanje korisnika. Konkretna uloga zavisi od toga koje podatke sistem prima i koju odluku ili prikaz treba da proizvede."
            }
        }
    }
}
