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
import com.example.pmuprojekat.ui.question.QuestionScreen
import com.example.pmuprojekat.ui.question.QuestionViewModel
import com.example.pmuprojekat.ui.theme.PMUProjekatTheme
import dagger.hilt.android.AndroidEntryPoint

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

                var openedLevelId by rememberSaveable {
                    mutableStateOf<String?>(null)
                }

                var openedQuestionId by rememberSaveable {
                    mutableStateOf<String?>(null)
                }

                LaunchedEffect(openedQuestionId) {
                    openedQuestionId?.let { questionId ->
                        questionViewModel.loadQuestion(questionId)
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

                    openedLevelId != null -> {
                        LevelQuestionsScreen(
                            uiState = homeUiState,
                            levelId = openedLevelId!!,
                            onBack = {
                                openedLevelId = null
                            },
                            onQuestionClick = { questionId ->
                                openedQuestionId = questionId
                            }
                        )
                    }

                    else -> {
                        SoftwareDesignHomeScreen(
                            uiState = homeUiState,
                            onLevelSelected = { levelId ->
                                homeViewModel.selectLevel(levelId)
                                openedLevelId = levelId
                            },
                            onStartLearning = {
                                openedLevelId = homeUiState.selectedLevel
                            },
                            onQuestionClick = { questionId ->
                                openedQuestionId = questionId
                            }
                        )
                    }
                }
            }
        }
    }
}