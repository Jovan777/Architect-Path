package com.example.pmuprojekat.ui.taskcreation

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.taskcreation.TaskCreationCorrectAnswerMode
import com.example.pmuprojekat.taskcreation.TaskCreationStepBlueprint
import com.example.pmuprojekat.taskcreation.TaskCreationTemplateDefinition
import com.example.pmuprojekat.taskcreation.TaskCreationTemplateRegistry
import com.example.pmuprojekat.ui.common.readableOutlinedTextFieldColors
import com.example.pmuprojekat.ui.home.AppPalette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TaskCreationScreen(
    uiState: TaskCreationUiState,
    viewModel: TaskCreationViewModel,
    onExit: () -> Unit
) {
    BackHandler {
        if (!viewModel.goBack()) onExit()
    }

    Scaffold(containerColor = AppPalette.Background) { innerPadding ->
        Column(
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
                )
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(top = 12.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TaskCreationTopBar(
                title = when (uiState.step) {
                    TaskCreationFlowStep.LEVEL -> "Dodaj svoj zadatak"
                    TaskCreationFlowStep.TEMPLATE -> "Izaberi šablon"
                    TaskCreationFlowStep.FORM -> uiState.selectedTemplate?.displayName ?: "Popuni zadatak"
                    TaskCreationFlowStep.PREVIEW -> "Pregled predloga"
                    TaskCreationFlowStep.SUCCESS -> "Predlog je sačuvan"
                },
                subtitle = when (uiState.step) {
                    TaskCreationFlowStep.LEVEL -> "Izaberi nivo, zatim popuni šablon zadatka koji odgovara vežbama u aplikaciji."
                    TaskCreationFlowStep.TEMPLATE -> "Izaberi tip zadatka koji želiš da predložiš za pregled."
                    TaskCreationFlowStep.FORM -> "Popuni sve podatke potrebne da zadatak kasnije može postati igriv."
                    TaskCreationFlowStep.PREVIEW -> "Proveri predlog pre slanja na pregled."
                    TaskCreationFlowStep.SUCCESS -> "Status slanja i tvoji sačuvani predlozi."
                },
                onBack = {
                    if (!viewModel.goBack()) onExit()
                }
            )

            when (uiState.step) {
                TaskCreationFlowStep.LEVEL -> TaskCreationLevelStep(viewModel)
                TaskCreationFlowStep.TEMPLATE -> TaskCreationTemplateStep(uiState.selectedLevelId, viewModel)
                TaskCreationFlowStep.FORM -> TaskCreationFormStep(uiState, viewModel)
                TaskCreationFlowStep.PREVIEW -> TaskCreationPreviewStep(uiState, viewModel)
                TaskCreationFlowStep.SUCCESS -> TaskCreationSuccessStep(uiState, viewModel, onExit)
            }
        }
    }
}

@Composable
private fun TaskCreationTopBar(
    title: String,
    subtitle: String,
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
                Text("<", color = AppPalette.TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
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
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun TaskCreationLevelStep(viewModel: TaskCreationViewModel) {
    TaskCreationInfoPanel(
        title = "Šabloni umesto praznog lista",
        body = "Svaki predlog se pravi po poznatom tipu vežbe, tako da kasnije može da se pregleda i objavi bez pogađanja strukture."
    )
    TaskCreationTemplateRegistry.levels.forEach { level ->
        TaskCreationLevelCard(level = level, onClick = { viewModel.selectLevel(level.levelId) })
    }
}

@Composable
private fun TaskCreationTemplateStep(
    levelId: String?,
    viewModel: TaskCreationViewModel
) {
    val templates = remember(levelId) {
        levelId?.let(TaskCreationTemplateRegistry::templatesForLevel).orEmpty()
    }
    val level = levelId?.let(TaskCreationTemplateRegistry::levelById)

    TaskCreationInfoPanel(
        title = level?.title ?: "Nivo",
        body = "Izaberi šablon koji najbolje odgovara zadatku koji želiš da napišeš."
    )
    templates.forEach { template ->
        TaskCreationTemplateCard(
            template = template,
            level = level,
            onClick = { viewModel.selectTemplate(template.templateId) }
        )
    }
}

@Composable
private fun TaskCreationFormStep(
    uiState: TaskCreationUiState,
    viewModel: TaskCreationViewModel
) {
    val template = uiState.selectedTemplate ?: return
    val draft = uiState.draft

    ValidationErrors(uiState.validationErrors)

    TaskCreationCard {
        SectionTitle("Osnovni podaci")
        FormTextField("Naslov zadatka", draft.title, viewModel::updateTitle)
        ScenarioTextFieldWithTxtImport(
            label = "Scenario / opis sistema",
            value = draft.scenario,
            onValueChange = viewModel::updateScenario
        )

        if (template.requiresDiagramReference) {
            DiagramImagePickerField(
                attachment = draft.diagramImage,
                onImageSelected = viewModel::updateDiagramImage,
                onRemoveImage = viewModel::removeDiagramImage
            )
        }

        if (template.requiresSketchChecklist) {
            ChecklistEditor(
                title = "Na crtežu označi",
                items = draft.checklistItems,
                onUpdate = viewModel::updateChecklistItem,
                onAdd = viewModel::addChecklistItem,
                onRemoveLast = { viewModel.removeChecklistItem(draft.checklistItems.lastIndex) },
                canRemove = draft.checklistItems.size > 2
            )
        }

        if (template.requiresInternalRubric) {
            FormTextField(
                label = "Smernica za AI analizu / rubrika",
                value = draft.internalRubric,
                onValueChange = viewModel::updateInternalRubric,
                minLines = 4
            )
        }
    }

    draft.steps.forEachIndexed { index, step ->
        StepEditorCard(
            template = template,
            stepIndex = index,
            step = step,
            viewModel = viewModel
        )
    }

    if (template.allowAdditionalSteps) {
        TaskCreationCard {
            Text(
                text = "Ovaj šablon podržava dodavanje više sličnih koraka.",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            AddRemoveRow(
                addLabel = "Dodaj korak",
                removeLabel = "Ukloni poslednji",
                canRemove = draft.steps.size > template.minStepCount,
                onAdd = viewModel::addRepeatableStep,
                onRemove = viewModel::removeLastStep
            )
        }
    }

    TaskCreationCard {
        FormTextField(
            label = "AI follow-up pitanje (opciono)",
            value = draft.aiFollowUp,
            onValueChange = viewModel::updateAiFollowUp,
            minLines = 2
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = viewModel::showPreview,
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppPalette.Blue)
        ) {
            Text("Pregledaj zadatak", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun StepEditorCard(
    template: TaskCreationTemplateDefinition,
    stepIndex: Int,
    step: TaskCreationStepDraft,
    viewModel: TaskCreationViewModel
) {
    val blueprint = TaskCreationTemplateRegistry.blueprintForStep(template, step.blueprintKey)

    TaskCreationCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            StepNumberBadge(stepIndex + 1)
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = step.title.ifBlank { "Korak ${stepIndex + 1}" },
                    color = AppPalette.TextPrimary,
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = blueprint?.rendererHint?.label ?: "Standardni korak",
                    color = AppPalette.TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        FormTextField(
            label = "Naslov koraka",
            value = step.title,
            onValueChange = { viewModel.updateStepTitle(stepIndex, it) }
        )
        FormTextField(
            label = "Instrukcija za korisnika",
            value = step.instruction,
            onValueChange = { viewModel.updateStepInstruction(stepIndex, it) },
            minLines = 2
        )

        when (step.type) {
            StepType.SINGLE_CHOICE.id,
            StepType.MULTI_CHOICE.id,
            StepType.VISUAL_MAPPING.id,
            StepType.HOTSPOT.id -> ChoiceStepEditor(stepIndex, step, blueprint, viewModel)

            StepType.ORDERED_CARDS.id -> OrderedStepEditor(stepIndex, step, blueprint, viewModel)
            StepType.CATEGORIZATION.id,
            StepType.ROLE_MAPPING.id -> MappingStepEditor(stepIndex, step, blueprint, viewModel)

            StepType.CODE_COMPLETION.id -> CodeCompletionStepEditor(stepIndex, step, viewModel)
            StepType.FREE_TEXT.id,
            StepType.MINI_ADR.id -> FreeTextStepEditor(stepIndex, step, viewModel)
        }
    }
}

@Composable
private fun CorrectAnswerModeSelector(
    mode: TaskCreationCorrectAnswerMode,
    onModeChange: (TaskCreationCorrectAnswerMode) -> Unit
) {
    SectionTitle("Broj tačnih odgovora")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryChip(
            label = "Samo jedan tačan odgovor",
            selected = mode == TaskCreationCorrectAnswerMode.SINGLE,
            onClick = { onModeChange(TaskCreationCorrectAnswerMode.SINGLE) }
        )
        CategoryChip(
            label = "Više tačnih odgovora",
            selected = mode == TaskCreationCorrectAnswerMode.MULTIPLE,
            onClick = { onModeChange(TaskCreationCorrectAnswerMode.MULTIPLE) }
        )
    }
}

@Composable
private fun ChoiceStepEditor(
    stepIndex: Int,
    step: TaskCreationStepDraft,
    blueprint: TaskCreationStepBlueprint?,
    viewModel: TaskCreationViewModel
) {
    val answerMode = step.correctAnswerMode
        ?: blueprint?.defaultCorrectAnswerMode
        ?: TaskCreationCorrectAnswerMode.MULTIPLE

    if (blueprint?.requiresCodeBlock == true || step.codeBlock.isNotBlank()) {
        FormTextField(
            label = "Kod / pseudo-kod za ovaj korak",
            value = step.codeBlock,
            onValueChange = { viewModel.updateStepCodeBlock(stepIndex, it) },
            minLines = 6,
            monospace = true
        )
    }

    CorrectAnswerModeSelector(
        mode = answerMode,
        onModeChange = { viewModel.updateStepCorrectAnswerMode(stepIndex, it) }
    )

    SectionTitle("Opcije odgovora")
    step.options.forEachIndexed { optionIndex, option ->
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFFF8FAFC),
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (blueprint?.usesOptionLabels == true) {
                        OutlinedTextField(
                            modifier = Modifier.width(76.dp),
                            value = option.label,
                            onValueChange = { viewModel.updateOptionLabel(stepIndex, optionIndex, it) },
                            label = { Text("Labela") },
                            singleLine = true,
                            colors = readableOutlinedTextFieldColors()
                        )
                    }
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = option.text,
                        onValueChange = { viewModel.updateOptionText(stepIndex, optionIndex, it) },
                        label = { Text("Tekst opcije ${optionIndex + 1}") },
                        singleLine = false,
                        minLines = 1,
                        colors = readableOutlinedTextFieldColors()
                    )
                }
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ToggleChip(
                        label = when {
                            option.isCorrect && answerMode == TaskCreationCorrectAnswerMode.SINGLE -> "Tačan odgovor"
                            option.isCorrect -> "Ukloni kao tačno"
                            else -> "Označi kao tačno"
                        },
                        selected = option.isCorrect,
                        selectedColor = Color(0xFF16A34A),
                        onClick = { viewModel.toggleCorrectOption(stepIndex, optionIndex) }
                    )
                    if (blueprint?.supportsDistractors == true) {
                        ToggleChip(
                            label = "Distraktor",
                            selected = option.isDistractor,
                            selectedColor = Color(0xFFE11D48),
                            onClick = { viewModel.toggleOptionDistractor(stepIndex, optionIndex) }
                        )
                    }
                }
                FormTextField(
                    label = "Napomena za pregled (opciono)",
                    value = option.metadata,
                    onValueChange = { viewModel.updateOptionMetadata(stepIndex, optionIndex, it) },
                    minLines = 1
                )
            }
        }
    }
    AddRemoveRow(
        addLabel = "Dodaj opciju",
        removeLabel = "Ukloni poslednju",
        canRemove = step.options.size > 2,
        onAdd = { viewModel.addOption(stepIndex) },
        onRemove = { viewModel.removeOption(stepIndex, step.options.lastIndex) }
    )
    FormTextField(
        label = "Feedback / objašnjenje za korak",
        value = step.explanation,
        onValueChange = { viewModel.updateStepExplanation(stepIndex, it) },
        minLines = 3
    )
}

@Composable
private fun OrderedStepEditor(
    stepIndex: Int,
    step: TaskCreationStepDraft,
    blueprint: TaskCreationStepBlueprint?,
    viewModel: TaskCreationViewModel
) {
    SectionTitle("Kartice i tačan redosled")
    Text(
        text = "Kartice koje pripadaju glavnom toku dobijaju redni broj. Zamke označi posebno.",
        color = AppPalette.TextSecondary,
        fontSize = 12.5.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.SemiBold
    )
    step.options.forEachIndexed { optionIndex, option ->
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        modifier = Modifier.width(78.dp),
                        value = option.correctOrder,
                        onValueChange = { viewModel.updateOptionCorrectOrder(stepIndex, optionIndex, it) },
                        label = { Text("Red") },
                        singleLine = true,
                        enabled = !option.isDistractor,
                        colors = readableOutlinedTextFieldColors()
                    )
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = option.text,
                        onValueChange = { viewModel.updateOptionText(stepIndex, optionIndex, it) },
                        label = { Text("Tekst kartice") },
                        singleLine = false,
                        minLines = 1,
                        colors = readableOutlinedTextFieldColors()
                    )
                }
                if (blueprint?.supportsDistractors == true) {
                    ToggleChip(
                        label = "Zamka / izbačena kartica",
                        selected = option.isDistractor,
                        selectedColor = Color(0xFFE11D48),
                        onClick = { viewModel.toggleOptionDistractor(stepIndex, optionIndex) }
                    )
                }
                FormTextField(
                    label = "Napomena za pregled (opciono)",
                    value = option.metadata,
                    onValueChange = { viewModel.updateOptionMetadata(stepIndex, optionIndex, it) }
                )
            }
        }
    }
    AddRemoveRow(
        addLabel = "Dodaj karticu",
        removeLabel = "Ukloni poslednju",
        canRemove = step.options.size > 2,
        onAdd = { viewModel.addOption(stepIndex) },
        onRemove = { viewModel.removeOption(stepIndex, step.options.lastIndex) }
    )
    FormTextField(
        label = "Feedback / objašnjenje za redosled",
        value = step.explanation,
        onValueChange = { viewModel.updateStepExplanation(stepIndex, it) },
        minLines = 3
    )
}

@Composable
private fun MappingStepEditor(
    stepIndex: Int,
    step: TaskCreationStepDraft,
    blueprint: TaskCreationStepBlueprint?,
    viewModel: TaskCreationViewModel
) {
    SectionTitle("Zone / kategorije")
    step.zones.forEachIndexed { zoneIndex, zone ->
        FormTextField(
            label = "Zona ${zoneIndex + 1}",
            value = zone.title,
            onValueChange = { viewModel.updateZoneTitle(stepIndex, zoneIndex, it) }
        )
    }
    AddRemoveRow(
        addLabel = "Dodaj zonu",
        removeLabel = "Ukloni poslednju",
        canRemove = step.zones.size > 2 && blueprint?.defaultZones.isNullOrEmpty(),
        onAdd = { viewModel.addZone(stepIndex) },
        onRemove = { viewModel.removeZone(stepIndex, step.zones.lastIndex) }
    )

    SectionTitle("Stavke za mapiranje")
    step.options.forEachIndexed { optionIndex, option ->
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFFF8FAFC),
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                FormTextField(
                    label = "Stavka ${optionIndex + 1}",
                    value = option.text,
                    onValueChange = { viewModel.updateOptionText(stepIndex, optionIndex, it) },
                    minLines = 2
                )
                if (blueprint?.supportsDistractors == true) {
                    ToggleChip(
                        label = "Distraktor / ne pripada nijednoj zoni",
                        selected = option.isDistractor,
                        selectedColor = Color(0xFFE11D48),
                        onClick = { viewModel.toggleOptionDistractor(stepIndex, optionIndex) }
                    )
                }
                if (!option.isDistractor) {
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        step.zones.forEachIndexed { zoneIndex, zone ->
                            CategoryChip(
                                label = zone.title.ifBlank { "Zona ${zoneIndex + 1}" },
                                selected = option.correctZoneIndex == zoneIndex,
                                onClick = { viewModel.updateOptionCorrectZone(stepIndex, optionIndex, zoneIndex) }
                            )
                        }
                    }
                }
                FormTextField(
                    label = "Napomena za pregled (opciono)",
                    value = option.metadata,
                    onValueChange = { viewModel.updateOptionMetadata(stepIndex, optionIndex, it) }
                )
            }
        }
    }
    AddRemoveRow(
        addLabel = "Dodaj stavku",
        removeLabel = "Ukloni poslednju",
        canRemove = step.options.size > 2,
        onAdd = { viewModel.addOption(stepIndex) },
        onRemove = { viewModel.removeOption(stepIndex, step.options.lastIndex) }
    )
    FormTextField(
        label = "Feedback / objašnjenje za mapiranje",
        value = step.explanation,
        onValueChange = { viewModel.updateStepExplanation(stepIndex, it) },
        minLines = 3
    )
}

@Composable
private fun CodeCompletionStepEditor(
    stepIndex: Int,
    step: TaskCreationStepDraft,
    viewModel: TaskCreationViewModel
) {
    FormTextField(
        label = "Pseudo-kod sa placeholderima",
        value = step.codeBlock,
        onValueChange = { viewModel.updateStepCodeBlock(stepIndex, it) },
        minLines = 8,
        monospace = true
    )
    SectionTitle("Prazna mesta")
    step.blanks.forEachIndexed { blankIndex, blank ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = blank.placeholder,
                onValueChange = { viewModel.updateBlankPlaceholder(stepIndex, blankIndex, it) },
                label = { Text("Placeholder") },
                singleLine = true,
                colors = readableOutlinedTextFieldColors()
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = blank.expectedAnswer,
                onValueChange = { viewModel.updateBlankExpectedAnswer(stepIndex, blankIndex, it) },
                label = { Text("Tačna dopuna") },
                singleLine = true,
                colors = readableOutlinedTextFieldColors()
            )
        }
    }
    AddRemoveRow(
        addLabel = "Dodaj prazno mesto",
        removeLabel = "Ukloni poslednje",
        canRemove = step.blanks.size > 1,
        onAdd = { viewModel.addBlank(stepIndex) },
        onRemove = { viewModel.removeBlank(stepIndex, step.blanks.lastIndex) }
    )
    FormTextField(
        label = "Feedback / objašnjenje",
        value = step.explanation,
        onValueChange = { viewModel.updateStepExplanation(stepIndex, it) },
        minLines = 3
    )
}

@Composable
private fun FreeTextStepEditor(
    stepIndex: Int,
    step: TaskCreationStepDraft,
    viewModel: TaskCreationViewModel
) {
    SectionTitle("Rubrika za pregled odgovora")
    step.rubricPoints.forEachIndexed { pointIndex, point ->
        FormTextField(
            label = "Očekivana tačka ${pointIndex + 1}",
            value = point,
            onValueChange = { viewModel.updateRubricPoint(stepIndex, pointIndex, it) },
            minLines = 2
        )
    }
    AddRemoveRow(
        addLabel = "Dodaj tačku",
        removeLabel = "Ukloni poslednju",
        canRemove = step.rubricPoints.size > 1,
        onAdd = { viewModel.addRubricPoint(stepIndex) },
        onRemove = { viewModel.removeRubricPoint(stepIndex, step.rubricPoints.lastIndex) }
    )
}

@Composable
private fun TaskCreationPreviewStep(
    uiState: TaskCreationUiState,
    viewModel: TaskCreationViewModel
) {
    val template = uiState.selectedTemplate ?: return
    val level = uiState.selectedLevelId?.let(TaskCreationTemplateRegistry::levelById)
    val draft = uiState.draft

    uiState.submissionErrorMessage?.let { message ->
        ImportStatusMessage(message = message, isError = true)
    }

    TaskCreationCard {
        PreviewLine("Nivo", level?.title ?: uiState.selectedLevelId.orEmpty())
        PreviewLine("Tip", "${template.questionIdPattern} • ${template.displayName}")
        PreviewLine("Naslov", draft.title)
        PreviewBlock("Scenario", draft.scenario)
        draft.diagramImage?.let { PreviewDiagramImage(it) }
        if (draft.checklistItems.any { it.isNotBlank() }) PreviewList("Checklista", draft.checklistItems.filter { it.isNotBlank() })
        if (draft.internalRubric.isNotBlank()) PreviewBlock("Smernica za AI analizu", draft.internalRubric)

        draft.steps.forEachIndexed { index, step ->
            PreviewStep(index, template, step)
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFFFFFBEB),
            border = BorderStroke(1.dp, Color(0xFFFDE68A))
        ) {
            Text(
                modifier = Modifier.padding(14.dp),
                text = "Ovaj predlog čuva sve podatke potrebne za budući pregled i ne objavljuje se automatski u regularnoj listi.",
                color = Color(0xFF92400E),
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                onClick = viewModel::editFromPreview,
                shape = RoundedCornerShape(18.dp)
            ) {
                Text("Izmeni", fontWeight = FontWeight.ExtraBold)
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                onClick = viewModel::submitForReview,
                enabled = !uiState.isSubmitting,
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppPalette.Blue)
            ) {
                if (uiState.isSubmitting) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp, color = Color.White)
                } else {
                    Text("Pošalji na pregled", fontWeight = FontWeight.ExtraBold)
                }
            }
        }
    }
}

@Composable
private fun PreviewStep(
    index: Int,
    template: TaskCreationTemplateDefinition,
    step: TaskCreationStepDraft
) {
    val blueprint = TaskCreationTemplateRegistry.blueprintForStep(template, step.blueprintKey)
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Korak ${index + 1}: ${step.title}",
                color = AppPalette.TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = blueprint?.rendererHint?.label ?: "Standardni korak",
                color = AppPalette.Blue,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
            )
            PreviewBlock("Instrukcija", step.instruction)
            if (step.codeBlock.isNotBlank()) PreviewBlock("Kod", step.codeBlock)
            if (step.zones.any { it.title.isNotBlank() }) PreviewList("Zone", step.zones.map { it.title }.filter { it.isNotBlank() })
            if (step.options.any { it.text.isNotBlank() }) {
                step.correctAnswerMode?.let { mode ->
                    PreviewLine(
                        "Broj tačnih odgovora",
                        if (mode == TaskCreationCorrectAnswerMode.SINGLE) {
                            "Samo jedan tačan odgovor"
                        } else {
                            "Više tačnih odgovora"
                        }
                    )
                }
                PreviewList(
                    "Opcije/stavke",
                    step.options.filter { it.text.isNotBlank() }.map { option ->
                        val flags = buildList {
                            if (option.isCorrect) add("tačno")
                            if (option.correctOrder.isNotBlank()) add("red ${option.correctOrder}")
                            if (step.zones.isNotEmpty() && !option.isDistractor) {
                                add("zona ${step.zones.getOrNull(option.correctZoneIndex)?.title.orEmpty()}")
                            }
                            if (option.isDistractor) add("distraktor")
                        }.joinToString(", ")
                        "${option.label.ifBlank { "-" }} ${option.text}${if (flags.isNotBlank()) " ($flags)" else ""}"
                    }
                )
            }
            if (step.blanks.any { it.placeholder.isNotBlank() || it.expectedAnswer.isNotBlank() }) {
                PreviewList("Prazna mesta", step.blanks.map { "${it.placeholder} -> ${it.expectedAnswer}" })
            }
            if (step.rubricPoints.any { it.isNotBlank() }) {
                PreviewList("Rubrika", step.rubricPoints.filter { it.isNotBlank() })
            }
            if (step.explanation.isNotBlank()) PreviewBlock("Feedback", step.explanation)
        }
    }
}

@Composable
private fun TaskCreationSuccessStep(
    uiState: TaskCreationUiState,
    viewModel: TaskCreationViewModel,
    onExit: () -> Unit
) {
    TaskCreationCard {
        Text(
            text = uiState.successMessage ?: "Zadatak je pripremljen za slanje na pregled.",
            color = Color(0xFF047857),
            fontSize = 16.sp,
            lineHeight = 23.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "Predlog ostaje odvojen od regularnih zadataka dok ga administrator ne odobri i objavi.",
            color = AppPalette.TextSecondary,
            fontSize = 13.sp,
            lineHeight = 19.sp
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = viewModel::startNewFlow,
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppPalette.Blue)
        ) {
            Text("Dodaj još jedan zadatak", fontWeight = FontWeight.ExtraBold)
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = onExit,
            shape = RoundedCornerShape(18.dp)
        ) {
            Text("Nazad na početnu", fontWeight = FontWeight.ExtraBold)
        }
    }

    if (uiState.localSubmissions.isNotEmpty()) {
        TaskCreationCard {
            SectionTitle("Moji lokalni predlozi")
            uiState.localSubmissions.take(5).forEach { submission ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF8FAFC),
                    border = BorderStroke(1.dp, AppPalette.Border)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = submission.title,
                            color = AppPalette.TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "${submission.reviewStatus} • ${submission.syncStatus}",
                            color = AppPalette.TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ScenarioTextFieldWithTxtImport(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var pendingImportedText by remember { mutableStateOf<String?>(null) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var isErrorMessage by remember { mutableStateOf(false) }

    fun showStatus(message: String, isError: Boolean) {
        statusMessage = message
        isErrorMessage = isError
    }

    fun applyImportedText(importedText: String, replace: Boolean) {
        val updatedText = if (replace || value.isBlank()) {
            importedText
        } else {
            value.trimEnd() + "\n\n" + importedText.trimStart()
        }
        onValueChange(updatedText)
        pendingImportedText = null
        showStatus("Tekst je učitan iz fajla.", isError = false)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        scope.launch {
            when (val result = withContext(Dispatchers.IO) { readScenarioTxtFile(context, uri) }) {
                is TxtScenarioImportResult.Success -> {
                    if (value.isBlank()) {
                        applyImportedText(result.text, replace = true)
                    } else {
                        pendingImportedText = result.text
                        statusMessage = null
                    }
                }
                is TxtScenarioImportResult.Error -> showStatus(result.message, isError = true)
            }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FormTextField(
            label = label,
            value = value,
            onValueChange = onValueChange,
            minLines = 5
        )
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { launcher.launch(arrayOf("text/plain")) },
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Učitaj scenario iz .txt fajla", fontWeight = FontWeight.ExtraBold)
        }
        statusMessage?.let { message ->
            ImportStatusMessage(message = message, isError = isErrorMessage)
        }
    }

    pendingImportedText?.let { importedText ->
        AlertDialog(
            onDismissRequest = { pendingImportedText = null },
            title = { Text("Polje već sadrži tekst") },
            text = { Text("Šta želiš da uradiš?") },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = { applyImportedText(importedText, replace = true) }) {
                        Text("Zameni")
                    }
                    TextButton(onClick = { applyImportedText(importedText, replace = false) }) {
                        Text("Dodaj na kraj")
                    }
                    TextButton(onClick = { pendingImportedText = null }) {
                        Text("Otkaži")
                    }
                }
            }
        )
    }
}

@Composable
private fun DiagramImagePickerField(
    attachment: TaskCreationDiagramImageDraft?,
    onImageSelected: (TaskCreationDiagramImageDraft) -> Unit,
    onRemoveImage: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var statusMessage by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        scope.launch {
            when (val result = withContext(Dispatchers.IO) { copyDiagramImageToInternalStorage(context, uri) }) {
                is DiagramImageImportResult.Success -> {
                    onImageSelected(result.attachment)
                    statusMessage = null
                }
                is DiagramImageImportResult.Error -> statusMessage = result.message
            }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SectionTitle("Dijagram / slika")
        if (attachment == null) {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { launcher.launch(arrayOf("image/png", "image/jpeg", "image/webp")) },
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Dodaj sliku dijagrama", fontWeight = FontWeight.ExtraBold)
            }
        } else {
            DiagramImagePreview(attachment = attachment)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { launcher.launch(arrayOf("image/png", "image/jpeg", "image/webp")) },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Promeni sliku", fontWeight = FontWeight.ExtraBold)
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onRemoveImage()
                        statusMessage = null
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Ukloni sliku", fontWeight = FontWeight.ExtraBold)
                }
            }
        }
        statusMessage?.let { message ->
            ImportStatusMessage(message = message, isError = true)
        }
    }
}

@Composable
private fun PreviewDiagramImage(attachment: TaskCreationDiagramImageDraft) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PreviewLine("Dijagram", attachment.originalFileName)
        DiagramImagePreview(attachment = attachment)
    }
}

@Composable
private fun DiagramImagePreview(attachment: TaskCreationDiagramImageDraft) {
    val imageBitmap = remember(attachment.localPath) {
        runCatching { BitmapFactory.decodeFile(attachment.localPath)?.asImageBitmap() }.getOrNull()
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (imageBitmap == null) {
                Text(
                    text = "Slika nije mogla da se učita.",
                    color = Color(0xFFB91C1C),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Pregled dijagrama",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White),
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = attachment.originalFileName,
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "${attachment.mimeType} • ${formatFileSize(attachment.sizeBytes)}",
                color = AppPalette.TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ImportStatusMessage(
    message: String,
    isError: Boolean
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = if (isError) Color(0xFFFEF2F2) else Color(0xFFECFDF5),
        border = BorderStroke(1.dp, if (isError) Color(0xFFFECACA) else Color(0xFFA7F3D0))
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
            text = message,
            color = if (isError) Color(0xFFB91C1C) else Color(0xFF047857),
            fontSize = 12.5.sp,
            lineHeight = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun formatFileSize(sizeBytes: Long): String {
    if (sizeBytes <= 0L) return "nepoznata veličina"
    val kb = sizeBytes / 1024.0
    return if (kb < 1024) {
        "${"%.1f".format(kb)} KB"
    } else {
        "${"%.1f".format(kb / 1024.0)} MB"
    }
}

@Composable
private fun ChecklistEditor(
    title: String,
    items: List<String>,
    onUpdate: (Int, String) -> Unit,
    onAdd: () -> Unit,
    onRemoveLast: () -> Unit,
    canRemove: Boolean
) {
    SectionTitle(title)
    items.forEachIndexed { index, item ->
        FormTextField(
            label = "Stavka ${index + 1}",
            value = item,
            onValueChange = { onUpdate(index, it) },
            minLines = 2
        )
    }
    AddRemoveRow(
        addLabel = "Dodaj stavku",
        removeLabel = "Ukloni poslednju",
        canRemove = canRemove,
        onAdd = onAdd,
        onRemove = onRemoveLast
    )
}

@Composable
private fun StepNumberBadge(number: Int) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEFF6FF)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = AppPalette.Blue,
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun TaskCreationInfoPanel(
    title: String,
    body: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = AppPalette.Navy,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(AppPalette.Navy, Color(0xFF1E3A8A), Color(0xFF312E81))
                    )
                )
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(title, color = Color(0xFF93C5FD), fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
            Text(body, color = Color.White, fontSize = 17.sp, lineHeight = 23.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun TaskCreationLevelCard(
    level: com.example.pmuprojekat.taskcreation.TaskCreationLevelDefinition,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, level.accentColor.copy(alpha = 0.32f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(level.accentColor),
                contentAlignment = Alignment.Center
            ) {
                Text(level.number.toString(), color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(modifier = Modifier.width(13.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(level.title, color = AppPalette.TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Text(
                    text = level.description,
                    color = AppPalette.TextSecondary,
                    fontSize = 12.5.sp,
                    lineHeight = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(">", color = level.accentColor, fontSize = 25.sp, fontWeight = FontWeight.Light)
        }
    }
}

@Composable
private fun TaskCreationTemplateCard(
    template: TaskCreationTemplateDefinition,
    level: com.example.pmuprojekat.taskcreation.TaskCreationLevelDefinition?,
    onClick: () -> Unit
) {
    val accent = level?.accentColor ?: AppPalette.Blue
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(accent.copy(alpha = 0.13f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(template.questionIdPattern.substringBefore("."), color = accent, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(template.displayName, color = AppPalette.TextPrimary, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
                    Text(template.questionIdPattern, color = accent, fontSize = 11.5.sp, fontWeight = FontWeight.ExtraBold)
                }
                Text(">", color = accent, fontSize = 23.sp)
            }
            Text(template.description, color = AppPalette.TextSecondary, fontSize = 13.sp, lineHeight = 18.sp)
            Text(
                text = "Broj koraka u šablonu: ${template.steps.size}",
                color = AppPalette.TextPrimary,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun TaskCreationCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            content = content
        )
    }
}

@Composable
private fun FormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    minLines: Int = 1,
    monospace: Boolean = false,
    enabled: Boolean = true
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        minLines = minLines,
        singleLine = minLines == 1,
        enabled = enabled,
        textStyle = if (monospace) {
            TextStyle(fontFamily = FontFamily.Monospace, color = AppPalette.TextPrimary)
        } else {
            TextStyle(color = AppPalette.TextPrimary)
        },
        colors = readableOutlinedTextFieldColors()
    )
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, color = AppPalette.TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
}

@Composable
private fun ToggleChip(
    label: String,
    selected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        color = if (selected) selectedColor.copy(alpha = 0.12f) else Color.White,
        border = BorderStroke(1.dp, if (selected) selectedColor else AppPalette.Border)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            text = label,
            color = if (selected) selectedColor else AppPalette.TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun CategoryChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        color = if (selected) Color(0xFFEFF6FF) else Color.White,
        border = BorderStroke(1.dp, if (selected) AppPalette.Blue else AppPalette.Border)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            text = label,
            color = if (selected) AppPalette.Blue else AppPalette.TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun AddRemoveRow(
    addLabel: String,
    removeLabel: String,
    canRemove: Boolean,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = onAdd,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(addLabel, fontWeight = FontWeight.ExtraBold)
        }
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = onRemove,
            enabled = canRemove,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(removeLabel, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun ValidationErrors(errors: List<String>) {
    if (errors.isEmpty()) return
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = Color(0xFFFFF7ED),
        border = BorderStroke(1.dp, Color(0xFFFDBA74))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text("Proveri unos", color = Color(0xFFC2410C), fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
            errors.forEach { error ->
                Text(
                    text = "- $error",
                    color = Color(0xFF9A3412),
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun PreviewLine(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(label, color = AppPalette.Blue, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
        Text(
            text = value.ifBlank { "-" },
            color = AppPalette.TextPrimary,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun PreviewBlock(label: String, value: String) {
    PreviewLine(label = label, value = value)
}

@Composable
private fun PreviewList(label: String, items: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(label, color = AppPalette.Blue, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
        items.ifEmpty { listOf("-") }.forEachIndexed { index, item ->
            Text(
                text = "${index + 1}. $item",
                color = AppPalette.TextPrimary,
                fontSize = 13.5.sp,
                lineHeight = 19.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private fun levelName(levelId: String): String {
    return LearningLevel.fromId(levelId).displayName
}
