package com.example.pmuprojekat.ui.aichat

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.ai.AiChatRole
import com.example.pmuprojekat.ai.AiChatRelevantTerm
import com.example.pmuprojekat.data.repository.AiChatMessage
import com.example.pmuprojekat.ui.common.readableOutlinedTextFieldColors
import com.example.pmuprojekat.ui.home.AppPalette

@Composable
fun AiChatScreen(
    uiState: AiChatUiState,
    onBack: () -> Unit,
    onInputChange: (String) -> Unit,
    onSend: () -> Unit,
    onSuggestionClick: (String) -> Unit,
    onRetry: () -> Unit,
    onClearConversation: () -> Unit
) {
    val listState = rememberLazyListState()
    val bottomItemCount = uiState.messages.size +
        if (uiState.isLoading) 1 else 0 +
        if (uiState.errorMessage != null) 1 else 0

    LaunchedEffect(bottomItemCount) {
        if (bottomItemCount > 0) {
            listState.animateScrollToItem(bottomItemCount - 1)
        }
    }

    Scaffold(
        containerColor = AppPalette.Background,
        bottomBar = {
            AiChatInputBar(
                inputText = uiState.inputText,
                isLoading = uiState.isLoading,
                canSend = uiState.canSend,
                onInputChange = onInputChange,
                onSend = onSend
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
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
                .padding(innerPadding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = 18.dp,
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 12.dp,
                end = 18.dp,
                bottom = 18.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AiChatTopBar(
                    onBack = onBack,
                    onClearConversation = onClearConversation,
                    hasMessages = uiState.messages.isNotEmpty()
                )
            }

            if (uiState.messages.isEmpty() && !uiState.isLoading) {
                item {
                    AiChatWelcomeCard()
                }
                item {
                    SuggestedPromptSection(
                        prompts = uiState.suggestedPrompts,
                        onSuggestionClick = onSuggestionClick
                    )
                }
            }

            if (uiState.relatedTermsForLastQuestion.isNotEmpty()) {
                item {
                    RelatedTermsSection(uiState.relatedTermsForLastQuestion)
                }
            }

            items(uiState.messages, key = AiChatMessage::id) { message ->
                AiMessageBubble(message)
            }

            if (uiState.isLoading) {
                item {
                    AiLoadingBubble()
                }
            }

            uiState.errorMessage?.let { error ->
                item {
                    AiChatErrorCard(
                        message = error,
                        canRetry = uiState.canRetry,
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}

@Composable
private fun AiChatTopBar(
    onBack: () -> Unit,
    onClearConversation: () -> Unit,
    hasMessages: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(46.dp)
                    .clickable { onBack() },
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = BorderStroke(1.dp, AppPalette.Border),
                shadowElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "‹",
                        color = AppPalette.Navy,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "Pričaj sa AI",
                    color = AppPalette.TextPrimary,
                    fontSize = 25.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Postavi pitanje iz projektovanja softvera, arhitekture, obrazaca ili pojmova iz aplikacije.",
                    color = AppPalette.TextSecondary,
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp
                )
            }
        }

        if (hasMessages) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClearConversation() },
                shape = RoundedCornerShape(16.dp),
                color = AppPalette.Indigo.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, AppPalette.Indigo.copy(alpha = 0.18f))
            ) {
                Text(
                    text = "Novi razgovor",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 11.dp),
                    color = AppPalette.Indigo,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun AiChatWelcomeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(1.dp, AppPalette.Indigo.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "AI mentor za učenje",
                color = AppPalette.Indigo,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Pitaj o obrascima, UML-u, arhitekturi, bazama, cache-u, read modelima, bezbednosti ili AI/RAG pojmovima. Ako pitanje ode van oblasti aplikacije, mentor će te vratiti na temu.",
                color = AppPalette.TextPrimary,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
        }
    }
}

@Composable
private fun SuggestedPromptSection(
    prompts: List<String>,
    onSuggestionClick: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
        Text(
            text = "Predlozi pitanja",
            color = AppPalette.TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold
        )
        prompts.forEach { prompt ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionClick(prompt) },
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                border = BorderStroke(1.dp, AppPalette.Border),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 13.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = prompt,
                        modifier = Modifier.weight(1f),
                        color = AppPalette.TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "›",
                        color = AppPalette.Indigo,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Composable
private fun RelatedTermsSection(terms: List<AiChatRelevantTerm>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Povezani pojmovi",
            color = AppPalette.TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            terms.forEach { term ->
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = AppPalette.Blue.copy(alpha = 0.1f),
                    border = BorderStroke(1.dp, AppPalette.Blue.copy(alpha = 0.18f))
                ) {
                    Text(
                        text = term.titleSr,
                        modifier = Modifier.padding(horizontal = 11.dp, vertical = 7.dp),
                        color = AppPalette.Blue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun AiMessageBubble(message: AiChatMessage) {
    val isUser = message.role == AiChatRole.USER
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(if (isUser) 0.84f else 0.92f),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = if (isUser) 20.dp else 6.dp,
                bottomEnd = if (isUser) 6.dp else 20.dp
            ),
            color = if (isUser) AppPalette.Blue else Color.White,
            border = if (isUser) null else BorderStroke(1.dp, AppPalette.Border),
            shadowElevation = if (isUser) 0.dp else 2.dp
        ) {
            Text(
                text = message.content,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp),
                color = if (isUser) Color.White else AppPalette.TextPrimary,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontWeight = if (isUser) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

@Composable
private fun AiLoadingBubble() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            border = BorderStroke(1.dp, AppPalette.Border),
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    color = AppPalette.Indigo,
                    strokeWidth = 2.dp
                )
                Text(
                    text = "AI razmišlja...",
                    color = AppPalette.TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun AiChatErrorCard(
    message: String,
    canRetry: Boolean,
    onRetry: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFFFF7ED),
        border = BorderStroke(1.dp, Color(0xFFFDBA74))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = message,
                color = Color(0xFF9A3412),
                fontSize = 13.sp,
                lineHeight = 19.sp,
                fontWeight = FontWeight.Bold
            )
            if (canRetry) {
                Button(
                    onClick = onRetry,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppPalette.Orange)
                ) {
                    Text(
                        text = "Pokušaj ponovo",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun AiChatInputBar(
    inputText: String,
    isLoading: Boolean,
    canSend: Boolean,
    onInputChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding(),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 14.dp,
                    top = 10.dp,
                    end = 14.dp,
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 10.dp
                ),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = onInputChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Postavi pitanje...") },
                enabled = !isLoading,
                minLines = 1,
                maxLines = 4,
                shape = RoundedCornerShape(18.dp),
                colors = readableOutlinedTextFieldColors(
                    focusedBorderColor = AppPalette.Blue,
                    unfocusedBorderColor = AppPalette.Border,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Button(
                onClick = onSend,
                enabled = canSend,
                modifier = Modifier.height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppPalette.Blue,
                    disabledContainerColor = AppPalette.Border
                )
            ) {
                Text(
                    text = "Pošalji",
                    color = if (canSend) Color.White else AppPalette.TextMuted,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
