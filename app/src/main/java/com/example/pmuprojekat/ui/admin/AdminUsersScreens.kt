package com.example.pmuprojekat.ui.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.items
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
import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.data.repository.AdminUserDetails
import com.example.pmuprojekat.data.repository.AdminUserProgress
import com.example.pmuprojekat.data.repository.LevelProgressSnapshot
import com.example.pmuprojekat.data.repository.SyncedTaskAttempt
import com.example.pmuprojekat.ui.home.AppPalette
import java.text.DateFormat
import java.util.Date

@Composable
fun AdminUsersScreen(
    uiState: AdminUsersUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onUserClick: (String) -> Unit
) {
    AdminLazyScreen(
        title = "Korisnici",
        subtitle = "Pregled korisnika i sinhronizovanog napretka.",
        onBack = onBack
    ) {
        when {
            uiState.isUsersLoading -> loadingItem()
            uiState.usersError != null -> messageItem(
                message = uiState.usersError,
                actionLabel = "Pokušaj ponovo",
                onAction = onRetry
            )
            uiState.users.isEmpty() -> messageItem("Još nema evidentiranih korisnika.")
            else -> items(
                items = uiState.users,
                key = AdminUserProgress::uid
            ) { user ->
                AdminUserCard(
                    user = user,
                    onClick = { onUserClick(user.uid) }
                )
            }
        }
    }
}

@Composable
fun AdminUserDetailScreen(
    uiState: AdminUsersUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onTaskClick: (String) -> Unit
) {
    AdminLazyScreen(
        title = "Napredak korisnika",
        subtitle = "Rezultati bez odgovora, AI razgovora i privatnog sadržaja.",
        onBack = onBack
    ) {
        when {
            uiState.isDetailsLoading -> loadingItem()
            uiState.detailsError != null -> messageItem(
                message = uiState.detailsError,
                actionLabel = "Pokušaj ponovo",
                onAction = onRetry
            )
            uiState.details == null -> messageItem("Podaci o korisniku trenutno nisu dostupni.")
            else -> {
                val details = uiState.details
                item {
                    UserOverviewCard(details)
                }
                item {
                    SectionTitle("Napredak po nivou")
                }
                LearningLevel.entries.forEach { level ->
                    item(key = "level_${level.id}") {
                        LevelProgressCard(
                            levelName = level.displayName,
                            progress = details.profile.progressByLevel[level.id]
                                ?: LevelProgressSnapshot()
                        )
                    }
                }
                item {
                    SectionTitle("Zadaci i pokušaji")
                }
                val taskSummaries = details.attempts
                    .groupBy(SyncedTaskAttempt::taskId)
                    .values
                    .map { attempts -> attempts.sortedByDescending { it.completedAt } }
                    .sortedByDescending { it.firstOrNull()?.completedAt ?: 0L }

                if (taskSummaries.isEmpty()) {
                    messageItem("Još nema sinhronizovanih pokušaja za ovog korisnika.")
                } else {
                    items(
                        items = taskSummaries,
                        key = { it.first().taskId }
                    ) { attempts ->
                        AttemptedTaskCard(
                            attempts = attempts,
                            onClick = { onTaskClick(attempts.first().taskId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminTaskAttemptHistoryScreen(
    uiState: AdminUsersUiState,
    taskId: String,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    val attempts = uiState.details?.attempts
        ?.filter { it.taskId == taskId }
        ?.sortedByDescending { it.completedAt }
        .orEmpty()
    val taskTitle = attempts.firstOrNull()?.taskTitle ?: "Istorija pokušaja"

    AdminLazyScreen(
        title = taskTitle,
        subtitle = "Puna istorija rezultata za izabrani zadatak.",
        onBack = onBack
    ) {
        when {
            uiState.isDetailsLoading -> loadingItem()
            uiState.detailsError != null -> messageItem(
                message = uiState.detailsError,
                actionLabel = "Pokušaj ponovo",
                onAction = onRetry
            )
            attempts.isEmpty() -> messageItem("Nema evidentiranih pokušaja za ovaj zadatak.")
            else -> items(
                items = attempts,
                key = SyncedTaskAttempt::attemptId
            ) { attempt ->
                AttemptHistoryCard(attempt)
            }
        }
    }
}

@Composable
private fun AdminUserCard(
    user: AdminUserProgress,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = user.displayName,
                    modifier = Modifier.weight(1f),
                    color = AppPalette.TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = ">",
                    color = AppPalette.Blue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "${user.totalPoints} poena • ${user.uniqueTasksAttempted} zadataka • ${user.totalAttempts} pokušaja",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
            Text(
                text = "Poslednja aktivnost: ${formatDateTime(user.lastActiveAt)}",
                color = AppPalette.TextMuted,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun UserOverviewCard(details: AdminUserDetails) {
    val attempts = details.attempts
    val average = attempts
        .map(SyncedTaskAttempt::percentage)
        .takeIf { it.isNotEmpty() }
        ?.average()
        ?.toInt()
        ?: 0
    val best = attempts.maxOfOrNull(SyncedTaskAttempt::percentage) ?: 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(17.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = details.profile.displayName,
                color = AppPalette.TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            OverviewLine("Ukupno poena", details.profile.totalPoints.toString())
            OverviewLine("Ukupno pokušaja", details.profile.totalAttempts.toString())
            OverviewLine("Pokušani zadaci", details.profile.uniqueTasksAttempted.toString())
            OverviewLine("Završeni zadaci", details.profile.completedTasksCount.toString())
            OverviewLine("Prosečan rezultat", "$average%")
            OverviewLine("Najbolji rezultat", "$best%")
            OverviewLine("Poslednja aktivnost", formatDateTime(details.profile.lastActiveAt))
        }
    }
}

@Composable
private fun OverviewLine(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            color = AppPalette.TextSecondary,
            fontSize = 13.sp
        )
        Text(
            text = value,
            color = AppPalette.TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun LevelProgressCard(
    levelName: String,
    progress: LevelProgressSnapshot
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = levelName,
                color = AppPalette.TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "${progress.attemptedTasks} zadataka • ${progress.totalAttempts} pokušaja • ${progress.completedTasks} završeno",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )
            Text(
                text = "Prosečan uspeh: ${progress.averageSuccessPercentage}%",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun AttemptedTaskCard(
    attempts: List<SyncedTaskAttempt>,
    onClick: () -> Unit
) {
    val latest = attempts.first()
    val best = attempts.maxOf(SyncedTaskAttempt::percentage)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = latest.taskTitle,
                    modifier = Modifier.weight(1f),
                    color = AppPalette.TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = ">",
                    color = AppPalette.Blue,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "${levelName(latest.level)} • ${latest.taskType}",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )
            Text(
                text = "${attempts.size} pokušaja • najbolji $best% • poslednji ${latest.percentage}%",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )
            Text(
                text = formatDateTime(latest.completedAt),
                color = AppPalette.TextMuted,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun AttemptHistoryCard(attempt: SyncedTaskAttempt) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Pokušaj ${attempt.attemptNumber} — ${attempt.percentage}% — ${attempt.pointsAwarded} poena",
                color = AppPalette.TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = formatDateTime(attempt.completedAt),
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(top = 4.dp),
        color = AppPalette.TextPrimary,
        fontSize = 17.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

private fun androidx.compose.foundation.lazy.LazyListScope.loadingItem() {
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = AppPalette.Blue)
        }
    }
}

private fun androidx.compose.foundation.lazy.LazyListScope.messageItem(
    message: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    item {
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
}

@Composable
private fun AdminLazyScreen(
    title: String,
    subtitle: String,
    onBack: () -> Unit,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit
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
            contentPadding = PaddingValues(
                start = 18.dp,
                end = 18.dp,
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 14.dp,
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AdminUsersHeader(title, subtitle, onBack)
            }
            content()
        }
    }
}

@Composable
private fun AdminUsersHeader(
    title: String,
    subtitle: String,
    onBack: () -> Unit
) {
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
                text = title,
                color = AppPalette.TextPrimary,
                fontSize = 24.sp,
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

private fun levelName(levelId: String): String {
    return LearningLevel.fromId(levelId).displayName
}

private fun formatDateTime(epochMillis: Long?): String {
    if (epochMillis == null || epochMillis <= 0L) return "nije dostupno"
    return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
        .format(Date(epochMillis))
}
