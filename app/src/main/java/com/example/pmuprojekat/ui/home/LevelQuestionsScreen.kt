package com.example.pmuprojekat.ui.home


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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.pmuprojekat.core.model.LearningLevel

@Composable
fun LevelQuestionsScreen(
    uiState: HomeUiState,
    levelId: String,
    onBack: () -> Unit,
    onQuestionClick: (String) -> Unit
) {
    val level by remember(uiState.levels, levelId) {
        derivedStateOf {
            uiState.levels.firstOrNull { it.levelId == levelId }
        }
    }

    val levelQuestions by remember(uiState.allQuestions, levelId) {
        derivedStateOf {
            uiState.allQuestions
                .filter { it.levelId == levelId }
                .sortedWith(
                    compareBy<QuestionPreviewUi> { it.wave ?: 0 }
                        .thenBy { it.orderIndex }
                )
        }
    }

    val availableWaves by remember(levelQuestions) {
        derivedStateOf {
            levelQuestions
                .mapNotNull { it.wave }
                .distinct()
                .sorted()
        }
    }

    var selectedWave by remember(levelId) {
        mutableStateOf<Int?>(null)
    }

    val visibleQuestions by remember(levelQuestions, selectedWave) {
        derivedStateOf {
            if (selectedWave == null) {
                levelQuestions
            } else {
                levelQuestions.filter { it.wave == selectedWave }
            }
        }
    }

    Scaffold(
        containerColor = AppPalette.Background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8FAFC),
                            Color(0xFFF1F5F9),
                            Color.White
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(
                        top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                        bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp)
                    .padding(top = 12.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LevelQuestionsHeader(
                    level = level,
                    levelId = levelId,
                    onBack = onBack
                )

                LevelOverviewCard(
                    level = level,
                    totalQuestions = levelQuestions.size
                )

                if (availableWaves.isNotEmpty()) {
                    WaveFilterRow(
                        waves = availableWaves,
                        selectedWave = selectedWave,
                        onWaveSelected = { wave ->
                            selectedWave = wave
                        },
                        onAllSelected = {
                            selectedWave = null
                        }
                    )
                }

                if (visibleQuestions.isEmpty()) {
                    EmptyLevelQuestionsCard()
                } else {
                    QuestionsByWaveList(
                        questions = visibleQuestions,
                        showWaveHeaders = selectedWave == null,
                        onQuestionClick = onQuestionClick
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelQuestionsHeader(
    level: LevelSummaryUi?,
    levelId: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(42.dp)
                .clickable { onBack() },
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            shadowElevation = 6.dp,
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "‹",
                    color = AppPalette.TextPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Nivo",
                color = AppPalette.Blue,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = level?.title ?: levelTitle(levelId),
                color = AppPalette.TextPrimary,
                fontSize = 24.sp,
                lineHeight = 29.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        LevelBadge(levelId = levelId)
    }
}

@Composable
private fun LevelBadge(levelId: String) {
    val color = levelColor(levelId)

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = color.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.24f))
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            text = levelTitle(levelId),
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun LevelOverviewCard(
    level: LevelSummaryUi?,
    totalQuestions: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = level?.description ?: "Izaberi zadatak i nastavi učenje.",
                color = AppPalette.TextPrimary,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                LevelStatChip(
                    modifier = Modifier.weight(1f),
                    title = totalQuestions.toString(),
                    subtitle = "zadataka"
                )

                LevelStatChip(
                    modifier = Modifier.weight(1f),
                    title = (level?.topicCount ?: 0).toString(),
                    subtitle = "tipova"
                )

                LevelStatChip(
                    modifier = Modifier.weight(1f),
                    title = "0",
                    subtitle = "rešeno"
                )
            }

            LinearProgressIndicator(
                progress = { 0f },
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
private fun LevelStatChip(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = AppPalette.TextPrimary,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = subtitle,
                color = AppPalette.TextSecondary,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun WaveFilterRow(
    waves: List<Int>,
    selectedWave: Int?,
    onWaveSelected: (Int) -> Unit,
    onAllSelected: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "TALASI",
            color = AppPalette.TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.4.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                text = "Svi",
                selected = selectedWave == null,
                onClick = onAllSelected
            )

            waves.forEach { wave ->
                FilterChip(
                    text = "Talas $wave",
                    selected = selectedWave == wave,
                    onClick = { onWaveSelected(wave) }
                )
            }
        }
    }
}

@Composable
private fun FilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
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
            text = text,
            color = if (selected) Color.White else AppPalette.TextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun QuestionsByWaveList(
    questions: List<QuestionPreviewUi>,
    showWaveHeaders: Boolean,
    onQuestionClick: (String) -> Unit
) {
    val grouped by remember(questions) {
        derivedStateOf {
            questions.groupBy { it.wave ?: 0 }.toSortedMap()
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        grouped.forEach { (wave, waveQuestions) ->
            if (showWaveHeaders) {
                Text(
                    text = if (wave == 0) "Bez talasa" else "Talas $wave",
                    color = AppPalette.TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                waveQuestions.forEach { question ->
                    LevelQuestionCard(
                        question = question,
                        onClick = { onQuestionClick(question.questionId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelQuestionCard(
    question: QuestionPreviewUi,
    onClick: () -> Unit
) {
    val color = difficultyColor(question.difficulty)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = question.questionId,
                    color = color,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = question.title,
                    color = AppPalette.TextPrimary,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = question.typeLabel,
                        color = AppPalette.TextSecondary,
                        fontSize = 11.5.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "  •  ",
                        color = AppPalette.TextMuted,
                        fontSize = 11.5.sp
                    )

                    Text(
                        text = question.difficulty,
                        color = color,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = "›",
                color = AppPalette.TextMuted,
                fontSize = 28.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun EmptyLevelQuestionsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Nema zadataka za ovaj nivo",
                color = AppPalette.TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "Kada se pitanja za ovaj nivo nalaze u Room bazi, pojaviće se ovde automatski.",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}

private fun levelTitle(levelId: String): String {
    return when (levelId) {
        LearningLevel.BEGINNER.id -> "Početnik"
        LearningLevel.JUNIOR.id -> "Junior"
        LearningLevel.MEDIOR.id -> "Medior"
        LearningLevel.SENIOR.id -> "Senior"
        LearningLevel.ARCHITECT.id -> "Arhitekta"
        else -> levelId
    }
}

private fun levelColor(levelId: String): Color {
    return when (levelId) {
        LearningLevel.BEGINNER.id -> AppPalette.Blue
        LearningLevel.JUNIOR.id -> AppPalette.Green
        LearningLevel.MEDIOR.id -> AppPalette.Orange
        LearningLevel.SENIOR.id -> AppPalette.Purple
        LearningLevel.ARCHITECT.id -> AppPalette.Indigo
        else -> AppPalette.Blue
    }
}

private fun difficultyColor(difficulty: String): Color {
    return when (difficulty.lowercase()) {
        "lako" -> AppPalette.Green
        "srednje" -> AppPalette.Orange
        "teže" -> AppPalette.Purple
        "easy" -> AppPalette.Green
        "medium" -> AppPalette.Orange
        "hard" -> AppPalette.Purple
        "expert" -> AppPalette.Indigo
        else -> AppPalette.Blue
    }
}
