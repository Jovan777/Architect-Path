package com.example.pmuprojekat.ui.admin

import android.net.Uri
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.data.repository.AdminDiagramImageContent
import com.example.pmuprojekat.data.repository.AdminModerationAction
import com.example.pmuprojekat.data.repository.AdminSubmissionFilter
import com.example.pmuprojekat.data.repository.AdminSubmissionStatus
import com.example.pmuprojekat.data.repository.AdminTaskBlankContent
import com.example.pmuprojekat.data.repository.AdminTaskOptionContent
import com.example.pmuprojekat.data.repository.AdminTaskStepContent
import com.example.pmuprojekat.data.repository.AdminTaskSubmissionDetail
import com.example.pmuprojekat.data.repository.AdminTaskSubmissionSummary
import com.example.pmuprojekat.data.repository.AdminTaskZoneContent
import com.example.pmuprojekat.ui.common.readableOutlinedTextFieldColors
import com.example.pmuprojekat.ui.home.AppPalette
import java.io.File
import java.text.DateFormat
import java.util.Date

@Composable
fun AdminTaskSubmissionsScreen(
    uiState: AdminTaskSubmissionsUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onFilterSelected: (AdminSubmissionFilter) -> Unit,
    onSubmissionClick: (String) -> Unit
) {
    AdminModerationScaffold(
        title = "Korisnički zadaci",
        subtitle = "Pregledaj predloge pre objavljivanja ili promocije.",
        onBack = onBack
    ) {
        item {
            SubmissionFilterRow(
                selected = uiState.selectedFilter,
                onSelected = onFilterSelected
            )
        }

        when {
            uiState.isListLoading -> item { ModerationLoadingCard() }
            uiState.listError != null -> item {
                ModerationMessageCard(
                    message = uiState.listError,
                    actionLabel = "Pokušaj ponovo",
                    onAction = onRetry
                )
            }
            uiState.filteredSubmissions.isEmpty() -> item {
                ModerationMessageCard(
                    message = emptyMessageFor(uiState.selectedFilter)
                )
            }
            else -> items(
                items = uiState.filteredSubmissions,
                key = AdminTaskSubmissionSummary::submissionId
            ) { submission ->
                SubmissionSummaryCard(
                    submission = submission,
                    onClick = { onSubmissionClick(submission.submissionId) }
                )
            }
        }
    }
}

@Composable
fun AdminTaskSubmissionDetailScreen(
    uiState: AdminTaskSubmissionsUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onPreview: () -> Unit,
    onTestSolve: () -> Unit,
    onRequestAction: (AdminModerationAction) -> Unit,
    onDismissConfirmation: () -> Unit,
    onConfirmAction: () -> Unit,
    onRejectionReasonChange: (String) -> Unit
) {
    val detail = uiState.detail

    AdminModerationScaffold(
        title = "Pregled predloga",
        subtitle = "Proveri kompletan sadržaj i način rada zadatka.",
        onBack = onBack
    ) {
        when {
            uiState.isDetailLoading -> item { ModerationLoadingCard() }
            uiState.detailError != null -> item {
                ModerationMessageCard(
                    message = uiState.detailError,
                    actionLabel = "Pokušaj ponovo",
                    onAction = onRetry
                )
            }
            detail == null -> item {
                ModerationMessageCard("Predlog zadatka nije dostupan.")
            }
            else -> {
                item { SubmissionOverviewCard(detail) }

                detail.portableImageWarning?.let { warning ->
                    item { ModerationWarningCard(warning) }
                }
                detail.contentError?.let { error ->
                    item { ModerationErrorCard(error) }
                }
                uiState.actionMessage?.let { message ->
                    item { ModerationSuccessCard(message) }
                }
                uiState.actionError?.let { error ->
                    item { ModerationErrorCard(error) }
                }

                detail.question?.let { question ->
                    item {
                        AdminContentSection(
                            title = "Scenario / opis sistema",
                            text = question.prompt.ifBlank { "Scenario nije unet." }
                        )
                    }

                    question.diagramImage?.let { image ->
                        item { AdminDiagramSection(image) }
                    }

                    if (!question.aiFollowUp.isNullOrBlank()) {
                        item {
                            AdminContentSection(
                                title = "AI follow-up pitanje",
                                text = question.aiFollowUp
                            )
                        }
                    }

                    if (question.drawingChecklist.isNotEmpty()) {
                        item {
                            AdminStringListSection(
                                title = "Checklista za crtež",
                                values = question.drawingChecklist
                            )
                        }
                    }

                    if (!question.internalAiRubric.isNullOrBlank()) {
                        item {
                            AdminContentSection(
                                title = "Interna smernica / rubrika",
                                text = question.internalAiRubric
                            )
                        }
                    }

                    item { ModerationSectionTitle("Koraci zadatka") }
                    items(
                        items = question.steps,
                        key = AdminTaskStepContent::stepId
                    ) { step ->
                        AdminTaskStepCard(step)
                    }
                }

                item {
                    PreviewActions(
                        enabled = detail.playableQuestion != null && !uiState.isActionRunning,
                        onPreview = onPreview,
                        onTestSolve = onTestSolve
                    )
                }

                item {
                    ModerationActions(
                        detail = detail,
                        isRunning = uiState.isActionRunning,
                        runningAction = uiState.runningAction,
                        onRequestAction = onRequestAction
                    )
                }
            }
        }
    }

    uiState.confirmationAction?.let { action ->
        ModerationConfirmationDialog(
            action = action,
            detail = detail,
            rejectionReason = uiState.rejectionReason,
            onRejectionReasonChange = onRejectionReasonChange,
            onDismiss = onDismissConfirmation,
            onConfirm = onConfirmAction
        )
    }
}

@Composable
fun AdminTaskPreviewLoadingScreen(onBack: () -> Unit) {
    AdminModerationScaffold(
        title = "Pregled zadatka",
        subtitle = "Pripremamo stvarni prikaz zadatka.",
        onBack = onBack
    ) {
        item { ModerationLoadingCard() }
    }
}

@Composable
private fun SubmissionFilterRow(
    selected: AdminSubmissionFilter,
    onSelected: (AdminSubmissionFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AdminSubmissionFilter.entries.forEach { filter ->
            FilterChip(
                selected = selected == filter,
                onClick = { onSelected(filter) },
                label = {
                    Text(
                        text = filter.label,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AppPalette.Navy,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = AppPalette.TextPrimary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selected == filter,
                    borderColor = AppPalette.Border,
                    selectedBorderColor = AppPalette.Navy
                )
            )
        }
    }
}

@Composable
private fun SubmissionSummaryCard(
    submission: AdminTaskSubmissionSummary,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = submission.title,
                    modifier = Modifier.weight(1f),
                    color = AppPalette.TextPrimary,
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                SubmissionStatusBadge(submission.moderationStatus)
            }

            Text(
                text = "${levelName(submission.level)} • ${taskTypeName(submission.taskType)}",
                color = AppPalette.Blue,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Predložio: ${submission.submitterDisplayName}",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp
            )
            Text(
                text = "Poslato: ${formatDateTime(submission.createdAt)}",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun SubmissionOverviewCard(detail: AdminTaskSubmissionDetail) {
    val summary = detail.summary
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = summary.title,
                    modifier = Modifier.weight(1f),
                    color = AppPalette.TextPrimary,
                    fontSize = 20.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                SubmissionStatusBadge(summary.moderationStatus)
            }
            AdminMetadataRow("Nivo", levelName(summary.level))
            AdminMetadataRow("Tip", taskTypeName(summary.taskType))
            AdminMetadataRow("Šablon", summary.templateId ?: "Nije naveden")
            AdminMetadataRow("Autor", summary.submitterDisplayName)
            AdminMetadataRow("Poslato", formatDateTime(summary.createdAt))
            summary.promotedToRemoteTaskId?.let {
                AdminMetadataRow("Zvanični zadatak", it)
            }
        }
    }
}

@Composable
private fun AdminTaskStepCard(step: AdminTaskStepContent) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = step.title,
                color = AppPalette.TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = stepTypeName(step.type),
                color = AppPalette.Blue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            if (step.instruction.isNotBlank()) {
                AdminInlineBlock("Uputstvo", step.instruction)
            }
            if (!step.correctAnswerMode.isNullOrBlank()) {
                AdminMetadataRow(
                    "Broj tačnih odgovora",
                    if (step.correctAnswerMode == "SINGLE") {
                        "Samo jedan tačan odgovor"
                    } else {
                        "Više tačnih odgovora"
                    }
                )
            }
            step.requiredCount?.takeIf { it > 0 }?.let {
                AdminMetadataRow("Obavezan broj izbora", it.toString())
            }
            if (!step.codeBlock.isNullOrBlank()) {
                AdminInlineBlock("Pseudo-kod / dodatni sadržaj", step.codeBlock)
            }
            if (step.zones.isNotEmpty()) {
                StepSubsectionTitle("Kategorije / zone")
                step.zones.forEach { zone ->
                    AdminZoneRow(zone)
                }
            }
            if (step.options.isNotEmpty()) {
                StepSubsectionTitle("Opcije / kartice")
                step.options.forEach { option ->
                    AdminOptionRow(
                        option = option,
                        correctZoneTitle = step.zones
                            .firstOrNull { it.zoneId == option.correctZoneId }
                            ?.title
                    )
                }
            }
            if (step.correctOptionIds.isNotEmpty()) {
                val correctAnswers = step.correctOptionIds.mapNotNull { correctId ->
                    step.options.firstOrNull { it.optionId == correctId }
                }.map { option ->
                    option.label
                        ?.takeIf(String::isNotBlank)
                        ?.let { "$it. ${option.text}" }
                        ?: option.text
                }
                AdminMetadataRow(
                    "Tačni odgovori",
                    correctAnswers
                        .takeIf { it.isNotEmpty() }
                        ?.joinToString()
                        ?: "Označeni su uz opcije."
                )
            }
            if (step.blanks.isNotEmpty()) {
                StepSubsectionTitle("Prazna mesta")
                step.blanks.forEach { blank ->
                    AdminBlankRow(blank)
                }
            }
            if (!step.explanation.isNullOrBlank()) {
                AdminInlineBlock("Feedback / objašnjenje", step.explanation)
            }
            if (step.rubricPoints.isNotEmpty()) {
                StepSubsectionTitle("Očekivano razmišljanje / rubrika")
                step.rubricPoints.forEachIndexed { index, point ->
                    Text(
                        text = "${index + 1}. $point",
                        color = AppPalette.TextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminZoneRow(zone: AdminTaskZoneContent) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = zone.order?.let { "$it." }.orEmpty(),
            color = AppPalette.Blue,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = zone.title,
            color = AppPalette.TextPrimary,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun AdminOptionRow(
    option: AdminTaskOptionContent,
    correctZoneTitle: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (option.isCorrect) {
                    Color(0xFFECFDF5)
                } else {
                    Color(0xFFF8FAFC)
                },
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = option.label?.let { "$it." }.orEmpty(),
                color = AppPalette.Blue,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = option.text,
                modifier = Modifier.weight(1f),
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
            if (option.isCorrect) {
                Text(
                    text = "TAČNO",
                    color = Color(0xFF047857),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            } else if (option.isDistractor) {
                Text(
                    text = "DISTRAKTOR",
                    color = Color(0xFFB45309),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        option.correctOrder?.let {
            Text(
                text = "Tačan redosled: $it",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )
        }
        option.correctZoneId?.let {
            Text(
                text = "Tačna zona: ${correctZoneTitle ?: "Nije pronađena"}",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp
            )
        }
        option.metadata?.let {
            Text(
                text = "Dodatni podatak: $it",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
private fun AdminBlankRow(blank: AdminTaskBlankContent) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = blank.order?.let { "Prazno mesto $it" } ?: "Prazno mesto",
            color = AppPalette.TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Prikaz: ${blank.placeholder}",
            color = AppPalette.TextPrimary,
            fontSize = 13.sp
        )
        Text(
            text = "Tačan unos: ${blank.correctValue}",
            color = Color(0xFF047857),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AdminDiagramSection(image: AdminDiagramImageContent) {
    val context = LocalContext.current
    val model: Any? = image.downloadUrl
        ?.takeIf(String::isNotBlank)
        ?: image.localUri
            ?.takeIf(String::isNotBlank)
            ?.let(Uri::parse)
        ?: image.localPath
            ?.takeIf(String::isNotBlank)
            ?.let(::File)
            ?.takeIf(File::isFile)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Dijagram / slika",
                color = AppPalette.TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold
            )
            if (model != null) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(model)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Dijagram korisničkog zadatka",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 180.dp, max = 360.dp),
                    contentScale = ContentScale.Fit,
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(28.dp),
                                color = AppPalette.Blue,
                                strokeWidth = 2.dp
                            )
                        }
                    },
                    success = { SubcomposeAsyncImageContent() },
                    error = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Slika dijagrama nije dostupna.",
                                color = AppPalette.TextSecondary,
                                fontSize = 13.sp
                            )
                        }
                    }
                )
            }
            AdminMetadataRow(
                "Naziv fajla",
                image.originalFileName ?: image.localFileName ?: "Nije naveden"
            )
            AdminMetadataRow("MIME tip", image.mimeType ?: "Nije naveden")
            image.sizeBytes?.let {
                AdminMetadataRow("Veličina", formatBytes(it))
            }
            AdminMetadataRow(
                "Udaljena slika",
                if (image.hasPortableSource) "Dostupna" else "Nije dostupna"
            )
            image.remoteStoragePath?.let {
                AdminMetadataRow("Storage putanja", it)
            }
        }
    }
}

@Composable
private fun PreviewActions(
    enabled: Boolean,
    onPreview: () -> Unit,
    onTestSolve: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ModerationSectionTitle("Provera zadatka")
        OutlinedButton(
            onClick = onPreview,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, AppPalette.Blue)
        ) {
            Text(
                text = "Pregledaj zadatak",
                color = AppPalette.Blue,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = onTestSolve,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppPalette.Navy)
        ) {
            Text(
                text = "Probno reši zadatak",
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "Probni režim ne menja napredak, XP, rang-listu niti istoriju pokušaja.",
            color = AppPalette.TextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp
        )
    }
}

@Composable
private fun ModerationActions(
    detail: AdminTaskSubmissionDetail,
    isRunning: Boolean,
    runningAction: AdminModerationAction?,
    onRequestAction: (AdminModerationAction) -> Unit
) {
    val isPromoted = !detail.summary.promotedToRemoteTaskId.isNullOrBlank()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ModerationSectionTitle("Moderacija")
        Button(
            onClick = { onRequestAction(AdminModerationAction.APPROVE) },
            enabled = !isRunning && !isPromoted,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF047857),
                contentColor = Color.White
            )
        ) {
            ActionButtonContent(
                loading = runningAction == AdminModerationAction.APPROVE,
                label = "Odobri kao korisnički zadatak"
            )
        }
        Button(
            onClick = { onRequestAction(AdminModerationAction.PROMOTE) },
            enabled = !isRunning &&
                (!detail.requiresPortableImage || detail.hasPortableImage),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppPalette.Navy,
                contentColor = Color.White
            )
        ) {
            ActionButtonContent(
                loading = runningAction == AdminModerationAction.PROMOTE,
                label = if (isPromoted) {
                    "Proveri postojeću promociju"
                } else {
                    "Promoviši u zvanične zadatke"
                }
            )
        }
        OutlinedButton(
            onClick = { onRequestAction(AdminModerationAction.REJECT) },
            enabled = !isRunning && !isPromoted,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, Color(0xFFBE123C))
        ) {
            ActionButtonContent(
                loading = runningAction == AdminModerationAction.REJECT,
                label = "Odbij zadatak",
                color = Color(0xFFBE123C)
            )
        }
    }
}

@Composable
private fun ActionButtonContent(
    loading: Boolean,
    label: String,
    color: Color = Color.White
) {
    if (loading) {
        CircularProgressIndicator(
            modifier = Modifier.size(18.dp),
            color = color,
            strokeWidth = 2.dp
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
    Text(
        text = label,
        color = color,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ModerationConfirmationDialog(
    action: AdminModerationAction,
    detail: AdminTaskSubmissionDetail?,
    rejectionReason: String,
    onRejectionReasonChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val title = when (action) {
        AdminModerationAction.APPROVE -> "Odobri korisnički zadatak?"
        AdminModerationAction.PROMOTE -> "Promoviši u zvanične zadatke?"
        AdminModerationAction.REJECT -> "Odbij zadatak?"
    }
    val message = when (action) {
        AdminModerationAction.APPROVE ->
            "Zadatak će postati javno dostupan u odeljku „Korisnički zadaci“."
        AdminModerationAction.PROMOTE ->
            "Biće napravljena zvanična kopija u „Novi zadaci“, dok originalni predlog neće biti javno dupliran."
        AdminModerationAction.REJECT ->
            "Predlog ostaje sačuvan u evidenciji, ali neće biti vidljiv korisnicima."
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = message,
                    color = AppPalette.TextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                val portableImageWarning = detail?.portableImageWarning
                if (action == AdminModerationAction.APPROVE && portableImageWarning != null) {
                    Text(
                        text = portableImageWarning,
                        color = Color(0xFFB45309),
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (action == AdminModerationAction.REJECT) {
                    OutlinedTextField(
                        value = rejectionReason,
                        onValueChange = onRejectionReasonChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Napomena (opciono)") },
                        minLines = 2,
                        maxLines = 4,
                        colors = readableOutlinedTextFieldColors()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (action) {
                        AdminModerationAction.REJECT -> Color(0xFFBE123C)
                        AdminModerationAction.APPROVE -> Color(0xFF047857)
                        AdminModerationAction.PROMOTE -> AppPalette.Navy
                    }
                )
            ) {
                Text(
                    text = when (action) {
                        AdminModerationAction.APPROVE -> "Odobri"
                        AdminModerationAction.PROMOTE -> "Promoviši"
                        AdminModerationAction.REJECT -> "Odbij"
                    }
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Otkaži")
            }
        }
    )
}

@Composable
private fun SubmissionStatusBadge(status: AdminSubmissionStatus) {
    val (label, colors) = when (status) {
        AdminSubmissionStatus.PENDING ->
            "NA ČEKANJU" to (Color(0xFFFFF7ED) to Color(0xFFB45309))
        AdminSubmissionStatus.APPROVED ->
            "ODOBREN" to (Color(0xFFECFDF5) to Color(0xFF047857))
        AdminSubmissionStatus.PROMOTED ->
            "PROMOVISAN" to (Color(0xFFEFF6FF) to Color(0xFF1D4ED8))
        AdminSubmissionStatus.REJECTED ->
            "ODBIJEN" to (Color(0xFFFFF1F2) to Color(0xFFBE123C))
        AdminSubmissionStatus.UNKNOWN ->
            "NEPOZNAT" to (Color(0xFFF1F5F9) to AppPalette.TextSecondary)
    }
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = colors.first
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            color = colors.second,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun AdminContentSection(title: String, text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = AppPalette.Blue,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = text,
                color = AppPalette.TextPrimary,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
        }
    }
}

@Composable
private fun AdminStringListSection(title: String, values: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                text = title,
                color = AppPalette.Blue,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
            values.forEachIndexed { index, value ->
                Text(
                    text = "${index + 1}. $value",
                    color = AppPalette.TextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun AdminInlineBlock(label: String, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            color = AppPalette.TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = text,
            color = AppPalette.TextPrimary,
            fontSize = 13.sp,
            lineHeight = 19.sp
        )
    }
}

@Composable
private fun AdminMetadataRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(0.42f),
            color = AppPalette.TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            modifier = Modifier.weight(0.58f),
            color = AppPalette.TextPrimary,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun StepSubsectionTitle(title: String) {
    HorizontalDivider(color = AppPalette.Border)
    Text(
        text = title,
        color = AppPalette.TextPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
private fun ModerationSectionTitle(title: String) {
    Text(
        text = title,
        color = AppPalette.TextPrimary,
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
private fun ModerationLoadingCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = AppPalette.Blue)
    }
}

@Composable
private fun ModerationMessageCard(
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
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                color = AppPalette.TextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction) {
                    Text(actionLabel)
                }
            }
        }
    }
}

@Composable
private fun ModerationWarningCard(message: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFFFF7ED),
        border = BorderStroke(1.dp, Color(0xFFFDBA74))
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(14.dp),
            color = Color(0xFF9A3412),
            fontSize = 13.sp,
            lineHeight = 19.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ModerationErrorCard(message: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFFFF1F2),
        border = BorderStroke(1.dp, Color(0xFFFDA4AF))
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(14.dp),
            color = Color(0xFFBE123C),
            fontSize = 13.sp,
            lineHeight = 19.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ModerationSuccessCard(message: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFECFDF5),
        border = BorderStroke(1.dp, Color(0xFF6EE7B7))
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(14.dp),
            color = Color(0xFF047857),
            fontSize = 13.sp,
            lineHeight = 19.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AdminModerationScaffold(
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
                .padding(innerPadding)
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                ),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                ModerationHeader(
                    title = title,
                    subtitle = subtitle,
                    onBack = onBack
                )
            }
            content()
        }
    }
}

@Composable
private fun ModerationHeader(
    title: String,
    subtitle: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
                fontSize = 27.sp,
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

private fun emptyMessageFor(filter: AdminSubmissionFilter): String {
    return when (filter) {
        AdminSubmissionFilter.PENDING -> "Nema zadataka koji čekaju pregled."
        AdminSubmissionFilter.APPROVED -> "Još nema odobrenih korisničkih zadataka."
        AdminSubmissionFilter.PROMOTED -> "Još nema promovisanih zadataka."
        AdminSubmissionFilter.REJECTED -> "Još nema odbijenih zadataka."
        AdminSubmissionFilter.ALL -> "Još nema korisničkih predloga."
    }
}

private fun levelName(value: String): String {
    return LearningLevel.entries.firstOrNull {
        it.id.equals(value, ignoreCase = true) ||
            it.name.equals(value, ignoreCase = true)
    }?.displayName ?: value.ifBlank { "Nivo nije naveden" }
}

private fun taskTypeName(value: String): String {
    return QuestionType.entries.firstOrNull {
        it.id.equals(value, ignoreCase = true) ||
            it.name.equals(value, ignoreCase = true)
    }?.displayName ?: value.ifBlank { "Tip nije naveden" }
}

private fun stepTypeName(value: String): String {
    return when (value) {
        "single_choice" -> "Jedan tačan odgovor"
        "multi_choice" -> "Više tačnih odgovora"
        "visual_mapping" -> "Vizuelni izbor"
        "ordered_cards" -> "Poređivanje kartica"
        "categorization" -> "Razvrstavanje u zone"
        "role_mapping" -> "Mapiranje uloga"
        "code_completion" -> "Dopuna pseudo-koda"
        "hotspot" -> "Označavanje problema"
        "free_text" -> "Slobodan odgovor"
        "mini_adr" -> "Mini ADR"
        else -> value.ifBlank { "Tip koraka nije naveden" }
    }
}

private fun formatDateTime(timestamp: Long?): String {
    if (timestamp == null || timestamp <= 0L) return "Nije dostupno"
    return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
        .format(Date(timestamp))
}

private fun formatBytes(bytes: Long): String {
    return when {
        bytes >= 1024L * 1024L -> "%.1f MB".format(bytes / (1024f * 1024f))
        bytes >= 1024L -> "%.1f KB".format(bytes / 1024f)
        else -> "$bytes B"
    }
}
