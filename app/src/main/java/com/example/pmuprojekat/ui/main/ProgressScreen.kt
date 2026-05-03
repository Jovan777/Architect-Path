package com.example.pmuprojekat.ui.main


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.ui.home.AppPalette
import com.example.pmuprojekat.ui.home.HomeUiState
import com.example.pmuprojekat.ui.home.LevelSummaryUi
import com.example.pmuprojekat.ui.home.SkillProgressUi

@Composable
fun ProgressScreen(
    uiState: HomeUiState,
    selectedTab: MainTab,
    onBottomTabSelected: (MainTab) -> Unit,
    onOpenTasks: () -> Unit
) {
    Scaffold(
        containerColor = AppPalette.Background,
        bottomBar = {
            MainBottomBar(
                selectedTab = selectedTab,
                onTabSelected = onBottomTabSelected
            )
        }
    ) { innerPadding ->
        Column(
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
                )
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(top = 12.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProgressHeader(uiState)

            ProgressHeroCard(uiState)

            NextBestActionCard(
                uiState = uiState,
                onOpenTasks = onOpenTasks
            )

            SectionTitle("Napredak po nivoima")

            uiState.levels.forEach { level ->
                LevelProgressCard(level = level)
            }

            SectionTitle("Mapa veština")

            if (uiState.skillStats.isEmpty()) {
                EmptyInsightCard(
                    title = "Još nema dovoljno podataka",
                    text = "Kada rešiš nekoliko zadataka, ovde će se pojaviti prikaz najjačih oblasti i oblasti za vežbu."
                )
            } else {
                uiState.skillStats.take(8).forEach { skill ->
                    SkillProgressCard(skill = skill)
                }
            }
        }
    }
}

@Composable
private fun ProgressHeader(uiState: HomeUiState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Napredak",
            color = AppPalette.TextPrimary,
            fontSize = 30.sp,
            lineHeight = 35.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "Prati kako se tvoje znanje razvija kroz nivoe, talase i tipove zadataka.",
            color = AppPalette.TextSecondary,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun ProgressHeroCard(uiState: HomeUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = AppPalette.Navy),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            AppPalette.Navy,
                            Color(0xFF1E3A8A),
                            Color(0xFF312E81)
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressRing(
                    progress = uiState.overallProgressPercent / 100f,
                    label = "${uiState.overallProgressPercent}%"
                )

                Spacer(modifier = Modifier.width(18.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Ukupan razvoj",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "${uiState.completedQuestions} od ${uiState.totalQuestions} zadataka je završeno.",
                        color = Color(0xFFCBD5E1),
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MiniDarkStat("+${uiState.xp}", "XP")
                        MiniDarkStat(uiState.streakDays.toString(), "streak")
                    }
                }
            }
        }
    }
}

@Composable
private fun CircularProgressRing(
    progress: Float,
    label: String
) {
    Box(
        modifier = Modifier.size(106.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(106.dp)) {
            drawArc(
                color = Color.White.copy(alpha = 0.18f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 11.dp.toPx(), cap = StrokeCap.Round)
            )

            drawArc(
                color = Color(0xFF60A5FA),
                startAngle = -90f,
                sweepAngle = 360f * progress.coerceIn(0f, 1f),
                useCenter = false,
                style = Stroke(width = 11.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            text = label,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun MiniDarkStat(
    title: String,
    subtitle: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.16f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = subtitle,
                color = Color(0xFFCBD5E1),
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun NextBestActionCard(
    uiState: HomeUiState,
    onOpenTasks: () -> Unit
) {
    val text = when {
        uiState.completedQuestions == 0 ->
            "Najbolji sledeći korak je da rešiš prvi zadatak iz trenutno izabranog nivoa."

        uiState.overallProgressPercent < 25 ->
            "Fokusiraj se na kontinuitet: završi bar jedan talas iz nivoa ${uiState.selectedLevelName}."

        uiState.overallProgressPercent < 60 ->
            "Sada već imaš osnovu. Pređi na zadatke koji kombinuju više koraka i obrazloženje."

        else ->
            "Vreme je za teže scenarije: incidenti, trade-off odluke i arhitektonske procene."
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Pametna preporuka",
                color = AppPalette.Indigo,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = text,
                color = AppPalette.TextPrimary,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            if (uiState.lastCompletedQuestion != null) {
                Text(
                    text = "Poslednje završeno: ${uiState.lastCompletedQuestion.questionId} — ${uiState.lastCompletedQuestion.title}",
                    color = AppPalette.TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Button(
                onClick = onOpenTasks,
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppPalette.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Otvori zadatke",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun LevelProgressCard(level: LevelSummaryUi) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = level.title,
                    color = AppPalette.TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "${level.progressPercent}%",
                    color = AppPalette.Blue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Text(
                text = "${level.completedCount}/${level.questionCount} zadataka",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )

            LinearProgressIndicator(
                progress = { level.progressPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(100.dp)),
                color = AppPalette.Blue,
                trackColor = Color(0xFFE2E8F0),
                strokeCap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun SkillProgressCard(skill: SkillProgressUi) {
    val progress = if (skill.totalCount == 0) {
        0
    } else {
        ((skill.completedCount.toFloat() / skill.totalCount.toFloat()) * 100).toInt()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = skill.typeLabel,
                    color = AppPalette.TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${skill.completedCount}/${skill.totalCount}",
                    color = AppPalette.TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(100.dp)),
                color = AppPalette.Green,
                trackColor = Color(0xFFE2E8F0),
                strokeCap = StrokeCap.Round
            )

            Text(
                text = if (skill.completedCount == 0) {
                    "Još nema rešenih zadataka iz ove oblasti."
                } else {
                    "Prosečan najbolji rezultat: ${skill.averageScorePercent}%"
                },
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text.uppercase(),
        color = AppPalette.TextPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.4.sp
    )
}

@Composable
private fun EmptyInsightCard(
    title: String,
    text: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                color = AppPalette.TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = text,
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}