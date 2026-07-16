package com.example.pmuprojekat.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.LevelXpProgress
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.core.model.TaskPersonalizer
import com.example.pmuprojekat.core.model.XpCalculator
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.repository.LearningRepository
import com.example.pmuprojekat.data.repository.RemoteTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlin.math.roundToInt

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: LearningRepository,
    private val remoteTaskRepository: RemoteTaskRepository
) : ViewModel() {

    val uiState = combine(
        repository.observeActiveUser(),
        repository.observeAllQuestions(),
        repository.observeApprovedUserQuestions(),
        repository.observeLocalUserProgress(),
        remoteTaskRepository.syncState
    ) { user, questions, approvedUserQuestions, progress, remoteSyncState ->

        val selectedLevelId = user?.currentLevel ?: LearningLevel.BEGINNER.id
        val selectedLevel = LearningLevel.fromId(selectedLevelId)
        val preferredFormats = TaskPersonalizer.parsePreferredFormats(
            user?.preferredTaskFormat ?: "Interaktivni koraci"
        )
        val learningFocus = TaskPersonalizer.parseLearningFocus(
            user?.learningFocus ?: "Balansirano učenje"
        )

        val completedIds = progress
            .filter { it.status == "completed" }
            .map { it.questionId }
            .toSet()

        val bestScoreByQuestionId = progress.associate {
            it.questionId to it.bestScorePercent
        }

        val bestXpByQuestionId = progress.associate {
            it.questionId to it.bestEarnedXp
        }

        val totalXp = questions.sumOf { question ->
            bestXpByQuestionId[question.questionId] ?: 0
        }

        val selectedLevelQuestions = questions
            .filter { it.level == selectedLevelId }
            .sortedBy { it.orderIndex }

        val completedQuestionsCount = completedIds.size

        val overallProgress = calculatePercent(
            completed = completedQuestionsCount,
            total = questions.size
        )

        val selectedCompletedCount = selectedLevelQuestions.count {
            completedIds.contains(it.questionId)
        }

        val selectedProgress = calculatePercent(
            completed = selectedCompletedCount,
            total = selectedLevelQuestions.size
        )

        val levelXpProgress = LearningLevel.entries.map { level ->
            val levelQuestions = questions.filter { it.level == level.id }
            val earnedXp = levelQuestions.sumOf { question ->
                bestXpByQuestionId[question.questionId] ?: 0
            }
            val maxXp = XpCalculator.maxXpForLevel(levelQuestions.size)

            LevelXpProgress(
                levelId = level.id,
                earnedXp = earnedXp,
                maxXp = maxXp,
                progressPercent = XpCalculator.progressPercent(
                    earnedXp = earnedXp,
                    maxXp = maxXp
                )
            )
        }

        val xpProgressByLevelId = levelXpProgress.associateBy { it.levelId }

        val levels = LearningLevel.entries.mapIndexed { index, level ->
            val levelQuestions = questions.filter { it.level == level.id }
            val completedInLevel = levelQuestions.count { completedIds.contains(it.questionId) }
            val xpProgress = xpProgressByLevelId.getValue(level.id)

            LevelSummaryUi(
                levelId = level.id,
                number = index + 1,
                title = level.displayName,
                description = levelDescription(level.id),
                topicCount = levelQuestions.map { it.type }.distinct().size,
                questionCount = levelQuestions.size,
                completedCount = completedInLevel,
                progressPercent = calculatePercent(
                    completed = completedInLevel,
                    total = levelQuestions.size
                ),
                earnedXp = xpProgress.earnedXp,
                maxXp = xpProgress.maxXp,
                xpProgressPercent = xpProgress.progressPercent
            )
        }

        val allQuestionPreviews = questions
            .sortedWith(
                compareBy<QuestionEntity> { levelOrder(it.level) }
                    .thenBy { it.wave ?: 0 }
                    .thenBy { it.orderIndex }
            )
            .map { question ->
                val metadata = TaskPersonalizer.metadataFor(
                    questionType = question.type,
                    level = question.level
                )

                QuestionPreviewUi(
                    questionId = question.questionId,
                    levelId = question.level,
                    title = question.title,
                    typeLabel = questionTypeLabel(question.type),
                    difficulty = difficultyLabel(question.difficulty),
                    wave = question.wave,
                    orderIndex = question.orderIndex,
                    isCompleted = completedIds.contains(question.questionId),
                    bestScorePercent = bestScoreByQuestionId[question.questionId] ?: 0,
                    format = metadata.format.displayName,
                    focus = metadata.focus.displayName,
                    personalizationScore = TaskPersonalizer.score(
                        metadata = metadata,
                        preferredFormats = preferredFormats,
                        learningFocus = learningFocus
                    ),
                    source = question.source,
                    publicationMode = question.publicationMode
                )
            }

        val personalizedQuestionPreviews = allQuestionPreviews
            .sortedWith(
                compareByDescending<QuestionPreviewUi> { it.personalizationScore }
                    .thenBy { levelOrder(it.levelId) }
                    .thenBy { it.wave ?: 0 }
                    .thenBy { it.orderIndex }
                    .thenBy { it.questionId }
            )

        val approvedUserQuestionPreviews = approvedUserQuestions
            .sortedWith(
                compareBy<QuestionEntity> { levelOrder(it.level) }
                    .thenBy { it.wave ?: 0 }
                    .thenBy { it.orderIndex }
            )
            .map { question ->
                val metadata = TaskPersonalizer.metadataFor(
                    questionType = question.type,
                    level = question.level
                )
                QuestionPreviewUi(
                    questionId = question.questionId,
                    levelId = question.level,
                    title = question.title,
                    typeLabel = questionTypeLabel(question.type),
                    difficulty = difficultyLabel(question.difficulty),
                    wave = question.wave,
                    orderIndex = question.orderIndex,
                    isCompleted = completedIds.contains(question.questionId),
                    bestScorePercent = bestScoreByQuestionId[question.questionId] ?: 0,
                    format = metadata.format.displayName,
                    focus = metadata.focus.displayName,
                    personalizationScore = TaskPersonalizer.score(
                        metadata = metadata,
                        preferredFormats = preferredFormats,
                        learningFocus = learningFocus
                    ),
                    source = question.source,
                    publicationMode = question.publicationMode
                )
            }

        val questionPreviews = allQuestionPreviews
            .filter { it.levelId == selectedLevelId }
            .filter { !it.isCompleted }
            .take(4)
            .ifEmpty {
                allQuestionPreviews
                    .filter { it.levelId == selectedLevelId }
                    .take(4)
            }

        val skillStats = questions
            .groupBy { questionTypeLabel(it.type) }
            .map { (typeLabel, typeQuestions) ->
                val completedInType = typeQuestions.filter { completedIds.contains(it.questionId) }

                val averageScore = completedInType
                    .mapNotNull { bestScoreByQuestionId[it.questionId] }
                    .takeIf { it.isNotEmpty() }
                    ?.average()
                    ?.roundToInt()
                    ?: 0

                SkillProgressUi(
                    typeLabel = typeLabel,
                    totalCount = typeQuestions.size,
                    completedCount = completedInType.size,
                    averageScorePercent = averageScore
                )
            }
            .sortedByDescending { it.completedCount }

        val lastCompletedId = progress
            .filter { it.status == "completed" }
            .maxByOrNull { it.updatedAt }
            ?.questionId

        val lastCompletedQuestion = allQuestionPreviews.firstOrNull {
            it.questionId == lastCompletedId
        }

        HomeUiState(
            isLoading = false,
            userName = user?.displayName ?: "Marko",
            selectedLevel = selectedLevelId,
            selectedLevelName = selectedLevel.displayName,
            totalQuestions = questions.size,
            selectedLevelQuestions = selectedLevelQuestions.size,
            completedQuestions = completedQuestionsCount,
            streakDays = user?.streakDays ?: 0,
            xp = totalXp,
            levelXpProgress = levelXpProgress,
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
            questionPreviews = questionPreviews,
            allQuestions = allQuestionPreviews,
            personalizedQuestions = personalizedQuestionPreviews,
            approvedUserQuestions = approvedUserQuestionPreviews,
            areRemoteTasksLoading = remoteSyncState.isLoading,
            remoteTasksError = remoteSyncState.errorMessage,
            completedQuestionIds = completedIds,
            skillStats = skillStats,
            lastCompletedQuestion = lastCompletedQuestion,
            learningGoal = user?.learningGoal ?: "1–2 zadatka dnevno",
            preferredTaskFormat = user?.preferredTaskFormat ?: "Interaktivni koraci",
            learningFocus = user?.learningFocus ?: "Balansirano učenje",
            aiFollowUpEnabled = user?.aiFollowUpEnabled ?: true,
            hasCompletedOnboarding = user?.hasCompletedOnboarding ?: false,
        )
    }
        .flowOn(Dispatchers.Default)
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )

    init {
        viewModelScope.launch {
            repository.initializeDatabaseIfNeeded()
            remoteTaskRepository.refreshRemoteTasks()
        }
    }

    fun selectLevel(levelId: String) {
        viewModelScope.launch {
            repository.updateCurrentLevel(levelId)
        }
    }

    fun updateProfileSettings(
        displayName: String,
        currentLevel: String,
        learningGoal: String,
        preferredTaskFormat: String,
        learningFocus: String,
        aiFollowUpEnabled: Boolean
    ) {
        viewModelScope.launch {
            repository.updateProfileSettings(
                displayName = displayName,
                currentLevel = currentLevel,
                learningGoal = learningGoal,
                preferredTaskFormat = preferredTaskFormat,
                learningFocus = learningFocus,
                aiFollowUpEnabled = aiFollowUpEnabled
            )
        }
    }

    fun completeOnboarding(
        displayName: String,
        currentLevel: String,
        preferredTaskFormat: String,
        learningFocus: String
    ) {
        viewModelScope.launch {
            repository.completeOnboarding(
                displayName = displayName,
                currentLevel = currentLevel,
                preferredTaskFormat = preferredTaskFormat,
                learningFocus = learningFocus
            )
        }
    }

    fun resetProgress() {
        viewModelScope.launch {
            repository.resetProgress()
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

    private fun levelOrder(levelId: String): Int {
        return when (levelId) {
            LearningLevel.BEGINNER.id -> 1
            LearningLevel.JUNIOR.id -> 2
            LearningLevel.MEDIOR.id -> 3
            LearningLevel.SENIOR.id -> 4
            LearningLevel.ARCHITECT.id -> 5
            else -> 99
        }
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
            "expert" -> "Expert"
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
