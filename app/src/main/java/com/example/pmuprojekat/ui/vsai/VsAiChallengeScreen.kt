package com.example.pmuprojekat.ui.vsai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.ai.VsAiCompletionReason
import com.example.pmuprojekat.ai.VsAiFinalAnalysis
import com.example.pmuprojekat.data.repository.VsAiMessage
import com.example.pmuprojekat.data.repository.VsAiMessageRole
import com.example.pmuprojekat.ui.home.AppPalette

@Composable
fun VsAiChallengeScreen(
    uiState: VsAiUiState,
    onBack: () -> Unit,
    onInputChange: (String) -> Unit,
    onSendAnswer: () -> Unit,
    onStopChallenge: () -> Unit,
    onRetry: () -> Unit,
    onNewChallenge: () -> Unit,
    onBackToLevels: () -> Unit,
    onOpenHistory: () -> Unit
) {
    when (uiState.stage) {
        VsAiStage.LOADING_FIRST_QUESTION,
        VsAiStage.IDLE -> VsAiLoadingScreen(uiState = uiState, onBack = onBack)

        VsAiStage.FINAL_ANALYSIS -> VsAiFinalScreen(
            uiState = uiState,
            onBack = onBack,
            onNewChallenge = onNewChallenge,
            onBackToLevels = onBackToLevels,
            onOpenHistory = onOpenHistory
        )

        VsAiStage.ERROR -> if (uiState.challenge == null) {
            VsAiStartErrorScreen(
                uiState = uiState,
                onBack = onBack,
                onRetry = onRetry
            )
        } else {
            VsAiActiveScreen(
                uiState = uiState,
                onBack = onBack,
                onInputChange = onInputChange,
                onSendAnswer = onSendAnswer,
                onStopChallenge = onStopChallenge,
                onRetry = onRetry
            )
        }

        else -> VsAiActiveScreen(
            uiState = uiState,
            onBack = onBack,
            onInputChange = onInputChange,
            onSendAnswer = onSendAnswer,
            onStopChallenge = onStopChallenge,
            onRetry = onRetry
        )
    }
}

@Composable
private fun VsAiStartErrorScreen(
    uiState: VsAiUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(containerColor = AppPalette.Background) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(screenBackground())
                .padding(innerPadding)
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 12.dp,
                    start = 18.dp,
                    end = 18.dp,
                    bottom = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            VsAiChallengeTopBar(
                title = uiState.levelContext?.displayName?.let { "$it VS AI" } ?: "VS AI izazov",
                subtitle = "Početno pitanje nije učitano.",
                onBack = onBack
            )
            VsAiErrorPanel(
                message = uiState.errorMessage
                    ?: "AI izazov trenutno nije dostupan. Pokušaj ponovo kasnije.",
                canRetry = uiState.canRetry,
                onRetry = onRetry
            )
        }
    }
}

@Composable
private fun VsAiLoadingScreen(
    uiState: VsAiUiState,
    onBack: () -> Unit
) {
    Scaffold(containerColor = AppPalette.Background) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(screenBackground())
                .padding(innerPadding)
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 12.dp,
                    start = 18.dp,
                    end = 18.dp,
                    bottom = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            VsAiChallengeTopBar(
                title = uiState.levelContext?.displayName?.let { "$it VS AI" } ?: "VS AI izazov",
                subtitle = "Pripremam pitanje prema nivou i dosadašnjem napretku.",
                onBack = onBack
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, AppPalette.Indigo.copy(alpha = 0.18f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(38.dp),
                        color = AppPalette.Indigo,
                        strokeWidth = 3.dp
                    )
                    Text(
                        text = "AI bira fokus izazova",
                        color = AppPalette.TextPrimary,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Ne šaljemo celu istoriju zadataka. Koristi se samo kratak pregled rezultata za izabrani nivo.",
                        color = AppPalette.TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun VsAiActiveScreen(
    uiState: VsAiUiState,
    onBack: () -> Unit,
    onInputChange: (String) -> Unit,
    onSendAnswer: () -> Unit,
    onStopChallenge: () -> Unit,
    onRetry: () -> Unit
) {
    val listState = rememberLazyListState()
    val visibleMessages = uiState.messages.filter { it.role != VsAiMessageRole.AI_FINAL_ANALYSIS }
    val activeHeaderItems = 3 + if (uiState.relevantTerms.isNotEmpty()) 1 else 0

    LaunchedEffect(visibleMessages.size, uiState.stage, activeHeaderItems) {
        if (visibleMessages.isNotEmpty()) {
            listState.animateScrollToItem(visibleMessages.lastIndex + activeHeaderItems)
        }
    }

    val showAnswerBar = uiState.stage != VsAiStage.BUILDING_FINAL_ANALYSIS
    Scaffold(
        containerColor = AppPalette.Background,
        bottomBar = {
            if (showAnswerBar) {
                VsAiAnswerBar(
                    input = uiState.inputText,
                    isEvaluating = uiState.stage == VsAiStage.EVALUATING,
                    enabled = uiState.stage == VsAiStage.ACTIVE,
                    canSend = uiState.canSend,
                    onInputChange = onInputChange,
                    onSend = onSendAnswer,
                    onStop = onStopChallenge
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(screenBackground())
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = 18.dp,
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 12.dp,
                end = 18.dp,
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                VsAiChallengeTopBar(
                    title = "${uiState.levelContext?.displayName ?: "VS AI"} izazov",
                    subtitle = "Runda ${uiState.currentRound} od ${uiState.maximumRounds}",
                    onBack = onBack
                )
            }
            item { VsAiRoundPanel(uiState) }
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = AppPalette.Blue.copy(alpha = 0.07f),
                    border = BorderStroke(1.dp, AppPalette.Blue.copy(alpha = 0.14f))
                ) {
                    Text(
                        text = "AI će nastaviti da postavlja pitanja dok odgovor ne bude dovoljno jak za ovaj nivo.",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 11.dp),
                        color = AppPalette.TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }

            if (uiState.relevantTerms.isNotEmpty()) {
                item { VsAiRelevantTerms(uiState) }
            }

            items(visibleMessages, key = VsAiMessage::id) { message ->
                VsAiMessageBubble(message)
            }

            if (uiState.stage == VsAiStage.EVALUATING) {
                item { VsAiEvaluatingBubble("AI procenjuje odgovor i bira da li nastavlja izazov...") }
            }

            if (uiState.stage == VsAiStage.BUILDING_FINAL_ANALYSIS) {
                item { VsAiEvaluatingBubble("AI priprema završnu analizu celog izazova...") }
            }

            uiState.errorMessage?.let { error ->
                item {
                    VsAiErrorPanel(
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
private fun VsAiRoundPanel(uiState: VsAiUiState) {
    val accent = levelAccent(uiState.levelContext?.levelId)
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = AppPalette.Navy,
        shadowElevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(AppPalette.Navy, accent.copy(alpha = 0.75f))
                    )
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = uiState.challenge?.difficultyLabel?.uppercase() ?: "AI SPARING",
                    color = Color.White.copy(alpha = 0.72f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Runda ${uiState.currentRound} / ${uiState.maximumRounds}",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Odgovori svojim rečima. AI neće prikazati internu rubriku.",
                    color = Color(0xFFE2E8F0),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White.copy(alpha = 0.13f)
            ) {
                Text(
                    text = "${uiState.roundsAnswered}/${uiState.maximumRounds}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun VsAiRelevantTerms(uiState: VsAiUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(
            text = "Lokalni kontekst",
            color = AppPalette.TextSecondary,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            uiState.relevantTerms.forEach { term ->
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = AppPalette.Indigo.copy(alpha = 0.08f),
                    border = BorderStroke(1.dp, AppPalette.Indigo.copy(alpha = 0.14f))
                ) {
                    Text(
                        text = term.titleSr,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        color = AppPalette.Indigo,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun VsAiMessageBubble(message: VsAiMessage) {
    val isUser = message.role == VsAiMessageRole.USER_ANSWER
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(if (isUser) 0.86f else 0.94f),
            shape = RoundedCornerShape(
                topStart = 18.dp,
                topEnd = 18.dp,
                bottomStart = if (isUser) 18.dp else 5.dp,
                bottomEnd = if (isUser) 5.dp else 18.dp
            ),
            color = if (isUser) AppPalette.Blue else Color.White,
            border = if (isUser) null else BorderStroke(1.dp, AppPalette.Border),
            shadowElevation = if (isUser) 0.dp else 2.dp
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = if (isUser) "Tvoj odgovor" else if (message.role == VsAiMessageRole.AI_QUESTION) "AI pitanje" else "AI provokacija",
                    color = if (isUser) Color.White.copy(alpha = 0.78f) else AppPalette.Indigo,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = message.content,
                    color = if (isUser) Color.White else AppPalette.TextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontWeight = if (isUser) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun VsAiEvaluatingBubble(text: String) {
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
                text = text,
                color = AppPalette.TextSecondary,
                fontSize = 12.5.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun VsAiErrorPanel(
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
                lineHeight = 18.sp,
                fontWeight = FontWeight.Bold
            )
            if (canRetry) {
                Button(
                    onClick = onRetry,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppPalette.Orange)
                ) {
                    Text("Pokušaj ponovo", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun VsAiAnswerBar(
    input: String,
    isEvaluating: Boolean,
    enabled: Boolean,
    canSend: Boolean,
    onInputChange: (String) -> Unit,
    onSend: () -> Unit,
    onStop: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 12.dp,
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = onInputChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 104.dp, max = 190.dp),
                enabled = enabled,
                placeholder = { Text("Napiši svoj odgovor...") },
                shape = RoundedCornerShape(18.dp),
                maxLines = 7,
                supportingText = {
                    Text("${input.length}/2000", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(onSend = { if (canSend) onSend() }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppPalette.Indigo,
                    unfocusedBorderColor = AppPalette.Border
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                OutlinedButton(
                    onClick = onStop,
                    modifier = Modifier.weight(1f),
                    enabled = !isEvaluating,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Color(0xFFDC2626)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFB91C1C))
                ) {
                    Text("Prekini izazov", fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = onSend,
                    modifier = Modifier.weight(1f),
                    enabled = canSend,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppPalette.Indigo)
                ) {
                    if (isEvaluating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Pošalji odgovor", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun VsAiFinalScreen(
    uiState: VsAiUiState,
    onBack: () -> Unit,
    onNewChallenge: () -> Unit,
    onBackToLevels: () -> Unit,
    onOpenHistory: () -> Unit
) {
    val analysis = uiState.finalAnalysis ?: return
    val accent = levelAccent(uiState.levelContext?.levelId)
    Scaffold(containerColor = AppPalette.Background) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(screenBackground())
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
                    title = "Rezultat izazova",
                    subtitle = "${uiState.levelContext?.displayName ?: "VS AI"} • ${completionReasonLabel(uiState.completionReason)}",
                    onBack = onBack
                )
            }
            item { VsAiScorePanel(analysis, accent) }
            item { AnalysisSection("Šta si dobro uradio", analysis.strengths) }
            item { AnalysisSection("Gde je odgovor bio slabiji", analysis.weakerPoints) }
            item {
                AnalysisSection(
                    "Kako se tvoje razmišljanje razvijalo tokom izazova",
                    analysis.reasoningDevelopment
                )
            }
            item { KeyConceptSection(analysis) }
            item { AnalysisSection("Sledeći konkretan korak", analysis.nextConcreteStep, accent) }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Button(
                        onClick = onNewChallenge,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accent)
                    ) {
                        Text("Novi izazov", fontWeight = FontWeight.ExtraBold)
                    }
                    OutlinedButton(
                        onClick = onBackToLevels,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text("Nazad na nivoe", fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(
                        onClick = onOpenHistory,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text("Istorija izazova", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun VsAiScorePanel(analysis: VsAiFinalAnalysis, accent: Color) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = AppPalette.Navy,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .background(Brush.linearGradient(listOf(AppPalette.Navy, accent.copy(alpha = 0.78f))))
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                modifier = Modifier.size(74.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White.copy(alpha = 0.14f),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.22f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = analysis.score.toString(),
                        color = Color.White,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "Ocena prema nivou",
                    color = Color.White.copy(alpha = 0.72f),
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = analysis.resultSummary,
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun AnalysisSection(title: String, content: String, accent: Color = AppPalette.Indigo) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(19.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(title, color = accent, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold)
            Text(
                text = content,
                color = AppPalette.TextPrimary,
                fontSize = 13.5.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun KeyConceptSection(analysis: VsAiFinalAnalysis) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(19.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                "Ključni koncepti za ponavljanje",
                color = AppPalette.Indigo,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
            analysis.keyConcepts.forEach { concept ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = AppPalette.Indigo.copy(alpha = 0.07f)
                ) {
                    Text(
                        text = concept,
                        modifier = Modifier.padding(horizontal = 11.dp, vertical = 8.dp),
                        color = AppPalette.TextPrimary,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
internal fun VsAiChallengeTopBar(
    title: String,
    subtitle: String,
    onBack: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier
                .size(44.dp)
                .clickable { onBack() },
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = BorderStroke(1.dp, AppPalette.Border),
            shadowElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("<", color = AppPalette.Navy, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 13.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = title,
                color = AppPalette.TextPrimary,
                fontSize = 23.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = subtitle,
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )
        }
    }
}

private fun screenBackground(): Brush {
    return Brush.verticalGradient(
        listOf(Color(0xFFF8FAFC), Color(0xFFF1F5F9), Color.White)
    )
}

internal fun levelAccent(levelId: String?): Color {
    return when (levelId) {
        "beginner" -> AppPalette.Blue
        "junior" -> AppPalette.Green
        "medior" -> AppPalette.Orange
        "senior" -> AppPalette.Purple
        "architect" -> AppPalette.Indigo
        else -> AppPalette.Indigo
    }
}

internal fun completionReasonLabel(reason: VsAiCompletionReason?): String {
    return when (reason) {
        VsAiCompletionReason.AI_SATISFIED -> "AI je zadovoljan odgovorom"
        VsAiCompletionReason.USER_STOPPED -> "prekinuto na zahtev korisnika"
        VsAiCompletionReason.MAX_ROUNDS -> "dostignut maksimalan broj rundi"
        VsAiCompletionReason.ERROR -> "završeno zbog greške"
        null -> "završeno"
    }
}
