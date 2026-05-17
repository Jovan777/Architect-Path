package com.example.pmuprojekat.ui.question


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.ui.home.AppPalette
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex

@Composable
fun QuestionScreen(
    uiState: QuestionUiState,
    onBack: () -> Unit,
    onBackToQuestionList: () -> Unit,
    hasNextQuestion: Boolean,
    onNextQuestion: () -> Unit,
    onRetryQuestion: () -> Unit,
    onToggleOption: (QuestionStepUi, String) -> Unit,
    onMoveOrderedOption: (QuestionStepUi, String, Int) -> Unit,
    onExcludeOrderedOption: (QuestionStepUi, String) -> Unit,
    onRestoreOrderedOption: (QuestionStepUi, String) -> Unit,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit,
    onUpdateBlankAnswer: (QuestionStepUi, String, String) -> Unit,
    onUpdateFreeText: (QuestionStepUi, String) -> Unit,
    onCheckStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onNextStep: () -> Unit,
    onFinishQuestion: () -> Unit
) {
    Scaffold(
        containerColor = AppPalette.Background
    ) { innerPadding ->
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
        ) {
            if (uiState.isLoading) {
                LoadingQuestionState()
            } else if (uiState.isCompleted) {
                QuestionResultScreen(
                    uiState = uiState,
                    hasNextQuestion = hasNextQuestion,
                    onNextQuestion = onNextQuestion,
                    onBack = onBackToQuestionList,
                    onRetryQuestion =  onRetryQuestion
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
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
                    QuestionHeader(
                        uiState = uiState,
                        onBack = onBack
                    )

                    QuestionProgress(uiState = uiState)

                    PromptCard(
                        title = uiState.title,
                        prompt = uiState.prompt
                    )

                    val step = uiState.currentStep

                    if (step != null) {
                        StepCard(
                            step = step,
                            draft = uiState.draft,
                            feedback = uiState.feedback,
                            isLocked = uiState.answeredStepIds.contains(step.stepId),
                            onToggleOption = onToggleOption,
                            onMoveOrderedOption = onMoveOrderedOption,
                            onExcludeOrderedOption = onExcludeOrderedOption,
                            onRestoreOrderedOption = onRestoreOrderedOption,
                            onMapOptionToZone = onMapOptionToZone,
                            onRemoveOptionZone = onRemoveOptionZone,
                            onUpdateBlankAnswer = onUpdateBlankAnswer,
                            onUpdateFreeText = onUpdateFreeText
                        )

                        QuestionActionBar(
                            uiState = uiState,
                            onCheckStep = onCheckStep,
                            onPreviousStep = onPreviousStep,
                            onNextStep = onNextStep,
                            onFinishQuestion = onFinishQuestion
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingQuestionState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Učitavanje zadatka...",
            color = AppPalette.TextSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun QuestionHeader(
    uiState: QuestionUiState,
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

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = uiState.questionId ?: "",
                color = AppPalette.Blue,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = uiState.title,
                color = AppPalette.TextPrimary,
                fontSize = 19.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        DifficultyBadge(uiState.difficulty)
    }
}

@Composable
private fun DifficultyBadge(difficulty: String) {
    val label = when (difficulty.lowercase()) {
        "easy" -> "Lako"
        "medium" -> "Srednje"
        "hard" -> "Teže"
        "expert" -> "Expert"
        else -> difficulty
    }

    val color = when (difficulty.lowercase()) {
        "easy" -> AppPalette.Green
        "medium" -> AppPalette.Orange
        "hard" -> AppPalette.Purple
        "expert" -> AppPalette.Indigo
        else -> AppPalette.Blue
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.22f))
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            text = label,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun QuestionProgress(uiState: QuestionUiState) {
    val progress = if (uiState.totalSteps == 0) {
        0f
    } else {
        (uiState.currentStepIndex + 1).toFloat() / uiState.totalSteps.toFloat()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Korak ${uiState.currentStepIndex + 1} od ${uiState.totalSteps}",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${uiState.scorePercent}%",
                color = AppPalette.Blue,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        LinearProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(100.dp)),
            color = AppPalette.Blue,
            trackColor = Color(0xFFE2E8F0),
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
private fun PromptCard(
    title: String,
    prompt: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Scenario",
                color = AppPalette.Blue,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = prompt,
                color = AppPalette.TextPrimary,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
        }
    }
}

@Composable
private fun StepCard(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onToggleOption: (QuestionStepUi, String) -> Unit,
    onMoveOrderedOption: (QuestionStepUi, String, Int) -> Unit,
    onExcludeOrderedOption: (QuestionStepUi, String) -> Unit,
    onRestoreOrderedOption: (QuestionStepUi, String) -> Unit,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit,
    onUpdateBlankAnswer: (QuestionStepUi, String, String) -> Unit,
    onUpdateFreeText: (QuestionStepUi, String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = step.title,
                    color = AppPalette.TextPrimary,
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = step.instruction,
                    color = AppPalette.TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }

            if (!step.codeBlock.isNullOrBlank()) {
                CodeBlock(code = step.codeBlock)
            }

            when (step.type) {
                StepType.SINGLE_CHOICE.id,
                StepType.VISUAL_MAPPING.id,
                StepType.MULTI_CHOICE.id,
                StepType.HOTSPOT.id -> {
                    ChoiceStepContent(
                        step = step,
                        draft = draft,
                        isLocked = isLocked,
                        onToggleOption = onToggleOption
                    )
                }

                StepType.ORDERED_CARDS.id -> {
                    OrderedCardsStepContent(
                        step = step,
                        draft = draft,
                        isLocked = isLocked,
                        onMoveOrderedOption = onMoveOrderedOption,
                        onExcludeOrderedOption = onExcludeOrderedOption,
                        onRestoreOrderedOption = onRestoreOrderedOption
                    )
                }

                StepType.CATEGORIZATION.id -> {
                    when {
                        isSeniorDiagnosisCategorizationStep(step) -> {
                            SeniorDiagnosisCategorizationStepContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }

                        isSeniorBalanceCategorizationStep(step) -> {
                            SeniorBalanceCategorizationStepContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }

                        else -> {
                            BinaryCategorizationStepContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }
                    }
                }

                StepType.ROLE_MAPPING.id -> {
                    MappingStepContent(
                        step = step,
                        draft = draft,
                        isLocked = isLocked,
                        onMapOptionToZone = onMapOptionToZone,
                        onRemoveOptionZone = onRemoveOptionZone
                    )
                }

                StepType.CODE_COMPLETION.id -> {
                    CodeCompletionStepContent(
                        step = step,
                        draft = draft,
                        isLocked = isLocked,
                        onUpdateBlankAnswer = onUpdateBlankAnswer
                    )
                }

                StepType.FREE_TEXT.id,
                StepType.MINI_ADR.id -> {
                    FreeTextStepContent(
                        step = step,
                        draft = draft,
                        isLocked = isLocked,
                        onUpdateFreeText = onUpdateFreeText
                    )
                }
            }

            if (feedback != null) {
                FeedbackCard(feedback = feedback)
            }
        }
    }
}

@Composable
private fun ChoiceStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    isLocked: Boolean,
    onToggleOption: (QuestionStepUi, String) -> Unit
) {
    val isCodeDecisionChoice =
        step.type == StepType.SINGLE_CHOICE.id && !step.codeBlock.isNullOrBlank()

    val answerAlreadySelected =
        isCodeDecisionChoice && draft.selectedOptionIds.isNotEmpty()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        step.options.forEach { option ->
            val selected = draft.selectedOptionIds.contains(option.optionId)

            SelectableOptionCard(
                label = displayableOptionLabel(option.label),
                text = option.text,
                selected = selected,
                enabled = !isLocked && !answerAlreadySelected,
                onClick = { onToggleOption(step, option.optionId) }
            )
        }

        if (answerAlreadySelected && !isLocked) {
            Text(
                text = "Odgovor je izabran. Možeš da proveriš korak i nastaviš dalje.",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private fun displayableOptionLabel(label: String?): String? {
    val normalized = label?.trim().orEmpty()

    return if (normalized.matches(Regex("^[A-D]$"))) {
        null
    } else {
        label
    }
}

@Composable
private fun SelectableOptionCard(
    label: String?,
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) AppPalette.Blue else AppPalette.Border
    val background = if (selected) Color(0xFFEFF6FF) else Color.White

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (enabled) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(20.dp),
        color = background,
        border = BorderStroke(if (selected) 1.5.dp else 1.dp, borderColor),
        shadowElevation = if (selected) 4.dp else 1.dp
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (selected) AppPalette.Blue else Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (selected) "✓" else "",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                if (!label.isNullOrBlank()) {
                    Text(
                        text = label,
                        color = AppPalette.Blue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                }

                Text(
                    text = text,
                    color = AppPalette.TextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun OrderedCardsStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    isLocked: Boolean,
    onMoveOrderedOption: (QuestionStepUi, String, Int) -> Unit,
    onExcludeOrderedOption: (QuestionStepUi, String) -> Unit,
    onRestoreOrderedOption: (QuestionStepUi, String) -> Unit
) {
    val optionById = remember(step.options) {
        step.options.associateBy { it.optionId }
    }

    val orderedOptions by remember(draft.orderedOptionIds, optionById) {
        derivedStateOf {
            draft.orderedOptionIds.mapNotNull { optionById[it] }
        }
    }

    val excludedOptions by remember(step.options, draft.excludedOptionIds) {
        derivedStateOf {
            step.options.filter { draft.excludedOptionIds.contains(it.optionId) }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = if (isLocked) {
                "Redosled je zaključan nakon provere."
            } else {
                "Zadrži karticu i prevuci je gore ili dole."
            },
            color = AppPalette.TextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        orderedOptions.forEachIndexed { index, option ->
            DraggableOrderedCardRow(
                number = index + 1,
                option = option,
                isLocked = isLocked,
                onMoveUp = { onMoveOrderedOption(step, option.optionId, -1) },
                onMoveDown = { onMoveOrderedOption(step, option.optionId, 1) }
            )
        }

        if (excludedOptions.isNotEmpty()) {
            Text(
                text = "Van redosleda",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            excludedOptions.forEach { option ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF8FAFC),
                    border = BorderStroke(1.dp, AppPalette.Border)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = option.text,
                            color = AppPalette.TextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )

                        TextButton(
                            onClick = { onRestoreOrderedOption(step, option.optionId) },
                            enabled = !isLocked
                        ) {
                            Text("Vrati", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun DraggableOrderedCardRow(
    number: Int,
    option: StepOptionUi,
    isLocked: Boolean,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit
) {
    var dragOffsetY by remember(option.optionId) {
        mutableStateOf(0f)
    }

    val dragThresholdPx = 42f
    val isDragging = dragOffsetY != 0f

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(if (isDragging) 2f else 0f)
            .graphicsLayer {
                translationY = dragOffsetY * 0.35f
                scaleX = if (isDragging) 1.02f else 1f
                scaleY = if (isDragging) 1.02f else 1f
            }
            .then(
                if (!isLocked) {
                    Modifier.pointerInput(option.optionId) {
                        detectDragGesturesAfterLongPress(
                            onDragEnd = {
                                dragOffsetY = 0f
                            },
                            onDragCancel = {
                                dragOffsetY = 0f
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()

                                dragOffsetY += dragAmount.y

                                when {
                                    dragOffsetY > dragThresholdPx -> {
                                        onMoveDown()
                                        dragOffsetY = 0f
                                    }

                                    dragOffsetY < -dragThresholdPx -> {
                                        onMoveUp()
                                        dragOffsetY = 0f
                                    }
                                }
                            }
                        )
                    }
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(16.dp),
        color = if (isDragging) Color(0xFFEFF6FF) else Color.White,
        border = BorderStroke(
            width = if (isDragging) 1.5.dp else 1.dp,
            color = if (isDragging) AppPalette.Blue else AppPalette.Border
        ),
        shadowElevation = if (isDragging) 8.dp else 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .background(AppPalette.Blue.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    color = AppPalette.Blue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(9.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = option.text,
                color = AppPalette.TextPrimary,
                fontSize = if (option.text.length > 70) 11.5.sp else 12.5.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            if (!isLocked) {
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "↕",
                    color = AppPalette.TextMuted,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}


@Composable
private fun OrderedCardRow(
    number: Int,
    option: StepOptionUi,
    onUp: () -> Unit,
    enabled: Boolean,
    onDown: () -> Unit,
    onExclude: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppPalette.Blue.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    color = AppPalette.Blue,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = option.text,
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    onClick = onUp,
                    enabled = enabled
                ) {
                    Text("↑")
                }

                TextButton(
                    onClick = onDown,
                    enabled = enabled
                ) {
                    Text("↓")
                }

                TextButton(
                    onClick = onExclude,
                    enabled = enabled
                ) {
                    Text("Izbaci", fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun MappingStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        step.options.forEach { option ->
            MappingOptionCard(
                step = step,
                option = option,
                selectedZoneId = draft.mappedZoneByOptionId[option.optionId],
                isLocked = isLocked,
                onMapOptionToZone = onMapOptionToZone,
                onRemoveOptionZone = onRemoveOptionZone
            )
        }
    }
}

private fun isSeniorDiagnosisCategorizationStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            step.stepId.startsWith("S1.") &&
            step.zones.any { it.title.contains("Simptomi", ignoreCase = true) } &&
            step.zones.any { it.title.contains("uzroci", ignoreCase = true) }
}

private fun isSeniorBalanceCategorizationStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            (step.stepId.startsWith("S2.") || step.stepId.startsWith("S4.")) &&
            step.zones.any { it.title.contains("Šta se dobija", ignoreCase = true) } &&
            step.zones.any {
                it.title.contains("gubi", ignoreCase = true) ||
                        it.title.contains("rizici", ignoreCase = true) ||
                        it.title.contains("cena", ignoreCase = true)
            }
}

@Composable
private fun SeniorDiagnosisCategorizationStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val zones = remember(step.zones) {
        step.zones.sortedBy { it.zoneOrder }
    }

    val symptomZone = zones.firstOrNull {
        it.title.contains("Simptomi", ignoreCase = true)
    } ?: zones.firstOrNull()

    val causeZone = zones.firstOrNull {
        it.title.contains("uzroci", ignoreCase = true)
    } ?: zones.drop(1).firstOrNull()

    if (symptomZone == null || causeZone == null) {
        BinaryCategorizationStepContent(
            step = step,
            draft = draft,
            feedback = feedback,
            isLocked = isLocked,
            onMapOptionToZone = onMapOptionToZone,
            onRemoveOptionZone = onRemoveOptionZone
        )
        return
    }

    SeniorSwipeCategorizationContent(
        step = step,
        draft = draft,
        feedback = feedback,
        isLocked = isLocked,
        leftZone = symptomZone,
        rightZone = causeZone,
        leftTitle = "Simptomi",
        rightTitle = "Mogući uzroci",
        leftHint = "Prevuci levo za simptom",
        rightHint = "Prevuci desno za uzrok",
        introText = if (isLocked) {
            "Raspored je zaključan. Zelene kartice su tačne, crvene nisu."
        } else {
            "Zadrži karticu i prevuci je levo ili desno. Kartica ostaje vidljiva, a nakon puštanja prelazi u izabranu grupu."
        },
        leftAccent = AppPalette.Blue,
        rightAccent = AppPalette.Purple,
        onMapOptionToZone = onMapOptionToZone
    )
}

@Composable
private fun SeniorBalanceCategorizationStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val zones = remember(step.zones) {
        step.zones.sortedBy { it.zoneOrder }
    }

    val gainZone = zones.firstOrNull {
        it.title.contains("dobija", ignoreCase = true)
    } ?: zones.firstOrNull()

    val costZone = zones.firstOrNull {
        it.title.contains("gubi", ignoreCase = true) ||
                it.title.contains("rizici", ignoreCase = true) ||
                it.title.contains("cena", ignoreCase = true)
    } ?: zones.drop(1).firstOrNull()

    if (gainZone == null || costZone == null) {
        BinaryCategorizationStepContent(
            step = step,
            draft = draft,
            feedback = feedback,
            isLocked = isLocked,
            onMapOptionToZone = onMapOptionToZone,
            onRemoveOptionZone = onRemoveOptionZone
        )
        return
    }

    val rightTitle = if (costZone.title.contains("cena", ignoreCase = true)) {
        "Prihvaćena cena"
    } else {
        "Rizici / gubici"
    }

    SeniorSwipeCategorizationContent(
        step = step,
        draft = draft,
        feedback = feedback,
        isLocked = isLocked,
        leftZone = gainZone,
        rightZone = costZone,
        leftTitle = "Šta se dobija",
        rightTitle = rightTitle,
        leftHint = "Prevuci levo za dobitak",
        rightHint = "Prevuci desno za cenu / rizik",
        introText = if (isLocked) {
            "Balans odluke je zaključan. Zelene kartice su tačne, crvene nisu."
        } else {
            "Zadrži karticu i prevuci je levo ako predstavlja dobitak, odnosno desno ako predstavlja cenu, gubitak ili rizik."
        },
        leftAccent = AppPalette.Green,
        rightAccent = AppPalette.Orange,
        onMapOptionToZone = onMapOptionToZone
    )
}

@Composable
private fun SeniorSwipeCategorizationContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    leftZone: StepZoneUi,
    rightZone: StepZoneUi,
    leftTitle: String,
    rightTitle: String,
    leftHint: String,
    rightHint: String,
    introText: String,
    leftAccent: Color,
    rightAccent: Color,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit
) {
    val showResultColors = feedback != null

    val unassignedOptions = step.options.filter { option ->
        draft.mappedZoneByOptionId[option.optionId] == null
    }

    val leftOptions = step.options.filter { option ->
        draft.mappedZoneByOptionId[option.optionId] == leftZone.zoneId
    }

    val rightOptions = step.options.filter { option ->
        draft.mappedZoneByOptionId[option.optionId] == rightZone.zoneId
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SeniorSwipeInstructionCard(
            text = introText,
            leftTitle = leftTitle,
            rightTitle = rightTitle,
            leftAccent = leftAccent,
            rightAccent = rightAccent,
            leftCount = leftOptions.size,
            rightCount = rightOptions.size
        )

        if (unassignedOptions.isNotEmpty()) {
            SeniorSwipeSection(
                title = "Kartice za raspoređivanje",
                subtitle = "Prevuci svaku karticu levo ili desno.",
                accentColor = AppPalette.Navy,
                options = unassignedOptions,
                step = step,
                selectedZoneId = null,
                leftZone = leftZone,
                rightZone = rightZone,
                leftHint = leftHint,
                rightHint = rightHint,
                leftAccent = leftAccent,
                rightAccent = rightAccent,
                showResultColors = showResultColors,
                isLocked = isLocked,
                onMapOptionToZone = onMapOptionToZone
            )
        }

        SeniorSwipeSection(
            title = leftTitle,
            subtitle = leftZone.title,
            accentColor = leftAccent,
            options = leftOptions,
            step = step,
            selectedZoneId = leftZone.zoneId,
            leftZone = leftZone,
            rightZone = rightZone,
            leftHint = leftHint,
            rightHint = rightHint,
            leftAccent = leftAccent,
            rightAccent = rightAccent,
            showResultColors = showResultColors,
            isLocked = isLocked,
            onMapOptionToZone = onMapOptionToZone
        )

        SeniorSwipeSection(
            title = rightTitle,
            subtitle = rightZone.title,
            accentColor = rightAccent,
            options = rightOptions,
            step = step,
            selectedZoneId = rightZone.zoneId,
            leftZone = leftZone,
            rightZone = rightZone,
            leftHint = leftHint,
            rightHint = rightHint,
            leftAccent = leftAccent,
            rightAccent = rightAccent,
            showResultColors = showResultColors,
            isLocked = isLocked,
            onMapOptionToZone = onMapOptionToZone
        )
    }
}

@Composable
private fun SeniorSwipeInstructionCard(
    text: String,
    leftTitle: String,
    rightTitle: String,
    leftAccent: Color,
    rightAccent: Color,
    leftCount: Int,
    rightCount: Int
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = text,
                color = AppPalette.TextSecondary,
                fontSize = 12.5.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SeniorTargetPill(
                    modifier = Modifier.weight(1f),
                    title = leftTitle,
                    count = leftCount,
                    accentColor = leftAccent
                )

                SeniorTargetPill(
                    modifier = Modifier.weight(1f),
                    title = rightTitle,
                    count = rightCount,
                    accentColor = rightAccent
                )
            }
        }
    }
}

@Composable
private fun SeniorTargetPill(
    modifier: Modifier,
    title: String,
    count: Int,
    accentColor: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = accentColor.copy(alpha = 0.10f),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.30f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                color = accentColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = count.toString(),
                color = accentColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
private fun SeniorSwipeSection(
    title: String,
    subtitle: String,
    accentColor: Color,
    options: List<StepOptionUi>,
    step: QuestionStepUi,
    selectedZoneId: String?,
    leftZone: StepZoneUi,
    rightZone: StepZoneUi,
    leftHint: String,
    rightHint: String,
    leftAccent: Color,
    rightAccent: Color,
    showResultColors: Boolean,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = accentColor.copy(alpha = 0.07f),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.24f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    color = accentColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = subtitle,
                    color = AppPalette.TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }

            if (options.isEmpty()) {
                Text(
                    text = "Još nema kartica.",
                    color = AppPalette.TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            } else {
                options.forEach { option ->
                    SeniorStableSwipeCard(
                        step = step,
                        option = option,
                        selectedZoneId = selectedZoneId,
                        leftZone = leftZone,
                        rightZone = rightZone,
                        leftHint = leftHint,
                        rightHint = rightHint,
                        leftAccent = leftAccent,
                        rightAccent = rightAccent,
                        showResultColors = showResultColors,
                        isLocked = isLocked,
                        onMapOptionToZone = onMapOptionToZone
                    )
                }
            }
        }
    }
}

@Composable
private fun SeniorStableSwipeCard(
    step: QuestionStepUi,
    option: StepOptionUi,
    selectedZoneId: String?,
    leftZone: StepZoneUi,
    rightZone: StepZoneUi,
    leftHint: String,
    rightHint: String,
    leftAccent: Color,
    rightAccent: Color,
    showResultColors: Boolean,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit
) {
    var dragX by remember(option.optionId, selectedZoneId) {
        mutableStateOf(0f)
    }

    val threshold = 80f
    val limitedVisualOffset = dragX.coerceIn(-28f, 28f)

    val targetZoneId = when {
        dragX <= -threshold -> leftZone.zoneId
        dragX >= threshold -> rightZone.zoneId
        else -> null
    }

    val isCorrect = selectedZoneId != null && selectedZoneId == option.correctZoneId
    val isWrong = showResultColors && !isCorrect

    val backgroundColor = when {
        showResultColors && isCorrect -> Color(0xFFDCFCE7)
        isWrong -> Color(0xFFFEE2E2)
        targetZoneId == leftZone.zoneId -> leftAccent.copy(alpha = 0.14f)
        targetZoneId == rightZone.zoneId -> rightAccent.copy(alpha = 0.14f)
        selectedZoneId != null -> Color.White
        else -> Color(0xFFFFFFFF)
    }

    val borderColor = when {
        showResultColors && isCorrect -> Color(0xFF22C55E)
        isWrong -> Color(0xFFEF4444)
        targetZoneId == leftZone.zoneId -> leftAccent
        targetZoneId == rightZone.zoneId -> rightAccent
        selectedZoneId != null -> AppPalette.Border
        else -> Color(0xFFCBD5E1)
    }

    val resultSymbol = when {
        showResultColors && isCorrect -> "✓"
        showResultColors && !isCorrect -> "✕"
        else -> null
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(if (dragX != 0f) 5f else 0f)
            .graphicsLayer {
                translationX = limitedVisualOffset
                rotationZ = (dragX / 60f).coerceIn(-2.5f, 2.5f)
                scaleX = if (dragX != 0f) 1.01f else 1f
                scaleY = if (dragX != 0f) 1.01f else 1f
            }
            .then(
                if (!isLocked) {
                    Modifier.pointerInput(option.optionId, selectedZoneId) {
                        detectDragGesturesAfterLongPress(
                            onDragEnd = {
                                when {
                                    dragX <= -threshold -> {
                                        onMapOptionToZone(
                                            step,
                                            option.optionId,
                                            leftZone.zoneId
                                        )
                                    }

                                    dragX >= threshold -> {
                                        onMapOptionToZone(
                                            step,
                                            option.optionId,
                                            rightZone.zoneId
                                        )
                                    }
                                }

                                dragX = 0f
                            },
                            onDragCancel = {
                                dragX = 0f
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragX += dragAmount.x
                            }
                        )
                    }
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(18.dp),
        color = backgroundColor,
        border = BorderStroke(
            width = if (dragX != 0f) 1.6.dp else 1.dp,
            color = borderColor
        ),
        shadowElevation = if (dragX != 0f) 8.dp else 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = option.text,
                    color = AppPalette.TextPrimary,
                    fontSize = 13.5.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight.SemiBold
                )

                if (resultSymbol != null) {
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = resultSymbol,
                        color = if (isCorrect) Color(0xFF15803D) else Color(0xFFB91C1C),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            if (!isLocked) {
                SeniorSwipeHintRow(
                    dragX = dragX,
                    threshold = threshold,
                    leftHint = leftHint,
                    rightHint = rightHint,
                    leftAccent = leftAccent,
                    rightAccent = rightAccent
                )
            }
        }
    }
}

@Composable
private fun SeniorSwipeHintRow(
    dragX: Float,
    threshold: Float,
    leftHint: String,
    rightHint: String,
    leftAccent: Color,
    rightAccent: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SeniorSwipeHintPill(
            modifier = Modifier.weight(1f),
            text = leftHint,
            active = dragX <= -threshold,
            accentColor = leftAccent
        )

        SeniorSwipeHintPill(
            modifier = Modifier.weight(1f),
            text = rightHint,
            active = dragX >= threshold,
            accentColor = rightAccent
        )
    }
}

@Composable
private fun SeniorSwipeHintPill(
    modifier: Modifier,
    text: String,
    active: Boolean,
    accentColor: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = if (active) accentColor else Color(0xFFF8FAFC),
        border = BorderStroke(
            1.dp,
            if (active) accentColor else AppPalette.Border
        )
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp),
            text = text,
            color = if (active) Color.White else AppPalette.TextSecondary,
            fontSize = 10.5.sp,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}



@Composable
private fun MappingOptionCard(
    step: QuestionStepUi,
    option: StepOptionUi,
    selectedZoneId: String?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = option.text,
                color = AppPalette.TextPrimary,
                fontSize = 13.5.sp,
                lineHeight = 19.sp,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                step.zones.forEach { zone ->
                    val selected = selectedZoneId == zone.zoneId

                    Surface(
                        modifier = Modifier.then(
                            if (!isLocked) {
                                Modifier.clickable {
                                    onMapOptionToZone(step, option.optionId, zone.zoneId)
                                }
                            } else {
                                Modifier
                            }
                        ),
                        shape = RoundedCornerShape(16.dp),
                        color = if (selected) AppPalette.Blue else Color(0xFFF8FAFC),
                        border = BorderStroke(
                            1.dp,
                            if (selected) AppPalette.Blue else AppPalette.Border
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                            text = zone.title,
                            color = if (selected) Color.White else AppPalette.TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (selectedZoneId != null) {
                    Surface(
                        modifier = Modifier.then(
                            if (!isLocked) {
                                Modifier.clickable {
                                    onRemoveOptionZone(step, option.optionId)
                                }
                            } else {
                                Modifier
                            }
                        ),
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFFF1F2),
                        border = BorderStroke(1.dp, Color(0xFFFDA4AF))
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                            text = "Ukloni",
                            color = Color(0xFFE11D48),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun BinaryCategorizationStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val zones = remember(step.zones) {
        step.zones.sortedBy { it.zoneOrder }
    }

    if (zones.size != 2) {
        MappingStepContent(
            step = step,
            draft = draft,
            isLocked = isLocked,
            onMapOptionToZone = onMapOptionToZone,
            onRemoveOptionZone = onRemoveOptionZone
        )
        return
    }

    val leftZone = zones[0]
    val rightZone = zones[1]
    val showResultColors = feedback != null

    val unassignedOptions = step.options.filter { option ->
        draft.mappedZoneByOptionId[option.optionId] == null
    }

    val leftOptions = step.options.filter { option ->
        draft.mappedZoneByOptionId[option.optionId] == leftZone.zoneId
    }

    val rightOptions = step.options.filter { option ->
        draft.mappedZoneByOptionId[option.optionId] == rightZone.zoneId
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if (!isLocked) {
            Text(
                text = "Rasporedi svaku karticu u levu ili desnu kolonu.",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        } else {
            Text(
                text = "Raspored je zaključan. Zelene kartice su tačne, crvene treba ponoviti.",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (unassignedOptions.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Neraspoređene kartice",
                    color = AppPalette.TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                unassignedOptions.forEach { option ->
                    BinaryCategoryCard(
                        step = step,
                        option = option,
                        selectedZoneId = null,
                        leftZone = leftZone,
                        rightZone = rightZone,
                        showResultColors = showResultColors,
                        isLocked = isLocked,
                        onMapOptionToZone = onMapOptionToZone,
                        onRemoveOptionZone = onRemoveOptionZone
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BinaryCategoryColumn(
                modifier = Modifier.weight(1f),
                title = "Prednosti",
                originalTitle = leftZone.title,
                step = step,
                zone = leftZone,
                options = leftOptions,
                draft = draft,
                rightZone = rightZone,
                showResultColors = showResultColors,
                isLocked = isLocked,
                onMapOptionToZone = onMapOptionToZone,
                onRemoveOptionZone = onRemoveOptionZone
            )

            BinaryCategoryColumn(
                modifier = Modifier.weight(1f),
                title = "Rizici",
                originalTitle = rightZone.title,
                step = step,
                zone = rightZone,
                options = rightOptions,
                draft = draft,
                rightZone = leftZone,
                showResultColors = showResultColors,
                isLocked = isLocked,
                onMapOptionToZone = onMapOptionToZone,
                onRemoveOptionZone = onRemoveOptionZone
            )
        }
    }
}

@Composable
private fun BinaryCategoryColumn(
    modifier: Modifier,
    title: String,
    originalTitle: String,
    step: QuestionStepUi,
    zone: StepZoneUi,
    options: List<StepOptionUi>,
    draft: StepAnswerDraft,
    rightZone: StepZoneUi,
    showResultColors: Boolean,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = AppPalette.TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = originalTitle,
                color = AppPalette.TextSecondary,
                fontSize = 10.5.sp,
                lineHeight = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (options.isEmpty()) {
                Text(
                    text = "Još nema kartica.",
                    color = AppPalette.TextMuted,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            } else {
                options.forEach { option ->
                    BinaryCategoryCard(
                        step = step,
                        option = option,
                        selectedZoneId = draft.mappedZoneByOptionId[option.optionId],
                        leftZone = zone,
                        rightZone = rightZone,
                        showResultColors = showResultColors,
                        isLocked = isLocked,
                        onMapOptionToZone = onMapOptionToZone,
                        onRemoveOptionZone = onRemoveOptionZone
                    )
                }
            }
        }
    }
}
@Composable
private fun BinaryCategoryCard(
    step: QuestionStepUi,
    option: StepOptionUi,
    selectedZoneId: String?,
    leftZone: StepZoneUi,
    rightZone: StepZoneUi,
    showResultColors: Boolean,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val isAssigned = selectedZoneId != null
    val isCorrect = selectedZoneId != null && selectedZoneId == option.correctZoneId

    val backgroundColor = when {
        showResultColors && isCorrect -> Color(0xFFDCFCE7)
        showResultColors && isAssigned && !isCorrect -> Color(0xFFFEE2E2)
        isAssigned -> Color(0xFFEFF6FF)
        else -> Color.White
    }

    val borderColor = when {
        showResultColors && isCorrect -> Color(0xFF22C55E)
        showResultColors && isAssigned && !isCorrect -> Color(0xFFEF4444)
        isAssigned -> AppPalette.Blue
        else -> AppPalette.Border
    }

    val resultSymbol = when {
        showResultColors && isCorrect -> "✓"
        showResultColors && isAssigned && !isCorrect -> "✕"
        else -> null
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        shadowElevation = if (isAssigned) 2.dp else 1.dp
    ) {
        Column(
            modifier = Modifier.padding(9.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = option.text,
                    color = AppPalette.TextPrimary,
                    fontSize = 11.5.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )

                if (resultSymbol != null) {
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = resultSymbol,
                        color = if (isCorrect) Color(0xFF15803D) else Color(0xFFB91C1C),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                } else if (!isLocked && isAssigned) {
                    Spacer(modifier = Modifier.width(6.dp))

                    Surface(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onRemoveOptionZone(step, option.optionId)
                            },
                        shape = CircleShape,
                        color = Color(0xFFFFE4E6),
                        border = BorderStroke(1.dp, Color(0xFFFDA4AF))
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "×",
                                color = Color(0xFFE11D48),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }

            if (!isLocked && !isAssigned) {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    CategoryMoveChip(
                        text = "Prednosti",
                        selected = false,
                        onClick = {
                            onMapOptionToZone(step, option.optionId, leftZone.zoneId)
                        }
                    )

                    CategoryMoveChip(
                        text = "Rizici",
                        selected = false,
                        onClick = {
                            onMapOptionToZone(step, option.optionId, rightZone.zoneId)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryMoveChip(
    text: String,
    selected: Boolean,
    danger: Boolean = false,
    onClick: () -> Unit
) {
    val background = when {
        danger -> Color(0xFFFFF1F2)
        selected -> AppPalette.Blue
        else -> Color.White
    }

    val contentColor = when {
        danger -> Color(0xFFE11D48)
        selected -> Color.White
        else -> AppPalette.TextSecondary
    }

    val borderColor = when {
        danger -> Color(0xFFFDA4AF)
        selected -> AppPalette.Blue
        else -> AppPalette.Border
    }

    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = background,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            text = text,
            color = contentColor,
            fontSize = 10.5.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun solidInputColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black,
    disabledTextColor = Color.Black,
    errorTextColor = Color.Black,

    cursorColor = Color.Black,
    errorCursorColor = Color.Black,

    focusedLabelColor = AppPalette.TextPrimary,
    unfocusedLabelColor = AppPalette.TextPrimary,
    disabledLabelColor = AppPalette.TextPrimary,
    errorLabelColor = AppPalette.TextPrimary,

    focusedBorderColor = AppPalette.Blue,
    unfocusedBorderColor = AppPalette.Border,
    disabledBorderColor = AppPalette.Border,
    errorBorderColor = Color(0xFFE11D48),

    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color.White,
    errorContainerColor = Color.White
)

@Composable
private fun CodeCompletionStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    isLocked: Boolean,
    onUpdateBlankAnswer: (QuestionStepUi, String, String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        step.blanks.forEach { blank ->
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = draft.blankAnswersByBlankId[blank.blankId] ?: "",
                onValueChange = { value ->
                    onUpdateBlankAnswer(step, blank.blankId, value)
                },
                enabled = !isLocked,
                label = {
                    Text(
                        text = "Prazno mesto ${blank.blankOrder}",
                        color = AppPalette.TextPrimary
                    )
                },
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                singleLine = true,
                shape = RoundedCornerShape(18.dp),
                colors = solidInputColors()
            )
        }
    }
}

@Composable
private fun FreeTextStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    isLocked: Boolean,
    onUpdateFreeText: (QuestionStepUi, String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        value = draft.freeTextAnswer,
        onValueChange = { value ->
            onUpdateFreeText(step, value)
        },
        enabled = !isLocked,
        label = {
            Text(
                text = "Tvoje obrazloženje",
                color = AppPalette.TextPrimary
            )
        },
        textStyle = androidx.compose.ui.text.TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        ),
        shape = RoundedCornerShape(18.dp),
        colors = solidInputColors()
    )
}

@Composable
private fun CodeBlock(code: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = AppPalette.Navy
    ) {
        Text(
            modifier = Modifier.padding(14.dp),
            text = code.trimIndent(),
            color = Color(0xFFE2E8F0),
            fontSize = 12.sp,
            lineHeight = 18.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
private fun FeedbackCard(feedback: StepFeedbackUi) {
    val color = if (feedback.isCorrect) AppPalette.Green else AppPalette.Orange
    val background = if (feedback.isCorrect) Color(0xFFECFDF5) else Color(0xFFFFF7ED)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = background,
        border = BorderStroke(1.dp, color.copy(alpha = 0.25f))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = feedback.title,
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = feedback.message,
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}
@Composable
private fun QuestionActionBar(
    uiState: QuestionUiState,
    onCheckStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onNextStep: () -> Unit,
    onFinishQuestion: () -> Unit
) {
    val currentStep = uiState.currentStep
    val hasAnsweredCurrent = currentStep != null &&
            uiState.answeredStepIds.contains(currentStep.stepId)

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = onCheckStep,
            enabled = currentStep != null && !hasAnsweredCurrent,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppPalette.Blue,
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFCBD5E1),
                disabledContentColor = Color.White
            )
        ) {
            Text(
                text = if (hasAnsweredCurrent) "Korak proveren" else "Proveri korak",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onPreviousStep,
                enabled = uiState.canGoPrevious,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text(
                    text = "Nazad",
                    color = AppPalette.TextPrimary
                )
            }

            Button(
                onClick = {
                    if (uiState.isLastStep) {
                        onFinishQuestion()
                    } else {
                        onNextStep()
                    }
                },
                enabled = hasAnsweredCurrent,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppPalette.Navy,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (uiState.isLastStep) "Završi zadatak" else "Dalje",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun QuestionResultScreen(
    uiState: QuestionUiState,
    hasNextQuestion: Boolean,
    onNextQuestion: () -> Unit,
    onRetryQuestion: () -> Unit,
    onBack: () -> Unit
) {
    val resultVisual = remember(uiState.scorePercent) {
        questionResultVisual(uiState.scorePercent)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            )
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("‹ Nazad")
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = resultVisual.symbol,
                    fontSize = 44.sp
                )

                Text(
                    text = resultVisual.title,
                    color = AppPalette.TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = resultVisual.message,
                    color = AppPalette.TextSecondary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Text(
                    text = uiState.questionId ?: "",
                    color = AppPalette.Blue,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ResultStatCard(
                        modifier = Modifier.weight(1f),
                        title = "${uiState.scorePercent}%",
                        subtitle = "rezultat"
                    )

                    ResultStatCard(
                        modifier = Modifier.weight(1f),
                        title = "+${uiState.xpReward}",
                        subtitle = "XP"
                    )
                }
            }
        }

        if (!uiState.aiFollowUp.isNullOrBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                border = BorderStroke(1.dp, AppPalette.Border)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "AI follow-up",
                        color = AppPalette.Indigo,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = uiState.aiFollowUp,
                        color = AppPalette.TextPrimary,
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Button(
            onClick = onNextQuestion,
            enabled = hasNextQuestion,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppPalette.Navy,
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFCBD5E1),
                disabledContentColor = Color.White
            )
        ) {
            Text(
                text = "Pređi na sledeći zadatak",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppPalette.Blue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Vrati se na listu zadataka",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(52.dp)
                    .clickable { onRetryQuestion() },
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 6.dp,
                border = BorderStroke(1.dp, AppPalette.Border)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "↻",
                        color = AppPalette.Blue,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Text(
                text = "Ponovi zadatak",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}


@Composable
private fun ResultStatCard(
    modifier: Modifier,
    title: String,
    subtitle: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = AppPalette.TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = subtitle,
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}


private data class QuestionResultVisual(
    val symbol: String,
    val title: String,
    val message: String
)

private fun questionResultVisual(scorePercent: Int): QuestionResultVisual {
    return when {
        scorePercent > 85 -> QuestionResultVisual(
            symbol = "🏆",
            title = "Odličan rezultat",
            message = "Zadatak je rešen veoma uspešno. Možeš odmah da pređeš na sledeći izazov."
        )

        scorePercent >= 70 -> QuestionResultVisual(
            symbol = "⭐",
            title = "Vrlo dobar rezultat",
            message = "Razumevanje je dobro. Vredi kratko pogledati korake u kojima si imao nesigurnost."
        )

        scorePercent >= 50 -> QuestionResultVisual(
            symbol = "👍",
            title = "Solidan pokušaj",
            message = "Osnova postoji, ali bi ponavljanje ovog tipa zadatka dodatno učvrstilo znanje."
        )

        scorePercent >= 30 -> QuestionResultVisual(
            symbol = "📘",
            title = "Potrebno je ponavljanje",
            message = "Zadatak je delimično savladan. Vrati se na objašnjenja i pokušaj sličan primer."
        )

        else -> QuestionResultVisual(
            symbol = "🔁",
            title = "Pokušaj ponovo",
            message = "Ovaj rezultat pokazuje da temu treba obnoviti pre prelaska na složenije zadatke."
        )
    }
}