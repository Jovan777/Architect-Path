package com.example.pmuprojekat.ui.main


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.ui.home.AppPalette
import com.example.pmuprojekat.ui.home.HomeUiState
import com.example.pmuprojekat.ui.home.QuestionPreviewUi

@Composable
fun WavesScreen(
    uiState: HomeUiState,
    selectedTab: MainTab,
    onBottomTabSelected: (MainTab) -> Unit,
    onLevelSelected: (String) -> Unit,
    onQuestionClick: (String) -> Unit
) {
    var selectedLevelId by remember(uiState.selectedLevel) {
        mutableStateOf(uiState.selectedLevel)
    }

    val level by remember(uiState.levels, selectedLevelId) {
        derivedStateOf {
            uiState.levels.firstOrNull { it.levelId == selectedLevelId }
        }
    }

    val questions by remember(uiState.allQuestions, selectedLevelId) {
        derivedStateOf {
            uiState.allQuestions
                .filter { it.levelId == selectedLevelId }
                .sortedWith(
                    compareBy<QuestionPreviewUi> { it.wave ?: 0 }
                        .thenBy { it.orderIndex }
                )
        }
    }

    val waves by remember(questions) {
        derivedStateOf {
            questions.groupBy { it.wave ?: 0 }.toSortedMap().toList()
        }
    }

    val completedCount by remember(questions) {
        derivedStateOf {
            questions.count { it.isCompleted }
        }
    }

    Scaffold(
        containerColor = AppPalette.Background,
        bottomBar = {
            MainBottomBar(
                selectedTab = selectedTab,
                onTabSelected = onBottomTabSelected
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFF8FAFC),
                            Color(0xFFF1F5F9),
                            Color.White
                        )
                    )
                )
                .padding(innerPadding)
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                ),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = 18.dp,
                top = 12.dp,
                end = 18.dp,
                bottom = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "title") {
                Text(
                text = "Talasi",
                color = AppPalette.TextPrimary,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
                )
            }

            item(key = "subtitle") {
                Text(
                text = "Uči kroz zaokružene celine. Svaki talas povezuje više tipova zadataka istog nivoa znanja.",
                color = AppPalette.TextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
                )
            }

            item(key = "level-selector") {
                LevelSelectorRow(
                levels = uiState.levels,
                selectedLevelId = selectedLevelId,
                onLevelSelected = { levelId ->
                    selectedLevelId = levelId
                    onLevelSelected(levelId)
                }
                )
            }

            item(key = "hero") {
                WavePathHero(
                levelName = level?.title ?: "Nivo",
                questionCount = questions.size,
                    completedCount = completedCount
                )
            }

            if (waves.isEmpty()) {
                item(key = "empty") {
                    EmptyWaveCard()
                }
            } else {
                items(
                    items = waves,
                    key = { (wave, _) -> "wave-$wave" }
                ) { (wave, waveQuestions) ->
                    WaveCard(
                        wave = wave,
                        questions = waveQuestions,
                        onQuestionClick = onQuestionClick
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelSelectorRow(
    levels: List<com.example.pmuprojekat.ui.home.LevelSummaryUi>,
    selectedLevelId: String,
    onLevelSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        levels.forEach { level ->
            val selected = selectedLevelId == level.levelId

            Surface(
                modifier = Modifier.clickable { onLevelSelected(level.levelId) },
                shape = RoundedCornerShape(18.dp),
                color = if (selected) AppPalette.Blue else Color.White,
                border = BorderStroke(
                    1.dp,
                    if (selected) AppPalette.Blue else AppPalette.Border
                ),
                shadowElevation = if (selected) 5.dp else 2.dp
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 13.dp, vertical = 9.dp),
                    text = level.title,
                    color = if (selected) Color.White else AppPalette.TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun WavePathHero(
    levelName: String,
    questionCount: Int,
    completedCount: Int
) {
    val progress = if (questionCount == 0) 0 else (completedCount * 100 / questionCount)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Putanja za nivo $levelName",
                color = AppPalette.TextPrimary,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "$completedCount od $questionCount zadataka završeno. Talasi ti pomažu da ne učiš nasumično, već kroz smislen redosled.",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )

            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(100.dp)),
                color = AppPalette.Blue,
                trackColor = Color(0xFFE2E8F0),
                strokeCap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun WaveCard(
    wave: Int,
    questions: List<QuestionPreviewUi>,
    onQuestionClick: (String) -> Unit
) {
    val completed = questions.count { it.isCompleted }
    val progress = if (questions.isEmpty()) 0 else completed * 100 / questions.size

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(17.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(AppPalette.Blue.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (wave == 0) "—" else wave.toString(),
                        color = AppPalette.Blue,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = if (wave == 0) "Zadaci bez talasa" else "Talas $wave",
                        color = AppPalette.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "$completed/${questions.size} završeno",
                        color = AppPalette.TextSecondary,
                        fontSize = 12.sp
                    )
                }

                Text(
                    text = "$progress%",
                    color = AppPalette.Blue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(100.dp)),
                color = AppPalette.Blue,
                trackColor = Color(0xFFE2E8F0),
                strokeCap = StrokeCap.Round
            )

            questions.take(4).forEach { question ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onQuestionClick(question.questionId) },
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFF8FAFC),
                    border = BorderStroke(1.dp, AppPalette.Border)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${question.questionId} — ${question.title}",
                            color = AppPalette.TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = if (question.isCompleted) "✓" else "›",
                            color = if (question.isCompleted) AppPalette.Green else AppPalette.TextMuted,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyWaveCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Text(
            modifier = Modifier.padding(18.dp),
            text = "Za ovaj nivo još nema zadataka.",
            color = AppPalette.TextSecondary,
            fontSize = 14.sp
        )
    }
}
