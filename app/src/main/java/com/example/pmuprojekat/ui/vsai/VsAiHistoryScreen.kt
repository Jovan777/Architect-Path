package com.example.pmuprojekat.ui.vsai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.ai.VsAiCompletionReason
import com.example.pmuprojekat.data.repository.VsAiAttempt
import com.example.pmuprojekat.data.repository.VsAiAttemptStatus
import com.example.pmuprojekat.ui.home.AppPalette
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun VsAiHistoryScreen(
    uiState: VsAiUiState,
    onBack: () -> Unit
) {
    val attempts = uiState.history.filter { it.status != VsAiAttemptStatus.IN_PROGRESS }
    Scaffold(containerColor = AppPalette.Background) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFF8FAFC), Color(0xFFF1F5F9), Color.White)
                    )
                )
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = 18.dp,
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 12.dp,
                end = 18.dp,
                bottom = 28.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                VsAiChallengeTopBar(
                    title = "Istorija izazova",
                    subtitle = "Prethodni VS AI pokušaji, rezultati i način završetka.",
                    onBack = onBack
                )
            }

            when {
                uiState.isHistoryLoading -> item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 42.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator(color = AppPalette.Indigo)
                        Text("Učitavam istoriju...", color = AppPalette.TextSecondary)
                    }
                }

                attempts.isEmpty() -> item { VsAiEmptyHistory() }

                else -> items(attempts, key = VsAiAttempt::id) { attempt ->
                    VsAiHistoryCard(attempt)
                }
            }
        }
    }
}

@Composable
private fun VsAiEmptyHistory() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border),
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                text = "Još nema završenih izazova",
                color = AppPalette.TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Izaberi nivo i završi prvi AI sparing. Rezultat će se pojaviti ovde.",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun VsAiHistoryCard(attempt: VsAiAttempt) {
    val accent = levelAccent(attempt.level)
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, accent.copy(alpha = 0.18f)),
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = accent.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = attempt.finalScore?.let { "$it%" } ?: "--",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                        color = accent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = levelDisplayName(attempt.level),
                        color = AppPalette.TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = formatAttemptDate(attempt.completedAt ?: attempt.updatedAt),
                        color = AppPalette.TextSecondary,
                        fontSize = 11.5.sp
                    )
                }
                Text(
                    text = "${attempt.roundsCount} r.",
                    color = AppPalette.TextSecondary,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = historyReasonLabel(attempt.completionReason),
                color = accent,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = attempt.finalSummary?.takeIf { it.isNotBlank() }
                    ?: "Za ovaj pokušaj nema završnog sažetka.",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}

private fun levelDisplayName(levelId: String): String {
    return when (levelId) {
        "beginner" -> "Početnik"
        "junior" -> "Junior"
        "medior" -> "Medior"
        "senior" -> "Senior"
        "architect" -> "Arhitekta"
        else -> "VS AI"
    }
}

private fun historyReasonLabel(reason: VsAiCompletionReason?): String {
    return when (reason) {
        VsAiCompletionReason.AI_SATISFIED -> "AI je prihvatio odgovor"
        VsAiCompletionReason.USER_STOPPED -> "Izazov je prekinut"
        VsAiCompletionReason.MAX_ROUNDS -> "Dostignut je maksimalan broj rundi"
        VsAiCompletionReason.ERROR -> "Izazov nije završen zbog greške"
        null -> "Završen pokušaj"
    }
}

private fun formatAttemptDate(timestamp: Long): String {
    return SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.ROOT).format(Date(timestamp))
}
