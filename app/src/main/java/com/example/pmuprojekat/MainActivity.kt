package com.example.pmuprojekat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import com.example.pmuprojekat.ui.home.SoftwareDesignHomeScreen
import com.example.pmuprojekat.ui.home.HomeViewModel
import com.example.pmuprojekat.ui.theme.PMUProjekatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PMUProjekatTheme {
                val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

                SoftwareDesignHomeScreen(
                    uiState = uiState,
                    onLevelSelected = homeViewModel::selectLevel,
                    onStartLearning = {
                        /*
                         * Ovde kasnije otvaramo ekran zadatka.
                         * Za sada ostaje pripremljeno mesto za navigaciju.
                         */
                    },
                    onQuestionClick = { questionId ->
                        /*
                         * Ovde kasnije otvaramo konkretan zadatak po ID-u.
                         * Primer: P1.1, J1.1, A2.3...
                         */
                    }
                )
            }
        }
    }
}