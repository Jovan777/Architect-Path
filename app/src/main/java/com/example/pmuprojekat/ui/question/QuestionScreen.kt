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

@Composable
fun QuestionScreen(
    uiState: QuestionUiState,
    onBack: () -> Unit,
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
                    onBack = onBack
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
                        onToggleOption = onToggleOption
                    )
                }

                StepType.ORDERED_CARDS.id -> {
                    OrderedCardsStepContent(
                        step = step,
                        draft = draft,
                        onMoveOrderedOption = onMoveOrderedOption,
                        onExcludeOrderedOption = onExcludeOrderedOption,
                        onRestoreOrderedOption = onRestoreOrderedOption
                    )
                }

                StepType.CATEGORIZATION.id,
                StepType.ROLE_MAPPING.id -> {
                    MappingStepContent(
                        step = step,
                        draft = draft,
                        onMapOptionToZone = onMapOptionToZone,
                        onRemoveOptionZone = onRemoveOptionZone
                    )
                }

                StepType.CODE_COMPLETION.id -> {
                    CodeCompletionStepContent(
                        step = step,
                        draft = draft,
                        onUpdateBlankAnswer = onUpdateBlankAnswer
                    )
                }

                StepType.FREE_TEXT.id,
                StepType.MINI_ADR.id -> {
                    FreeTextStepContent(
                        step = step,
                        draft = draft,
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
    onToggleOption: (QuestionStepUi, String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        step.options.forEach { option ->
            val selected = draft.selectedOptionIds.contains(option.optionId)

            SelectableOptionCard(
                label = option.label,
                text = option.text,
                selected = selected,
                onClick = { onToggleOption(step, option.optionId) }
            )
        }
    }
}

@Composable
private fun SelectableOptionCard(
    label: String?,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) AppPalette.Blue else AppPalette.Border
    val background = if (selected) Color(0xFFEFF6FF) else Color.White

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Redosled",
            color = AppPalette.TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold
        )

        orderedOptions.forEachIndexed { index, option ->
            OrderedCardRow(
                number = index + 1,
                option = option,
                onUp = { onMoveOrderedOption(step, option.optionId, -1) },
                onDown = { onMoveOrderedOption(step, option.optionId, 1) },
                onExclude = { onExcludeOrderedOption(step, option.optionId) }
            )
        }

        if (excludedOptions.isNotEmpty()) {
            Text(
                text = "Van redosleda",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            excludedOptions.forEach { option ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFF8FAFC),
                    border = BorderStroke(1.dp, AppPalette.Border)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = option.text,
                            color = AppPalette.TextSecondary,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )

                        TextButton(
                            onClick = { onRestoreOrderedOption(step, option.optionId) }
                        ) {
                            Text("Vrati")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderedCardRow(
    number: Int,
    option: StepOptionUi,
    onUp: () -> Unit,
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
                TextButton(onClick = onUp) {
                    Text("↑")
                }

                TextButton(onClick = onDown) {
                    Text("↓")
                }

                TextButton(onClick = onExclude) {
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
                onMapOptionToZone = onMapOptionToZone,
                onRemoveOptionZone = onRemoveOptionZone
            )
        }
    }
}

@Composable
private fun MappingOptionCard(
    step: QuestionStepUi,
    option: StepOptionUi,
    selectedZoneId: String?,
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
                        modifier = Modifier.clickable {
                            onMapOptionToZone(step, option.optionId, zone.zoneId)
                        },
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
                        modifier = Modifier.clickable {
                            onRemoveOptionZone(step, option.optionId)
                        },
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
private fun CodeCompletionStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
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
                label = {
                    Text("Prazno mesto ${blank.blankOrder}")
                },
                singleLine = true,
                shape = RoundedCornerShape(18.dp)
            )
        }
    }
}

@Composable
private fun FreeTextStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
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
        label = {
            Text("Tvoje obrazloženje")
        },
        shape = RoundedCornerShape(18.dp)
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
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppPalette.Blue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Proveri korak",
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
                    text = if (uiState.isLastStep) "Završi" else "Dalje",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun QuestionResultScreen(
    uiState: QuestionUiState,
    onBack: () -> Unit
) {
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
            Text("‹ Nazad na početnu")
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
                    text = "🏆",
                    fontSize = 44.sp
                )

                Text(
                    text = "Zadatak završen",
                    color = AppPalette.TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
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
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppPalette.Blue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Vrati se na početnu",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
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
