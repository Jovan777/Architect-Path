package com.example.pmuprojekat.ui.home

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
    val overallProgressPercent: Int = 0,
    val selectedLevelProgressPercent: Int = 0,

    val activeCardTitle: String = "Prvi modul — Početnik",
    val activeCardSubtitle: String = "Izaberi nivo i započni učenje.",
    val activeCardProgressPercent: Int = 0,

    val levels: List<LevelSummaryUi> = emptyList(),

    /**
     * Kratak prikaz na početnoj strani.
     */
    val questionPreviews: List<QuestionPreviewUi> = emptyList(),

    /**
     * Sva pitanja iz baze, da možemo da otvorimo ekran nivoa
     * i prikažemo sve zadatke tog nivoa.
     */
    val allQuestions: List<QuestionPreviewUi> = emptyList()
)

data class LevelSummaryUi(
    val levelId: String,
    val number: Int,
    val title: String,
    val description: String,
    val topicCount: Int,
    val questionCount: Int,
    val completedCount: Int = 0
)

data class QuestionPreviewUi(
    val questionId: String,
    val levelId: String,
    val title: String,
    val typeLabel: String,
    val difficulty: String,
    val wave: Int?,
    val orderIndex: Int
)