package com.example.pmuprojekat.ui.main


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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

@Composable
fun ProfileScreen(
    uiState: HomeUiState,
    selectedTab: MainTab,
    onBottomTabSelected: (MainTab) -> Unit,
    onOpenSettings: () -> Unit,
    onOpenLeaderboard: () -> Unit,
    onOpenAdminAccess: () -> Unit
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
            ProfileHeroCard(uiState)

            LearningIdentityCard(uiState)

            ProfileInsightGrid(uiState)

            BadgesSection(uiState)

            LearningPortfolioCard(uiState)

            ProfileSettingsPreview(
                uiState = uiState,
                onOpenSettings = onOpenSettings
            )

            TextButton(
                onClick = onOpenLeaderboard,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Rang-lista",
                    color = AppPalette.Blue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            TextButton(
                onClick = onOpenAdminAccess,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Admin pristup",
                    color = AppPalette.TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ProfileHeroCard(uiState: HomeUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(34.dp),
        colors = CardDefaults.cardColors(containerColor = AppPalette.Navy),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            AppPalette.Navy,
                            Color(0xFF172554),
                            Color(0xFF4C1D95)
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(74.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFF60A5FA),
                                        Color(0xFFA78BFA)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.userName.take(1).uppercase(),
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = uiState.userName,
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = "Software Design Learner",
                            color = Color(0xFFCBD5E1),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Trenutni fokus: ${uiState.selectedLevelName}",
                            color = Color(0xFF93C5FD),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ProfileDarkStat(
                        modifier = Modifier.weight(1f),
                        value = uiState.completedQuestions.toString(),
                        label = "rešeno"
                    )

                    ProfileDarkStat(
                        modifier = Modifier.weight(1f),
                        value = uiState.xp.toString(),
                        label = "XP"
                    )

                    ProfileDarkStat(
                        modifier = Modifier.weight(1f),
                        value = "${uiState.overallProgressPercent}%",
                        label = "ukupno"
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileDarkStat(
    modifier: Modifier,
    value: String,
    label: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = Color.White.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.16f))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = label,
                color = Color(0xFFCBD5E1),
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun LearningIdentityCard(uiState: HomeUiState) {
    val identity = when {
        uiState.completedQuestions == 0 -> "Istraživač početnik"
        uiState.overallProgressPercent < 20 -> "Pattern Explorer"
        uiState.overallProgressPercent < 45 -> "Refactoring Builder"
        uiState.overallProgressPercent < 70 -> "System Thinker"
        else -> "Architecture Strategist"
    }

    val description = when (identity) {
        "Istraživač početnik" ->
            "Tvoj profil još nema dovoljno rešenih zadataka. Prvi cilj je da stekneš ritam i upoznaš osnovne tipove pitanja."

        "Pattern Explorer" ->
            "Dobro napreduješ kroz osnove. Fokus je na prepoznavanju obrazaca i razlici između sličnih rešenja."

        "Refactoring Builder" ->
            "Ulaziš u praktično razmišljanje: struktura koda, uloge klasa i uklanjanje loših zavisnosti."

        "System Thinker" ->
            "Tvoj rad već prelazi nivo pojedinačnog obrasca i ulazi u sistemske posledice odluka."

        else ->
            "Razmišljaš kroz trade-off, skaliranje, incidente i granice sistema — upravo ono što radi arhitekta."
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Learning identity",
                color = AppPalette.Indigo,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = identity,
                color = AppPalette.TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = description,
                color = AppPalette.TextSecondary,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )

            LinearProgressIndicator(
                progress = { uiState.overallProgressPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(100.dp)),
                color = AppPalette.Indigo,
                trackColor = Color(0xFFE2E8F0),
                strokeCap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun ProfileInsightGrid(uiState: HomeUiState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfileInsightCard(
            modifier = Modifier.weight(1f),
            icon = "🎯",
            title = strongestArea(uiState),
            subtitle = "najjača oblast"
        )

        ProfileInsightCard(
            modifier = Modifier.weight(1f),
            icon = "🧭",
            title = growthArea(uiState),
            subtitle = "sledeći fokus"
        )
    }
}

@Composable
private fun ProfileInsightCard(
    modifier: Modifier,
    icon: String,
    title: String,
    subtitle: String
) {
    Card(
        modifier = modifier.height(142.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = icon,
                fontSize = 26.sp
            )

            Column {
                Text(
                    text = title,
                    color = AppPalette.TextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = subtitle,
                    color = AppPalette.TextSecondary,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun BadgesSection(uiState: HomeUiState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "ZNAČKE",
            color = AppPalette.TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.4.sp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BadgeCard(
                modifier = Modifier.weight(1f),
                unlocked = uiState.completedQuestions >= 1,
                icon = "🚀",
                title = "Prvi start"
            )

            BadgeCard(
                modifier = Modifier.weight(1f),
                unlocked = uiState.completedQuestions >= 10,
                icon = "🧩",
                title = "Pattern ritam"
            )

            BadgeCard(
                modifier = Modifier.weight(1f),
                unlocked = uiState.overallProgressPercent >= 50,
                icon = "🏗️",
                title = "Graditelj"
            )
        }
    }
}

@Composable
private fun BadgeCard(
    modifier: Modifier,
    unlocked: Boolean,
    icon: String,
    title: String
) {
    val background = if (unlocked) Color.White else Color(0xFFF1F5F9)
    val textColor = if (unlocked) AppPalette.TextPrimary else AppPalette.TextMuted

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        color = background,
        border = BorderStroke(1.dp, AppPalette.Border),
        shadowElevation = if (unlocked) 4.dp else 1.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                text = if (unlocked) icon else "🔒",
                fontSize = 25.sp
            )

            Text(
                text = title,
                color = textColor,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun LearningPortfolioCard(uiState: HomeUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Portfolio učenja",
                color = AppPalette.TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )

            PortfolioRow(
                label = "Aktivni nivo",
                value = uiState.selectedLevelName
            )

            PortfolioRow(
                label = "Ukupna baza pitanja",
                value = "${uiState.totalQuestions} zadataka"
            )

            PortfolioRow(
                label = "Završeno",
                value = "${uiState.completedQuestions} zadataka"
            )

            PortfolioRow(
                label = "Poslednji rezultat",
                value = uiState.lastCompletedQuestion?.let {
                    "${it.questionId} — ${it.bestScorePercent}%"
                } ?: "Još nema završenih zadataka"
            )
        }
    }
}

@Composable
private fun PortfolioRow(
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            color = AppPalette.TextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier.weight(1.2f),
            text = value,
            color = AppPalette.TextPrimary,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProfileSettingsPreview(
    uiState: HomeUiState,
    onOpenSettings: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Podešavanja profila",
                    color = AppPalette.TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                TextButton(
                    onClick = onOpenSettings
                ) {
                    Text(
                        text = "Uredi",
                        color = AppPalette.Blue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            SettingPreviewRow("Cilj učenja", uiState.learningGoal)
            FavoriteTaskTypesPreview(uiState.preferredTaskFormat)
            SettingPreviewRow("Fokus učenja", uiState.learningFocus)
            SettingPreviewRow(
                label = "AI follow-up",
                value = if (uiState.aiFollowUpEnabled) "Uključen" else "Isključen"
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FavoriteTaskTypesPreview(
    preferredTaskFormat: String
) {
    val taskTypes = preferredTaskFormat
        .split(",")
        .map { it.trim() }
        .filter { it.isNotEmpty() }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Omiljeni tipovi zadataka",
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            if (taskTypes.isEmpty()) {
                Text(
                    text = "Nisu izabrani omiljeni tipovi zadataka.",
                    color = AppPalette.TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    taskTypes.forEach { taskType ->
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color(0xFFEFF6FF),
                            border = BorderStroke(1.dp, AppPalette.Blue.copy(alpha = 0.16f))
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 11.dp, vertical = 7.dp),
                                text = taskType,
                                color = AppPalette.Blue,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingPreviewRow(
    label: String,
    value: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = label,
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = value,
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private fun strongestArea(uiState: HomeUiState): String {
    return uiState.skillStats
        .filter { it.completedCount > 0 }
        .maxByOrNull { it.averageScorePercent }
        ?.typeLabel
        ?: "Još nije poznato"
}

private fun growthArea(uiState: HomeUiState): String {
    return uiState.skillStats
        .filter { it.completedCount < it.totalCount }
        .minByOrNull { it.completedCount }
        ?.typeLabel
        ?: uiState.selectedLevelName
}
