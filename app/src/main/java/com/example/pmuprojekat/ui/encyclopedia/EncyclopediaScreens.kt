package com.example.pmuprojekat.ui.encyclopedia

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.pmuprojekat.ai.EncyclopediaExplanationMode
import com.example.pmuprojekat.data.encyclopedia.EncyclopediaCategory
import com.example.pmuprojekat.data.encyclopedia.EncyclopediaTerm
import com.example.pmuprojekat.ui.common.readableOutlinedTextFieldColors
import com.example.pmuprojekat.ui.home.AppPalette

@Composable
fun EncyclopediaCategoriesScreen(
    categories: List<EncyclopediaCategory>,
    onBack: () -> Unit,
    onCategoryClick: (String) -> Unit,
    onTermClick: (String, String) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val trimmedSearchQuery = searchQuery.trim()
    val searchResults = if (trimmedSearchQuery.isBlank()) {
        emptyList()
    } else {
        categories.flatMap { category ->
            category.terms
                .filter { term -> term.matchesGlobalSearch(trimmedSearchQuery, category.title) }
                .map { term -> term to category }
        }
    }

    EncyclopediaScaffold {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = encyclopediaContentPadding(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                EncyclopediaTopBar(
                    title = "Enciklopedija pojmova",
                    subtitle = "Kratka objašnjenja ključnih pojmova iz projektovanja softvera, arhitekture i AI oblasti.",
                    onBack = onBack
                )
            }

            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Pretraži pojmove") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = readableOutlinedTextFieldColors(
                        focusedBorderColor = AppPalette.Indigo,
                        unfocusedBorderColor = AppPalette.Border,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            if (trimmedSearchQuery.isBlank()) {
                item {
                    Text(
                        text = "Oblasti",
                        color = AppPalette.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                items(categories, key = EncyclopediaCategory::id) { category ->
                    EncyclopediaCategoryCard(
                        category = category,
                        onClick = { onCategoryClick(category.id) }
                    )
                }
            } else {
                item {
                    Text(
                        text = termCountLabel(searchResults.size),
                        color = AppPalette.TextSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (searchResults.isEmpty()) {
                    item {
                        EmptyEncyclopediaCard("Nema pojmova za unetu pretragu.")
                    }
                } else {
                    items(
                        items = searchResults,
                        key = { (term, category) -> "${category.id}:${term.id}" }
                    ) { (term, category) ->
                        EncyclopediaSearchResultCard(
                            term = term,
                            categoryTitle = category.title,
                            onClick = { onTermClick(category.id, term.id) }
                        )
                    }
                }
            }
        }
    }
}

private fun EncyclopediaTerm.matchesGlobalSearch(
    query: String,
    categoryTitle: String
): Boolean {
    return titleSr.contains(query, ignoreCase = true) ||
        titleEn.contains(query, ignoreCase = true) ||
        shortExplanation.contains(query, ignoreCase = true) ||
        categoryTitle.contains(query, ignoreCase = true)
}

@Composable
private fun EncyclopediaSearchResultCard(
    term: EncyclopediaTerm,
    categoryTitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = term.titleSr,
                    color = AppPalette.TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = term.titleEn,
                    color = AppPalette.Indigo,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = categoryTitle,
                    color = AppPalette.TextMuted,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = term.shortExplanation,
                    color = AppPalette.TextSecondary,
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "›",
                color = AppPalette.Indigo.copy(alpha = 0.8f),
                fontSize = 28.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}
@Composable
fun EncyclopediaTermsScreen(
    category: EncyclopediaCategory?,
    onBack: () -> Unit,
    onTermClick: (String) -> Unit
) {
    var searchQuery by rememberSaveable(category?.id) { mutableStateOf("") }
    val filteredTerms = category?.terms.orEmpty().filter { term ->
        searchQuery.isBlank() ||
            term.titleSr.contains(searchQuery, ignoreCase = true) ||
            term.titleEn.contains(searchQuery, ignoreCase = true) ||
            term.shortExplanation.contains(searchQuery, ignoreCase = true)
    }

    EncyclopediaScaffold {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = encyclopediaContentPadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                EncyclopediaTopBar(
                    title = category?.title ?: "Oblast nije pronađena",
                    subtitle = category?.description
                        ?: "Vrati se na listu oblasti i izaberi dostupnu kategoriju.",
                    onBack = onBack
                )
            }

            if (category != null) {
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Pretraži pojmove") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = readableOutlinedTextFieldColors(
                            focusedBorderColor = AppPalette.Indigo,
                            unfocusedBorderColor = AppPalette.Border,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }

                item {
                    Text(
                        text = termCountLabel(filteredTerms.size),
                        color = AppPalette.TextSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (filteredTerms.isEmpty()) {
                    item {
                        EmptyEncyclopediaCard("Nema pojmova koji odgovaraju pretrazi.")
                    }
                } else {
                    items(filteredTerms, key = EncyclopediaTerm::id) { term ->
                        EncyclopediaTermCard(
                            term = term,
                            onClick = { onTermClick(term.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EncyclopediaTermDetailScreen(
    category: EncyclopediaCategory?,
    term: EncyclopediaTerm?,
    uiState: EncyclopediaUiState,
    onBack: () -> Unit,
    onExplain: (EncyclopediaExplanationMode) -> Unit,
    onCustomQuestionChange: (String) -> Unit,
    onAskCustomQuestion: () -> Unit
) {
    EncyclopediaScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(encyclopediaContentPadding()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            EncyclopediaTopBar(
                title = term?.titleSr ?: "Pojam nije pronađen",
                subtitle = term?.titleEn ?: "Vrati se na listu pojmova.",
                onBack = onBack
            )

            if (category != null && term != null) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = AppPalette.Indigo.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = category.title,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        color = AppPalette.Indigo,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    border = BorderStroke(1.dp, AppPalette.Border)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Kratko objašnjenje",
                            color = AppPalette.Indigo,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = term.shortExplanation,
                            color = AppPalette.TextPrimary,
                            fontSize = 16.sp,
                            lineHeight = 25.sp
                        )
                    }
                }

                Text(
                    text = "Pitaj AI mentora",
                    color = AppPalette.TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                EncyclopediaExplanationMode.entries.forEach { mode ->
                    EncyclopediaActionButton(
                        mode = mode,
                        isLoading = uiState.isAiLoading && uiState.activeMode == mode,
                        enabled = !uiState.isAiLoading,
                        onClick = { onExplain(mode) }
                    )
                }

                uiState.aiError?.let { error ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFFF7ED),
                        border = BorderStroke(1.dp, Color(0xFFFDBA74))
                    ) {
                        Text(
                            text = error,
                            modifier = Modifier.padding(14.dp),
                            color = Color(0xFF9A3412),
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                if (uiState.isAiLoading) {
                    AiLoadingCard()
                } else if (uiState.aiResponseText != null) {
                    AiResponseCard(
                        title = uiState.aiResponseTitle.orEmpty(),
                        response = uiState.aiResponseText
                    )
                }

                CustomTermQuestionSection(
                    questionText = uiState.customQuestionText,
                    isLoading = uiState.isCustomQuestionLoading,
                    error = uiState.customQuestionError,
                    answer = uiState.customQuestionAnswer,
                    onQuestionChange = onCustomQuestionChange,
                    onAsk = onAskCustomQuestion
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun EncyclopediaScaffold(content: @Composable () -> Unit) {
    Scaffold(containerColor = AppPalette.Background) { innerPadding ->
        Box(
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
        ) {
            content()
        }
    }
}

@Composable
private fun EncyclopediaTopBar(
    title: String,
    subtitle: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            modifier = Modifier
                .size(44.dp)
                .clickable { onBack() },
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            shadowElevation = 5.dp,
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "‹",
                    color = AppPalette.TextPrimary,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

        Spacer(modifier = Modifier.size(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = title,
                color = AppPalette.TextPrimary,
                fontSize = 26.sp,
                lineHeight = 31.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = subtitle,
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun EncyclopediaCategoryCard(
    category: EncyclopediaCategory,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, AppPalette.Indigo.copy(alpha = 0.22f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppPalette.Indigo.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category.badgeText,
                    color = AppPalette.Indigo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.size(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = category.title,
                    color = AppPalette.TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                category.description?.let { description ->
                    Text(
                        text = description,
                        color = AppPalette.TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = termCountLabel(category.terms.size),
                    color = AppPalette.Indigo,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "›",
                color = AppPalette.Indigo,
                fontSize = 30.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun EncyclopediaTermCard(
    term: EncyclopediaTerm,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = term.titleSr,
                    color = AppPalette.TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = term.titleEn,
                    color = AppPalette.Indigo,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = term.shortExplanation,
                    color = AppPalette.TextSecondary,
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "›",
                color = AppPalette.Indigo.copy(alpha = 0.8f),
                fontSize = 28.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun EncyclopediaActionButton(
    mode: EncyclopediaExplanationMode,
    isLoading: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = AppPalette.Indigo.copy(alpha = if (enabled) 0.1f else 0.05f),
        border = BorderStroke(1.dp, AppPalette.Indigo.copy(alpha = 0.22f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = mode.actionLabel,
                color = if (enabled) AppPalette.Indigo else AppPalette.TextMuted,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    color = AppPalette.Indigo,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "›",
                    color = if (enabled) AppPalette.Indigo else AppPalette.TextMuted,
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Composable
private fun CustomTermQuestionSection(
    questionText: String,
    isLoading: Boolean,
    error: String?,
    answer: String?,
    onQuestionChange: (String) -> Unit,
    onAsk: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Pitaj o ovom pojmu",
            color = AppPalette.TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(1.dp, AppPalette.Border),
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = questionText,
                    onValueChange = onQuestionChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Šta ti nije jasno o ovom pojmu?") },
                    minLines = 2,
                    maxLines = 4,
                    isError = error != null && answer == null,
                    enabled = !isLoading,
                    shape = RoundedCornerShape(16.dp),
                    colors = readableOutlinedTextFieldColors(
                        focusedBorderColor = AppPalette.Indigo,
                        unfocusedBorderColor = AppPalette.Border,
                        errorBorderColor = Color(0xFFDC2626),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color(0xFFF8FAFC)
                    )
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable(enabled = !isLoading) { onAsk() },
                    shape = RoundedCornerShape(14.dp),
                    color = AppPalette.Indigo.copy(alpha = if (isLoading) 0.45f else 1f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        }
                        Text(
                            text = "Pitaj",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }

        error?.let { message ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFFF7ED),
                border = BorderStroke(1.dp, Color(0xFFFDBA74))
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(14.dp),
                    color = Color(0xFF9A3412),
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (isLoading) {
            AiLoadingCard(message = "AI priprema odgovor...")
        } else if (answer != null) {
            AiResponseCard(
                title = "Odgovor AI mentora",
                response = answer
            )
        }
    }
}

@Composable
private fun AiLoadingCard(message: String = "AI priprema objašnjenje...") {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border),
        shadowElevation = 3.dp
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = AppPalette.Indigo,
                strokeWidth = 2.5.dp
            )
            Text(
                text = message,
                color = AppPalette.TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun AiResponseCard(
    title: String,
    response: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F3FF)),
        border = BorderStroke(1.dp, AppPalette.Indigo.copy(alpha = 0.25f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                text = title,
                color = AppPalette.Indigo,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = response,
                color = AppPalette.TextPrimary,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun EmptyEncyclopediaCard(message: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(20.dp),
            color = AppPalette.TextSecondary,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun encyclopediaContentPadding() = androidx.compose.foundation.layout.PaddingValues(
    start = 18.dp,
    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 12.dp,
    end = 18.dp,
    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 24.dp
)

private fun termCountLabel(count: Int): String {
    return when {
        count % 10 == 1 && count % 100 != 11 -> "$count pojam"
        count % 10 in 2..4 && count % 100 !in 12..14 -> "$count pojma"
        else -> "$count pojmova"
    }
}
