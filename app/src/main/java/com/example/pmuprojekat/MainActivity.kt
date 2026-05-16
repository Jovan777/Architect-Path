package com.example.pmuprojekat

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pmuprojekat.ui.home.HomeViewModel
import com.example.pmuprojekat.ui.home.LevelQuestionsScreen
import com.example.pmuprojekat.ui.home.SoftwareDesignHomeScreen
import com.example.pmuprojekat.ui.main.MainTab
import com.example.pmuprojekat.ui.main.ProfileScreen
import com.example.pmuprojekat.ui.main.ProgressScreen
import com.example.pmuprojekat.ui.main.TasksScreen
import com.example.pmuprojekat.ui.main.WavesScreen
import com.example.pmuprojekat.ui.question.QuestionScreen
import com.example.pmuprojekat.ui.question.QuestionViewModel
import com.example.pmuprojekat.ui.theme.PMUProjekatTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.pmuprojekat.ui.main.SettingsScreen
import com.example.pmuprojekat.ui.home.QuestionPreviewUi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val questionViewModel: QuestionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PMUProjekatTheme {
                val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
                val questionUiState by questionViewModel.uiState.collectAsStateWithLifecycle()

                var routeBackStack by rememberSaveable {
                    mutableStateOf(listOf(tabRoute(MainTab.HOME)))
                }

                val currentRoute = routeBackStack.last()
                val selectedTab = tabFromRoute(currentRoute) ?: MainTab.HOME
                val openedLevelId = levelIdFromRoute(currentRoute)
                val openedQuestionId = questionIdFromRoute(currentRoute)
                val openedSettings = currentRoute == settingsRoute()

                fun popBackStack() {
                    if (routeBackStack.size > 1) {
                        routeBackStack = routeBackStack.dropLast(1)
                    }
                }

                fun navigateTo(route: String) {
                    if (routeBackStack.lastOrNull() == route) return
                    routeBackStack = routeBackStack + route
                }

                fun selectTab(tab: MainTab) {
                    if (tab == MainTab.HOME) {
                        routeBackStack = listOf(tabRoute(MainTab.HOME))
                    } else {
                        navigateTo(tabRoute(tab))
                    }
                }

                fun openLevel(levelId: String) {
                    homeViewModel.selectLevel(levelId)
                    navigateTo(levelRoute(levelId))
                }

                fun openQuestion(questionId: String) {
                    questionViewModel.startQuestion(questionId)
                    navigateTo(questionRoute(questionId))
                }

                fun replaceCurrentQuestion(questionId: String) {
                    questionViewModel.startQuestion(questionId)

                    val newRoute = questionRoute(questionId)

                    routeBackStack = if (routeBackStack.lastOrNull()?.startsWith("question:") == true) {
                        routeBackStack.dropLast(1) + newRoute
                    } else {
                        routeBackStack + newRoute
                    }
                }

                fun goBackToQuestionList(questionId: String?) {
                    val levelId = homeUiState.allQuestions
                        .firstOrNull { it.questionId == questionId }
                        ?.levelId
                        ?: questionUiState.level.takeIf { it.isNotBlank() }
                        ?: homeUiState.selectedLevel

                    homeViewModel.selectLevel(levelId)

                    val targetLevelRoute = levelRoute(levelId)
                    val existingLevelIndex = routeBackStack.indexOfLast { it == targetLevelRoute }

                    routeBackStack = if (existingLevelIndex >= 0) {
                        routeBackStack.take(existingLevelIndex + 1)
                    } else {
                        listOf(
                            tabRoute(MainTab.HOME),
                            targetLevelRoute
                        )
                    }
                }

                BackHandler(enabled = routeBackStack.size > 1) {
                    popBackStack()
                }

                LaunchedEffect(openedQuestionId) {
                    openedQuestionId?.let { questionId ->
                        questionViewModel.ensureQuestionLoaded(questionId)
                    }
                }

                when {
                    openedQuestionId != null -> {
                        val nextQuestionId = nextQuestionIdAfter(
                            currentQuestionId = openedQuestionId,
                            questions = homeUiState.allQuestions
                        )

                        QuestionScreen(
                            uiState = questionUiState,
                            onBack = {
                                popBackStack()
                            },
                            onBackToQuestionList = {
                                goBackToQuestionList(openedQuestionId)
                            },
                            hasNextQuestion = nextQuestionId != null,
                            onNextQuestion = {
                                nextQuestionId?.let { questionId ->
                                    replaceCurrentQuestion(questionId)
                                }
                            },
                            onRetryQuestion = {
                                openedQuestionId?.let { questionId ->
                                    questionViewModel.startQuestion(questionId)
                                }
                            },
                            onToggleOption = questionViewModel::toggleOption,
                            onMoveOrderedOption = questionViewModel::moveOrderedOption,
                            onExcludeOrderedOption = questionViewModel::excludeOrderedOption,
                            onRestoreOrderedOption = questionViewModel::restoreOrderedOption,
                            onMapOptionToZone = questionViewModel::mapOptionToZone,
                            onRemoveOptionZone = questionViewModel::removeOptionZone,
                            onUpdateBlankAnswer = questionViewModel::updateBlankAnswer,
                            onUpdateFreeText = questionViewModel::updateFreeText,
                            onCheckStep = questionViewModel::checkCurrentStep,
                            onPreviousStep = questionViewModel::goToPreviousStep,
                            onNextStep = questionViewModel::goToNextStep,
                            onFinishQuestion = questionViewModel::finishQuestion
                        )
                    }

                    openedSettings -> {
                        SettingsScreen(
                            uiState = homeUiState,
                            onBack = {
                                popBackStack()
                            },
                            onSaveProfile = homeViewModel::updateProfileSettings,
                            onResetProgress = homeViewModel::resetProgress
                        )
                    }

                    openedLevelId != null -> {
                        LevelQuestionsScreen(
                            uiState = homeUiState,
                            levelId = openedLevelId,
                            onBack = {
                                popBackStack()
                            },
                            onQuestionClick = ::openQuestion
                        )
                    }

                    selectedTab == MainTab.HOME -> {
                        SoftwareDesignHomeScreen(
                            uiState = homeUiState,
                            onLevelSelected = ::openLevel,
                            onStartLearning = {
                                openLevel(homeUiState.selectedLevel)
                            },
                            onQuestionClick = ::openQuestion,
                            selectedTab = MainTab.HOME,
                            onBottomTabSelected = ::selectTab
                        )
                    }

                    selectedTab == MainTab.WAVES -> {
                        WavesScreen(
                            uiState = homeUiState,
                            selectedTab = MainTab.WAVES,
                            onBottomTabSelected = ::selectTab,
                            onLevelSelected = homeViewModel::selectLevel,
                            onQuestionClick = ::openQuestion
                        )
                    }

                    selectedTab == MainTab.TASKS -> {
                        TasksScreen(
                            uiState = homeUiState,
                            selectedTab = MainTab.TASKS,
                            onBottomTabSelected = ::selectTab,
                            onQuestionClick = ::openQuestion
                        )
                    }

                    selectedTab == MainTab.PROGRESS -> {
                        ProgressScreen(
                            uiState = homeUiState,
                            selectedTab = MainTab.PROGRESS,
                            onBottomTabSelected = ::selectTab,
                            onOpenTasks = {
                                navigateTo(tabRoute(MainTab.TASKS))
                            }
                        )
                    }

                    selectedTab == MainTab.PROFILE -> {
                        ProfileScreen(
                            uiState = homeUiState,
                            selectedTab = MainTab.PROFILE,
                            onBottomTabSelected = ::selectTab,
                            onOpenSettings = {
                                navigateTo(settingsRoute())
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun tabRoute(tab: MainTab): String {
    return "tab:${tab.name}"
}

private fun levelRoute(levelId: String): String {
    return "level:$levelId"
}

private fun questionRoute(questionId: String): String {
    return "question:$questionId"
}

private fun settingsRoute(): String {
    return "settings"
}

private fun tabFromRoute(route: String): MainTab? {
    if (!route.startsWith("tab:")) return null

    return runCatching {
        MainTab.valueOf(route.substringAfter("tab:"))
    }.getOrNull()
}

private fun levelIdFromRoute(route: String): String? {
    return route
        .takeIf { it.startsWith("level:") }
        ?.substringAfter("level:")
}

private fun questionIdFromRoute(route: String): String? {
    return route
        .takeIf { it.startsWith("question:") }
        ?.substringAfter("question:")
}

private fun nextQuestionIdAfter(
    currentQuestionId: String?,
    questions: List<QuestionPreviewUi>
): String? {
    if (currentQuestionId == null) return null

    val orderedQuestions = questions.sortedWith(
        compareBy<QuestionPreviewUi> { mainLevelOrder(it.levelId) }
            .thenBy { it.wave ?: 0 }
            .thenBy { it.orderIndex }
            .thenBy { it.questionId }
    )

    val currentIndex = orderedQuestions.indexOfFirst {
        it.questionId == currentQuestionId
    }

    if (currentIndex == -1) return null

    return orderedQuestions
        .getOrNull(currentIndex + 1)
        ?.questionId
}

private fun mainLevelOrder(levelId: String): Int {
    return when (levelId) {
        "beginner" -> 1
        "junior" -> 2
        "medior" -> 3
        "senior" -> 4
        "architect" -> 5
        else -> 99
    }
}
