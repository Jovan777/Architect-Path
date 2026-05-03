package com.example.pmuprojekat

import android.os.Bundle
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

                var selectedTabName by rememberSaveable {
                    mutableStateOf(MainTab.HOME.name)
                }

                var openedLevelId by rememberSaveable {
                    mutableStateOf<String?>(null)
                }

                var openedQuestionId by rememberSaveable {
                    mutableStateOf<String?>(null)
                }

                var openedSettings by rememberSaveable {
                    mutableStateOf(false)
                }

                val selectedTab = MainTab.valueOf(selectedTabName)

                fun selectTab(tab: MainTab) {
                    selectedTabName = tab.name
                    openedLevelId = null
                    openedQuestionId = null
                    openedSettings = false
                }

                fun openQuestion(questionId: String) {
                    questionViewModel.startQuestion(questionId)
                    openedQuestionId = questionId
                }

                LaunchedEffect(openedQuestionId) {
                    openedQuestionId?.let { questionId ->
                        questionViewModel.ensureQuestionLoaded(questionId)
                    }
                }

                when {
                    openedQuestionId != null -> {
                        QuestionScreen(
                            uiState = questionUiState,
                            onBack = {
                                openedQuestionId = null
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
                                openedSettings = false
                            },
                            onSaveProfile = homeViewModel::updateProfileSettings,
                            onResetProgress = homeViewModel::resetProgress
                        )
                    }

                    openedLevelId != null -> {
                        LevelQuestionsScreen(
                            uiState = homeUiState,
                            levelId = openedLevelId!!,
                            onBack = {
                                openedLevelId = null
                            },
                            onQuestionClick = ::openQuestion
                        )
                    }

                    selectedTab == MainTab.HOME -> {
                        SoftwareDesignHomeScreen(
                            uiState = homeUiState,
                            onLevelSelected = { levelId ->
                                homeViewModel.selectLevel(levelId)
                                openedLevelId = levelId
                            },
                            onStartLearning = {
                                openedLevelId = homeUiState.selectedLevel
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
                                selectTab(MainTab.TASKS)
                            }
                        )
                    }

                    selectedTab == MainTab.PROFILE -> {
                        ProfileScreen(
                            uiState = homeUiState,
                            selectedTab = MainTab.PROFILE,
                            onBottomTabSelected = ::selectTab,
                            onOpenSettings = {openedSettings = true}
                        )
                    }
                }
            }
        }
    }
}
