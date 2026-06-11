package com.example.pmuprojekat.ui.question


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.ui.home.AppPalette
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

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
    onUpdateOrderedOptions: (QuestionStepUi, List<String>) -> Unit,
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
                        prompt = uiState.prompt,
                        diagramImageName = uiState.diagramImageName
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
                            onUpdateOrderedOptions = onUpdateOrderedOptions,
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
private fun CorrectnessBadge(
    isCorrect: Boolean,
    modifier: Modifier = Modifier
) {
    val iconColor = if (isCorrect) Color(0xFF15803D) else Color(0xFFB91C1C)
    val backgroundColor = if (isCorrect) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
    val borderColor = if (isCorrect) Color(0xFF22C55E) else Color(0xFFEF4444)

    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isCorrect) {
                DrawnCheckMark(
                    color = iconColor,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                DrawnXMark(
                    color = iconColor,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun DrawnCheckMark(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.4.dp.toPx()
        drawLine(
            color = color,
            start = Offset(size.width * 0.18f, size.height * 0.55f),
            end = Offset(size.width * 0.42f, size.height * 0.78f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.42f, size.height * 0.78f),
            end = Offset(size.width * 0.84f, size.height * 0.22f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun DrawnXMark(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.4.dp.toPx()
        drawLine(
            color = color,
            start = Offset(size.width * 0.2f, size.height * 0.2f),
            end = Offset(size.width * 0.8f, size.height * 0.8f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.8f, size.height * 0.2f),
            end = Offset(size.width * 0.2f, size.height * 0.8f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
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
    prompt: String,
    diagramImageName: String?
) {
    val context = LocalContext.current
    val diagramImageResId = remember(diagramImageName, context) {
        diagramImageName
            ?.takeIf { it.isNotBlank() }
            ?.let { imageName ->
                context.resources.getIdentifier(
                    imageName,
                    "drawable",
                    context.packageName
                )
            }
            ?: 0
    }

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

            if (diagramImageResId != 0) {
                DiagramImagePreview(imageResId = diagramImageResId)
            }
        }
    }
}

@Composable
private fun DiagramImagePreview(
    imageResId: Int
) {
    var showFullScreenViewer by remember(imageResId) {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showFullScreenViewer = true },
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Dijagram sistema",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 160.dp, max = 360.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Dodirni za uvećanje",
                color = AppPalette.Blue,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }

    if (showFullScreenViewer) {
        FullScreenZoomableImageDialog(
            imageResId = imageResId,
            onDismiss = { showFullScreenViewer = false }
        )
    }
}

@Composable
private fun FullScreenZoomableImageDialog(
    imageResId: Int,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xF20F172A))
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                )
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 18.dp, top = 16.dp, end = 72.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "Dijagram sistema",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Uvećaj prstima i pomeraj dok je slika uvećana.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 14.dp, end = 14.dp)
                    .size(44.dp)
                    .clickable { onDismiss() },
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.14f),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.24f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "X",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            ZoomableDiagramImage(
                imageResId = imageResId,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 86.dp)
            )
        }
    }
}

@Composable
private fun ZoomableDiagramImage(
    imageResId: Int,
    modifier: Modifier = Modifier
) {
    var scale by remember(imageResId) {
        mutableStateOf(1f)
    }
    var offset by remember(imageResId) {
        mutableStateOf(Offset.Zero)
    }

    val transformState = rememberTransformableState { zoomChange, panChange, _ ->
        val nextScale = (scale * zoomChange).coerceIn(1f, 4.5f)
        scale = nextScale

        offset = if (nextScale == 1f) {
            Offset.Zero
        } else {
            offset + panChange
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Dijagram sistema",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(transformState),
            contentScale = ContentScale.Fit
        )
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
    onUpdateOrderedOptions: (QuestionStepUi, List<String>) -> Unit,
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
                StepType.VISUAL_MAPPING.id -> {
                    ChoiceStepContent(
                        step = step,
                        draft = draft,
                        isLocked = isLocked,
                        onToggleOption = onToggleOption
                    )
                }

                StepType.MULTI_CHOICE.id,
                StepType.HOTSPOT.id -> {
                    if (isArchitectCompositionComponentStep(step)) {
                        ArchitectComponentSelectionContent(
                            step = step,
                            draft = draft,
                            feedback = feedback,
                            isLocked = isLocked,
                            onToggleOption = onToggleOption
                        )
                    } else {
                        ChoiceStepContent(
                            step = step,
                            draft = draft,
                            isLocked = isLocked,
                            onToggleOption = onToggleOption
                        )
                    }
                }

                StepType.ORDERED_CARDS.id -> {
                    if (isArchitectExtensionOrderedStep(step)) {
                        ArchitectOrderedCardsContent(
                            step = step,
                            draft = draft,
                            feedback = feedback,
                            isLocked = isLocked,
                            onUpdateOrderedOptions = onUpdateOrderedOptions,
                            onExcludeOrderedOption = onExcludeOrderedOption,
                            onRestoreOrderedOption = onRestoreOrderedOption
                        )
                    } else {
                        OrderedCardsStepContent(
                            step = step,
                            draft = draft,
                            feedback = feedback,
                            isLocked = isLocked,
                            onUpdateOrderedOptions = onUpdateOrderedOptions,
                            onRestoreOrderedOption = onRestoreOrderedOption
                        )
                    }
                }

                StepType.CATEGORIZATION.id -> {
                    when {
                        isArchitectStyleZoneMappingStep(step) -> {
                            MappingStepContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }

                        isArchitectType6DefenseStep(step) -> {
                            ArchitectDefenseBoardContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }

                        isArchitectScalingEffectMappingStep(step) -> {
                            ArchitectFocusedEffectLineMappingContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }

                        isArchitectCompromisePriorityStep(step) -> {
                            ArchitectPriorityBoardContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }

                        isArchitectCompromiseSignalStep(step) -> {
                            ArchitectSignalBoardContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }

                        isArchitectCompositionBoardStep(step) -> {
                            ArchitectMultiZoneCompositionContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }

                        isArchitectCauseSymptomStep(step) -> {
                            ArchitectCauseSymptomContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }

                        isArchitectThreeWayReviewStep(step) -> {
                            ArchitectThreeWayCategorizationContent(
                                step = step,
                                draft = draft,
                                feedback = feedback,
                                isLocked = isLocked,
                                onMapOptionToZone = onMapOptionToZone,
                                onRemoveOptionZone = onRemoveOptionZone
                            )
                        }

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
            val optionLabel = if (isCodeDecisionChoice) {
                option.label?.trim()?.takeIf { it.isNotBlank() }
            } else {
                displayableOptionLabel(option.label)
            }

            SelectableOptionCard(
                label = optionLabel,
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
                if (selected) {
                    DrawnCheckMark(
                        color = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
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

private fun isArchitectCompositionComponentStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.MULTI_CHOICE.id &&
            step.stepId.startsWith("A4.") &&
            step.stepId.endsWith("_s1")
}

private fun isArchitectStyleZoneMappingStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            step.stepId.startsWith("A2.") &&
            step.stepId.endsWith("_s2")
}

@Composable
private fun ArchitectComponentSelectionContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onToggleOption: (QuestionStepUi, String) -> Unit
) {
    val showResultColors = feedback != null
    val selectedCount = draft.selectedOptionIds.size
    val requiredCount = step.requiredCount ?: step.options.count { it.isCorrect }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFF8FAFC),
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Arhitektonski blokovi",
                        color = AppPalette.TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = if (isLocked) {
                            "Izbor je zakljucan. Zeleni blokovi pripadaju osnovnoj arhitekturi; crveni su zamke."
                        } else {
                            "Izaberi blokove potrebne za prvu stabilnu arhitekturu. Primamljive, ali stetne dodatke ostavi van izbora."
                        },
                        color = AppPalette.TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = AppPalette.Blue.copy(alpha = 0.12f),
                    border = BorderStroke(1.dp, AppPalette.Blue.copy(alpha = 0.25f))
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                        text = "$selectedCount/$requiredCount",
                        color = AppPalette.Blue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        step.options.forEach { option ->
            val selected = draft.selectedOptionIds.contains(option.optionId)
            val missedRequired = showResultColors && option.isCorrect && !selected
            val correctSelected = showResultColors && option.isCorrect && selected
            val wrongSelected = showResultColors && !option.isCorrect && selected

            val backgroundColor = when {
                correctSelected -> Color(0xFFDCFCE7)
                wrongSelected -> Color(0xFFFEE2E2)
                missedRequired -> Color(0xFFFFF7ED)
                selected -> Color(0xFFEFF6FF)
                else -> Color.White
            }

            val borderColor = when {
                correctSelected -> Color(0xFF22C55E)
                wrongSelected -> Color(0xFFEF4444)
                missedRequired -> Color(0xFFF59E0B)
                selected -> AppPalette.Blue
                else -> AppPalette.Border
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (!isLocked) {
                            Modifier.clickable { onToggleOption(step, option.optionId) }
                        } else {
                            Modifier
                        }
                    ),
                shape = RoundedCornerShape(18.dp),
                color = backgroundColor,
                border = BorderStroke(if (selected) 1.5.dp else 1.dp, borderColor),
                shadowElevation = if (selected) 4.dp else 1.dp
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                when {
                                    correctSelected -> Color(0xFF22C55E)
                                    wrongSelected -> Color(0xFFEF4444)
                                    missedRequired -> Color(0xFFF59E0B)
                                    selected -> AppPalette.Blue
                                    else -> Color(0xFFE2E8F0)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            correctSelected || selected -> DrawnCheckMark(
                                color = Color.White,
                                modifier = Modifier.size(13.dp)
                            )

                            wrongSelected -> DrawnXMark(
                                color = Color.White,
                                modifier = Modifier.size(12.dp)
                            )

                            missedRequired -> Text(
                                text = "!",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = option.text,
                            color = AppPalette.TextPrimary,
                            fontSize = 14.sp,
                            lineHeight = 19.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        if (showResultColors && (missedRequired || wrongSelected)) {
                            Text(
                                text = if (missedRequired) "Nedostaje obavezna komponenta" else "Izabrana je pogresna komponenta",
                                color = if (missedRequired) Color(0xFFB45309) else Color(0xFFB91C1C),
                                fontSize = 11.sp,
                                lineHeight = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArchitectPressureDefenseContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onToggleOption: (QuestionStepUi, String) -> Unit
) {
    val showResultColors = feedback != null
    val pressureTitle = step.title
        .replace("â€”", "-")
        .lines()
        .joinToString(" ") { it.trim() }
        .trim()

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            color = AppPalette.Navy,
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Odbrani odluku",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = pressureTitle,
                    color = Color(0xFFE2E8F0),
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        step.options.forEach { option ->
            val selected = draft.selectedOptionIds.contains(option.optionId)
            val correctSelected = showResultColors && selected && option.isCorrect
            val wrongSelected = showResultColors && selected && !option.isCorrect
            val missedCorrect = showResultColors && option.isCorrect && !selected

            val backgroundColor = when {
                correctSelected -> Color(0xFFDCFCE7)
                wrongSelected -> Color(0xFFFEE2E2)
                missedCorrect -> Color(0xFFFFF7ED)
                selected -> Color(0xFFEFF6FF)
                else -> Color.White
            }

            val borderColor = when {
                correctSelected -> Color(0xFF22C55E)
                wrongSelected -> Color(0xFFEF4444)
                missedCorrect -> Color(0xFFF59E0B)
                selected -> AppPalette.Blue
                else -> AppPalette.Border
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (!isLocked) {
                            Modifier.clickable { onToggleOption(step, option.optionId) }
                        } else {
                            Modifier
                        }
                    ),
                shape = RoundedCornerShape(18.dp),
                color = backgroundColor,
                border = BorderStroke(1.dp, borderColor),
                shadowElevation = if (selected) 4.dp else 1.dp
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(if (selected) AppPalette.Indigo else Color(0xFFE2E8F0)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (selected) {
                            DrawnCheckMark(
                                color = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        modifier = Modifier.weight(1f),
                        text = option.text,
                        color = AppPalette.TextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderedCardsStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onUpdateOrderedOptions: (QuestionStepUi, List<String>) -> Unit,
    onRestoreOrderedOption: (QuestionStepUi, String) -> Unit
) {
    val optionById = remember(step.options) {
        step.options.associateBy { it.optionId }
    }

    var localOrderedOptionIds by remember(step.stepId) {
        mutableStateOf(draft.orderedOptionIds)
    }
    var draggedOptionId by remember(step.stepId) {
        mutableStateOf<String?>(null)
    }
    var dragOffsetY by remember(step.stepId) {
        mutableStateOf(0f)
    }
    var dragStartTopY by remember(step.stepId) {
        mutableStateOf(0f)
    }
    val itemHeightsPx = remember(step.stepId) {
        mutableStateMapOf<String, Int>()
    }
    val density = LocalDensity.current
    val itemSpacingPx = with(density) { 8.dp.toPx() }
    val fallbackItemHeightPx = with(density) { 58.dp.toPx() }

    LaunchedEffect(draft.orderedOptionIds) {
        if (draggedOptionId == null) {
            localOrderedOptionIds = draft.orderedOptionIds
        }
    }

    fun itemHeight(optionId: String): Float {
        return itemHeightsPx[optionId]?.toFloat()?.takeIf { it > 0f } ?: fallbackItemHeightPx
    }

    fun topForIndex(ids: List<String>, index: Int): Float {
        var top = 0f
        ids.take(index).forEach { optionId ->
            top += itemHeight(optionId) + itemSpacingPx
        }
        return top
    }

    fun listHeight(ids: List<String>): Float {
        if (ids.isEmpty()) return 0f
        return ids.sumOf { itemHeight(it).toDouble() }.toFloat() +
                itemSpacingPx * (ids.size - 1)
    }

    fun moveDraggedItem(optionId: String, offsetY: Float) {
        val draggedHeight = itemHeight(optionId)
        val draggedCenter = dragStartTopY + offsetY + draggedHeight / 2f
        val compactIds = localOrderedOptionIds.filterNot { it == optionId }
        var targetIndex = 0
        var runningTop = 0f

        compactIds.forEachIndexed { index, otherId ->
            val otherCenter = runningTop + itemHeight(otherId) / 2f
            if (draggedCenter > otherCenter) {
                targetIndex = index + 1
            }
            runningTop += itemHeight(otherId) + itemSpacingPx
        }

        if (localOrderedOptionIds.indexOf(optionId) != targetIndex) {
            localOrderedOptionIds = localOrderedOptionIds
                .filterNot { it == optionId }
                .toMutableList()
                .apply {
                    add(targetIndex.coerceIn(0, size), optionId)
                }
        }
    }

    val excludedOptions by remember(step.options, draft.excludedOptionIds) {
        derivedStateOf {
            step.options.filter { draft.excludedOptionIds.contains(it.optionId) }
        }
    }

    val showResultColors = feedback != null
    val expectedCount = remember(step.options) {
        step.options.count { it.correctOrder != null }
    }
    val correctPositions = if (showResultColors) {
        localOrderedOptionIds.withIndex().count { (index, optionId) ->
            val option = optionById[optionId]
            option != null && !option.isDistractor && option.correctOrder == index + 1
        }
    } else {
        0
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
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

        if (showResultColors) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, AppPalette.Border)
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Tačno poređano: $correctPositions/$expectedCount",
                    color = AppPalette.TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(density) { listHeight(localOrderedOptionIds).toDp() })
        ) {
            localOrderedOptionIds.forEachIndexed { index, optionId ->
                val option = optionById[optionId] ?: return@forEachIndexed
                key(optionId) {
                    val slotTop = topForIndex(localOrderedOptionIds, index)
                    val animatedTop by animateFloatAsState(
                        targetValue = slotTop,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        ),
                        label = "orderedCardTop"
                    )
                    val isDragging = draggedOptionId == optionId
                    val cardTop = if (isDragging) {
                        dragStartTopY + dragOffsetY
                    } else {
                        animatedTop
                    }

                    SmoothOrderedCardRow(
                        modifier = Modifier
                            .offset { IntOffset(0, cardTop.roundToInt()) }
                            .zIndex(if (isDragging) 4f else 0f)
                            .onGloballyPositioned { coordinates ->
                                itemHeightsPx[optionId] = coordinates.size.height
                            },
                        number = index + 1,
                        option = option,
                        isLocked = isLocked,
                        showResultColors = showResultColors,
                        isCorrectPosition = option.correctOrder == index + 1 && !option.isDistractor,
                        isDragging = isDragging,
                        onDragStart = {
                            draggedOptionId = optionId
                            dragStartTopY = topForIndex(localOrderedOptionIds, index)
                            dragOffsetY = 0f
                        },
                        onDrag = { deltaY ->
                            val updatedOffset = dragOffsetY + deltaY
                            dragOffsetY = updatedOffset
                            moveDraggedItem(optionId, updatedOffset)
                        },
                        onDragEnd = {
                            val finalOrder = localOrderedOptionIds
                            draggedOptionId = null
                            dragOffsetY = 0f
                            dragStartTopY = 0f
                            onUpdateOrderedOptions(step, finalOrder)
                        }
                    )
                }
            }
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
private fun SmoothOrderedCardRow(
    modifier: Modifier = Modifier,
    number: Int,
    option: StepOptionUi,
    isLocked: Boolean,
    showResultColors: Boolean,
    isCorrectPosition: Boolean,
    isDragging: Boolean,
    onDragStart: () -> Unit,
    onDrag: (Float) -> Unit,
    onDragEnd: () -> Unit
) {
    val isWrongAfterCheck = showResultColors && !isCorrectPosition

    val backgroundColor = when {
        showResultColors && isCorrectPosition -> Color(0xFFDCFCE7)
        isWrongAfterCheck -> Color(0xFFFEE2E2)
        isDragging -> Color(0xFFEFF6FF)
        else -> Color.White
    }

    val borderColor = when {
        showResultColors && isCorrectPosition -> Color(0xFF22C55E)
        isWrongAfterCheck -> Color(0xFFEF4444)
        isDragging -> AppPalette.Blue
        else -> AppPalette.Border
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                rotationZ = if (isDragging) 0.45f else 0f
                scaleX = if (isDragging) 1.025f else 1f
                scaleY = if (isDragging) 1.025f else 1f
                alpha = if (isDragging) 0.98f else 1f
            },
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        border = BorderStroke(if (isDragging) 1.6.dp else 1.dp, borderColor),
        shadowElevation = if (isDragging) 10.dp else 2.dp
    ) {
        Row(
            modifier = Modifier
                .then(
                    if (!isLocked) {
                        Modifier.pointerInput(option.optionId) {
                            detectDragGesturesAfterLongPress(
                                onDragStart = {
                                    onDragStart()
                                },
                                onDragEnd = onDragEnd,
                                onDragCancel = onDragEnd,
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    onDrag(dragAmount.y)
                                }
                            )
                        }
                    } else {
                        Modifier
                    }
                )
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(10.dp))
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

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = option.text,
                color = AppPalette.TextPrimary,
                fontSize = if (option.text.length > 78) 11.5.sp else 12.5.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(8.dp))

            when {
                showResultColors -> CorrectnessBadge(
                    isCorrect = isCorrectPosition,
                    modifier = Modifier.size(26.dp)
                )

                !isLocked -> DragHandle(
                    modifier = Modifier.size(width = 18.dp, height = 24.dp),
                    color = AppPalette.TextMuted
                )
            }
        }
    }
}

@Composable
private fun DragHandle(
    modifier: Modifier = Modifier,
    color: Color
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 1.5.dp.toPx()
        val left = size.width * 0.25f
        val right = size.width * 0.75f
        val y1 = size.height * 0.32f
        val y2 = size.height * 0.5f
        val y3 = size.height * 0.68f

        listOf(y1, y2, y3).forEach { y ->
            drawLine(
                color = color,
                start = Offset(left, y),
                end = Offset(right, y),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
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

private fun isArchitectExtensionOrderedStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.ORDERED_CARDS.id &&
            (
                    step.stepId.startsWith("A1.") && step.stepId.endsWith("_s2") ||
                            step.stepId.startsWith("A4.") && step.stepId.endsWith("_s2") ||
                            step.stepId.startsWith("A5.") && step.stepId.endsWith("_s1")
                    )
}

@Composable
private fun ArchitectOrderedCardsContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onUpdateOrderedOptions: (QuestionStepUi, List<String>) -> Unit,
    onExcludeOrderedOption: (QuestionStepUi, String) -> Unit,
    onRestoreOrderedOption: (QuestionStepUi, String) -> Unit
) {
    val optionById = remember(step.options) {
        step.options.associateBy { it.optionId }
    }

    var localOrderedOptionIds by remember(step.stepId) {
        mutableStateOf(draft.orderedOptionIds)
    }
    var draggedOptionId by remember(step.stepId) {
        mutableStateOf<String?>(null)
    }
    var dragOffsetY by remember(step.stepId) {
        mutableStateOf(0f)
    }
    var dragStartTopY by remember(step.stepId) {
        mutableStateOf(0f)
    }
    val itemHeightsPx = remember(step.stepId) {
        mutableStateMapOf<String, Int>()
    }
    val density = LocalDensity.current
    val itemSpacingPx = with(density) { 7.dp.toPx() }
    val fallbackItemHeightPx = with(density) { 62.dp.toPx() }

    LaunchedEffect(draft.orderedOptionIds) {
        if (draggedOptionId == null) {
            localOrderedOptionIds = draft.orderedOptionIds
        }
    }

    fun itemHeight(optionId: String): Float {
        return itemHeightsPx[optionId]?.toFloat()?.takeIf { it > 0f } ?: fallbackItemHeightPx
    }

    fun topForIndex(ids: List<String>, index: Int): Float {
        var top = 0f
        ids.take(index).forEach { optionId ->
            top += itemHeight(optionId) + itemSpacingPx
        }
        return top
    }

    fun listHeight(ids: List<String>): Float {
        if (ids.isEmpty()) return 0f
        return ids.sumOf { itemHeight(it).toDouble() }.toFloat() +
                itemSpacingPx * (ids.size - 1)
    }

    fun moveDraggedItem(optionId: String, offsetY: Float) {
        val draggedHeight = itemHeight(optionId)
        val draggedCenter = dragStartTopY + offsetY + draggedHeight / 2f
        val compactIds = localOrderedOptionIds.filterNot { it == optionId }
        var targetIndex = 0
        var runningTop = 0f

        compactIds.forEachIndexed { index, otherId ->
            val otherCenter = runningTop + itemHeight(otherId) / 2f
            if (draggedCenter > otherCenter) {
                targetIndex = index + 1
            }
            runningTop += itemHeight(otherId) + itemSpacingPx
        }

        if (localOrderedOptionIds.indexOf(optionId) != targetIndex) {
            localOrderedOptionIds = localOrderedOptionIds
                .filterNot { it == optionId }
                .toMutableList()
                .apply {
                    add(targetIndex.coerceIn(0, size), optionId)
                }
        }
    }

    val excludedOptions by remember(step.options, draft.excludedOptionIds) {
        derivedStateOf {
            step.options.filter { draft.excludedOptionIds.contains(it.optionId) }
        }
    }

    val showResultColors = feedback != null

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFFF8FAFC),
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = if (isLocked) {
                    "Tok je zakljucan. Zelene kartice su na mestu, crvene treba preispitati, a pravilno izbacene zamke su oznacene kao tacne."
                } else {
                    "Zadrzati karticu i pomeraj je gore ili dole. Crvenim X izbaci korak koji je zamka ili nepotreban za arhitektonski tok."
                },
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                text = "Glavni redosled",
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(density) { listHeight(localOrderedOptionIds).toDp() })
            ) {
                localOrderedOptionIds.forEachIndexed { index, optionId ->
                    val option = optionById[optionId] ?: return@forEachIndexed
                    key(optionId) {
                        val slotTop = topForIndex(localOrderedOptionIds, index)
                        val animatedTop by animateFloatAsState(
                            targetValue = slotTop,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            ),
                            label = "architectOrderedCardTop"
                        )
                        val isDragging = draggedOptionId == optionId
                        val cardTop = if (isDragging) {
                            dragStartTopY + dragOffsetY
                        } else {
                            animatedTop
                        }

                        ArchitectOrderedCardRow(
                            modifier = Modifier
                                .offset { IntOffset(0, cardTop.roundToInt()) }
                                .zIndex(if (isDragging) 4f else 0f)
                                .onGloballyPositioned { coordinates ->
                                    itemHeightsPx[optionId] = coordinates.size.height
                                },
                            number = index + 1,
                            option = option,
                            isLocked = isLocked,
                            showResultColors = showResultColors,
                            isCorrectPosition = option.correctOrder == index + 1 && !option.isDistractor,
                            isDragging = isDragging,
                            onDragStart = {
                                draggedOptionId = optionId
                                dragStartTopY = topForIndex(localOrderedOptionIds, index)
                                dragOffsetY = 0f
                            },
                            onDrag = { deltaY ->
                                val updatedOffset = dragOffsetY + deltaY
                                dragOffsetY = updatedOffset
                                moveDraggedItem(optionId, updatedOffset)
                            },
                            onDragEnd = {
                                val finalOrder = localOrderedOptionIds
                                draggedOptionId = null
                                dragOffsetY = 0f
                                dragStartTopY = 0f
                                onUpdateOrderedOptions(step, finalOrder)
                            },
                            onExclude = {
                                if (draggedOptionId == null) {
                                    localOrderedOptionIds = localOrderedOptionIds.filterNot { it == optionId }
                                    onExcludeOrderedOption(step, option.optionId)
                                }
                            }
                        )
                    }
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFFFFF7ED),
            border = BorderStroke(1.dp, Color(0xFFFED7AA))
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Izbacene / zamke",
                        color = Color(0xFF9A3412),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = excludedOptions.size.toString(),
                        color = Color(0xFF9A3412),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                if (excludedOptions.isEmpty()) {
                    Text(
                        text = "Jos nema izbacenih kartica.",
                        color = AppPalette.TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                } else {
                    excludedOptions.forEach { option ->
                        ArchitectExcludedOrderedCard(
                            option = option,
                            isLocked = isLocked,
                            showResultColors = showResultColors,
                            onRestore = { onRestoreOrderedOption(step, option.optionId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArchitectOrderedCardRow(
    modifier: Modifier = Modifier,
    number: Int,
    option: StepOptionUi,
    isLocked: Boolean,
    showResultColors: Boolean,
    isCorrectPosition: Boolean,
    isDragging: Boolean,
    onDragStart: () -> Unit,
    onDrag: (Float) -> Unit,
    onDragEnd: () -> Unit,
    onExclude: () -> Unit
) {
    val isWrongAfterCheck = showResultColors && !isCorrectPosition

    val backgroundColor = when {
        showResultColors && isCorrectPosition -> Color(0xFFDCFCE7)
        isWrongAfterCheck -> Color(0xFFFEE2E2)
        isDragging -> Color(0xFFEFF6FF)
        else -> Color.White
    }

    val borderColor = when {
        showResultColors && isCorrectPosition -> Color(0xFF22C55E)
        isWrongAfterCheck -> Color(0xFFEF4444)
        isDragging -> AppPalette.Blue
        else -> AppPalette.Border
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                rotationZ = if (isDragging) 0.6f else 0f
                scaleX = if (isDragging) 1.025f else 1f
                scaleY = if (isDragging) 1.025f else 1f
                alpha = if (isDragging) 0.98f else 1f
            },
        shape = RoundedCornerShape(14.dp),
        color = backgroundColor,
        border = BorderStroke(if (isDragging) 1.6.dp else 1.dp, borderColor),
        shadowElevation = if (isDragging) 10.dp else 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (!isLocked) {
                            Modifier.pointerInput(option.optionId) {
                                detectDragGesturesAfterLongPress(
                                    onDragStart = {
                                        onDragStart()
                                    },
                                    onDragEnd = onDragEnd,
                                    onDragCancel = onDragEnd,
                                    onDrag = { change, dragAmount ->
                                        change.consume()
                                        onDrag(dragAmount.y)
                                    }
                                )
                            }
                        } else {
                            Modifier
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(RoundedCornerShape(8.dp))
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
                    fontSize = if (option.text.length > 95) 11.sp else 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(7.dp))

            if (showResultColors) {
                CorrectnessBadge(
                    isCorrect = isCorrectPosition,
                    modifier = Modifier.size(26.dp)
                )
            } else if (!isLocked) {
                Surface(
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onExclude() },
                    shape = CircleShape,
                    color = Color(0xFFFFE4E6),
                    border = BorderStroke(1.dp, Color(0xFFFDA4AF))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "X",
                            color = Color(0xFFE11D48),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArchitectExcludedOrderedCard(
    option: StepOptionUi,
    isLocked: Boolean,
    showResultColors: Boolean,
    onRestore: () -> Unit
) {
    val isCorrectExclusion = option.isDistractor
    val backgroundColor = when {
        showResultColors && isCorrectExclusion -> Color(0xFFDCFCE7)
        showResultColors -> Color(0xFFFEE2E2)
        else -> Color.White
    }
    val borderColor = when {
        showResultColors && isCorrectExclusion -> Color(0xFF22C55E)
        showResultColors -> Color(0xFFEF4444)
        else -> Color(0xFFFED7AA)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = option.text,
                color = AppPalette.TextPrimary,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (showResultColors) {
                Text(
                    text = if (isCorrectExclusion) "tačno izbačeno" else "pogrešno izbačeno",
                    color = if (isCorrectExclusion) Color(0xFF15803D) else Color(0xFFB91C1C),
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            } else {
                TextButton(
                    onClick = onRestore,
                    enabled = !isLocked
                ) {
                    Text("Vrati", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun MappingStepContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi? = null,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val showResultColors = feedback != null

    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        step.options.forEach { option ->
            MappingOptionCard(
                step = step,
                option = option,
                selectedZoneId = draft.mappedZoneByOptionId[option.optionId],
                showResultColors = showResultColors,
                isLocked = isLocked,
                onMapOptionToZone = onMapOptionToZone,
                onRemoveOptionZone = onRemoveOptionZone
            )
        }
    }
}

private fun isArchitectCompositionBoardStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            step.stepId.startsWith("A4.") &&
            step.stepId.endsWith("_s2")
}

private fun isArchitectCauseSymptomStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            step.stepId.startsWith("A3.") &&
            step.zones.any { it.title.contains("Simptomi", ignoreCase = true) } &&
            step.zones.any { it.title.contains("Arhitektonski uzroci", ignoreCase = true) }
}

private fun isArchitectThreeWayReviewStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            step.stepId.startsWith("A3.") &&
            step.stepId.endsWith("_s4") &&
            step.zones.any { it.title.contains("Dobici", ignoreCase = true) } &&
            step.zones.size >= 3
}

@Composable
private fun ArchitectMultiZoneCompositionContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    ArchitectZoneBoardContent(
        step = step,
        draft = draft,
        feedback = feedback,
        isLocked = isLocked,
        intro = if (isLocked) {
            "Arhitektonska tabla je zakljucana. Zelene komponente su u pravoj zoni, crvene nisu."
        } else {
            "Spakuj komponente u zone arhitekture. Svaka kartica ostaje osetljiva i mozes je prebaciti pre provere."
        },
        unassignedTitle = "Komponente za rasporedjivanje",
        emptyUnassignedText = "Sve komponente su trenutno u zonama.",
        zoneAccent = { index ->
            listOf(AppPalette.Blue, AppPalette.Green, AppPalette.Orange, AppPalette.Purple, AppPalette.Indigo)[index % 5]
        },
        onMapOptionToZone = onMapOptionToZone,
        onRemoveOptionZone = onRemoveOptionZone
    )
}

@Composable
private fun ArchitectCauseSymptomContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    ArchitectZoneBoardContent(
        step = step,
        draft = draft,
        feedback = feedback,
        isLocked = isLocked,
        intro = if (isLocked) {
            "Dijagnostičarska mapa je zaključana. Proveri razliku izmeđ‘u vidljivog simptoma i uzroka u arhitekturi."
        } else {
            "Poveži signale: ono što korisnik vidi ide u simptome, a strukturni razlog u arhitektonske uzroke."
        },
        unassignedTitle = "Signali iz sistema",
        emptyUnassignedText = "Svi signali su rasporedjeni.",
        zoneAccent = { index ->
            if (index == 0) AppPalette.Blue else AppPalette.Purple
        },
        onMapOptionToZone = onMapOptionToZone,
        onRemoveOptionZone = onRemoveOptionZone
    )
}

@Composable
private fun ArchitectThreeWayCategorizationContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    ArchitectZoneBoardContent(
        step = step,
        draft = draft,
        feedback = feedback,
        isLocked = isLocked,
        intro = if (isLocked) {
            "Trodela odluke je zaključana: dobitak, novi rizik i stvar koju treba posebno projektovati."
        } else {
            "Razdvoji efekat revizije na dobitke, nove rizike i stvari koje arhitektura mora posebno da projektuje."
        },
        unassignedTitle = "Posledice revizije",
        emptyUnassignedText = "Sve posledice su smeštene u neku od tri zone.",
        zoneAccent = { index ->
            listOf(AppPalette.Green, AppPalette.Orange, AppPalette.Indigo)[index % 3]
        },
        onMapOptionToZone = onMapOptionToZone,
        onRemoveOptionZone = onRemoveOptionZone
    )
}

@Composable
private fun ArchitectZoneBoardContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    intro: String,
    unassignedTitle: String,
    emptyUnassignedText: String,
    zoneAccent: (Int) -> Color,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val zones = remember(step.zones) {
        step.zones.sortedBy { it.zoneOrder }
    }
    val showResultColors = feedback != null
    val unassignedOptions = step.options.filter { option ->
        draft.mappedZoneByOptionId[option.optionId] == null
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFFF8FAFC),
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = intro,
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(1.dp, AppPalette.Border),
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = unassignedTitle,
                        color = AppPalette.TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = unassignedOptions.size.toString(),
                        color = AppPalette.TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                if (unassignedOptions.isEmpty()) {
                    Text(
                        text = emptyUnassignedText,
                        color = AppPalette.TextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                } else {
                    unassignedOptions.forEach { option ->
                        ArchitectZoneOptionCard(
                            step = step,
                            option = option,
                            zones = zones,
                            selectedZoneId = null,
                            currentZoneId = null,
                            showResultColors = showResultColors,
                            isLocked = isLocked,
                            accentColor = AppPalette.Navy,
                            onMapOptionToZone = onMapOptionToZone,
                            onRemoveOptionZone = onRemoveOptionZone
                        )
                    }
                }
            }
        }

        zones.forEachIndexed { index, zone ->
            val zoneOptions = step.options.filter { option ->
                draft.mappedZoneByOptionId[option.optionId] == zone.zoneId
            }

            ArchitectZoneContainer(
                step = step,
                zone = zone,
                zones = zones,
                options = zoneOptions,
                draft = draft,
                showResultColors = showResultColors,
                isLocked = isLocked,
                accentColor = zoneAccent(index),
                onMapOptionToZone = onMapOptionToZone,
                onRemoveOptionZone = onRemoveOptionZone
            )
        }
    }
}

@Composable
private fun ArchitectZoneContainer(
    step: QuestionStepUi,
    zone: StepZoneUi,
    zones: List<StepZoneUi>,
    options: List<StepOptionUi>,
    draft: StepAnswerDraft,
    showResultColors: Boolean,
    isLocked: Boolean,
    accentColor: Color,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = accentColor.copy(alpha = 0.07f),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .background(accentColor.copy(alpha = 0.16f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = zone.zoneOrder.toString(),
                        color = accentColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.width(9.dp))

                Text(
                    modifier = Modifier.weight(1f),
                    text = zone.title,
                    color = AppPalette.TextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            if (options.isEmpty()) {
                Text(
                    text = "Zona je prazna.",
                    color = AppPalette.TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            } else {
                options.forEach { option ->
                    ArchitectZoneOptionCard(
                        step = step,
                        option = option,
                        zones = zones,
                        selectedZoneId = draft.mappedZoneByOptionId[option.optionId],
                        currentZoneId = zone.zoneId,
                        showResultColors = showResultColors,
                        isLocked = isLocked,
                        accentColor = accentColor,
                        onMapOptionToZone = onMapOptionToZone,
                        onRemoveOptionZone = onRemoveOptionZone
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectZoneOptionCard(
    step: QuestionStepUi,
    option: StepOptionUi,
    zones: List<StepZoneUi>,
    selectedZoneId: String?,
    currentZoneId: String?,
    showResultColors: Boolean,
    isLocked: Boolean,
    accentColor: Color,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val isAssigned = selectedZoneId != null
    val isCorrect = selectedZoneId != null && selectedZoneId == option.correctZoneId
    val backgroundColor = when {
        showResultColors && isCorrect -> Color(0xFFDCFCE7)
        showResultColors && !isCorrect -> Color(0xFFFEE2E2)
        isAssigned -> Color.White
        else -> Color(0xFFFFFFFF)
    }
    val borderColor = when {
        showResultColors && isCorrect -> Color(0xFF22C55E)
        showResultColors && !isCorrect -> Color(0xFFEF4444)
        isAssigned -> accentColor.copy(alpha = 0.55f)
        else -> AppPalette.Border
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        shadowElevation = if (isAssigned) 2.dp else 1.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = option.text,
                    color = AppPalette.TextPrimary,
                    fontSize = 12.5.sp,
                    lineHeight = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )

                if (showResultColors) {
                    Spacer(modifier = Modifier.width(8.dp))

                    CorrectnessBadge(
                        isCorrect = isCorrect,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            if (!isLocked) {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    zones.forEach { zone ->
                        if (zone.zoneId != currentZoneId) {
                            CategoryMoveChip(
                                text = zone.title,
                                selected = false,
                                onClick = {
                                    onMapOptionToZone(step, option.optionId, zone.zoneId)
                                }
                            )
                        }
                    }

                    if (isAssigned) {
                        CategoryMoveChip(
                            text = "Ukloni",
                            selected = false,
                            danger = true,
                            onClick = {
                                onRemoveOptionZone(step, option.optionId)
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun isArchitectScalingEffectMappingStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            step.stepId.startsWith("A5.") &&
            step.stepId.endsWith("_s3")
}

@Composable
private fun ArchitectFocusedEffectLineMappingContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val zones = remember(step.zones) { step.zones.sortedBy { it.zoneOrder } }
    if (zones.isEmpty()) {
        MappingStepContent(
            step = step,
            draft = draft,
            isLocked = isLocked,
            onMapOptionToZone = onMapOptionToZone,
            onRemoveOptionZone = onRemoveOptionZone
        )
        return
    }

    val showResultColors = feedback != null
    val leftConnectorCenters = remember(step.stepId) { mutableStateMapOf<String, Offset>() }
    var activeTargetConnectorCenter by remember(step.stepId) { mutableStateOf<Offset?>(null) }
    var activeTargetBounds by remember(step.stepId) { mutableStateOf<Rect?>(null) }
    var boardOrigin by remember(step.stepId) { mutableStateOf(Offset.Zero) }
    var activeOptionId by remember(step.stepId) { mutableStateOf<String?>(null) }
    var dragEnd by remember(step.stepId) { mutableStateOf<Offset?>(null) }
    var targetHighlighted by remember(step.stepId) { mutableStateOf(false) }
    var activeZoneIndex by remember(step.stepId, zones.size) { mutableStateOf(0) }
    val safeActiveZoneIndex = activeZoneIndex.coerceIn(0, zones.lastIndex)
    val activeZone = zones[safeActiveZoneIndex]
    val activeConnectedCount = draft.mappedZoneByOptionId.count { it.value == activeZone.zoneId }

    fun local(point: Offset): Offset = point - boardOrigin

    fun isOverActiveTarget(point: Offset?): Boolean {
        if (point == null) return false
        val bounds = activeTargetBounds
        val hitPadding = 72f
        val insideCard = bounds != null &&
                point.x >= bounds.left - hitPadding &&
                point.x <= bounds.right + hitPadding &&
                point.y >= bounds.top - hitPadding &&
                point.y <= bounds.bottom + hitPadding
        val nearConnector = activeTargetConnectorCenter?.let { center ->
            val dx = center.x - point.x
            val dy = center.y - point.y
            dx * dx + dy * dy < 16000f
        } ?: false
        return insideCard || nearConnector
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFF8FAFC),
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = if (isLocked) {
                    "Veze su zaključane. Zelene veze su tačne, crvene vode ka pogrešnom tipu efekta."
                } else {
                    "Poveži odluke koje pripadaju trenutno prikazanom tipu efekta."
                },
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        ArchitectEffectSelector(
            zones = zones,
            activeIndex = safeActiveZoneIndex,
            mappedZoneByOptionId = draft.mappedZoneByOptionId,
            onSelect = { index ->
                activeZoneIndex = index
                activeOptionId = null
                dragEnd = null
                targetHighlighted = false
            },
            onPrevious = {
                activeZoneIndex = (safeActiveZoneIndex - 1).coerceAtLeast(0)
                activeOptionId = null
                dragEnd = null
                targetHighlighted = false
            },
            onNext = {
                activeZoneIndex = (safeActiveZoneIndex + 1).coerceAtMost(zones.lastIndex)
                activeOptionId = null
                dragEnd = null
                targetHighlighted = false
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    boardOrigin = coordinates.positionInRoot()
                }
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                draft.mappedZoneByOptionId.forEach { (optionId, zoneId) ->
                    if (zoneId != activeZone.zoneId) return@forEach
                    val start = leftConnectorCenters[optionId]?.let(::local)
                    val end = activeTargetConnectorCenter?.let(::local)
                    if (start != null && end != null) {
                        val option = step.options.firstOrNull { it.optionId == optionId }
                        val correct = option?.correctZoneId == activeZone.zoneId
                        val color = when {
                            showResultColors && correct -> Color(0xFF22C55E)
                            showResultColors && !correct -> Color(0xFFEF4444)
                            else -> AppPalette.Blue
                        }
                        drawArchitectConnection(start, end, color)
                    }
                }

                val active = activeOptionId
                val start = active?.let { leftConnectorCenters[it] }?.let(::local)
                val end = dragEnd?.let(::local)
                if (start != null && end != null) {
                    drawArchitectConnection(start, end, AppPalette.Indigo.copy(alpha = 0.78f))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1.1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    step.options.forEach { option ->
                        val selectedZoneId = draft.mappedZoneByOptionId[option.optionId]
                        val selectedZone = zones.firstOrNull { it.zoneId == selectedZoneId }
                        val selectedForActiveZone = selectedZoneId == activeZone.zoneId
                        val correct = selectedZoneId != null && selectedZoneId == option.correctZoneId
                        val missingForActiveZone = showResultColors &&
                                selectedZoneId == null &&
                                option.correctZoneId == activeZone.zoneId
                        val wrongAwayFromActiveZone = showResultColors &&
                                selectedZoneId != null &&
                                selectedZoneId != activeZone.zoneId &&
                                option.correctZoneId == activeZone.zoneId
                        ArchitectFocusedDecisionCard(
                            option = option,
                            selectedZone = selectedZone,
                            activeZone = activeZone,
                            showResultColors = showResultColors,
                            selectedForActiveZone = selectedForActiveZone,
                            isCorrect = correct,
                            isMissingForActiveZone = missingForActiveZone,
                            isWrongAwayFromActiveZone = wrongAwayFromActiveZone,
                            isLocked = isLocked,
                            onRemoveActiveConnection = {
                                onRemoveOptionZone(step, option.optionId)
                            },
                            onConnectorPositioned = { center ->
                                leftConnectorCenters[option.optionId] = center
                            },
                            onDragStart = {
                                activeOptionId = option.optionId
                                dragEnd = leftConnectorCenters[option.optionId]
                            },
                            onDrag = { point ->
                                dragEnd = point
                                targetHighlighted = isOverActiveTarget(point)
                            },
                            onDragEnd = {
                                if (isOverActiveTarget(dragEnd)) {
                                    onMapOptionToZone(step, option.optionId, activeZone.zoneId)
                                }
                                activeOptionId = null
                                dragEnd = null
                                targetHighlighted = false
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(0.85f)
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {
                    ArchitectFocusedEffectTargetCard(
                        zone = activeZone,
                        connectedCount = activeConnectedCount,
                        currentIndex = safeActiveZoneIndex,
                        totalCount = zones.size,
                        highlighted = targetHighlighted,
                        onTargetPositioned = { connector, bounds ->
                            activeTargetConnectorCenter = connector
                            activeTargetBounds = bounds
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectEffectSelector(
    zones: List<StepZoneUi>,
    activeIndex: Int,
    mappedZoneByOptionId: Map<String, String>,
    onSelect: (Int) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onPrevious,
                enabled = activeIndex > 0
            ) {
                Text("Prethodni", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(18.dp),
                color = AppPalette.Navy,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Efekat ${activeIndex + 1} / ${zones.size}",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = zones[activeIndex].title,
                        color = Color(0xFFE2E8F0),
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            TextButton(
                onClick = onNext,
                enabled = activeIndex < zones.lastIndex
            ) {
                Text("Sledeći", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            zones.forEachIndexed { index, zone ->
                val connectedCount = mappedZoneByOptionId.count { it.value == zone.zoneId }
                ArchitectEffectSelectorChip(
                    label = "Efekat ${index + 1}",
                    connectedCount = connectedCount,
                    selected = index == activeIndex,
                    onClick = { onSelect(index) }
                )
            }
        }
    }
}

@Composable
private fun ArchitectEffectSelectorChip(
    label: String,
    connectedCount: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected) Color(0xFFEFF6FF) else Color.White
    val content = if (selected) AppPalette.Blue else AppPalette.TextSecondary
    val border = if (selected) AppPalette.Blue else AppPalette.Border

    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = background,
        border = BorderStroke(1.dp, border),
        shadowElevation = if (selected) 3.dp else 0.dp
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            text = "$label - $connectedCount",
            color = content,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawArchitectConnection(
    start: Offset,
    end: Offset,
    color: Color
) {
    val controlX = (start.x + end.x) / 2f
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(start.x, start.y)
        cubicTo(controlX, start.y, controlX, end.y, end.x, end.y)
    }
    drawPath(
        path = path,
        color = color,
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5f, cap = StrokeCap.Round)
    )
    drawCircle(color = color, radius = 6f, center = start)
    drawCircle(color = color, radius = 6f, center = end)
}

@Composable
private fun ArchitectFocusedDecisionCard(
    option: StepOptionUi,
    selectedZone: StepZoneUi?,
    activeZone: StepZoneUi,
    showResultColors: Boolean,
    selectedForActiveZone: Boolean,
    isCorrect: Boolean,
    isMissingForActiveZone: Boolean,
    isWrongAwayFromActiveZone: Boolean,
    isLocked: Boolean,
    onRemoveActiveConnection: () -> Unit,
    onConnectorPositioned: (Offset) -> Unit,
    onDragStart: () -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit
) {
    var connectorRoot by remember(option.optionId) { mutableStateOf(Offset.Zero) }
    val wrongOnActiveZone = showResultColors && selectedForActiveZone && !isCorrect
    val backgroundColor = when {
        showResultColors && selectedForActiveZone && isCorrect -> Color(0xFFECFDF5)
        wrongOnActiveZone || isWrongAwayFromActiveZone -> Color(0xFFFEE2E2)
        isMissingForActiveZone -> Color(0xFFFFF7ED)
        selectedForActiveZone -> Color(0xFFEFF6FF)
        selectedZone != null -> Color(0xFFF8FAFC)
        else -> Color.White
    }
    val borderColor = when {
        showResultColors && selectedForActiveZone && isCorrect -> Color(0xFF22C55E)
        wrongOnActiveZone || isWrongAwayFromActiveZone -> Color(0xFFEF4444)
        isMissingForActiveZone -> Color(0xFFF59E0B)
        selectedForActiveZone -> AppPalette.Blue
        selectedZone != null -> Color(0xFFCBD5E1)
        else -> AppPalette.Border
    }
    val statusText = when {
        showResultColors && selectedForActiveZone && isCorrect -> "Tačno povezano sa ovim efektom"
        wrongOnActiveZone -> "Pogrešan tip efekta"
        isWrongAwayFromActiveZone -> "Treba da bude ovde, sada je u: ${selectedZone?.title.orEmpty()}"
        isMissingForActiveZone -> "Nedostaje veza sa ovim efektom"
        selectedForActiveZone -> "Povezano sa ovim efektom"
        selectedZone != null -> "Povezano sa: ${selectedZone.title}"
        else -> "Nije povezano"
    }
    val statusColor = when {
        showResultColors && selectedForActiveZone && isCorrect -> Color(0xFF15803D)
        wrongOnActiveZone || isWrongAwayFromActiveZone -> Color(0xFFB91C1C)
        isMissingForActiveZone -> Color(0xFFC2410C)
        selectedForActiveZone -> AppPalette.Blue
        else -> AppPalette.TextMuted
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        shadowElevation = if (selectedForActiveZone) 5.dp else 1.dp
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = option.text,
                    color = AppPalette.TextPrimary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = statusText,
                    color = statusColor,
                    fontSize = 10.5.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (!isLocked && selectedForActiveZone) {
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    modifier = Modifier
                        .size(26.dp)
                        .clickable { onRemoveActiveConnection() },
                    shape = CircleShape,
                    color = Color(0xFFFFE4E6),
                    border = BorderStroke(1.dp, Color(0xFFFDA4AF))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "X",
                            color = Color(0xFFE11D48),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                modifier = Modifier
                    .size(30.dp)
                    .onGloballyPositioned { coordinates ->
                        val position = coordinates.positionInRoot()
                        connectorRoot = position
                        onConnectorPositioned(
                            Offset(
                                position.x + coordinates.size.width / 2f,
                                position.y + coordinates.size.height / 2f
                            )
                        )
                    }
                    .then(
                        if (!isLocked) {
                            Modifier.pointerInput(option.optionId, activeZone.zoneId) {
                                detectDragGestures(
                                    onDragStart = {
                                        onDragStart()
                                    },
                                    onDragEnd = onDragEnd,
                                    onDragCancel = onDragEnd,
                                    onDrag = { change, _ ->
                                        change.consume()
                                        onDrag(connectorRoot + change.position)
                                    }
                                )
                            }
                        } else {
                            Modifier
                        }
                    ),
                shape = CircleShape,
                color = if (isLocked) Color(0xFFCBD5E1) else AppPalette.Blue,
                shadowElevation = if (isLocked) 1.dp else 5.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "•",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectFocusedEffectTargetCard(
    zone: StepZoneUi,
    connectedCount: Int,
    currentIndex: Int,
    totalCount: Int,
    highlighted: Boolean,
    onTargetPositioned: (Offset, Rect) -> Unit
) {
    val accent = if (highlighted) AppPalette.Indigo else AppPalette.Navy
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(168.dp)
            .onGloballyPositioned { coordinates ->
                val position = coordinates.positionInRoot()
                val width = coordinates.size.width.toFloat()
                val height = coordinates.size.height.toFloat()
                onTargetPositioned(
                    Offset(position.x + width * 0.08f, position.y + height / 2f),
                    Rect(
                        left = position.x,
                        top = position.y,
                        right = position.x + width,
                        bottom = position.y + height
                    )
                )
            },
        shape = RoundedCornerShape(24.dp),
        color = if (highlighted) Color(0xFFEFF6FF) else Color(0xFFF8FAFC),
        border = BorderStroke(1.5.dp, if (highlighted) AppPalette.Indigo else AppPalette.Border),
        shadowElevation = if (highlighted) 9.dp else 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = accent
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp),
                    text = "Efekat ${currentIndex + 1} / $totalCount",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Text(
                text = zone.title,
                color = AppPalette.TextPrimary,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = BorderStroke(1.dp, if (highlighted) AppPalette.Indigo else AppPalette.Border)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                    text = if (highlighted) {
                        "Pusti ovde"
                    } else {
                        "$connectedCount povezanih odluka"
                    },
                    color = if (highlighted) AppPalette.Indigo else AppPalette.TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun ArchitectDecisionConnectorCard(
    option: StepOptionUi,
    selectedZoneId: String?,
    showResultColors: Boolean,
    isCorrect: Boolean,
    isMissing: Boolean,
    isLocked: Boolean,
    onConnectorPositioned: (Offset) -> Unit,
    onDragStart: () -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit
) {
    var connectorRoot by remember(option.optionId) { mutableStateOf(Offset.Zero) }
    val borderColor = when {
        showResultColors && isCorrect -> Color(0xFF22C55E)
        showResultColors && selectedZoneId != null -> Color(0xFFEF4444)
        isMissing -> Color(0xFFF59E0B)
        selectedZoneId != null -> AppPalette.Blue
        else -> AppPalette.Border
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = if (isMissing) Color(0xFFFFF7ED) else Color.White,
        border = BorderStroke(1.dp, borderColor),
        shadowElevation = if (selectedZoneId != null) 4.dp else 1.dp
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = option.text,
                color = AppPalette.TextPrimary,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                modifier = Modifier
                    .size(30.dp)
                    .onGloballyPositioned { coordinates ->
                        val position = coordinates.positionInRoot()
                        connectorRoot = position
                        onConnectorPositioned(
                            Offset(
                                position.x + coordinates.size.width / 2f,
                                position.y + coordinates.size.height / 2f
                            )
                        )
                    }
                    .then(
                        if (!isLocked) {
                            Modifier.pointerInput(option.optionId) {
                                detectDragGestures(
                                    onDragStart = {
                                        onDragStart()
                                    },
                                    onDragEnd = onDragEnd,
                                    onDragCancel = onDragEnd,
                                    onDrag = { change, _ ->
                                        change.consume()
                                        onDrag(connectorRoot + change.position)
                                    }
                                )
                            }
                        } else {
                            Modifier
                        }
                    ),
                shape = CircleShape,
                color = AppPalette.Blue,
                shadowElevation = 5.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "•",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectEffectTargetCard(
    zone: StepZoneUi,
    connectedCount: Int,
    highlighted: Boolean,
    isLocked: Boolean,
    onConnectorPositioned: (Offset) -> Unit
) {
    val accent = if (highlighted) AppPalette.Indigo else AppPalette.Navy
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = if (highlighted) Color(0xFFEFF6FF) else Color(0xFFF8FAFC),
        border = BorderStroke(1.3.dp, if (highlighted) AppPalette.Indigo else AppPalette.Border),
        shadowElevation = if (highlighted) 6.dp else 1.dp
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(26.dp)
                    .onGloballyPositioned { coordinates ->
                        val position = coordinates.positionInRoot()
                        onConnectorPositioned(
                            Offset(
                                position.x + coordinates.size.width / 2f,
                                position.y + coordinates.size.height / 2f
                            )
                        )
                    },
                shape = CircleShape,
                color = accent
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = connectedCount.toString(),
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = zone.title,
                color = AppPalette.TextPrimary,
                fontSize = 11.5.sp,
                lineHeight = 15.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
private fun ArchitectConnectionChip(
    optionText: String,
    zoneTitle: String,
    enabled: Boolean,
    onRemove: () -> Unit
) {
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
                text = "${optionText.take(46)} -> ${zoneTitle.take(34)}",
                color = AppPalette.TextSecondary,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                fontWeight = FontWeight.Bold
            )

            if (enabled) {
                Surface(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onRemove() },
                    shape = CircleShape,
                    color = Color(0xFFFFE4E6)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "X",
                            color = Color(0xFFE11D48),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

private fun isArchitectCompromisePriorityStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            step.stepId.startsWith("A6.") &&
            step.stepId.endsWith("_s3")
}

private fun isArchitectCompromiseSignalStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            step.stepId.startsWith("A6.") &&
            step.stepId.endsWith("_s4")
}

private fun isArchitectType6DefenseStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            step.stepId.startsWith("A6.") &&
            step.stepId.endsWith("_s2")
}

@Composable
private fun ArchitectDefenseBoardContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val zones = remember(step.zones) { step.zones.sortedBy { it.zoneOrder } }
    val showResultColors = feedback != null
    var selectedOptionId by remember(step.stepId, isLocked) { mutableStateOf<String?>(null) }
    val selectedOption = step.options.firstOrNull { it.optionId == selectedOptionId }
    val unassigned = step.options.filter { draft.mappedZoneByOptionId[it.optionId] == null }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ArchitectType6CommandHeader(
            title = "Arhitektonska odluka pod pritiskom",
            subtitle = if (isLocked) {
                "Tabla odbrane je zakljucana. Zelene odbrane odgovaraju na pritisak; crvene slabe odluku."
            } else {
                "Izaberi jednu odbranu, zatim je povezi sa pritiskom koji neutralise."
            },
            accent = AppPalette.Indigo,
            marker = "ADR"
        )

        ArchitectDefensePulseStrip(
            zones = zones,
            draft = draft,
            step = step,
            accent = AppPalette.Indigo
        )

        ArchitectSelectedCardNotice(
            selectedOption = selectedOption,
            accent = AppPalette.Indigo,
            emptyText = "Nema izabrane odbrane. Izaberi karticu iz spila."
        )

        zones.forEachIndexed { index, zone ->
            val assigned = step.options.filter { draft.mappedZoneByOptionId[it.optionId] == zone.zoneId }
            ArchitectPressurePanel(
                zone = zone,
                pressureIndex = index + 1,
                assigned = assigned,
                selectedOption = selectedOption,
                draft = draft,
                step = step,
                showResultColors = showResultColors,
                isLocked = isLocked,
                onAssignSelected = {
                    val optionId = selectedOptionId ?: return@ArchitectPressurePanel
                    onMapOptionToZone(step, optionId, zone.zoneId)
                    selectedOptionId = null
                },
                onSelectCard = { optionId ->
                    selectedOptionId = optionId
                },
                onRemoveCard = { optionId ->
                    onRemoveOptionZone(step, optionId)
                    if (selectedOptionId == optionId) selectedOptionId = null
                }
            )
        }

        ArchitectType6CardDeck(
            title = "Spil odbrana",
            emptyText = "Sve odbrane su povezane sa pritiscima.",
            options = unassigned,
            selectedOptionId = selectedOptionId,
            showResultColors = showResultColors,
            isLocked = isLocked,
            accent = AppPalette.Indigo,
            onSelectCard = { optionId ->
                selectedOptionId = if (selectedOptionId == optionId) null else optionId
            }
        )
    }
}

@Composable
private fun ArchitectType6CommandHeader(
    title: String,
    subtitle: String,
    accent: Color,
    marker: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = AppPalette.Navy,
        shadowElevation = 9.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(AppPalette.Navy, accent.copy(alpha = 0.72f), AppPalette.Navy)
                    )
                )
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.14f))
                        .border(1.dp, Color.White.copy(alpha = 0.24f), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = marker,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = subtitle,
                        color = Color(0xFFE2E8F0),
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectSelectedCardNotice(
    selectedOption: StepOptionUi?,
    accent: Color,
    emptyText: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = if (selectedOption == null) Color(0xFFF8FAFC) else accent.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, if (selectedOption == null) Color(0xFFE2E8F0) else accent.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = if (selectedOption == null) "Konzola za postavljanje" else "Izabrana kartica",
                color = if (selectedOption == null) AppPalette.TextSecondary else accent,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = selectedOption?.text ?: emptyText,
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ArchitectDefensePulseStrip(
    zones: List<StepZoneUi>,
    draft: StepAnswerDraft,
    step: QuestionStepUi,
    accent: Color
) {
    val completion = zones.map { zone ->
        step.options.any { option -> draft.mappedZoneByOptionId[option.optionId] == zone.zoneId }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                text = "Mapa pritisaka",
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
            ) {
                val y = size.height / 2f
                val start = 18f
                val end = size.width - 18f
                drawLine(
                    color = Color(0xFFCBD5E1),
                    start = Offset(start, y),
                    end = Offset(end, y),
                    strokeWidth = 4f,
                    cap = StrokeCap.Round
                )
                val count = zones.size.coerceAtLeast(1)
                zones.forEachIndexed { index, _ ->
                    val x = if (count == 1) size.width / 2f else start + ((end - start) * index / (count - 1))
                    val active = completion.getOrElse(index) { false }
                    drawCircle(
                        color = if (active) accent else Color(0xFFFFFFFF),
                        radius = if (active) 11f else 9f,
                        center = Offset(x, y)
                    )
                    drawCircle(
                        color = if (active) accent.copy(alpha = 0.20f) else Color(0xFFCBD5E1).copy(alpha = 0.45f),
                        radius = if (active) 18f else 14f,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectPressurePanel(
    zone: StepZoneUi,
    pressureIndex: Int,
    assigned: List<StepOptionUi>,
    selectedOption: StepOptionUi?,
    draft: StepAnswerDraft,
    step: QuestionStepUi,
    showResultColors: Boolean,
    isLocked: Boolean,
    onAssignSelected: () -> Unit,
    onSelectCard: (String) -> Unit,
    onRemoveCard: (String) -> Unit
) {
    val canAccept = selectedOption != null && !isLocked
    val accent = Color(0xFFF97316)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (canAccept) Modifier.clickable { onAssignSelected() } else Modifier),
        shape = RoundedCornerShape(26.dp),
        color = Color.White,
        border = BorderStroke(1.5.dp, if (canAccept) AppPalette.Indigo else accent.copy(alpha = 0.34f)),
        shadowElevation = if (canAccept) 10.dp else 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(accent.copy(alpha = 0.10f), Color.White, AppPalette.Indigo.copy(alpha = if (canAccept) 0.13f else 0.04f))
                    )
                )
                .padding(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(accent.copy(alpha = 0.17f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = pressureIndex.toString(),
                            color = accent,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "VEKTOR PRITISKA",
                            color = accent,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = zone.title,
                            color = AppPalette.TextPrimary,
                            fontSize = 14.sp,
                            lineHeight = 19.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.92f),
                    border = BorderStroke(1.dp, if (canAccept) AppPalette.Indigo.copy(alpha = 0.5f) else Color(0xFFE2E8F0)),
                    shadowElevation = if (assigned.isEmpty()) 0.dp else 3.dp
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        Text(
                            text = if (assigned.isEmpty()) "Mesto za odbranu je slobodno" else "Odbrana je povezana",
                            color = if (assigned.isEmpty()) AppPalette.TextSecondary else AppPalette.Indigo,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        if (assigned.isEmpty()) {
                            Text(
                                text = if (canAccept) "Dodirni ovaj pritisak da povezes izabranu odbranu." else "Izaberi karticu odbrane iz spila.",
                                color = AppPalette.TextMuted,
                                fontSize = 12.5.sp,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        } else {
                            assigned.forEach { option ->
                                ArchitectType6AssignmentCard(
                                    option = option,
                                    selected = selectedOption?.optionId == option.optionId,
                                    selectedZoneId = draft.mappedZoneByOptionId[option.optionId],
                                    showResultColors = showResultColors,
                                    isLocked = isLocked,
                                    accent = AppPalette.Indigo,
                                    onSelect = { onSelectCard(option.optionId) },
                                    onRemove = { onRemoveCard(option.optionId) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArchitectType6CardDeck(
    title: String,
    emptyText: String,
    options: List<StepOptionUi>,
    selectedOptionId: String?,
    showResultColors: Boolean,
    isLocked: Boolean,
    accent: Color,
    onSelectCard: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    color = AppPalette.TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = accent.copy(alpha = 0.12f)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
                        text = options.size.toString(),
                        color = accent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
            if (options.isEmpty()) {
                Text(
                    text = emptyText,
                    color = AppPalette.TextMuted,
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp
                )
            } else {
                options.forEach { option ->
                    ArchitectType6AssignmentCard(
                        option = option,
                        selected = selectedOptionId == option.optionId,
                        selectedZoneId = null,
                        showResultColors = showResultColors,
                        isLocked = isLocked,
                        accent = accent,
                        onSelect = { onSelectCard(option.optionId) },
                        onRemove = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectPriorityBoardContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val zones = remember(step.zones) { step.zones.sortedBy { it.zoneOrder } }
    val showResultColors = feedback != null
    var selectedOptionId by remember(step.stepId, isLocked) { mutableStateOf<String?>(null) }
    val selectedOption = step.options.firstOrNull { it.optionId == selectedOptionId }
    val unassigned = step.options.filter { draft.mappedZoneByOptionId[it.optionId] == null }
    val accents = listOf(AppPalette.Green, AppPalette.Indigo, Color(0xFFE11D48))

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ArchitectType6CommandHeader(
            title = "Tabla arhitektonskih prioriteta",
            subtitle = if (isLocked) {
                "Tabla strategije je zakljucana. Pregledaj boju trake na svakoj kartici odluke."
            } else {
                "Izaberi karticu odluke, zatim je postavi na vremensku traku: sada, kasnije ili odbaci."
            },
            accent = AppPalette.Green,
            marker = "SAD"
        )

        ArchitectSelectedCardNotice(
            selectedOption = selectedOption,
            accent = AppPalette.Green,
            emptyText = "Nema izabrane odluke. Izaberi karticu, zatim dodirni stratesku traku."
        )

        ArchitectType6CardDeck(
            title = "Spil odluka",
            emptyText = "Sve odluke su na tabli strategije.",
            options = unassigned,
            selectedOptionId = selectedOptionId,
            showResultColors = showResultColors,
            isLocked = isLocked,
            accent = AppPalette.Green,
            onSelectCard = { optionId ->
                selectedOptionId = if (selectedOptionId == optionId) null else optionId
            }
        )

        zones.forEachIndexed { index, zone ->
            val assigned = step.options.filter { draft.mappedZoneByOptionId[it.optionId] == zone.zoneId }
            ArchitectPriorityPanel(
                zone = zone,
                label = when (index) {
                    0 -> "SADA"
                    1 -> "KASNIJE"
                    else -> "NE"
                },
                subtitle = when (index) {
                    0 -> "trenutni pravac arhitekture"
                    1 -> "buduci backlog"
                    else -> "blokiran pravac"
                },
                accent = accents[index % accents.size],
                assigned = assigned,
                selectedOption = selectedOption,
                draft = draft,
                step = step,
                showResultColors = showResultColors,
                isLocked = isLocked,
                onAssignSelected = {
                    val optionId = selectedOptionId ?: return@ArchitectPriorityPanel
                    onMapOptionToZone(step, optionId, zone.zoneId)
                    selectedOptionId = null
                },
                onSelectCard = { optionId -> selectedOptionId = optionId },
                onRemoveCard = { optionId ->
                    onRemoveOptionZone(step, optionId)
                    if (selectedOptionId == optionId) selectedOptionId = null
                }
            )
        }
    }
}

@Composable
private fun ArchitectPriorityPanel(
    zone: StepZoneUi,
    label: String,
    subtitle: String,
    accent: Color,
    assigned: List<StepOptionUi>,
    selectedOption: StepOptionUi?,
    draft: StepAnswerDraft,
    step: QuestionStepUi,
    showResultColors: Boolean,
    isLocked: Boolean,
    onAssignSelected: () -> Unit,
    onSelectCard: (String) -> Unit,
    onRemoveCard: (String) -> Unit
) {
    val canAccept = selectedOption != null && !isLocked
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (canAccept) Modifier.clickable { onAssignSelected() } else Modifier),
        shape = RoundedCornerShape(26.dp),
        color = Color.White,
        border = BorderStroke(1.5.dp, accent.copy(alpha = if (canAccept) 0.9f else 0.42f)),
        shadowElevation = if (canAccept) 9.dp else 3.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(58.dp)
                        .height(44.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(accent.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = accent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.width(11.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = subtitle.uppercase(),
                        color = accent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = zone.title,
                        color = AppPalette.TextPrimary,
                        fontSize = 14.sp,
                        lineHeight = 19.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                val centerY = size.height / 2f
                drawLine(
                    color = accent.copy(alpha = 0.34f),
                    start = Offset(10f, centerY),
                    end = Offset(size.width - 10f, centerY),
                    strokeWidth = 5f,
                    cap = StrokeCap.Round
                )
                drawCircle(
                    color = accent,
                    radius = if (assigned.isEmpty()) 5f else 8f,
                    center = Offset(size.width * 0.18f, centerY)
                )
                drawCircle(
                    color = accent.copy(alpha = 0.22f),
                    radius = 13f,
                    center = Offset(size.width * 0.18f, centerY)
                )
            }

            if (assigned.isEmpty()) {
                Text(
                    text = if (canAccept) "Dodirni traku da postavis izabranu odluku." else "Nema odluka na ovoj traci.",
                    color = AppPalette.TextMuted,
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp
                )
            } else {
                assigned.forEach { option ->
                    ArchitectType6AssignmentCard(
                        option = option,
                        selected = selectedOption?.optionId == option.optionId,
                        selectedZoneId = draft.mappedZoneByOptionId[option.optionId],
                        showResultColors = showResultColors,
                        isLocked = isLocked,
                        accent = accent,
                        onSelect = { onSelectCard(option.optionId) },
                        onRemove = { onRemoveCard(option.optionId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectSignalBoardContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val zones = remember(step.zones) { step.zones.sortedBy { it.zoneOrder } }
    val showResultColors = feedback != null
    var selectedOptionId by remember(step.stepId, isLocked) { mutableStateOf<String?>(null) }
    val selectedOption = step.options.firstOrNull { it.optionId == selectedOptionId }
    val unassigned = step.options.filter { draft.mappedZoneByOptionId[it.optionId] == null }
    val accents = listOf(Color(0xFF0EA5E9), Color(0xFF64748B), Color(0xFFF59E0B))

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ArchitectType6CommandHeader(
            title = "Konzola za analizu signala",
            subtitle = if (isLocked) {
                "Tabla dokaza je zakljucana. Zelene klasifikacije su ispravne; crvene traze arhitektonsku proveru."
            } else {
                "Izaberi signal i razvrstaj ga kao proveren dokaz, sum ili oprez."
            },
            accent = Color(0xFFF59E0B),
            marker = "SIG"
        )

        ArchitectSignalStatusPanel(
            zones = zones,
            draft = draft,
            step = step,
            accents = accents
        )

        ArchitectSelectedCardNotice(
            selectedOption = selectedOption,
            accent = Color(0xFFF59E0B),
            emptyText = "Nema izabranog signala. Izaberi dokaz, zatim dodirni zonu analize."
        )

        ArchitectType6CardDeck(
            title = "Spil nerazvrstanih signala",
            emptyText = "Svi signali su prosli kroz konzolu za analizu.",
            options = unassigned,
            selectedOptionId = selectedOptionId,
            showResultColors = showResultColors,
            isLocked = isLocked,
            accent = Color(0xFFF59E0B),
            onSelectCard = { optionId ->
                selectedOptionId = if (selectedOptionId == optionId) null else optionId
            }
        )

        zones.forEachIndexed { index, zone ->
            val assigned = step.options.filter { draft.mappedZoneByOptionId[it.optionId] == zone.zoneId }
            ArchitectSignalPanel(
                zone = zone,
                label = when (index) {
                    0 -> "PRAVI SIGNAL"
                    1 -> "LAZNI SIGNAL"
                    else -> "OPREZ"
                },
                hint = when (index) {
                    0 -> "jak dokaz"
                    1 -> "slabo ili varljivo"
                    else -> "opasno, ali ne automatski"
                },
                accent = accents[index % accents.size],
                assigned = assigned,
                selectedOption = selectedOption,
                draft = draft,
                step = step,
                showResultColors = showResultColors,
                isLocked = isLocked,
                onAssignSelected = {
                    val optionId = selectedOptionId ?: return@ArchitectSignalPanel
                    onMapOptionToZone(step, optionId, zone.zoneId)
                    selectedOptionId = null
                },
                onSelectCard = { optionId -> selectedOptionId = optionId },
                onRemoveCard = { optionId ->
                    onRemoveOptionZone(step, optionId)
                    if (selectedOptionId == optionId) selectedOptionId = null
                }
            )
        }
    }
}

@Composable
private fun ArchitectSignalStatusPanel(
    zones: List<StepZoneUi>,
    draft: StepAnswerDraft,
    step: QuestionStepUi,
    accents: List<Color>
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = Color(0xFF0F172A),
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Telemetrija dokaza",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
            zones.forEachIndexed { index, zone ->
                val count = step.options.count { draft.mappedZoneByOptionId[it.optionId] == zone.zoneId }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(accents[index % accents.size])
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = zone.title,
                        color = Color(0xFFE2E8F0),
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = count.toString(),
                        color = accents[index % accents.size],
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectSignalPanel(
    zone: StepZoneUi,
    label: String,
    hint: String,
    accent: Color,
    assigned: List<StepOptionUi>,
    selectedOption: StepOptionUi?,
    draft: StepAnswerDraft,
    step: QuestionStepUi,
    showResultColors: Boolean,
    isLocked: Boolean,
    onAssignSelected: () -> Unit,
    onSelectCard: (String) -> Unit,
    onRemoveCard: (String) -> Unit
) {
    val canAccept = selectedOption != null && !isLocked
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (canAccept) Modifier.clickable { onAssignSelected() } else Modifier),
        shape = RoundedCornerShape(25.dp),
        color = Color.White,
        border = BorderStroke(1.5.dp, if (canAccept) accent else accent.copy(alpha = 0.45f)),
        shadowElevation = if (canAccept) 8.dp else 3.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(accent.copy(alpha = 0.16f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = assigned.size.toString(),
                        color = accent,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.width(11.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = label,
                        color = accent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = hint,
                        color = AppPalette.TextSecondary,
                        fontSize = 11.5.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = zone.title,
                        color = AppPalette.TextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            if (assigned.isEmpty()) {
                Text(
                    text = if (canAccept) "Dodirni zonu da klasifikujes izabrani signal." else "Nema dokaza u ovoj zoni.",
                    color = AppPalette.TextMuted,
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp
                )
            } else {
                assigned.forEach { option ->
                    ArchitectType6AssignmentCard(
                        option = option,
                        selected = selectedOption?.optionId == option.optionId,
                        selectedZoneId = draft.mappedZoneByOptionId[option.optionId],
                        showResultColors = showResultColors,
                        isLocked = isLocked,
                        accent = accent,
                        onSelect = { onSelectCard(option.optionId) },
                        onRemove = { onRemoveCard(option.optionId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ArchitectType6AssignmentCard(
    option: StepOptionUi,
    selected: Boolean,
    selectedZoneId: String?,
    showResultColors: Boolean,
    isLocked: Boolean,
    accent: Color,
    onSelect: () -> Unit,
    onRemove: () -> Unit
) {
    val isCorrect = selectedZoneId != null && selectedZoneId == option.correctZoneId && !option.isDistractor
    val isWrong = showResultColors && selectedZoneId != null && !isCorrect
    val isMissing = showResultColors && selectedZoneId == null && !option.isDistractor
    val background = when {
        showResultColors && isCorrect -> Color(0xFFDCFCE7)
        isWrong -> Color(0xFFFEE2E2)
        isMissing -> Color(0xFFFFF7ED)
        selected -> accent.copy(alpha = 0.12f)
        else -> Color.White
    }
    val border = when {
        showResultColors && isCorrect -> Color(0xFF22C55E)
        isWrong -> Color(0xFFEF4444)
        isMissing -> Color(0xFFF59E0B)
        selected -> accent
        else -> AppPalette.Border
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (!isLocked) Modifier.clickable { onSelect() } else Modifier),
        shape = RoundedCornerShape(18.dp),
        color = background,
        border = BorderStroke(if (selected) 1.7.dp else 1.dp, border),
        shadowElevation = if (selected) 5.dp else 1.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(border)
            ) {
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
            if (!isLocked && selectedZoneId != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    modifier = Modifier
                        .size(26.dp)
                        .clickable { onRemove() },
                    shape = CircleShape,
                    color = Color(0xFFFFE4E6)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "X",
                            color = Color(0xFFE11D48),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArchitectPriorityWorkspaceContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val zones = remember(step.zones) { step.zones.sortedBy { it.zoneOrder } }
    val showResultColors = feedback != null
    val accents = listOf(AppPalette.Green, AppPalette.Indigo, Color(0xFFE11D48))
    val unassigned = step.options.filter { draft.mappedZoneByOptionId[it.optionId] == null }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        ArchitectWorkspaceHeader(
            title = "Tabla prioriteta strategije",
            subtitle = if (isLocked) {
                "Tabla prioriteta je zakljucana. Zelene odluke su dobro postavljene; crvene su u pogresnoj traci."
            } else {
                "Izaberi odluku i postavi je na arhitektonsku vremensku liniju: sada, kasnije ili nikako."
            },
            accent = AppPalette.Indigo
        )

        ArchitectCardTray(
            title = "Kartice odluka",
            emptyText = "Sve odluke su na tabli.",
            options = unassigned,
            zones = zones,
            accents = accents,
            step = step,
            draft = draft,
            showResultColors = showResultColors,
            isLocked = isLocked,
            onMapOptionToZone = onMapOptionToZone,
            onRemoveOptionZone = onRemoveOptionZone
        )

        zones.forEachIndexed { index, zone ->
            val zoneOptions = step.options.filter { draft.mappedZoneByOptionId[it.optionId] == zone.zoneId }
            val title = when (index) {
                0 -> "SADA / kritican put"
                1 -> "KASNIJE / arhitektonski backlog"
                else -> "NE RADITI / pogresan pravac"
            }
            ArchitectPriorityLane(
                title = title,
                zone = zone,
                options = zoneOptions,
                zones = zones,
                accents = accents,
                accent = accents[index % accents.size],
                step = step,
                draft = draft,
                showResultColors = showResultColors,
                isLocked = isLocked,
                onMapOptionToZone = onMapOptionToZone,
                onRemoveOptionZone = onRemoveOptionZone
            )
        }
    }
}

@Composable
private fun ArchitectSignalAnalysisWorkspaceContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val zones = remember(step.zones) { step.zones.sortedBy { it.zoneOrder } }
    val showResultColors = feedback != null
    val accents = listOf(Color(0xFF10B981), Color(0xFF64748B), Color(0xFFF59E0B))
    val unassigned = step.options.filter { draft.mappedZoneByOptionId[it.optionId] == null }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        ArchitectWorkspaceHeader(
            title = "Tabla analize signala",
            subtitle = if (isLocked) {
                "Dokazi su zakljucani. Zelene klasifikacije signala su dobre; crvene treba preispitati."
            } else {
                "Klasifikuj arhitektonske dokaze kao pravi signal, sum ili oprez pre promene odluke."
            },
            accent = Color(0xFFF59E0B)
        )

        ArchitectCardTray(
            title = "Nerazvrstani signali",
            emptyText = "Svi signali su klasifikovani.",
            options = unassigned,
            zones = zones,
            accents = accents,
            step = step,
            draft = draft,
            showResultColors = showResultColors,
            isLocked = isLocked,
            onMapOptionToZone = onMapOptionToZone,
            onRemoveOptionZone = onRemoveOptionZone
        )

        zones.forEachIndexed { index, zone ->
            val zoneOptions = step.options.filter { draft.mappedZoneByOptionId[it.optionId] == zone.zoneId }
            val label = when (index) {
                0 -> "PRAVI SIGNAL"
                1 -> "SUM / LAZNI SIGNAL"
                else -> "SIGNAL ZA OPREZ"
            }
            ArchitectEvidencePanel(
                label = label,
                zone = zone,
                options = zoneOptions,
                zones = zones,
                accents = accents,
                accent = accents[index % accents.size],
                step = step,
                draft = draft,
                showResultColors = showResultColors,
                isLocked = isLocked,
                onMapOptionToZone = onMapOptionToZone,
                onRemoveOptionZone = onRemoveOptionZone
            )
        }
    }
}

@Composable
private fun ArchitectWorkspaceHeader(
    title: String,
    subtitle: String,
    accent: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = AppPalette.Navy,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(accent),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 15.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = subtitle,
                    color = Color(0xFFE2E8F0),
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ArchitectCardTray(
    title: String,
    emptyText: String,
    options: List<StepOptionUi>,
    zones: List<StepZoneUi>,
    accents: List<Color>,
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    showResultColors: Boolean,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Border),
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                color = AppPalette.TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )

            if (options.isEmpty()) {
                Text(
                    text = emptyText,
                    color = AppPalette.TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            } else {
                options.forEach { option ->
                    ArchitectBoardDecisionCard(
                        option = option,
                        zones = zones,
                        accents = accents,
                        selectedZoneId = null,
                        step = step,
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
private fun ArchitectPriorityLane(
    title: String,
    zone: StepZoneUi,
    options: List<StepOptionUi>,
    zones: List<StepZoneUi>,
    accents: List<Color>,
    accent: Color,
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    showResultColors: Boolean,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = accent.copy(alpha = 0.08f),
        border = BorderStroke(1.4.dp, accent.copy(alpha = 0.42f))
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                color = accent,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = zone.title,
                color = AppPalette.TextPrimary,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Bold
            )

            if (options.isEmpty()) {
                Text(
                    text = "Nema odluka ovde.",
                    color = AppPalette.TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            } else {
                options.forEach { option ->
                    ArchitectBoardDecisionCard(
                        option = option,
                        zones = zones,
                        accents = accents,
                        selectedZoneId = draft.mappedZoneByOptionId[option.optionId],
                        step = step,
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
private fun ArchitectEvidencePanel(
    label: String,
    zone: StepZoneUi,
    options: List<StepOptionUi>,
    zones: List<StepZoneUi>,
    accents: List<Color>,
    accent: Color,
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    showResultColors: Boolean,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        border = BorderStroke(1.4.dp, accent.copy(alpha = 0.5f)),
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(accent.copy(alpha = 0.16f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = options.size.toString(),
                        color = accent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = label,
                        color = accent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = zone.title,
                        color = AppPalette.TextPrimary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (options.isEmpty()) {
                Text(
                    text = "Nema signala u ovoj zoni analize.",
                    color = AppPalette.TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            } else {
                options.forEach { option ->
                    ArchitectBoardDecisionCard(
                        option = option,
                        zones = zones,
                        accents = accents,
                        selectedZoneId = draft.mappedZoneByOptionId[option.optionId],
                        step = step,
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
private fun ArchitectBoardDecisionCard(
    option: StepOptionUi,
    zones: List<StepZoneUi>,
    accents: List<Color>,
    selectedZoneId: String?,
    step: QuestionStepUi,
    showResultColors: Boolean,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val isCorrect = selectedZoneId != null && selectedZoneId == option.correctZoneId
    val background = when {
        showResultColors && isCorrect -> Color(0xFFDCFCE7)
        showResultColors && selectedZoneId != null -> Color(0xFFFEE2E2)
        showResultColors && selectedZoneId == null -> Color(0xFFFFF7ED)
        selectedZoneId != null -> Color(0xFFF8FAFC)
        else -> Color.White
    }
    val border = when {
        showResultColors && isCorrect -> Color(0xFF22C55E)
        showResultColors && selectedZoneId != null -> Color(0xFFEF4444)
        showResultColors && selectedZoneId == null -> Color(0xFFF59E0B)
        else -> AppPalette.Border
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        color = background,
        border = BorderStroke(1.dp, border),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                text = option.text,
                color = AppPalette.TextPrimary,
                fontSize = 12.5.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.SemiBold
            )

            if (!isLocked) {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    zones.forEachIndexed { index, zone ->
                        Surface(
                            modifier = Modifier.clickable {
                                onMapOptionToZone(step, option.optionId, zone.zoneId)
                            },
                            shape = RoundedCornerShape(14.dp),
                            color = if (selectedZoneId == zone.zoneId) accents[index % accents.size] else Color(0xFFF8FAFC),
                            border = BorderStroke(1.dp, accents[index % accents.size].copy(alpha = 0.45f))
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp),
                                text = zone.title,
                                color = if (selectedZoneId == zone.zoneId) Color.White else AppPalette.TextSecondary,
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }

                    if (selectedZoneId != null) {
                        Surface(
                            modifier = Modifier.clickable {
                                onRemoveOptionZone(step, option.optionId)
                            },
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0xFFFFE4E6),
                            border = BorderStroke(1.dp, Color(0xFFFDA4AF))
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp),
                                text = "Ukloni",
                                color = Color(0xFFE11D48),
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArchitectCompromiseBoardContent(
    step: QuestionStepUi,
    draft: StepAnswerDraft,
    feedback: StepFeedbackUi?,
    isLocked: Boolean,
    boardTitle: String,
    unassignedTitle: String,
    intro: String,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    ArchitectZoneBoardContent(
        step = step,
        draft = draft,
        feedback = feedback,
        isLocked = isLocked,
        intro = if (isLocked) "$boardTitle je zakljucana. Zelene kartice su dobra procena; crvene treba preispitati." else intro,
        unassignedTitle = unassignedTitle,
        emptyUnassignedText = "Sve kartice su na tabli.",
        zoneAccent = { index ->
            listOf(AppPalette.Green, AppPalette.Orange, Color(0xFFE11D48))[index % 3]
        },
        onMapOptionToZone = onMapOptionToZone,
        onRemoveOptionZone = onRemoveOptionZone
    )
}

private fun isSeniorDiagnosisCategorizationStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            step.stepId.startsWith("S1.") &&
            step.zones.any { it.title.contains("Simptomi", ignoreCase = true) } &&
            step.zones.any { it.title.contains("uzroci", ignoreCase = true) }
}

private fun isSeniorBalanceCategorizationStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.CATEGORIZATION.id &&
            (step.stepId.startsWith("S2.") || step.stepId.startsWith("S4.") || step.stepId.startsWith("S5.")) &&
            step.zones.any { it.title.contains("Šta se dobija", ignoreCase = true) } &&
            step.zones.any {
                it.title.contains("gubi", ignoreCase = true) ||
                        it.title.contains("rizici", ignoreCase = true) ||
                        it.title.contains("cena", ignoreCase = true)
            }
}

private fun seniorPrioritySwipeLabel(leftZone: StepZoneUi): String {
    val title = leftZone.title.lowercase()
    return when {
        title.contains("prioritet") -> "← PRIORITET"
        title.contains("prednost") -> "← PREDNOST"
        title.contains("ispravno") || title.contains("tač") || title.contains("tac") -> "← ISPRAVNO"
        title.contains("dobija") || title.contains("dobit") || title.contains("korist") -> "← KORIST"
        else -> "← PRIORITET"
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
        leftTitle = "← SIMPTOMI",
        rightTitle = "UZROCI →",
        leftHint = "← SIMPTOMI",
        rightHint = "UZROCI →",
        introText = if (isLocked) {
            "Raspored je zaključan. Zelene kartice su tačne, crvene nisu."
        } else {
            "Prevuci karticu ka simptomima ili uzrocima."
        },
        quickSwipe = true,
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

    val oldRightTitle = if (costZone.title.contains("cena", ignoreCase = true)) {
        "Prihvaćena cena"
    } else {
        "Rizici / gubici"
    }
    val isTradeOffSwipe = step.stepId.startsWith("S4.")
    val quickSwipe = step.stepId.startsWith("S2.") || isTradeOffSwipe || step.stepId.startsWith("S5.")
    val isPrioritySwipe = step.stepId.startsWith("S5.")
    val leftSwipeLabel = when {
        isPrioritySwipe -> seniorPrioritySwipeLabel(gainZone)
        quickSwipe -> "← DOBITAK"
        else -> "Šta se dobija"
    }
    val rightSwipeLabel = if (isTradeOffSwipe) {
        "CENA/RIZIK →"
    } else if (quickSwipe) {
        "RIZICI/GUBICI →"
    } else {
        oldRightTitle
    }

    SeniorSwipeCategorizationContent(
        step = step,
        draft = draft,
        feedback = feedback,
        isLocked = isLocked,
        leftZone = gainZone,
        rightZone = costZone,
        leftTitle = leftSwipeLabel,
        rightTitle = rightSwipeLabel,
        leftHint = leftSwipeLabel,
        rightHint = rightSwipeLabel,
        introText = if (isLocked) {
            "Balans odluke je zaključan. Zelene kartice su tačne, crvene nisu."
        } else if (isPrioritySwipe) {
            "Prevuci karticu ka prioritetu ili riziku."
        } else if (isTradeOffSwipe) {
            "Prevuci karticu ka dobitku ili prihvacenoj ceni."
        } else if (quickSwipe) {
            "Prevuci karticu ka dobitku ili riziku."
        } else {
            "Zadrži karticu i prevuci je levo ako predstavlja dobitak, odnosno desno ako predstavlja cenu, gubitak ili rizik."
        },
        quickSwipe = quickSwipe,
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
    quickSwipe: Boolean,
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
                subtitle = if (quickSwipe) "Brzo prevuci kartice levo ili desno." else "Prevuci svaku karticu levo ili desno.",
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
                quickSwipe = quickSwipe,
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
            quickSwipe = quickSwipe,
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
            quickSwipe = quickSwipe,
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
    quickSwipe: Boolean,
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
                        quickSwipe = quickSwipe,
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
    quickSwipe: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit
) {
    var dragX by remember(option.optionId, selectedZoneId) {
        mutableStateOf(0f)
    }

    val density = LocalDensity.current
    val threshold = with(density) { 72.dp.toPx() }
    val maxVisualOffset = with(density) { 92.dp.toPx() }
    val limitedVisualOffset = dragX.coerceIn(-maxVisualOffset, maxVisualOffset)
    val animatedVisualOffset by animateFloatAsState(
        targetValue = limitedVisualOffset,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "seniorSwipeOffset"
    )

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

    val showResultBadge = showResultColors

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(if (dragX != 0f) 5f else 0f)
            .graphicsLayer {
                translationX = animatedVisualOffset
                rotationZ = (animatedVisualOffset / maxVisualOffset * 3f).coerceIn(-3f, 3f)
                scaleX = if (dragX != 0f) 1.01f else 1f
                scaleY = if (dragX != 0f) 1.01f else 1f
            }
            .then(
                if (!isLocked) {
                    Modifier.pointerInput(option.optionId, selectedZoneId, quickSwipe) {
                        if (quickSwipe) {
                            detectHorizontalDragGestures(
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
                                onHorizontalDrag = { change, dragAmount ->
                                    change.consume()
                                    dragX += dragAmount
                                }
                            )
                        } else {
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

                if (showResultBadge) {
                    Spacer(modifier = Modifier.width(8.dp))

                    CorrectnessBadge(
                        isCorrect = isCorrect,
                        modifier = Modifier.size(27.dp)
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
            active = dragX < -threshold * 0.25f,
            accentColor = leftAccent
        )

        SeniorSwipeHintPill(
            modifier = Modifier.weight(1f),
            text = rightHint,
            active = dragX > threshold * 0.25f,
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
            fontSize = 11.5.sp,
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
    showResultColors: Boolean,
    isLocked: Boolean,
    onMapOptionToZone: (QuestionStepUi, String, String) -> Unit,
    onRemoveOptionZone: (QuestionStepUi, String) -> Unit
) {
    val expectedZone = step.zones.firstOrNull { it.zoneId == option.correctZoneId }
    val hasExpectedZone = expectedZone != null
    val isCorrect = hasExpectedZone && selectedZoneId == option.correctZoneId
    val isWrong = showResultColors && hasExpectedZone && selectedZoneId != option.correctZoneId
    val cardColor = when {
        showResultColors && isCorrect -> Color(0xFFDCFCE7)
        isWrong -> Color(0xFFFEE2E2)
        else -> Color.White
    }
    val cardBorder = when {
        showResultColors && isCorrect -> Color(0xFF22C55E)
        isWrong -> Color(0xFFEF4444)
        else -> AppPalette.Border
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = cardColor,
        border = BorderStroke(1.dp, cardBorder),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
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

                if (showResultColors && hasExpectedZone) {
                    Spacer(modifier = Modifier.width(8.dp))

                    CorrectnessBadge(
                        isCorrect = isCorrect,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                step.zones.forEach { zone ->
                    val selected = selectedZoneId == zone.zoneId
                    val selectedCorrect = showResultColors && selected && zone.zoneId == option.correctZoneId
                    val selectedWrong = showResultColors && selected && zone.zoneId != option.correctZoneId
                    val expectedWhenWrong = isWrong && zone.zoneId == option.correctZoneId
                    val chipColor = when {
                        selectedCorrect -> Color(0xFF22C55E)
                        selectedWrong -> Color(0xFFEF4444)
                        selected -> AppPalette.Blue
                        expectedWhenWrong -> Color(0xFFECFDF5)
                        else -> Color(0xFFF8FAFC)
                    }
                    val chipBorder = when {
                        selectedCorrect -> Color(0xFF16A34A)
                        selectedWrong -> Color(0xFFDC2626)
                        selected -> AppPalette.Blue
                        expectedWhenWrong -> Color(0xFF22C55E)
                        else -> AppPalette.Border
                    }
                    val chipTextColor = when {
                        selectedCorrect || selectedWrong || selected -> Color.White
                        expectedWhenWrong -> Color(0xFF15803D)
                        else -> AppPalette.TextSecondary
                    }

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
                        color = chipColor,
                        border = BorderStroke(1.dp, chipBorder)
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                            text = zone.title,
                            color = chipTextColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (selectedZoneId != null && !isLocked) {
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

            if (showResultColors && isWrong) {
                Text(
                    text = if (selectedZoneId == null) {
                        "Nije rasporedjeno. Trebalo je: ${expectedZone?.title.orEmpty()}"
                    } else {
                        "Trebalo je: ${expectedZone?.title.orEmpty()}"
                    },
                    color = Color(0xFFB91C1C),
                    fontSize = 11.5.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Bold
                )
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

    val showResultBadge = showResultColors && isAssigned

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

                if (showResultBadge) {
                    Spacer(modifier = Modifier.width(6.dp))

                    CorrectnessBadge(
                        isCorrect = isCorrect,
                        modifier = Modifier.size(24.dp)
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
                            DrawnXMark(
                                color = Color(0xFFE11D48),
                                modifier = Modifier.size(10.dp)
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
    if (isArchitectCompromiseMiniAdrStep(step)) {
        ArchitectMiniAdrTemplate()
        Spacer(modifier = Modifier.height(12.dp))
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isArchitectCompromiseMiniAdrStep(step)) 190.dp else 150.dp),
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

private fun isArchitectCompromiseMiniAdrStep(step: QuestionStepUi): Boolean {
    return step.type == StepType.MINI_ADR.id &&
            step.stepId.startsWith("A6.")
}

@Composable
private fun ArchitectMiniAdrTemplate() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AppPalette.Indigo.copy(alpha = 0.28f)),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(AppPalette.Indigo.copy(alpha = 0.13f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ADR",
                        color = AppPalette.Indigo,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Mini ADR obrazac",
                        color = AppPalette.TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Koristi ovu strukturu za zapis ispod.",
                        color = AppPalette.TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Text(
                text = """
Odluka:
Biramo __.

Razlog:
Najvaznija ogranicenja su __.

Prihvacena cena:
Prihvatamo __.

Signal za preispitivanje odluke:
Preispitacemo odluku ako __.
                """.trimIndent(),
                color = AppPalette.TextSecondary,
                fontSize = 12.5.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
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
                        subtitle = "novih XP"
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
