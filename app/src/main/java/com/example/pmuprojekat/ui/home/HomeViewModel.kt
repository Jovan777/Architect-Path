package com.example.pmuprojekat.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.repository.LearningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: LearningRepository
) : ViewModel() {

    val uiState = combine(
        repository.observeActiveUser(),
        repository.observeAllQuestions()
    ) { user, questions ->

        val selectedLevelId = user?.currentLevel ?: LearningLevel.BEGINNER.id
        val selectedLevel = LearningLevel.fromId(selectedLevelId)

        val selectedLevelQuestions = questions
            .filter { it.level == selectedLevelId }
            .sortedBy { it.orderIndex }

        val completedQuestions = user?.completedQuestions ?: 0

        val overallProgress = calculatePercent(
            completed = completedQuestions,
            total = questions.size
        )

        val selectedProgress = calculatePercent(
            completed = 0,
            total = selectedLevelQuestions.size
        )

        val levels = LearningLevel.entries.mapIndexed { index, level ->
            val levelQuestions = questions.filter { it.level == level.id }

            LevelSummaryUi(
                levelId = level.id,
                number = index + 1,
                title = level.displayName,
                description = levelDescription(level.id),
                topicCount = levelQuestions.map { it.type }.distinct().size,
                questionCount = levelQuestions.size,
                completedCount = 0
            )
        }

        val questionPreviews = selectedLevelQuestions
            .take(4)
            .map { question ->
                QuestionPreviewUi(
                    questionId = question.questionId,
                    title = question.title,
                    typeLabel = questionTypeLabel(question.type),
                    difficulty = difficultyLabel(question.difficulty),
                    wave = question.wave
                )
            }

        HomeUiState(
            isLoading = false,
            userName = user?.displayName ?: "Marko",
            selectedLevel = selectedLevelId,
            selectedLevelName = selectedLevel.displayName,
            totalQuestions = questions.size,
            selectedLevelQuestions = selectedLevelQuestions.size,
            completedQuestions = completedQuestions,
            streakDays = user?.streakDays ?: 0,
            xp = user?.xp ?: 0,
            overallProgressPercent = overallProgress,
            selectedLevelProgressPercent = selectedProgress,
            activeCardTitle = activeCardTitle(
                selectedLevel = selectedLevel,
                questions = selectedLevelQuestions
            ),
            activeCardSubtitle = activeCardSubtitle(
                selectedLevel = selectedLevel,
                questions = selectedLevelQuestions
            ),
            activeCardProgressPercent = selectedProgress,
            levels = levels,
            questionPreviews = questionPreviews
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )

    init {
        viewModelScope.launch {
            repository.initializeDatabaseIfNeeded()
        }
    }

    fun selectLevel(levelId: String) {
        viewModelScope.launch {
            repository.updateCurrentLevel(levelId)
        }
    }

    private fun calculatePercent(
        completed: Int,
        total: Int
    ): Int {
        if (total <= 0) return 0
        return ((completed.toFloat() / total.toFloat()) * 100)
            .roundToInt()
            .coerceIn(0, 100)
    }

    private fun levelDescription(levelId: String): String {
        return when (levelId) {
            LearningLevel.BEGINNER.id -> "Osnove obrazaca i dobrih praksi"
            LearningLevel.JUNIOR.id -> "Primeni obrasce i piši čist kod"
            LearningLevel.MEDIOR.id -> "Poveži komponente u sisteme"
            LearningLevel.SENIOR.id -> "Kvalitet, performanse i operacije"
            LearningLevel.ARCHITECT.id -> "Arhitektura, odluke i evolucija"
            else -> "Interaktivno učenje projektovanja"
        }
    }

    private fun questionTypeLabel(type: String): String {
        return QuestionType.entries
            .firstOrNull { it.id == type }
            ?.displayName
            ?: type
    }

    private fun difficultyLabel(difficulty: String): String {
        return when (difficulty.lowercase()) {
            "easy" -> "Lako"
            "medium" -> "Srednje"
            "hard" -> "Teže"
            else -> difficulty
        }
    }

    private fun activeCardTitle(
        selectedLevel: LearningLevel,
        questions: List<QuestionEntity>
    ): String {
        val firstQuestion = questions.firstOrNull()

        return when {
            firstQuestion == null -> "Nema ubačenih zadataka"
            selectedLevel == LearningLevel.BEGINNER -> "Početni modul — Početnik"
            selectedLevel == LearningLevel.JUNIOR -> "Praktični modul — Junior"
            firstQuestion.wave != null -> "Talas ${firstQuestion.wave} — ${selectedLevel.displayName}"
            else -> "Modul — ${selectedLevel.displayName}"
        }
    }

    private fun activeCardSubtitle(
        selectedLevel: LearningLevel,
        questions: List<QuestionEntity>
    ): String {
        val firstQuestion = questions.firstOrNull()

        return when {
            firstQuestion == null -> "Za ovaj nivo još nisu ubačena pitanja."
            selectedLevel == LearningLevel.BEGINNER ->
                "Kreni od osnovnog prepoznavanja obrazaca."

            selectedLevel == LearningLevel.JUNIOR ->
                "Vežbaj strukturu, uloge i osnovnu implementaciju."

            else ->
                "Preporučeno: ${firstQuestion.questionId} — ${firstQuestion.title}"
        }
    }
}