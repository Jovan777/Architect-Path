package com.example.pmuprojekat.ui.vsai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.LevelXpProgress
import com.example.pmuprojekat.ui.home.AppPalette
import com.example.pmuprojekat.ui.home.HomeUiState

private data class VsAiLevelOptionUi(
    val levelId: String,
    val number: Int,
    val title: String,
    val description: String,
    val accentColor: Color,
    val softColor: Color,
    val icon: String,
    val xpProgress: LevelXpProgress?,
    val isUnlocked: Boolean = true
)

@Composable
fun VsAiLevelSelectionScreen(
    uiState: HomeUiState,
    historyCount: Int,
    onBack: () -> Unit,
    onLevelSelected: (String) -> Unit,
    onOpenHistory: () -> Unit
) {
    val options = remember(uiState.levelXpProgress) {
        vsAiLevelOptions(uiState.levelXpProgress)
    }

    Scaffold(
        containerColor = AppPalette.Background
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
            VsAiTopBar(
                title = "VS AI mod",
                subtitle = "Odaberi nivo izazova i uđi u duel sa AI protivnikom.",
                onBack = onBack
            )

            VsAiHeroPanel()

            VsAiHistoryEntryCard(
                historyCount = historyCount,
                onClick = onOpenHistory
            )

            options.forEach { option ->
                VsAiLevelCard(
                    option = option,
                    onClick = { onLevelSelected(option.levelId) }
                )
            }
        }
    }
}

@Composable
private fun VsAiHistoryEntryCard(
    historyCount: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Indigo.copy(alpha = 0.22f)),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(AppPalette.Indigo.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = historyCount.toString(),
                    color = AppPalette.Indigo,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Istorija izazova",
                    color = AppPalette.TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = if (historyCount == 0) {
                        "Još nema završenih VS AI pokušaja."
                    } else {
                        "Pogledaj rezultate i razloge završetka prethodnih pokušaja."
                    },
                    color = AppPalette.TextSecondary,
                    fontSize = 12.5.sp,
                    lineHeight = 17.sp
                )
            }
            Text(
                text = ">",
                color = AppPalette.Indigo,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun VsAiTopBar(
    title: String,
    subtitle: String,
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

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = AppPalette.TextPrimary,
                fontSize = 27.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = subtitle,
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun VsAiHeroPanel() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        color = AppPalette.Navy,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(
                            AppPalette.Navy,
                            Color(0xFF1E3A8A),
                            Color(0xFF312E81)
                        )
                    )
                )
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "AI sparing arena",
                color = Color(0xFF93C5FD),
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Od Početnika do Arhitekte — izaberi svoj AI izazov.",
                color = Color.White,
                fontSize = 18.sp,
                lineHeight = 23.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Kartice su spremne za buduće XP uslove otključavanja, ali su trenutno dostupne za pregled toka.",
                color = Color(0xFFCBD5E1),
                fontSize = 12.5.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun VsAiLevelCard(
    option: VsAiLevelOptionUi,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(116.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, option.accentColor.copy(alpha = 0.32f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(78.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(option.accentColor)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                option.accentColor,
                                option.accentColor.copy(alpha = 0.72f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option.number.toString(),
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = option.title,
                    color = AppPalette.TextPrimary,
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = option.description,
                    color = AppPalette.TextSecondary,
                    fontSize = 12.5.sp,
                    lineHeight = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                val xp = option.xpProgress
                Text(
                    text = if (xp == null) {
                        "VS AI izazov"
                    } else {
                        "${xp.earnedXp}/${xp.maxXp} XP za ovaj nivo"
                    },
                    color = option.accentColor,
                    fontSize = 11.5.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(RoundedCornerShape(21.dp))
                    .background(
                        Brush.radialGradient(
                            listOf(
                                option.accentColor.copy(alpha = 0.22f),
                                option.softColor
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option.icon,
                    color = option.accentColor,
                    fontSize = if (option.icon == "</>") 16.sp else 25.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "›",
                color = option.accentColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

private fun vsAiLevelOptions(
    xpProgress: List<LevelXpProgress>
): List<VsAiLevelOptionUi> {
    val xpByLevel = xpProgress.associateBy { it.levelId }
    return listOf(
        VsAiLevelOptionUi(
            levelId = LearningLevel.BEGINNER.id,
            number = 1,
            title = "Početnik VS AI",
            description = "Osnovni obrasci, pojmovi i prva arhitektonska razmišljanja.",
            accentColor = AppPalette.Blue,
            softColor = Color(0xFFEFF6FF),
            icon = "!",
            xpProgress = xpByLevel[LearningLevel.BEGINNER.id]
        ),
        VsAiLevelOptionUi(
            levelId = LearningLevel.JUNIOR.id,
            number = 2,
            title = "Junior VS AI",
            description = "Primena obrazaca i tehničko rezonovanje u jednostavnijim scenarijima.",
            accentColor = AppPalette.Green,
            softColor = Color(0xFFECFDF5),
            icon = "</>",
            xpProgress = xpByLevel[LearningLevel.JUNIOR.id]
        ),
        VsAiLevelOptionUi(
            levelId = LearningLevel.MEDIOR.id,
            number = 3,
            title = "Medior VS AI",
            description = "Povezivanje komponenti, tokova i sistemskih odluka.",
            accentColor = AppPalette.Orange,
            softColor = Color(0xFFFFF7ED),
            icon = "⊙",
            xpProgress = xpByLevel[LearningLevel.MEDIOR.id]
        ),
        VsAiLevelOptionUi(
            levelId = LearningLevel.SENIOR.id,
            number = 4,
            title = "Senior VS AI",
            description = "Performanse, posledice odluka i napredniji trade-off scenariji.",
            accentColor = AppPalette.Purple,
            softColor = Color(0xFFF5F3FF),
            icon = "◇",
            xpProgress = xpByLevel[LearningLevel.SENIOR.id]
        ),
        VsAiLevelOptionUi(
            levelId = LearningLevel.ARCHITECT.id,
            number = 5,
            title = "Arhitekta VS AI",
            description = "Sistemsko razmišljanje, evolucija arhitekture i složene odluke.",
            accentColor = AppPalette.Indigo,
            softColor = Color(0xFFEEF2FF),
            icon = "▦",
            xpProgress = xpByLevel[LearningLevel.ARCHITECT.id]
        )
    )
}
