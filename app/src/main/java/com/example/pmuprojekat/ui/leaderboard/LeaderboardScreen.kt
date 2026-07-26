package com.example.pmuprojekat.ui.leaderboard

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.data.repository.LeaderboardEntry
import com.example.pmuprojekat.ui.home.AppPalette

@Composable
fun LeaderboardScreen(
    uiState: LeaderboardUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
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
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = 18.dp,
                end = 18.dp,
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 14.dp,
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                LeaderboardHeader(onBack = onBack)
            }

            when {
                uiState.isLoading -> item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AppPalette.Blue)
                    }
                }

                uiState.errorMessage != null -> item {
                    MessageCard(
                        message = uiState.errorMessage,
                        actionLabel = "Pokušaj ponovo",
                        onAction = onRetry
                    )
                }

                uiState.entries.isEmpty() -> item {
                    MessageCard(message = "Još nema korisnika na rang-listi.")
                }

                else -> {
                    itemsIndexed(
                        items = uiState.entries,
                        key = { _, entry -> entry.uid }
                    ) { index, entry ->
                        LeaderboardRow(
                            position = index + 1,
                            entry = entry,
                            isCurrentUser = entry.uid == uiState.currentUserId
                        )
                    }

                    val currentOutsideLoadedList = uiState.currentUserEntry?.takeIf { current ->
                        uiState.entries.none { it.uid == current.uid }
                    }
                    currentOutsideLoadedList?.let { current ->
                        item {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tvoj rezultat",
                                color = AppPalette.TextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        item {
                            LeaderboardRow(
                                position = null,
                                entry = current,
                                isCurrentUser = true
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LeaderboardHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = BorderStroke(1.dp, AppPalette.Border),
            shadowElevation = 2.dp
        ) {
            TextButton(
                onClick = onBack,
                modifier = Modifier.size(52.dp)
            ) {
                Text(
                    text = "<",
                    color = AppPalette.Navy,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp, top = 2.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Rang-lista korisnika",
                color = AppPalette.TextPrimary,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Poredak prema ukupnim poenima osvojenim u zadacima.",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun LeaderboardRow(
    position: Int?,
    entry: LeaderboardEntry,
    isCurrentUser: Boolean
) {
    val podiumStyle = podiumStyleFor(position)
    val containerColor = when {
        podiumStyle != null -> podiumStyle.containerColor
        isCurrentUser -> Color(0xFFEFF6FF)
        else -> Color.White
    }
    val borderColor = when {
        podiumStyle != null -> podiumStyle.borderColor
        isCurrentUser -> Color(0xFF93C5FD)
        else -> AppPalette.Border
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        border = BorderStroke(if (podiumStyle != null) 1.5.dp else 1.dp, borderColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (podiumStyle != null) 4.dp else 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            position?.let {
                LeaderboardPosition(
                    position = it,
                    podiumStyle = podiumStyle
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = entry.displayName,
                        color = AppPalette.TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (isCurrentUser) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AppPalette.Blue
                        ) {
                            Text(
                                text = "Ti",
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
            Text(
                text = "${entry.totalPoints} poena",
                color = podiumStyle?.accentColor ?: AppPalette.TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
private fun LeaderboardPosition(
    position: Int,
    podiumStyle: PodiumStyle?
) {
    if (podiumStyle == null) {
        Text(
            text = "$position.",
            modifier = Modifier.padding(end = 12.dp),
            color = AppPalette.Indigo,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold
        )
        return
    }

    Surface(
        modifier = Modifier
            .padding(end = 12.dp)
            .size(40.dp),
        shape = RoundedCornerShape(12.dp),
        color = podiumStyle.badgeColor
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = position.toString(),
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

private data class PodiumStyle(
    val containerColor: Color,
    val borderColor: Color,
    val badgeColor: Color,
    val accentColor: Color
)

private fun podiumStyleFor(position: Int?): PodiumStyle? {
    return when (position) {
        1 -> PodiumStyle(
            containerColor = Color(0xFFFFFDE7),
            borderColor = Color(0xFFFACC15),
            badgeColor = Color(0xFFEAB308),
            accentColor = Color(0xFF854D0E)
        )
        2 -> PodiumStyle(
            containerColor = Color(0xFFF8FAFC),
            borderColor = Color(0xFF94A3B8),
            badgeColor = Color(0xFF64748B),
            accentColor = Color(0xFF475569)
        )
        3 -> PodiumStyle(
            containerColor = Color(0xFFFFF7ED),
            borderColor = Color(0xFFEA580C),
            badgeColor = Color(0xFFC2410C),
            accentColor = Color(0xFF9A3412)
        )
        else -> null
    }
}

@Composable
private fun MessageCard(
    message: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = message,
                color = AppPalette.TextSecondary,
                fontSize = 14.sp
            )
            if (actionLabel != null && onAction != null) {
                Button(onClick = onAction) {
                    Text(actionLabel)
                }
            }
        }
    }
}
