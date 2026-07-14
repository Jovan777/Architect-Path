package com.example.pmuprojekat.ui.home

import androidx.compose.runtime.Immutable
import com.example.pmuprojekat.core.model.LevelXpProgress

@Immutable
data class HomeUiState(
    val isLoading: Boolean = true,

    val userName: String = "Marko",
    val selectedLevel: String = "beginner",
    val selectedLevelName: String = "Početnik",

    val totalQuestions: Int = 0,
    val selectedLevelQuestions: Int = 0,
    val completedQuestions: Int = 0,

    val streakDays: Int = 0,
    val xp: Int = 0,
    val levelXpProgress: List<LevelXpProgress> = emptyList(),
    val overallProgressPercent: Int = 0,
    val selectedLevelProgressPercent: Int = 0,

    val activeCardTitle: String = "Prvi modul — Početnik",
    val activeCardSubtitle: String = "Izaberi nivo i započni učenje.",
    val activeCardProgressPercent: Int = 0,

    val levels: List<LevelSummaryUi> = emptyList(),

    val questionPreviews: List<QuestionPreviewUi> = emptyList(),
    val allQuestions: List<QuestionPreviewUi> = emptyList(),
    val personalizedQuestions: List<QuestionPreviewUi> = emptyList(),
    val approvedUserQuestions: List<QuestionPreviewUi> = emptyList(),
    val areRemoteTasksLoading: Boolean = false,
    val remoteTasksError: String? = null,

    val completedQuestionIds: Set<String> = emptySet(),
    val skillStats: List<SkillProgressUi> = emptyList(),
    val lastCompletedQuestion: QuestionPreviewUi? = null,

    val learningGoal: String = "1–2 zadatka dnevno",
    val preferredTaskFormat: String = "Interaktivni koraci",
    val learningFocus: String = "Balansirano učenje",
    val aiFollowUpEnabled: Boolean = true,
    val hasCompletedOnboarding: Boolean = false,
)

@Immutable
data class LevelSummaryUi(
    val levelId: String,
    val number: Int,
    val title: String,
    val description: String,
    val topicCount: Int,
    val questionCount: Int,
    val completedCount: Int = 0,
    val progressPercent: Int = 0,
    val earnedXp: Int = 0,
    val maxXp: Int = 0,
    val xpProgressPercent: Int = 0
)

@Immutable
data class QuestionPreviewUi(
    val questionId: String,
    val levelId: String = "",
    val title: String,
    val typeLabel: String,
    val difficulty: String,
    val wave: Int?,
    val orderIndex: Int = 0,
    val isCompleted: Boolean = false,
    val bestScorePercent: Int = 0,
    val format: String = "",
    val focus: String = "",
    val personalizationScore: Int = 0
)

@Immutable
data class SkillProgressUi(
    val typeLabel: String,
    val totalCount: Int,
    val completedCount: Int,
    val averageScorePercent: Int
)
