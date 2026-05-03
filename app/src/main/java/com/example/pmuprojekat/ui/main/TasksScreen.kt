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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.ui.home.AppPalette
import com.example.pmuprojekat.ui.home.HomeUiState
import com.example.pmuprojekat.ui.home.QuestionPreviewUi

private enum class TaskStatusFilter(
    val label: String
) {
    ALL("Svi"),
    OPEN("Nerešeni"),
    DONE("Rešeni")
}

@Composable
fun TasksScreen(
    uiState: HomeUiState,
    selectedTab: MainTab,
    onBottomTabSelected: (MainTab) -> Unit,
    onQuestionClick: (String) -> Unit
) {
    var selectedLevelId by remember(uiState.selectedLevel) {
        mutableStateOf(uiState.selectedLevel)
    }

    var statusFilter by remember {
        mutableStateOf(TaskStatusFilter.ALL)
    }

    var search by remember {
        mutableStateOf("")
    }

    val filteredQuestions by remember(
        uiState.personalizedQuestions,
        selectedLevelId,
        statusFilter,
        search
    ) {
        derivedStateOf {
            uiState.personalizedQuestions
                .filter { it.levelId == selectedLevelId }
                .filter { question ->
                    when (statusFilter) {
                        TaskStatusFilter.ALL -> true
                        TaskStatusFilter.OPEN -> !question.isCompleted
                        TaskStatusFilter.DONE -> question.isCompleted
                    }
                }
                .filter { question ->
                    search.isBlank() ||
                            question.title.contains(search, ignoreCase = true) ||
                            question.questionId.contains(search, ignoreCase = true) ||
                            question.typeLabel.contains(search, ignoreCase = true)
                }
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
                    text = "Zadaci",
                    color = AppPalette.TextPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            item(key = "subtitle") {
                Text(
                text = "Katalog svih zadataka. Pretraži, filtriraj i direktno uđi u vežbu.",
                color = AppPalette.TextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
                )
            }

            item(key = "search") {
                OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = search,
                onValueChange = { search = it },
                label = { Text("Pretraži po ID-u, nazivu ili tipu") },
                singleLine = true,
                shape = RoundedCornerShape(20.dp)
                )
            }

            item(key = "level-filter") {
                LevelFilterRow(
                levels = uiState.levels,
                selectedLevelId = selectedLevelId,
                onSelected = { selectedLevelId = it }
                )
            }

            item(key = "status-filter") {
                StatusFilterRow(
                selected = statusFilter,
                onSelected = { statusFilter = it }
                )
            }

            item(key = "count") {
                Text(
                text = "${filteredQuestions.size} zadataka",
                color = AppPalette.TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
                )
            }

            items(
                items = filteredQuestions,
                key = { it.questionId }
            ) { question ->
                TaskCatalogCard(
                    question = question,
                    onClick = { onQuestionClick(question.questionId) }
                )
            }
        }
    }
}

@Composable
private fun LevelFilterRow(
    levels: List<com.example.pmuprojekat.ui.home.LevelSummaryUi>,
    selectedLevelId: String,
    onSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        levels.forEach { level ->
            FilterChip(
                text = level.title,
                selected = selectedLevelId == level.levelId,
                onClick = { onSelected(level.levelId) }
            )
        }
    }
}

@Composable
private fun StatusFilterRow(
    selected: TaskStatusFilter,
    onSelected: (TaskStatusFilter) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TaskStatusFilter.entries.forEach { filter ->
            FilterChip(
                text = filter.label,
                selected = selected == filter,
                onClick = { onSelected(filter) }
            )
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
private fun TaskCatalogCard(
    question: QuestionPreviewUi,
    onClick: () -> Unit
) {
    val color = when (question.difficulty.lowercase()) {
        "lako" -> AppPalette.Green
        "srednje" -> AppPalette.Orange
        "teže" -> AppPalette.Purple
        "expert" -> AppPalette.Indigo
        else -> AppPalette.Blue
    }

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

                Text(
                    text = "${question.typeLabel} • ${question.difficulty}",
                    color = AppPalette.TextSecondary,
                    fontSize = 11.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Surface(
                shape = CircleShape,
                color = if (question.isCompleted) AppPalette.Green.copy(alpha = 0.13f) else Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, AppPalette.Border)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                    text = if (question.isCompleted) "✓" else "›",
                    color = if (question.isCompleted) AppPalette.Green else AppPalette.TextMuted,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}
