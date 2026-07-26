package com.example.pmuprojekat

import android.net.Uri
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
import com.example.pmuprojekat.ui.onboarding.OnboardingScreen
import com.example.pmuprojekat.ui.question.QuestionScreen
import com.example.pmuprojekat.ui.question.QuestionSessionMode
import com.example.pmuprojekat.ui.question.QuestionViewModel
import com.example.pmuprojekat.ui.taskcreation.TaskCreationScreen
import com.example.pmuprojekat.ui.taskcreation.TaskCreationViewModel
import com.example.pmuprojekat.ui.theme.PMUProjekatTheme
import com.example.pmuprojekat.ui.aichat.AiChatScreen
import com.example.pmuprojekat.ui.aichat.AiChatViewModel
import com.example.pmuprojekat.ui.admin.AdminAccessScreen
import com.example.pmuprojekat.ui.admin.AdminAuthViewModel
import com.example.pmuprojekat.ui.admin.AdminTaskAttemptHistoryScreen
import com.example.pmuprojekat.ui.admin.AdminTaskPreviewLoadingScreen
import com.example.pmuprojekat.ui.admin.AdminTaskSubmissionDetailScreen
import com.example.pmuprojekat.ui.admin.AdminTaskSubmissionsScreen
import com.example.pmuprojekat.ui.admin.AdminTaskSubmissionsViewModel
import com.example.pmuprojekat.ui.admin.AdminUserDetailScreen
import com.example.pmuprojekat.ui.admin.AdminUsersScreen
import com.example.pmuprojekat.ui.admin.AdminUsersViewModel
import com.example.pmuprojekat.ui.leaderboard.LeaderboardScreen
import com.example.pmuprojekat.ui.leaderboard.LeaderboardViewModel
import com.example.pmuprojekat.ui.vsai.VsAiChallengeScreen
import com.example.pmuprojekat.ui.vsai.VsAiHistoryScreen
import com.example.pmuprojekat.ui.vsai.VsAiLevelSelectionScreen
import com.example.pmuprojekat.ui.vsai.VsAiViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.pmuprojekat.ui.main.SettingsScreen
import com.example.pmuprojekat.ui.home.QuestionPreviewUi
import com.example.pmuprojekat.ui.encyclopedia.EncyclopediaCategoriesScreen
import com.example.pmuprojekat.ui.encyclopedia.EncyclopediaTermDetailScreen
import com.example.pmuprojekat.ui.encyclopedia.EncyclopediaTermsScreen
import com.example.pmuprojekat.ui.encyclopedia.EncyclopediaViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val questionViewModel: QuestionViewModel by viewModels()
    private val taskCreationViewModel: TaskCreationViewModel by viewModels()
    private val encyclopediaViewModel: EncyclopediaViewModel by viewModels()
    private val aiChatViewModel: AiChatViewModel by viewModels()
    private val vsAiViewModel: VsAiViewModel by viewModels()
    private val adminAuthViewModel: AdminAuthViewModel by viewModels()
    private val adminUsersViewModel: AdminUsersViewModel by viewModels()
    private val adminTaskSubmissionsViewModel: AdminTaskSubmissionsViewModel by viewModels()
    private val leaderboardViewModel: LeaderboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PMUProjekat)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PMUProjekatTheme {
                val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
                val questionUiState by questionViewModel.uiState.collectAsStateWithLifecycle()
                val taskCreationUiState by taskCreationViewModel.uiState.collectAsStateWithLifecycle()
                val encyclopediaUiState by encyclopediaViewModel.uiState.collectAsStateWithLifecycle()
                val aiChatUiState by aiChatViewModel.uiState.collectAsStateWithLifecycle()
                val vsAiUiState by vsAiViewModel.uiState.collectAsStateWithLifecycle()
                val adminAuthUiState by adminAuthViewModel.uiState.collectAsStateWithLifecycle()
                val adminUsersUiState by adminUsersViewModel.uiState.collectAsStateWithLifecycle()
                val adminTaskSubmissionsUiState by
                    adminTaskSubmissionsViewModel.uiState.collectAsStateWithLifecycle()
                val leaderboardUiState by leaderboardViewModel.uiState.collectAsStateWithLifecycle()

                var routeBackStack by rememberSaveable {
                    mutableStateOf(listOf(tabRoute(MainTab.HOME)))
                }

                val currentRoute = routeBackStack.last()
                val selectedTab = tabFromRoute(currentRoute) ?: MainTab.HOME
                val openedLevelId = levelIdFromRoute(currentRoute)
                val openedQuestionId = questionIdFromRoute(currentRoute)
                val openedSettings = currentRoute == settingsRoute()
                val openedVsAi = currentRoute == vsAiRoute()
                val openedVsAiLevelId = vsAiLevelIdFromRoute(currentRoute)
                val openedVsAiHistory = currentRoute == vsAiHistoryRoute()
                val openedTaskCreation = currentRoute == taskCreationRoute()
                val openedEncyclopedia = currentRoute == encyclopediaRoute()
                val openedEncyclopediaCategoryId = encyclopediaCategoryIdFromRoute(currentRoute)
                val openedEncyclopediaTerm = encyclopediaTermIdsFromRoute(currentRoute)
                val openedAiChat = currentRoute == aiChatRoute()
                val openedAdminAccess = currentRoute == adminAccessRoute()
                val openedAdminUsers = currentRoute == adminUsersRoute()
                val openedAdminUserId = adminUserIdFromRoute(currentRoute)
                val openedAdminTask = adminUserTaskFromRoute(currentRoute)
                val openedAdminTaskSubmissions =
                    currentRoute == adminTaskSubmissionsRoute()
                val openedAdminTaskSubmissionId =
                    adminTaskSubmissionIdFromRoute(currentRoute)
                val openedAdminTaskPlay = adminTaskPlayFromRoute(currentRoute)
                val openedLeaderboard = currentRoute == leaderboardRoute()

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

                fun goBackToVsAiLevels() {
                    val existingIndex = routeBackStack.indexOfLast { it == vsAiRoute() }
                    routeBackStack = if (existingIndex >= 0) {
                        routeBackStack.take(existingIndex + 1)
                    } else {
                        listOf(tabRoute(MainTab.HOME), vsAiRoute())
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

                LaunchedEffect(openedVsAiLevelId) {
                    openedVsAiLevelId?.let(vsAiViewModel::ensureChallenge)
                }

                LaunchedEffect(openedAdminAccess) {
                    if (openedAdminAccess) {
                        adminAuthViewModel.verifySession()
                    }
                }

                LaunchedEffect(openedLeaderboard) {
                    if (openedLeaderboard) {
                        leaderboardViewModel.load()
                    }
                }

                LaunchedEffect(openedAdminUsers) {
                    if (openedAdminUsers) {
                        adminUsersViewModel.loadUsers()
                    }
                }

                LaunchedEffect(openedAdminUserId) {
                    openedAdminUserId?.let(adminUsersViewModel::loadUserDetails)
                }

                LaunchedEffect(openedAdminTask) {
                    openedAdminTask?.let { route ->
                        adminUsersViewModel.loadUserDetails(route.userId)
                    }
                }

                LaunchedEffect(openedAdminTaskSubmissions) {
                    if (openedAdminTaskSubmissions) {
                        adminTaskSubmissionsViewModel.loadSubmissions()
                    }
                }

                LaunchedEffect(openedAdminTaskSubmissionId) {
                    openedAdminTaskSubmissionId?.let(
                        adminTaskSubmissionsViewModel::loadSubmission
                    )
                }

                LaunchedEffect(openedAdminTaskPlay) {
                    openedAdminTaskPlay?.let { route ->
                        if (
                            adminTaskSubmissionsUiState.detail
                                ?.summary
                                ?.submissionId != route.submissionId
                        ) {
                            adminTaskSubmissionsViewModel.loadSubmission(route.submissionId)
                        }
                    }
                }

                LaunchedEffect(
                    openedAdminTaskPlay,
                    adminTaskSubmissionsUiState.detail
                ) {
                    val playRoute = openedAdminTaskPlay
                    val detail = adminTaskSubmissionsUiState.detail
                    if (
                        playRoute != null &&
                        detail?.summary?.submissionId == playRoute.submissionId
                    ) {
                        detail.playableQuestion?.let { question ->
                            when (playRoute.mode) {
                                QuestionSessionMode.ADMIN_PREVIEW ->
                                    questionViewModel.startAdminPreview(question)
                                QuestionSessionMode.ADMIN_TEST ->
                                    questionViewModel.startAdminTest(question)
                                QuestionSessionMode.NORMAL -> Unit
                            }
                        }
                    }
                }

                LaunchedEffect(adminUsersUiState.requiresAdminLogin) {
                    if (adminUsersUiState.requiresAdminLogin) {
                        val adminAccessIndex = routeBackStack.indexOfLast {
                            it == adminAccessRoute()
                        }
                        routeBackStack = if (adminAccessIndex >= 0) {
                            routeBackStack.take(adminAccessIndex + 1)
                        } else {
                            listOf(
                                tabRoute(MainTab.PROFILE),
                                adminAccessRoute()
                            )
                        }
                        adminUsersViewModel.consumeAuthorizationRedirect()
                        adminAuthViewModel.verifySession()
                    }
                }

                LaunchedEffect(adminTaskSubmissionsUiState.requiresAdminLogin) {
                    if (adminTaskSubmissionsUiState.requiresAdminLogin) {
                        val adminAccessIndex = routeBackStack.indexOfLast {
                            it == adminAccessRoute()
                        }
                        routeBackStack = if (adminAccessIndex >= 0) {
                            routeBackStack.take(adminAccessIndex + 1)
                        } else {
                            listOf(
                                tabRoute(MainTab.PROFILE),
                                adminAccessRoute()
                            )
                        }
                        adminTaskSubmissionsViewModel.consumeAuthorizationRedirect()
                        adminAuthViewModel.verifySession()
                    }
                }

                when {
                    !homeUiState.isLoading && !homeUiState.hasCompletedOnboarding -> {
                        OnboardingScreen(
                            uiState = homeUiState,
                            onComplete = homeViewModel::completeOnboarding
                        )
                    }

                    openedAdminTaskPlay != null -> {
                        val playableQuestion = adminTaskSubmissionsUiState.detail
                            ?.takeIf {
                                it.summary.submissionId ==
                                    openedAdminTaskPlay.submissionId
                            }
                            ?.playableQuestion
                        val isQuestionReady =
                            playableQuestion != null &&
                                questionUiState.questionId ==
                                    playableQuestion.question.questionId &&
                                questionUiState.sessionMode ==
                                    openedAdminTaskPlay.mode

                        if (!isQuestionReady) {
                            AdminTaskPreviewLoadingScreen(onBack = ::popBackStack)
                        } else {
                            QuestionScreen(
                                uiState = questionUiState,
                                onBack = ::popBackStack,
                                onBackToQuestionList = ::popBackStack,
                                hasNextQuestion = false,
                                onNextQuestion = {},
                                onRetryQuestion = {
                                    when (openedAdminTaskPlay.mode) {
                                        QuestionSessionMode.ADMIN_PREVIEW ->
                                            questionViewModel.startAdminPreview(playableQuestion)
                                        QuestionSessionMode.ADMIN_TEST ->
                                            questionViewModel.startAdminTest(playableQuestion)
                                        QuestionSessionMode.NORMAL -> Unit
                                    }
                                },
                                onToggleOption = questionViewModel::toggleOption,
                                onMoveOrderedOption = questionViewModel::moveOrderedOption,
                                onUpdateOrderedOptions =
                                    questionViewModel::updateOrderedOptions,
                                onExcludeOrderedOption =
                                    questionViewModel::excludeOrderedOption,
                                onRestoreOrderedOption =
                                    questionViewModel::restoreOrderedOption,
                                onMapOptionToZone =
                                    questionViewModel::mapOptionToZone,
                                onRemoveOptionZone =
                                    questionViewModel::removeOptionZone,
                                onUpdateBlankAnswer =
                                    questionViewModel::updateBlankAnswer,
                                onUpdateFreeText = questionViewModel::updateFreeText,
                                onUpdateAiFollowUpAnswer =
                                    questionViewModel::updateAiFollowUpAnswer,
                                onRequestAiAnalysis =
                                    questionViewModel::requestAiAnalysis,
                                onRequestSketchAnalysis =
                                    questionViewModel::requestSketchAnalysis,
                                onClearSketchAnalysis =
                                    questionViewModel::clearSketchAnalysis,
                                onCheckStep = questionViewModel::checkCurrentStep,
                                onPreviousStep = questionViewModel::goToPreviousStep,
                                onNextStep = questionViewModel::goToNextStep,
                                onFinishQuestion = questionViewModel::finishQuestion
                            )
                        }
                    }

                    openedAdminTaskSubmissionId != null -> {
                        AdminTaskSubmissionDetailScreen(
                            uiState = adminTaskSubmissionsUiState,
                            onBack = ::popBackStack,
                            onRetry = {
                                adminTaskSubmissionsViewModel.loadSubmission(
                                    openedAdminTaskSubmissionId
                                )
                            },
                            onPreview = {
                                navigateTo(
                                    adminTaskPlayRoute(
                                        submissionId = openedAdminTaskSubmissionId,
                                        mode = QuestionSessionMode.ADMIN_PREVIEW
                                    )
                                )
                            },
                            onTestSolve = {
                                navigateTo(
                                    adminTaskPlayRoute(
                                        submissionId = openedAdminTaskSubmissionId,
                                        mode = QuestionSessionMode.ADMIN_TEST
                                    )
                                )
                            },
                            onRequestAction =
                                adminTaskSubmissionsViewModel::requestAction,
                            onDismissConfirmation =
                                adminTaskSubmissionsViewModel::dismissConfirmation,
                            onConfirmAction =
                                adminTaskSubmissionsViewModel::confirmAction,
                            onRejectionReasonChange =
                                adminTaskSubmissionsViewModel::updateRejectionReason
                        )
                    }

                    openedAdminTaskSubmissions -> {
                        AdminTaskSubmissionsScreen(
                            uiState = adminTaskSubmissionsUiState,
                            onBack = ::popBackStack,
                            onRetry =
                                adminTaskSubmissionsViewModel::loadSubmissions,
                            onFilterSelected =
                                adminTaskSubmissionsViewModel::selectFilter,
                            onSubmissionClick = { submissionId ->
                                navigateTo(
                                    adminTaskSubmissionRoute(submissionId)
                                )
                            }
                        )
                    }

                    openedAdminTask != null -> {
                        AdminTaskAttemptHistoryScreen(
                            uiState = adminUsersUiState,
                            taskId = openedAdminTask.taskId,
                            onBack = ::popBackStack,
                            onRetry = {
                                adminUsersViewModel.loadUserDetails(openedAdminTask.userId)
                            }
                        )
                    }

                    openedAdminUserId != null -> {
                        AdminUserDetailScreen(
                            uiState = adminUsersUiState,
                            onBack = ::popBackStack,
                            onRetry = {
                                adminUsersViewModel.loadUserDetails(openedAdminUserId)
                            },
                            onTaskClick = { taskId ->
                                navigateTo(
                                    adminUserTaskRoute(
                                        userId = openedAdminUserId,
                                        taskId = taskId
                                    )
                                )
                            }
                        )
                    }

                    openedAdminUsers -> {
                        AdminUsersScreen(
                            uiState = adminUsersUiState,
                            onBack = ::popBackStack,
                            onRetry = adminUsersViewModel::loadUsers,
                            onUserClick = { uid ->
                                navigateTo(adminUserRoute(uid))
                            }
                        )
                    }

                    openedAdminAccess -> {
                        AdminAccessScreen(
                            uiState = adminAuthUiState,
                            onBack = ::popBackStack,
                            onEmailChange = adminAuthViewModel::updateEmail,
                            onPasswordChange = adminAuthViewModel::updatePassword,
                            onSignIn = adminAuthViewModel::signIn,
                            onSignOut = {
                                adminUsersViewModel.clearProtectedData()
                                adminTaskSubmissionsViewModel.clearProtectedData()
                                adminAuthViewModel.signOut()
                            },
                            onOpenUsers = {
                                navigateTo(adminUsersRoute())
                            },
                            onOpenTaskSubmissions = {
                                navigateTo(adminTaskSubmissionsRoute())
                            }
                        )
                    }

                    openedLeaderboard -> {
                        LeaderboardScreen(
                            uiState = leaderboardUiState,
                            onBack = ::popBackStack,
                            onRetry = leaderboardViewModel::load
                        )
                    }

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
                            onUpdateOrderedOptions = questionViewModel::updateOrderedOptions,
                            onExcludeOrderedOption = questionViewModel::excludeOrderedOption,
                            onRestoreOrderedOption = questionViewModel::restoreOrderedOption,
                            onMapOptionToZone = questionViewModel::mapOptionToZone,
                            onRemoveOptionZone = questionViewModel::removeOptionZone,
                            onUpdateBlankAnswer = questionViewModel::updateBlankAnswer,
                            onUpdateFreeText = questionViewModel::updateFreeText,
                            onUpdateAiFollowUpAnswer = questionViewModel::updateAiFollowUpAnswer,
                            onRequestAiAnalysis = questionViewModel::requestAiAnalysis,
                            onRequestSketchAnalysis = questionViewModel::requestSketchAnalysis,
                            onClearSketchAnalysis = questionViewModel::clearSketchAnalysis,
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

                    openedVsAi -> {
                        VsAiLevelSelectionScreen(
                            uiState = homeUiState,
                            historyCount = vsAiUiState.completedHistoryCount,
                            onBack = {
                                popBackStack()
                            },
                            onLevelSelected = { levelId ->
                                navigateTo(vsAiLevelRoute(levelId))
                            },
                            onOpenHistory = {
                                navigateTo(vsAiHistoryRoute())
                            }
                        )
                    }

                    openedVsAiLevelId != null -> {
                        VsAiChallengeScreen(
                            uiState = vsAiUiState,
                            onBack = {
                                popBackStack()
                            },
                            onInputChange = vsAiViewModel::updateInput,
                            onSendAnswer = vsAiViewModel::submitAnswer,
                            onStopChallenge = vsAiViewModel::stopChallenge,
                            onRetry = vsAiViewModel::retry,
                            onNewChallenge = {
                                vsAiViewModel.startNewChallenge(openedVsAiLevelId)
                            },
                            onBackToLevels = ::goBackToVsAiLevels,
                            onOpenHistory = {
                                navigateTo(vsAiHistoryRoute())
                            }
                        )
                    }

                    openedVsAiHistory -> {
                        VsAiHistoryScreen(
                            uiState = vsAiUiState,
                            onBack = ::popBackStack
                        )
                    }

                    openedTaskCreation -> {
                        TaskCreationScreen(
                            uiState = taskCreationUiState,
                            viewModel = taskCreationViewModel,
                            onExit = {
                                popBackStack()
                            }
                        )
                    }

                    openedAiChat -> {
                        AiChatScreen(
                            uiState = aiChatUiState,
                            onBack = {
                                popBackStack()
                            },
                            onInputChange = aiChatViewModel::updateInput,
                            onSend = aiChatViewModel::sendCurrentMessage,
                            onSuggestionClick = aiChatViewModel::sendSuggestion,
                            onRetry = aiChatViewModel::retryLastFailed,
                            onClearConversation = aiChatViewModel::clearConversation
                        )
                    }

                    openedEncyclopediaTerm != null -> {
                        val (categoryId, termId) = openedEncyclopediaTerm
                        val category = encyclopediaUiState.categories
                            .firstOrNull { it.id == categoryId }
                        val term = category?.terms?.firstOrNull { it.id == termId }

                        EncyclopediaTermDetailScreen(
                            category = category,
                            term = term,
                            uiState = encyclopediaUiState,
                            onBack = {
                                popBackStack()
                            },
                            onExplain = { mode ->
                                encyclopediaViewModel.explain(termId, mode)
                            },
                            onCustomQuestionChange = encyclopediaViewModel::updateCustomQuestionText,
                            onAskCustomQuestion = {
                                encyclopediaViewModel.askCustomQuestion(termId)
                            }
                        )
                    }

                    openedEncyclopediaCategoryId != null -> {
                        val category = encyclopediaUiState.categories
                            .firstOrNull { it.id == openedEncyclopediaCategoryId }

                        EncyclopediaTermsScreen(
                            category = category,
                            onBack = {
                                popBackStack()
                            },
                            onTermClick = { termId ->
                                encyclopediaViewModel.clearExplanation()
                                navigateTo(
                                    encyclopediaTermRoute(
                                        categoryId = openedEncyclopediaCategoryId,
                                        termId = termId
                                    )
                                )
                            }
                        )
                    }

                    openedEncyclopedia -> {
                        EncyclopediaCategoriesScreen(
                            categories = encyclopediaUiState.categories,
                            onBack = {
                                popBackStack()
                            },
                            onCategoryClick = { categoryId ->
                                navigateTo(encyclopediaCategoryRoute(categoryId))
                            },
                            onTermClick = { categoryId, termId ->
                                encyclopediaViewModel.clearExplanation()
                                navigateTo(
                                    encyclopediaTermRoute(
                                        categoryId = categoryId,
                                        termId = termId
                                    )
                                )
                            }
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
                            onOpenVsAi = {
                                navigateTo(vsAiRoute())
                            },
                            onOpenTaskCreation = {
                                taskCreationViewModel.startNewFlow()
                                navigateTo(taskCreationRoute())
                            },
                            onOpenEncyclopedia = {
                                navigateTo(encyclopediaRoute())
                            },
                            onOpenAiChat = {
                                navigateTo(aiChatRoute())
                            },
                            onOpenLeaderboard = {
                                navigateTo(leaderboardRoute())
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
                            },
                            onOpenAdminAccess = {
                                navigateTo(adminAccessRoute())
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

private fun adminAccessRoute(): String {
    return "admin-access"
}

private fun adminUsersRoute(): String {
    return "admin-users"
}

private fun adminTaskSubmissionsRoute(): String {
    return "admin-task-submissions"
}

private fun adminTaskSubmissionRoute(submissionId: String): String {
    return "admin-task-submission:${Uri.encode(submissionId)}"
}

private fun adminTaskPlayRoute(
    submissionId: String,
    mode: QuestionSessionMode
): String {
    val modeValue = when (mode) {
        QuestionSessionMode.ADMIN_PREVIEW -> "preview"
        QuestionSessionMode.ADMIN_TEST -> "test"
        QuestionSessionMode.NORMAL -> error("Normalan režim nije admin pregled.")
    }
    return "admin-task-play:$modeValue:${Uri.encode(submissionId)}"
}

private fun adminUserRoute(userId: String): String {
    return "admin-user:${Uri.encode(userId)}"
}

private fun adminUserTaskRoute(userId: String, taskId: String): String {
    return "admin-user-task:${Uri.encode(userId)}:${Uri.encode(taskId)}"
}

private fun leaderboardRoute(): String {
    return "leaderboard"
}

private fun vsAiRoute(): String {
    return "vs-ai"
}

private fun taskCreationRoute(): String {
    return "task-creation"
}

private fun aiChatRoute(): String {
    return "ai-chat"
}

private fun encyclopediaRoute(): String {
    return "encyclopedia"
}

private fun encyclopediaCategoryRoute(categoryId: String): String {
    return "encyclopedia-category:$categoryId"
}

private fun encyclopediaTermRoute(categoryId: String, termId: String): String {
    return "encyclopedia-term:$categoryId:$termId"
}

private fun vsAiLevelRoute(levelId: String): String {
    return "vs-ai-level:$levelId"
}

private fun vsAiHistoryRoute(): String {
    return "vs-ai-history"
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

private fun vsAiLevelIdFromRoute(route: String): String? {
    return route
        .takeIf { it.startsWith("vs-ai-level:") }
        ?.substringAfter("vs-ai-level:")
}

private fun adminUserIdFromRoute(route: String): String? {
    return route
        .takeIf { it.startsWith("admin-user:") }
        ?.substringAfter("admin-user:")
        ?.let(Uri::decode)
}

private fun adminTaskSubmissionIdFromRoute(route: String): String? {
    return route
        .takeIf { it.startsWith("admin-task-submission:") }
        ?.substringAfter("admin-task-submission:")
        ?.let(Uri::decode)
}

private data class AdminTaskPlayRoute(
    val submissionId: String,
    val mode: QuestionSessionMode
)

private fun adminTaskPlayFromRoute(route: String): AdminTaskPlayRoute? {
    if (!route.startsWith("admin-task-play:")) return null
    val parts = route.substringAfter("admin-task-play:").split(':', limit = 2)
    if (parts.size != 2) return null
    val mode = when (parts[0]) {
        "preview" -> QuestionSessionMode.ADMIN_PREVIEW
        "test" -> QuestionSessionMode.ADMIN_TEST
        else -> return null
    }
    return AdminTaskPlayRoute(
        submissionId = Uri.decode(parts[1]),
        mode = mode
    )
}

private data class AdminUserTaskRoute(
    val userId: String,
    val taskId: String
)

private fun adminUserTaskFromRoute(route: String): AdminUserTaskRoute? {
    if (!route.startsWith("admin-user-task:")) return null
    val parts = route.substringAfter("admin-user-task:").split(':', limit = 2)
    if (parts.size != 2) return null
    return AdminUserTaskRoute(
        userId = Uri.decode(parts[0]),
        taskId = Uri.decode(parts[1])
    )
}

private fun encyclopediaCategoryIdFromRoute(route: String): String? {
    return route
        .takeIf { it.startsWith("encyclopedia-category:") }
        ?.substringAfter("encyclopedia-category:")
}

private fun encyclopediaTermIdsFromRoute(route: String): Pair<String, String>? {
    if (!route.startsWith("encyclopedia-term:")) return null

    val routeParts = route.substringAfter("encyclopedia-term:").split(':', limit = 2)
    if (routeParts.size != 2) return null

    return routeParts[0] to routeParts[1]
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
