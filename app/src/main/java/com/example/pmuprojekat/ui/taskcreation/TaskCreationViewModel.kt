package com.example.pmuprojekat.ui.taskcreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.data.local.entity.UserTaskSubmissionEntity
import com.example.pmuprojekat.data.repository.TaskSubmissionRepository
import com.example.pmuprojekat.data.repository.SubmissionSyncResult
import com.example.pmuprojekat.taskcreation.TaskCreationCorrectAnswerMode
import com.example.pmuprojekat.taskcreation.TaskCreationStepBlueprint
import com.example.pmuprojekat.taskcreation.TaskCreationTemplateDefinition
import com.example.pmuprojekat.taskcreation.TaskCreationTemplateRegistry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID
import javax.inject.Inject

enum class TaskCreationFlowStep {
    LEVEL,
    TEMPLATE,
    FORM,
    PREVIEW,
    SUCCESS
}

data class TaskCreationOptionDraft(
    val label: String = "",
    val text: String = "",
    val isCorrect: Boolean = false,
    val correctOrder: String = "",
    val correctZoneIndex: Int = 0,
    val isDistractor: Boolean = false,
    val metadata: String = ""
)

data class TaskCreationZoneDraft(
    val title: String = ""
)

data class TaskCreationBlankDraft(
    val placeholder: String = "",
    val expectedAnswer: String = ""
)

data class TaskCreationStepDraft(
    val blueprintKey: String,
    val type: String,
    val title: String,
    val instruction: String,
    val correctAnswerMode: TaskCreationCorrectAnswerMode? = null,
    val codeBlock: String = "",
    val explanation: String = "",
    val options: List<TaskCreationOptionDraft> = emptyList(),
    val zones: List<TaskCreationZoneDraft> = emptyList(),
    val blanks: List<TaskCreationBlankDraft> = emptyList(),
    val rubricPoints: List<String> = emptyList()
)

data class TaskCreationDraft(
    val title: String = "",
    val scenario: String = "",
    val diagramReference: String = "",
    val aiFollowUp: String = "",
    val internalRubric: String = "",
    val checklistItems: List<String> = listOf("", ""),
    val steps: List<TaskCreationStepDraft> = emptyList()
)

data class UserTaskSubmissionSummaryUi(
    val id: String,
    val title: String,
    val level: String,
    val taskType: String,
    val reviewStatus: String,
    val syncStatus: String
)

data class TaskCreationUiState(
    val step: TaskCreationFlowStep = TaskCreationFlowStep.LEVEL,
    val selectedLevelId: String? = null,
    val selectedTemplateId: String? = null,
    val draft: TaskCreationDraft = TaskCreationDraft(),
    val validationErrors: List<String> = emptyList(),
    val isSubmitting: Boolean = false,
    val successMessage: String? = null,
    val localSubmissions: List<UserTaskSubmissionSummaryUi> = emptyList()
) {
    val selectedTemplate: TaskCreationTemplateDefinition?
        get() = selectedTemplateId?.let(TaskCreationTemplateRegistry::templateById)
}

@HiltViewModel
class TaskCreationViewModel @Inject constructor(
    private val repository: TaskSubmissionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskCreationUiState())
    val uiState: StateFlow<TaskCreationUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeLocalSubmissions().collect { submissions ->
                _uiState.value = _uiState.value.copy(
                    localSubmissions = submissions.map { it.toSummaryUi() }
                )
            }
        }
    }

    fun startNewFlow() {
        _uiState.value = _uiState.value.copy(
            step = TaskCreationFlowStep.LEVEL,
            selectedLevelId = null,
            selectedTemplateId = null,
            draft = TaskCreationDraft(),
            validationErrors = emptyList(),
            successMessage = null
        )
    }

    fun selectLevel(levelId: String) {
        _uiState.value = _uiState.value.copy(
            selectedLevelId = levelId,
            selectedTemplateId = null,
            draft = TaskCreationDraft(),
            validationErrors = emptyList(),
            step = TaskCreationFlowStep.TEMPLATE
        )
    }

    fun selectTemplate(templateId: String) {
        val template = TaskCreationTemplateRegistry.templateById(templateId) ?: return
        _uiState.value = _uiState.value.copy(
            selectedLevelId = template.level,
            selectedTemplateId = templateId,
            draft = initialDraftForTemplate(template),
            validationErrors = emptyList(),
            step = TaskCreationFlowStep.FORM
        )
    }

    fun goBack(): Boolean {
        val state = _uiState.value
        _uiState.value = when (state.step) {
            TaskCreationFlowStep.SUCCESS -> state.copy(step = TaskCreationFlowStep.LEVEL, successMessage = null)
            TaskCreationFlowStep.PREVIEW -> state.copy(step = TaskCreationFlowStep.FORM)
            TaskCreationFlowStep.FORM -> state.copy(step = TaskCreationFlowStep.TEMPLATE)
            TaskCreationFlowStep.TEMPLATE -> state.copy(step = TaskCreationFlowStep.LEVEL)
            TaskCreationFlowStep.LEVEL -> return false
        }
        return true
    }

    fun editFromPreview() {
        _uiState.value = _uiState.value.copy(
            step = TaskCreationFlowStep.FORM,
            validationErrors = emptyList()
        )
    }

    fun updateTitle(value: String) = updateDraft { it.copy(title = value) }
    fun updateScenario(value: String) = updateDraft { it.copy(scenario = value) }
    fun updateDiagramReference(value: String) = updateDraft { it.copy(diagramReference = value) }
    fun updateAiFollowUp(value: String) = updateDraft { it.copy(aiFollowUp = value) }
    fun updateInternalRubric(value: String) = updateDraft { it.copy(internalRubric = value) }

    fun updateChecklistItem(index: Int, value: String) {
        updateStringList(index, { it.checklistItems }) { draft, items ->
            draft.copy(checklistItems = items.mapIndexed { i, item -> if (i == index) value else item })
        }
    }

    fun addChecklistItem() = updateDraft { it.copy(checklistItems = it.checklistItems + "") }

    fun removeChecklistItem(index: Int) = updateDraft {
        it.copy(checklistItems = it.checklistItems.filterIndexed { i, _ -> i != index }.ifEmpty { listOf("", "") })
    }

    fun updateStepTitle(stepIndex: Int, value: String) = updateStep(stepIndex) { it.copy(title = value) }
    fun updateStepInstruction(stepIndex: Int, value: String) = updateStep(stepIndex) { it.copy(instruction = value) }
    fun updateStepCodeBlock(stepIndex: Int, value: String) = updateStep(stepIndex) { it.copy(codeBlock = value) }
    fun updateStepExplanation(stepIndex: Int, value: String) = updateStep(stepIndex) { it.copy(explanation = value) }

    fun updateStepCorrectAnswerMode(stepIndex: Int, mode: TaskCreationCorrectAnswerMode) {
        updateStep(stepIndex) { step ->
            val options = if (mode == TaskCreationCorrectAnswerMode.SINGLE) {
                val firstCorrectIndex = step.options.indexOfFirst { it.isCorrect }
                step.options.mapIndexed { index, option ->
                    option.copy(isCorrect = firstCorrectIndex >= 0 && index == firstCorrectIndex)
                }
            } else {
                step.options
            }
            step.copy(correctAnswerMode = mode, options = options)
        }
    }

    fun addRepeatableStep() {
        val state = _uiState.value
        val template = state.selectedTemplate ?: return
        if (!template.allowAdditionalSteps) return
        val blueprintKey = template.repeatableStepKey ?: return
        val blueprint = TaskCreationTemplateRegistry.blueprintForStep(template, blueprintKey) ?: return
        updateDraft { draft ->
            draft.copy(steps = draft.steps + initialStepDraft(blueprint, draft.steps.size))
        }
    }

    fun removeLastStep() {
        val state = _uiState.value
        val template = state.selectedTemplate ?: return
        if (state.draft.steps.size <= template.minStepCount) return
        updateDraft { it.copy(steps = it.steps.dropLast(1)) }
    }

    fun updateOptionLabel(stepIndex: Int, optionIndex: Int, value: String) {
        updateOption(stepIndex, optionIndex) { it.copy(label = value) }
    }

    fun updateOptionText(stepIndex: Int, optionIndex: Int, value: String) {
        updateOption(stepIndex, optionIndex) { it.copy(text = value) }
    }

    fun toggleCorrectOption(stepIndex: Int, optionIndex: Int) {
        val step = _uiState.value.draft.steps.getOrNull(stepIndex) ?: return
        if (step.correctAnswerMode == TaskCreationCorrectAnswerMode.SINGLE) {
            updateStep(stepIndex) {
                it.copy(
                    options = it.options.mapIndexed { index, option ->
                        option.copy(isCorrect = index == optionIndex)
                    }
                )
            }
        } else {
            updateOption(stepIndex, optionIndex) { it.copy(isCorrect = !it.isCorrect) }
        }
    }

    fun updateOptionCorrectOrder(stepIndex: Int, optionIndex: Int, value: String) {
        updateOption(stepIndex, optionIndex) { it.copy(correctOrder = value.filter { char -> char.isDigit() }) }
    }

    fun updateOptionCorrectZone(stepIndex: Int, optionIndex: Int, zoneIndex: Int) {
        updateOption(stepIndex, optionIndex) { it.copy(correctZoneIndex = zoneIndex) }
    }

    fun toggleOptionDistractor(stepIndex: Int, optionIndex: Int) {
        updateOption(stepIndex, optionIndex) { it.copy(isDistractor = !it.isDistractor) }
    }

    fun updateOptionMetadata(stepIndex: Int, optionIndex: Int, value: String) {
        updateOption(stepIndex, optionIndex) { it.copy(metadata = value) }
    }

    fun addOption(stepIndex: Int) {
        val state = _uiState.value
        val step = state.draft.steps.getOrNull(stepIndex) ?: return
        val template = state.selectedTemplate
        val blueprint = template?.let { TaskCreationTemplateRegistry.blueprintForStep(it, step.blueprintKey) }
        val nextIndex = step.options.size
        val option = initialOption(nextIndex, blueprint)
        updateStep(stepIndex) { it.copy(options = it.options + option) }
    }

    fun removeOption(stepIndex: Int, optionIndex: Int) {
        updateStep(stepIndex) { step ->
            val updated = step.options.filterIndexed { i, _ -> i != optionIndex }
            step.copy(options = updated.ifEmpty { listOf(initialOption(0, null), initialOption(1, null)) })
        }
    }

    fun updateZoneTitle(stepIndex: Int, zoneIndex: Int, value: String) {
        updateStep(stepIndex) { step ->
            step.copy(zones = step.zones.mapIndexed { i, zone -> if (i == zoneIndex) zone.copy(title = value) else zone })
        }
    }

    fun addZone(stepIndex: Int) {
        updateStep(stepIndex) { it.copy(zones = it.zones + TaskCreationZoneDraft()) }
    }

    fun removeZone(stepIndex: Int, zoneIndex: Int) {
        updateStep(stepIndex) { step ->
            val zones = step.zones.filterIndexed { i, _ -> i != zoneIndex }.ifEmpty {
                listOf(TaskCreationZoneDraft(), TaskCreationZoneDraft())
            }
            step.copy(
                zones = zones,
                options = step.options.map { option ->
                    option.copy(correctZoneIndex = option.correctZoneIndex.coerceIn(0, zones.lastIndex))
                }
            )
        }
    }

    fun updateBlankPlaceholder(stepIndex: Int, blankIndex: Int, value: String) {
        updateBlank(stepIndex, blankIndex) { it.copy(placeholder = value) }
    }

    fun updateBlankExpectedAnswer(stepIndex: Int, blankIndex: Int, value: String) {
        updateBlank(stepIndex, blankIndex) { it.copy(expectedAnswer = value) }
    }

    fun addBlank(stepIndex: Int) {
        updateStep(stepIndex) { it.copy(blanks = it.blanks + TaskCreationBlankDraft(placeholder = "__________")) }
    }

    fun removeBlank(stepIndex: Int, blankIndex: Int) {
        updateStep(stepIndex) { step ->
            step.copy(blanks = step.blanks.filterIndexed { i, _ -> i != blankIndex }.ifEmpty { listOf(TaskCreationBlankDraft()) })
        }
    }

    fun updateRubricPoint(stepIndex: Int, pointIndex: Int, value: String) {
        updateStep(stepIndex) { step ->
            step.copy(rubricPoints = step.rubricPoints.mapIndexed { i, point -> if (i == pointIndex) value else point })
        }
    }

    fun addRubricPoint(stepIndex: Int) {
        updateStep(stepIndex) { it.copy(rubricPoints = it.rubricPoints + "") }
    }

    fun removeRubricPoint(stepIndex: Int, pointIndex: Int) {
        updateStep(stepIndex) { step ->
            step.copy(rubricPoints = step.rubricPoints.filterIndexed { i, _ -> i != pointIndex }.ifEmpty { listOf("") })
        }
    }

    fun showPreview() {
        val errors = validateCurrentDraft()
        _uiState.value = _uiState.value.copy(
            validationErrors = errors,
            step = if (errors.isEmpty()) TaskCreationFlowStep.PREVIEW else TaskCreationFlowStep.FORM
        )
    }

    fun submitForReview() {
        val state = _uiState.value
        val errors = validateCurrentDraft()
        val template = state.selectedTemplate

        if (errors.isNotEmpty() || template == null || state.selectedLevelId == null) {
            _uiState.value = state.copy(validationErrors = errors, step = TaskCreationFlowStep.FORM)
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSubmitting = true, validationErrors = emptyList())
            val submission = buildSubmissionEntity(
                levelId = state.selectedLevelId,
                template = template,
                draft = state.draft
            )
            val result = repository.submitForReview(submission)
            _uiState.value = _uiState.value.copy(
                isSubmitting = false,
                step = TaskCreationFlowStep.SUCCESS,
                successMessage = when (result) {
                    is SubmissionSyncResult.Uploaded -> "Zadatak je poslat na pregled."
                    is SubmissionSyncResult.SavedLocally ->
                        "Zadatak je sačuvan lokalno, ali slanje na internet bazu nije uspelo. " +
                            "Pokušaj ponovo kasnije."
                }
            )
        }
    }

    private fun initialDraftForTemplate(template: TaskCreationTemplateDefinition): TaskCreationDraft {
        return TaskCreationDraft(
            steps = template.steps.mapIndexed { index, blueprint -> initialStepDraft(blueprint, index) },
            checklistItems = if (template.requiresSketchChecklist) listOf("", "", "") else listOf("", "")
        )
    }

    private fun initialStepDraft(
        blueprint: TaskCreationStepBlueprint,
        index: Int
    ): TaskCreationStepDraft {
        val options = if (blueprint.minOptions > 0) {
            List(blueprint.minOptions) { optionIndex -> initialOption(optionIndex, blueprint) }
        } else {
            emptyList()
        }
        val zones = when {
            blueprint.defaultZones.isNotEmpty() -> blueprint.defaultZones.map { TaskCreationZoneDraft(it) }
            blueprint.minZones > 0 -> List(blueprint.minZones) { TaskCreationZoneDraft() }
            else -> emptyList()
        }
        val blanks = if (blueprint.minBlanks > 0) {
            List(blueprint.minBlanks) { TaskCreationBlankDraft(placeholder = "__________") }
        } else {
            emptyList()
        }
        val rubricPoints = if (blueprint.requiresRubricPoints) listOf("", "") else emptyList()

        return TaskCreationStepDraft(
            blueprintKey = blueprint.key,
            type = blueprint.type.id,
            title = if (index == 0) blueprint.title else blueprint.title,
            instruction = blueprint.instruction,
            correctAnswerMode = blueprint.defaultCorrectAnswerMode,
            options = options,
            zones = zones,
            blanks = blanks,
            rubricPoints = rubricPoints
        )
    }

    private fun initialOption(
        index: Int,
        blueprint: TaskCreationStepBlueprint?
    ): TaskCreationOptionDraft {
        val correctOrder = if (blueprint?.requiresCorrectOrder == true) (index + 1).toString() else ""
        val label = if (blueprint?.usesOptionLabels == true) optionLabel(index) else ""
        return TaskCreationOptionDraft(
            label = label,
            correctOrder = correctOrder
        )
    }

    private fun updateDraft(transform: (TaskCreationDraft) -> TaskCreationDraft) {
        _uiState.value = _uiState.value.copy(
            draft = transform(_uiState.value.draft),
            validationErrors = emptyList()
        )
    }

    private fun updateStep(stepIndex: Int, transform: (TaskCreationStepDraft) -> TaskCreationStepDraft) {
        updateDraft { draft ->
            val steps = draft.steps
            if (stepIndex !in steps.indices) return@updateDraft draft
            draft.copy(steps = steps.mapIndexed { i, step -> if (i == stepIndex) transform(step) else step })
        }
    }

    private fun updateOption(
        stepIndex: Int,
        optionIndex: Int,
        transform: (TaskCreationOptionDraft) -> TaskCreationOptionDraft
    ) {
        updateStep(stepIndex) { step ->
            if (optionIndex !in step.options.indices) return@updateStep step
            step.copy(
                options = step.options.mapIndexed { i, option ->
                    if (i == optionIndex) transform(option) else option
                }
            )
        }
    }

    private fun updateBlank(
        stepIndex: Int,
        blankIndex: Int,
        transform: (TaskCreationBlankDraft) -> TaskCreationBlankDraft
    ) {
        updateStep(stepIndex) { step ->
            if (blankIndex !in step.blanks.indices) return@updateStep step
            step.copy(
                blanks = step.blanks.mapIndexed { i, blank ->
                    if (i == blankIndex) transform(blank) else blank
                }
            )
        }
    }

    private fun updateStringList(
        index: Int,
        listSelector: (TaskCreationDraft) -> List<String>,
        transform: (TaskCreationDraft, List<String>) -> TaskCreationDraft
    ) {
        val draft = _uiState.value.draft
        val list = listSelector(draft)
        if (index !in list.indices) return
        updateDraft { transform(it, list) }
    }

    private fun validateCurrentDraft(): List<String> {
        val state = _uiState.value
        val template = state.selectedTemplate ?: return listOf("Izaberi šablon zadatka.")
        val draft = state.draft
        val errors = mutableListOf<String>()

        if (draft.title.isBlank()) errors += "Naslov je obavezan."
        if (draft.scenario.isBlank()) errors += "Scenario ili opis zadatka je obavezan."
        if (template.requiresDiagramReference && draft.diagramReference.isBlank()) {
            errors += "Za ovaj tip zadatka dodaj referencu ili opis dijagrama."
        }
        if (template.requiresSketchChecklist && draft.checklistItems.count { it.isNotBlank() } < 2) {
            errors += "Dodaj najmanje dve stavke za checklistu crteža."
        }
        if (template.requiresInternalRubric && draft.internalRubric.isBlank()) {
            errors += "Interna AI smernica/rubrika je obavezna za ovaj tip zadatka."
        }

        draft.steps.forEachIndexed { index, step ->
            val blueprint = TaskCreationTemplateRegistry.blueprintForStep(template, step.blueprintKey)
            val stepLabel = "Korak ${index + 1} (${step.title.ifBlank { blueprint?.title ?: step.type }})"

            if (step.title.isBlank()) errors += "$stepLabel: naslov koraka je obavezan."
            if (step.instruction.isBlank()) errors += "$stepLabel: instrukcija je obavezna."

            when (step.type) {
                StepType.SINGLE_CHOICE.id,
                StepType.MULTI_CHOICE.id,
                StepType.VISUAL_MAPPING.id,
                StepType.HOTSPOT.id -> validateChoiceStep(step, blueprint, stepLabel, errors)

                StepType.ORDERED_CARDS.id -> validateOrderedStep(step, stepLabel, errors)
                StepType.CATEGORIZATION.id,
                StepType.ROLE_MAPPING.id -> validateMappingStep(step, blueprint, stepLabel, errors)

                StepType.CODE_COMPLETION.id -> validateCodeStep(step, stepLabel, errors)
                StepType.FREE_TEXT.id,
                StepType.MINI_ADR.id -> validateFreeTextStep(step, stepLabel, errors)
            }
        }

        return errors
    }

    private fun validateChoiceStep(
        step: TaskCreationStepDraft,
        blueprint: TaskCreationStepBlueprint?,
        stepLabel: String,
        errors: MutableList<String>
    ) {
        if (blueprint?.requiresCodeBlock == true && step.codeBlock.isBlank()) {
            errors += "$stepLabel: kod ili pseudo-kod je obavezan."
        }
        val filledOptions = step.options.filter { it.text.isNotBlank() }
        val minimum = (blueprint?.minOptions ?: 2).coerceAtLeast(2)
        if (filledOptions.size < minimum) errors += "$stepLabel: dodaj najmanje $minimum opcije."
        val correctCount = filledOptions.count { it.isCorrect }
        val mode = step.correctAnswerMode
            ?: blueprint?.defaultCorrectAnswerMode
            ?: if (blueprint?.requiresSingleCorrectOption == true) {
                TaskCreationCorrectAnswerMode.SINGLE
            } else {
                TaskCreationCorrectAnswerMode.MULTIPLE
            }
        when (mode) {
            TaskCreationCorrectAnswerMode.SINGLE -> if (correctCount != 1) {
                errors += "$stepLabel: označi tačno jednu tačnu opciju."
            }
            TaskCreationCorrectAnswerMode.MULTIPLE -> if (correctCount < 1) {
                errors += "$stepLabel: označi najmanje jednu tačnu opciju."
            }
        }
        if (blueprint?.requiresExplanation != false && step.explanation.isBlank()) {
            errors += "$stepLabel: dodaj feedback/objašnjenje."
        }
    }

    private fun validateOrderedStep(
        step: TaskCreationStepDraft,
        stepLabel: String,
        errors: MutableList<String>
    ) {
        val requiredCards = step.options.filter { it.text.isNotBlank() && !it.isDistractor }
        if (requiredCards.size < 2) errors += "$stepLabel: dodaj najmanje dve kartice koje pripadaju redosledu."
        val orders = requiredCards.mapNotNull { it.correctOrder.toIntOrNull() }
        if (orders.size != requiredCards.size || orders.toSet().size != orders.size || orders.any { it <= 0 }) {
            errors += "$stepLabel: svaka obavezna kartica mora imati jedinstven pozitivan redni broj."
        }
        if (step.explanation.isBlank()) errors += "$stepLabel: dodaj feedback/objašnjenje."
    }

    private fun validateMappingStep(
        step: TaskCreationStepDraft,
        blueprint: TaskCreationStepBlueprint?,
        stepLabel: String,
        errors: MutableList<String>
    ) {
        val zones = step.zones.filter { it.title.isNotBlank() }
        val minimumZones = (blueprint?.minZones ?: 2).coerceAtLeast(2)
        if (zones.size < minimumZones) errors += "$stepLabel: dodaj najmanje $minimumZones zone."
        val mappedItems = step.options.filter { it.text.isNotBlank() && !it.isDistractor }
        if (mappedItems.size < 2) errors += "$stepLabel: dodaj najmanje dve stavke za mapiranje."
        if (zones.isNotEmpty() && mappedItems.any { it.correctZoneIndex !in zones.indices }) {
            errors += "$stepLabel: svaka stavka mora imati važeću tačnu zonu."
        }
        if (blueprint?.requiresExplanation != false && step.explanation.isBlank()) {
            errors += "$stepLabel: dodaj feedback/objašnjenje."
        }
    }

    private fun validateCodeStep(
        step: TaskCreationStepDraft,
        stepLabel: String,
        errors: MutableList<String>
    ) {
        if (step.codeBlock.isBlank()) errors += "$stepLabel: pseudo-kod je obavezan."
        val blanks = step.blanks.filter { it.placeholder.isNotBlank() || it.expectedAnswer.isNotBlank() }
        if (blanks.isEmpty()) errors += "$stepLabel: dodaj bar jedno prazno mesto."
        blanks.forEachIndexed { index, blank ->
            if (blank.placeholder.isBlank()) errors += "$stepLabel: prazno mesto ${index + 1} mora imati placeholder."
            if (blank.expectedAnswer.isBlank()) errors += "$stepLabel: prazno mesto ${index + 1} mora imati tačan odgovor."
        }
        if (step.explanation.isBlank()) errors += "$stepLabel: dodaj feedback/objašnjenje."
    }

    private fun validateFreeTextStep(
        step: TaskCreationStepDraft,
        stepLabel: String,
        errors: MutableList<String>
    ) {
        if (step.rubricPoints.count { it.isNotBlank() } < 1) {
            errors += "$stepLabel: dodaj najmanje jednu očekivanu tačku/rubriku za pregled."
        }
    }

    private fun buildSubmissionEntity(
        levelId: String,
        template: TaskCreationTemplateDefinition,
        draft: TaskCreationDraft
    ): UserTaskSubmissionEntity {
        val now = System.currentTimeMillis()
        return UserTaskSubmissionEntity(
            id = "local_${UUID.randomUUID()}",
            createdAt = now,
            updatedAt = now,
            level = levelId,
            taskType = template.taskType,
            templateId = template.templateId,
            title = draft.title.trim(),
            authorType = "USER",
            source = "USER_SUBMISSION",
            reviewStatus = "PENDING",
            publicationTarget = "USER_TASKS",
            schemaVersion = 2,
            payloadJson = buildPayloadJson(levelId, template, draft),
            localOnly = true,
            remoteSubmissionId = null,
            syncStatus = "READY_FOR_UPLOAD",
            createdByRole = "USER",
            publicationMode = "USER_TASKS",
            isPublic = false,
            approvedAt = null,
            approvedBy = null,
            rejectionReason = null
        )
    }

    private fun buildPayloadJson(
        levelId: String,
        template: TaskCreationTemplateDefinition,
        draft: TaskCreationDraft
    ): String {
        val question = JSONObject()
            .put("questionIdPattern", template.questionIdPattern)
            .put("level", levelId)
            .put("type", template.taskType)
            .put("title", draft.title.trim())
            .put("prompt", draft.scenario.trim())
            .put("diagramImageName", draft.diagramReference.trim())
            .put("aiFollowUp", draft.aiFollowUp.trim())
            .put("internalAiRubric", draft.internalRubric.trim())
            .put("drawingChecklist", JSONArray(draft.checklistItems.mapNotBlank()))
            .put(
                "steps",
                JSONArray(draft.steps.mapIndexed { index, step ->
                    stepToJson(template, step, index)
                })
            )

        return JSONObject()
            .put("schemaVersion", 2)
            .put("createdByRole", "USER")
            .put("reviewStatus", "PENDING")
            .put("publicationMode", "USER_TASKS")
            .put("source", "USER_SUBMISSION")
            .put("level", levelId)
            .put("taskType", template.taskType)
            .put("templateId", template.templateId)
            .put("templateName", template.displayName)
            .put("questionIdPattern", template.questionIdPattern)
            .put("seedSource", template.seedSource)
            .put("uiRenderer", template.uiRenderer)
            .put("evaluationLogic", template.evaluationLogic)
            .put("features", JSONArray(template.features))
            .put("question", question)
            .toString()
    }

    private fun stepToJson(
        template: TaskCreationTemplateDefinition,
        step: TaskCreationStepDraft,
        stepIndex: Int
    ): JSONObject {
        val blueprint = TaskCreationTemplateRegistry.blueprintForStep(template, step.blueprintKey)
        val zones = step.zones.filter { it.title.isNotBlank() }
        val zoneIds = zones.mapIndexed { index, _ -> "s${stepIndex + 1}_z${index + 1}" }
        val filledOptions = step.options.filter { it.text.isNotBlank() }
        val optionIds = filledOptions.mapIndexed { optionIndex, _ -> "s${stepIndex + 1}_o${optionIndex + 1}" }
        val correctOptionIds = filledOptions.mapIndexedNotNull { optionIndex, option ->
            optionIds[optionIndex].takeIf { option.isCorrect }
        }
        val correctAnswerMode = step.correctAnswerMode ?: blueprint?.defaultCorrectAnswerMode

        return JSONObject()
            .put("stepId", "user_${template.templateId}_s${stepIndex + 1}")
            .put("blueprintKey", step.blueprintKey)
            .put("type", step.type)
            .put("title", step.title.trim())
            .put("instruction", step.instruction.trim())
            .put("requiredCount", requiredCountForStep(step, blueprint))
            .put("codeBlock", step.codeBlock.trim())
            .put("explanation", step.explanation.trim())
            .put("rendererHint", blueprint?.rendererHint?.name.orEmpty())
            .put("correctAnswerMode", correctAnswerMode?.name ?: JSONObject.NULL)
            .put("correctOptionIds", JSONArray(correctOptionIds))
            .put(
                "zones",
                JSONArray(zones.mapIndexed { index, zone ->
                    JSONObject()
                        .put("zoneId", zoneIds[index])
                        .put("title", zone.title.trim())
                        .put("zoneOrder", index + 1)
                })
            )
            .put(
                "options",
                JSONArray(filledOptions.mapIndexed { optionIndex, option ->
                    val correctZoneId = zoneIds.getOrNull(option.correctZoneIndex).orEmpty()
                    JSONObject()
                        .put("optionId", optionIds[optionIndex])
                        .put("label", option.label.ifBlank { generatedLabel(blueprint, optionIndex) })
                        .put("text", option.text.trim())
                        .put("optionOrder", optionIndex + 1)
                        .put("isCorrect", option.isCorrect)
                        .put("correctOrder", option.correctOrder.toIntOrNull() ?: JSONObject.NULL)
                        .put("correctZoneId", if (!option.isDistractor && correctZoneId.isNotBlank()) correctZoneId else JSONObject.NULL)
                        .put("isDistractor", option.isDistractor)
                        .put("metadata", option.metadata.trim().ifBlank { JSONObject.NULL })
                })
            )
            .put(
                "blanks",
                JSONArray(step.blanks.filter { it.placeholder.isNotBlank() || it.expectedAnswer.isNotBlank() }.mapIndexed { blankIndex, blank ->
                    JSONObject()
                        .put("blankId", "s${stepIndex + 1}_b${blankIndex + 1}")
                        .put("blankOrder", blankIndex + 1)
                        .put("placeholder", blank.placeholder.trim())
                        .put("correctValue", blank.expectedAnswer.trim())
                })
            )
            .put("rubricPoints", JSONArray(step.rubricPoints.mapNotBlank()))
    }

    private fun requiredCountForStep(
        step: TaskCreationStepDraft,
        blueprint: TaskCreationStepBlueprint?
    ): Int {
        val mode = step.correctAnswerMode ?: blueprint?.defaultCorrectAnswerMode
        return when {
            mode == TaskCreationCorrectAnswerMode.SINGLE -> 1
            mode == TaskCreationCorrectAnswerMode.MULTIPLE -> {
                step.options.count { it.text.isNotBlank() && it.isCorrect }.coerceAtLeast(1)
            }
            step.type == StepType.MULTI_CHOICE.id || step.type == StepType.HOTSPOT.id -> {
                step.options.count { it.text.isNotBlank() && it.isCorrect }.coerceAtLeast(1)
            }
            else -> if (blueprint?.requiresSingleCorrectOption == true) 1 else 0
        }
    }

    private fun generatedLabel(
        blueprint: TaskCreationStepBlueprint?,
        index: Int
    ): String {
        return if (blueprint?.usesOptionLabels == true) optionLabel(index) else ""
    }

    private fun optionLabel(index: Int): String {
        return ('A'.code + index).toChar().toString()
    }

    private fun List<String>.mapNotBlank(): List<String> {
        return map { it.trim() }.filter { it.isNotBlank() }
    }

    private fun UserTaskSubmissionEntity.toSummaryUi(): UserTaskSubmissionSummaryUi {
        return UserTaskSubmissionSummaryUi(
            id = id,
            title = title,
            level = level,
            taskType = taskType,
            reviewStatus = reviewStatus,
            syncStatus = syncStatus
        )
    }
}
