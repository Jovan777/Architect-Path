package com.example.pmuprojekat.taskcreation

import androidx.compose.ui.graphics.Color
import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.ui.home.AppPalette

enum class TaskCreationRendererHint(val label: String) {
    DEFAULT("Standardni prikaz"),
    NEUTRAL_VISUAL_CHOICE("Neutralne A/B/C/D kartice"),
    INLINE_CODE_COMPLETION("Inline dopuna pseudo-koda"),
    ROLE_MAPPING("Mapiranje uloga"),
    HOTSPOT("Označavanje grešaka"),
    SMOOTH_ORDERING("Glatko poređivanje kartica"),
    ORDERING_WITH_TRAPS("Poređivanje sa zamkama"),
    SENIOR_SWIPE("Brzo swipe razvrstavanje"),
    ARCHITECT_STYLE_MAPPING("Arhitektonsko mapiranje zona"),
    ARCHITECT_COMPONENT_SELECTION("Izbor komponenti sa distraktorima"),
    ARCHITECT_COMPOSITION_BOARD("Arhitektonska tabla komponenti"),
    ARCHITECT_FOCUSED_EFFECT_MAPPING("Fokusirano mapiranje efekata"),
    ARCHITECT_DEFENSE_BOARD("Tabla odbrane odluke"),
    ARCHITECT_PRIORITY_BOARD("Tabla prioriteta"),
    ARCHITECT_SIGNAL_BOARD("Tabla signala"),
    MINI_ADR("Mini ADR"),
    SKETCH_CAMERA("Skica fotografijom")
}

enum class TaskCreationCorrectAnswerMode {
    SINGLE,
    MULTIPLE
}

data class TaskCreationStepBlueprint(
    val key: String,
    val type: StepType,
    val title: String,
    val instruction: String,
    val purpose: String,
    val rendererHint: TaskCreationRendererHint = TaskCreationRendererHint.DEFAULT,
    val minOptions: Int = 0,
    val minZones: Int = 0,
    val minBlanks: Int = 0,
    val defaultZones: List<String> = emptyList(),
    val requiresCodeBlock: Boolean = false,
    val requiresSingleCorrectOption: Boolean = false,
    val requiresAtLeastOneCorrectOption: Boolean = false,
    val defaultCorrectAnswerMode: TaskCreationCorrectAnswerMode? = null,
    val requiresCorrectOrder: Boolean = false,
    val requiresCorrectZone: Boolean = false,
    val supportsDistractors: Boolean = false,
    val usesOptionLabels: Boolean = false,
    val requiresExplanation: Boolean = true,
    val requiresRubricPoints: Boolean = false,
    val allowOptionEditing: Boolean = true,
    val allowZoneEditing: Boolean = true
)

data class TaskCreationTemplateDefinition(
    val templateId: String,
    val level: String,
    val questionIdPattern: String,
    val taskType: String,
    val displayName: String,
    val description: String,
    val seedSource: String,
    val uiRenderer: String,
    val evaluationLogic: String,
    val requiredInputs: List<String>,
    val features: List<String>,
    val steps: List<TaskCreationStepBlueprint>,
    val requiresDiagramReference: Boolean = false,
    val requiresSketchChecklist: Boolean = false,
    val requiresInternalRubric: Boolean = false,
    val allowAdditionalSteps: Boolean = false,
    val repeatableStepKey: String? = null,
    val minStepCount: Int = steps.size
)

data class TaskCreationLevelDefinition(
    val levelId: String,
    val number: Int,
    val title: String,
    val description: String,
    val accentColor: Color,
    val softColor: Color,
    val icon: String
)

object TaskCreationTemplateRegistry {

    val levels = listOf(
        TaskCreationLevelDefinition(
            levelId = LearningLevel.BEGINNER.id,
            number = 1,
            title = "Početnik",
            description = "Osnovni pojmovi, obrasci i kratka pitanja za proveru razumevanja.",
            accentColor = AppPalette.Blue,
            softColor = Color(0xFFEFF6FF),
            icon = "1"
        ),
        TaskCreationLevelDefinition(
            levelId = LearningLevel.JUNIOR.id,
            number = 2,
            title = "Junior",
            description = "Pseudo-kod, uloge klasa, refaktorisanje i prepoznavanje grešaka.",
            accentColor = AppPalette.Green,
            softColor = Color(0xFFECFDF5),
            icon = "</>"
        ),
        TaskCreationLevelDefinition(
            levelId = LearningLevel.MEDIOR.id,
            number = 3,
            title = "Medior",
            description = "Tokovi sistema, zahtevi, posledice odluka i ograničenja.",
            accentColor = AppPalette.Orange,
            softColor = Color(0xFFFFF7ED),
            icon = "M"
        ),
        TaskCreationLevelDefinition(
            levelId = LearningLevel.SENIOR.id,
            number = 4,
            title = "Senior",
            description = "Produkcijski problemi, trade-off analiza, incidenti i prioriteti.",
            accentColor = AppPalette.Purple,
            softColor = Color(0xFFF5F3FF),
            icon = "S"
        ),
        TaskCreationLevelDefinition(
            levelId = LearningLevel.ARCHITECT.id,
            number = 5,
            title = "Arhitekta",
            description = "Složeni sistemi, evolucija arhitekture i strateške odluke.",
            accentColor = AppPalette.Indigo,
            softColor = Color(0xFFEEF2FF),
            icon = "A"
        )
    )

    val templates: List<TaskCreationTemplateDefinition> = listOf(
        beginnerP1(),
        beginnerP2(),
        beginnerP3(),
        beginnerP4(),
        juniorJ1(),
        juniorJ2(),
        juniorJ3(),
        juniorJ4(),
        mediorM1(),
        mediorM2(),
        mediorM3(),
        mediorM4(),
        mediorM5(),
        seniorS1(),
        seniorS2(),
        seniorS3(),
        seniorS4(),
        seniorS5(),
        architectA1(),
        architectA2(),
        architectA3(),
        architectA4(),
        architectA5(),
        architectA6(),
        architectA7()
    )

    fun templatesForLevel(levelId: String): List<TaskCreationTemplateDefinition> {
        return templates.filter { it.level == levelId }
    }

    fun templateById(templateId: String): TaskCreationTemplateDefinition? {
        return templates.firstOrNull { it.templateId == templateId }
    }

    fun levelById(levelId: String): TaskCreationLevelDefinition? {
        return levels.firstOrNull { it.levelId == levelId }
    }

    fun blueprintForStep(template: TaskCreationTemplateDefinition, blueprintKey: String): TaskCreationStepBlueprint? {
        return template.steps.firstOrNull { it.key == blueprintKey }
    }

    private fun beginnerP1() = template(
        templateId = "beginner_p1_pattern_recognition",
        level = LearningLevel.BEGINNER,
        questionIdPattern = "P1.x",
        questionType = QuestionType.PATTERN_RECOGNITION,
        displayName = "Prepoznavanje obrasca",
        description = "Jedan izbor: korisnik bira obrazac ili pojam koji najbolje odgovara scenariju.",
        seedSource = "BeginnerWave*Seed.kt / BeginnerSeedBuilders.patternRecognitionQuestion",
        uiRenderer = "ChoiceStepContent",
        evaluationLogic = "evaluateSingleChoice: selected optionId mora imati isCorrect=true",
        requiredInputs = listOf("naslov", "scenario", "opcije", "tačan odgovor", "objašnjenje"),
        features = listOf("jedan korak", "opcije", "tačna opcija", "feedback", "AI follow-up"),
        steps = listOf(choiceStep("p1_choice", "Izbor obrasca", single = true, labels = true))
    )

    private fun beginnerP2() = template(
        templateId = "beginner_p2_visual_mapping",
        level = LearningLevel.BEGINNER,
        questionIdPattern = "P2.x",
        questionType = QuestionType.VISUAL_MAPPING,
        displayName = "Visual mapping",
        description = "Jedan neutralno označen A/B/C/D izbor za prepoznavanje prikaza ili veze.",
        seedSource = "BeginnerWave*Seed.kt / BeginnerSeedBuilders.visualMappingQuestion",
        uiRenderer = "ChoiceStepContent",
        evaluationLogic = "evaluateSingleChoice",
        requiredInputs = listOf("opis prikaza", "neutralne A-D opcije", "tačan odgovor", "objašnjenje"),
        features = listOf("visual mapping", "neutralne labelе A-D", "bez otkrivanja imena obrasca"),
        steps = listOf(choiceStep("p2_visual", "Izbor prikaza", type = StepType.VISUAL_MAPPING, single = true, labels = true, hint = TaskCreationRendererHint.NEUTRAL_VISUAL_CHOICE))
    )

    private fun beginnerP3() = template(
        templateId = "beginner_p3_multi_step",
        level = LearningLevel.BEGINNER,
        questionIdPattern = "P3.x",
        questionType = QuestionType.MULTI_STEP_UNDERSTANDING,
        displayName = "Višekoračno razumevanje",
        description = "Više nezavisnih izbornih koraka koji proveravaju razumevanje istog pojma.",
        seedSource = "BeginnerWave*Seed.kt / BeginnerSeedBuilders.multiStepUnderstandingQuestion",
        uiRenderer = "ChoiceStepContent po koraku",
        evaluationLogic = "evaluateSingleChoice po koraku",
        requiredInputs = listOf("scenario", "više izbornih koraka", "opcije i tačan odgovor po koraku", "feedback po koraku"),
        features = listOf("više koraka", "opcije", "per-step feedback"),
        steps = listOf(
            choiceStep("p3_step_1", "Korak razumevanja 1", single = true, labels = true),
            choiceStep("p3_step_2", "Korak razumevanja 2", single = true, labels = true)
        ),
        allowAdditionalSteps = true,
        repeatableStepKey = "p3_step_2",
        minStepCount = 1
    )

    private fun beginnerP4() = template(
        templateId = "beginner_p4_pattern_comparison",
        level = LearningLevel.BEGINNER,
        questionIdPattern = "P4.x",
        questionType = QuestionType.PATTERN_COMPARISON,
        displayName = "Razlikovanje obrazaca",
        description = "Više poređenja sličnih obrazaca kroz izbor najboljeg objašnjenja.",
        seedSource = "BeginnerWave*Seed.kt / BeginnerSeedBuilders.patternComparisonQuestion",
        uiRenderer = "ChoiceStepContent po koraku",
        evaluationLogic = "evaluateSingleChoice po koraku",
        requiredInputs = listOf("scenario", "pitanja za poređenje", "opcije", "tačni odgovori", "feedback"),
        features = listOf("više koraka", "poređenje", "feedback po koraku"),
        steps = listOf(
            choiceStep("p4_step_1", "Poređenje 1", single = true, labels = true),
            choiceStep("p4_step_2", "Poređenje 2", single = true, labels = true)
        ),
        allowAdditionalSteps = true,
        repeatableStepKey = "p4_step_2",
        minStepCount = 1
    )

    private fun juniorJ1() = template(
        templateId = "junior_j1_code_completion",
        level = LearningLevel.JUNIOR,
        questionIdPattern = "J1.x",
        questionType = QuestionType.CODE_COMPLETION,
        displayName = "Dopuna pseudo-koda",
        description = "Inline popunjavanje praznih mesta u pseudo-kodu.",
        seedSource = "JuniorCodeCompletionSeed.kt / JuniorSeedBuilders.codeCompletionQuestion",
        uiRenderer = "CodeCompletionStepContent + J1 inline code renderer",
        evaluationLogic = "evaluateCodeCompletion: blankAnswersByBlankId mora odgovarati correctValue",
        requiredInputs = listOf("scenario", "pseudo-kod", "placeholderi", "tačne dopune", "objašnjenje"),
        features = listOf("codeBlock", "pseudo-code blanks", "inline editor", "feedback"),
        steps = listOf(
            codeCompletionStep("j1_code", "Dopuna pseudo-koda", TaskCreationRendererHint.INLINE_CODE_COMPLETION)
        )
    )

    private fun juniorJ2() = template(
        templateId = "junior_j2_role_mapping",
        level = LearningLevel.JUNIOR,
        questionIdPattern = "J2.x",
        questionType = QuestionType.ROLE_MAPPING,
        displayName = "Mapiranje uloga",
        description = "Mapiranje klasa, komponenti ili odgovornosti u uloge/zones.",
        seedSource = "JuniorRoleMappingSeed.kt / JuniorSeedBuilders.roleMappingQuestion",
        uiRenderer = "MappingStepContent",
        evaluationLogic = "evaluateMapping: mappedZoneByOptionId mora biti correctZoneId",
        requiredInputs = listOf("scenario", "uloge/zone", "stavke", "tačna zona za svaku stavku", "objašnjenje"),
        features = listOf("zones", "mapped options", "correctZoneId"),
        steps = listOf(mappingStep("j2_roles", "Mapiranje uloga", StepType.ROLE_MAPPING, TaskCreationRendererHint.ROLE_MAPPING))
    )

    private fun juniorJ3() = template(
        templateId = "junior_j3_refactoring",
        level = LearningLevel.JUNIOR,
        questionIdPattern = "J3.x",
        questionType = QuestionType.REFACTORING,
        displayName = "Refaktorisanje",
        description = "Izborni koraci o refaktorisanju, često uz kod, plus završno objašnjenje.",
        seedSource = "JuniorRefactoringSeed.kt / JuniorSeedBuilders.refactoringQuestion",
        uiRenderer = "ChoiceStepContent + FreeTextStepContent",
        evaluationLogic = "choice evaluatori; FREE_TEXT se beleži bez automatskog bodovanja",
        requiredInputs = listOf("scenario", "kod/kontekst", "izborni koraci", "tačni odgovori", "free-text prompt/rubrika"),
        features = listOf("codeBlock", "single/multi choice", "free text", "per-step feedback"),
        steps = listOf(
            choiceStep("j3_choice_1", "Izbor refaktorisanja", single = false, labels = true).copy(requiresCodeBlock = true),
            freeTextStep("j3_reflection", "Obrazloženje refaktorisanja")
        ),
        allowAdditionalSteps = true,
        repeatableStepKey = "j3_choice_1",
        minStepCount = 2
    )

    private fun juniorJ4() = template(
        templateId = "junior_j4_error_detection",
        level = LearningLevel.JUNIOR,
        questionIdPattern = "J4.x",
        questionType = QuestionType.ERROR_DETECTION,
        displayName = "Prepoznavanje greške",
        description = "Hotspot/multi-select izbor grešaka u kodu ili dizajnu.",
        seedSource = "JuniorErrorDetectionSeed.kt / JuniorSeedBuilders.errorDetectionQuestion",
        uiRenderer = "ChoiceStepContent za HOTSPOT",
        evaluationLogic = "evaluateMultiChoice: skup izabranih optionId mora odgovarati svim isCorrect opcijama",
        requiredInputs = listOf("scenario", "kod/opis", "moguće greške", "tačne greške", "objašnjenje"),
        features = listOf("hotspot", "codeBlock", "multi choice", "feedback"),
        steps = listOf(choiceStep("j4_hotspot", "Pronađi grešku", type = StepType.HOTSPOT, single = false, labels = true, hint = TaskCreationRendererHint.HOTSPOT).copy(requiresCodeBlock = true))
    )

    private fun mediorM1() = template(
        templateId = "medior_m1_sequence_logic",
        level = LearningLevel.MEDIOR,
        questionIdPattern = "M1.x",
        questionType = QuestionType.SEQUENCE_LOGIC,
        displayName = "Logika redosleda",
        description = "Poređivanje kartica toka sistema, uz moguće distraktore, pa izbor posledice/objašnjenja.",
        seedSource = "MediorSequenceLogicSeed.kt / MediorSeedBuilders.sequenceQuestion",
        uiRenderer = "OrderedCardsStepContent + ChoiceStepContent",
        evaluationLogic = "evaluateOrderedCards + evaluateSingleChoice",
        requiredInputs = listOf("scenario", "ordered cards", "correctOrder", "distraktori", "follow-up izbor", "feedback"),
        features = listOf("ordered cards", "distractors", "single choice", "multi-step"),
        steps = listOf(
            orderedStep("m1_order", "Poređaj tok izvršavanja", traps = true),
            choiceStep("m1_followup", "Provera posledice", single = true, labels = true)
        )
    )

    private fun mediorM2() = template(
        templateId = "medior_m2_system_code",
        level = LearningLevel.MEDIOR,
        questionIdPattern = "M2.x",
        questionType = QuestionType.CODE_COMPLETION,
        displayName = "Dopuna dizajna/koda",
        description = "Izbor A-D kartice koja najbolje dopunjava postojeći pseudo-kod.",
        seedSource = "MediorSystemCodeSeed.kt / MediorSeedBuilders.codeDecisionQuestion",
        uiRenderer = "ChoiceStepContent sa codeBlock kontekstom",
        evaluationLogic = "evaluateSingleChoice preko isCorrect opcije",
        requiredInputs = listOf("scenario", "pseudo-kod", "A-D solution cards", "tačan label", "objašnjenje"),
        features = listOf("codeBlock", "labeled options", "single choice"),
        steps = listOf(choiceStep("m2_code_choice", "Izaberi dopunu dizajna", single = true, labels = true).copy(requiresCodeBlock = true))
    )

    private fun mediorM3() = template(
        templateId = "medior_m3_requirement_mapping",
        level = LearningLevel.MEDIOR,
        questionIdPattern = "M3.x",
        questionType = QuestionType.SYSTEM_REQUIREMENT_MAPPING,
        displayName = "Zahtevi i obrasci",
        description = "Mapiranje zahteva u obrasce/komponente, uz opcioni izborni follow-up.",
        seedSource = "MediorRequirementMappingSeed.kt / MediorSeedBuilders.mappingQuestion",
        uiRenderer = "MappingStepContent + optional ChoiceStepContent",
        evaluationLogic = "evaluateMapping + choice evaluator",
        requiredInputs = listOf("zahtevi", "zone/obrasci", "stavke", "correctZoneId", "follow-up ako postoji"),
        features = listOf("role mapping", "optional choice step", "feedback"),
        steps = listOf(
            mappingStep("m3_mapping", "Mapiraj zahteve", StepType.ROLE_MAPPING, TaskCreationRendererHint.ROLE_MAPPING),
            choiceStep("m3_followup", "Dodatna provera", single = true, labels = true)
        )
    )

    private fun mediorM4() = template(
        templateId = "medior_m4_constraint_decision",
        level = LearningLevel.MEDIOR,
        questionIdPattern = "M4.x",
        questionType = QuestionType.CONSTRAINT_DECISION,
        displayName = "Izbor pod ograničenjima",
        description = "Sekvenca izbornih odluka pod ograničenjima, uz završno obrazloženje.",
        seedSource = "MediorConstraintDecisionSeed.kt / MediorSeedBuilders.constraintQuestion",
        uiRenderer = "ChoiceStepContent + FreeTextStepContent",
        evaluationLogic = "choice evaluatori; FREE_TEXT se beleži",
        requiredInputs = listOf("scenario", "odluke/opcije", "tačni izbori", "objašnjenja", "free-text prompt"),
        features = listOf("multi-step choice", "constraints", "free text"),
        steps = listOf(
            choiceStep("m4_decision_1", "Izbor pod ograničenjem", single = true, labels = true),
            choiceStep("m4_decision_2", "Provera odluke", single = true, labels = true),
            freeTextStep("m4_reflection", "Obrazloženje odluke")
        ),
        allowAdditionalSteps = true,
        repeatableStepKey = "m4_decision_2",
        minStepCount = 2
    )

    private fun mediorM5() = template(
        templateId = "medior_m5_consequence_analysis",
        level = LearningLevel.MEDIOR,
        questionIdPattern = "M5.x",
        questionType = QuestionType.CONSEQUENCE_ANALYSIS,
        displayName = "Analiza posledica",
        description = "Kombinacija izbora, razvrstavanja posledica i završnog obrazloženja.",
        seedSource = "MediorConsequenceAnalysisSeed.kt / MediorSeedBuilders.consequenceQuestion",
        uiRenderer = "ChoiceStepContent + MappingStepContent + FreeTextStepContent",
        evaluationLogic = "choice evaluatori + evaluateMapping + FREE_TEXT",
        requiredInputs = listOf("scenario", "opcije posledica", "kategorije", "stavke po kategoriji", "free-text prompt"),
        features = listOf("choice", "categorization", "free text"),
        steps = listOf(
            choiceStep("m5_choice", "Izaberi posledicu", single = false, labels = true),
            mappingStep("m5_categories", "Razvrstaj posledice", StepType.CATEGORIZATION),
            freeTextStep("m5_reflection", "Obrazloženje posledica")
        )
    )

    private fun seniorS1() = template(
        templateId = "senior_s1_production_diagnosis",
        level = LearningLevel.SENIOR,
        questionIdPattern = "S1.x",
        questionType = QuestionType.PRODUCTION_DIAGNOSIS,
        displayName = "Produkcijska dijagnostika",
        description = "Dijagnostika kroz swipe razvrstavanje, izbor komponente, posledice, intervenciju i refleksiju.",
        seedSource = "SeniorProductionDiagnosisSeed.kt / SeniorSeedBuilders.diagnosisQuestion",
        uiRenderer = "SeniorDiagnosisCategorizationStepContent + ChoiceStepContent + FreeTextStepContent",
        evaluationLogic = "evaluateMapping, evaluateSingleChoice, evaluateMultiChoice, FREE_TEXT",
        requiredInputs = listOf("incident", "simptomi/uzroci", "component choice", "consequence choices", "first action", "free-text prompt"),
        features = listOf("quick swipe", "multi-step", "single choice", "multi choice", "free text"),
        steps = listOf(
            mappingStep("s1_diagnosis", "Razdvoji simptome i moguće uzroke", StepType.CATEGORIZATION, TaskCreationRendererHint.SENIOR_SWIPE, listOf("Simptomi", "Mogući uzroci")),
            choiceStep("s1_component", "Izaberi komponentu ili tok", single = true, labels = true),
            choiceStep("s1_consequences", "Izaberi posledice", single = false, labels = true),
            choiceStep("s1_first_action", "Prvi istražni korak", single = true, labels = true),
            freeTextStep("s1_reflection", "Obrazloženje dijagnoze")
        )
    )

    private fun seniorS2() = template(
        templateId = "senior_s2_optimization_strategy",
        level = LearningLevel.SENIOR,
        questionIdPattern = "S2.x",
        questionType = QuestionType.OPTIMIZATION_STRATEGY,
        displayName = "Strategija optimizacije",
        description = "Izbor strategije, pa brzo razdvajanje dobitaka i rizika/gubitaka.",
        seedSource = "SeniorOptimizationStrategySeed.kt / SeniorSeedBuilders.optimizationQuestion",
        uiRenderer = "ChoiceStepContent + SeniorBalanceCategorizationStepContent + FreeTextStepContent",
        evaluationLogic = "evaluateSingleChoice + evaluateMapping + FREE_TEXT",
        requiredInputs = listOf("scenario", "strategije", "tačna strategija", "dobici/rizici", "free-text prompt"),
        features = listOf("single choice", "quick swipe categorization", "free text"),
        steps = listOf(
            choiceStep("s2_strategy", "Izaberi strategiju", single = true, labels = true),
            mappingStep("s2_balance", "Razdvoji dobitke i rizike/gubitke", StepType.CATEGORIZATION, TaskCreationRendererHint.SENIOR_SWIPE, listOf("Dobitak", "Rizici/gubici")),
            freeTextStep("s2_reflection", "Obrazloženje optimizacije")
        )
    )

    private fun seniorS3() = template(
        templateId = "senior_s3_incident_analysis",
        level = LearningLevel.SENIOR,
        questionIdPattern = "S3.x",
        questionType = QuestionType.INCIDENT_ANALYSIS,
        displayName = "Incident analiza",
        description = "Izbor intervencije, izbor metrika i završno objašnjenje.",
        seedSource = "SeniorIncidentAnalysisSeed.kt / SeniorSeedBuilders.incidentQuestion",
        uiRenderer = "ChoiceStepContent + FreeTextStepContent",
        evaluationLogic = "evaluateSingleChoice + evaluateMultiChoice + FREE_TEXT",
        requiredInputs = listOf("incident", "intervencije", "tačna intervencija", "metrike", "free-text prompt"),
        features = listOf("single choice", "multi choice", "free text"),
        steps = listOf(
            choiceStep("s3_intervention", "Izaberi intervenciju", single = true, labels = true),
            choiceStep("s3_metrics", "Izaberi metrike", single = false, labels = true),
            freeTextStep("s3_reflection", "Obrazloženje incidenta")
        )
    )

    private fun seniorS4() = template(
        templateId = "senior_s4_trade_off",
        level = LearningLevel.SENIOR,
        questionIdPattern = "S4.x",
        questionType = QuestionType.TRADE_OFF,
        displayName = "Trade-off evaluacija",
        description = "Izbor rešenja, pa swipe razvrstavanje koristi i prihvaćene cene/rizika.",
        seedSource = "SeniorTradeOffSeed.kt / SeniorSeedBuilders.tradeOffQuestion",
        uiRenderer = "ChoiceStepContent + SeniorBalanceCategorizationStepContent + FreeTextStepContent",
        evaluationLogic = "evaluateSingleChoice + evaluateMapping + FREE_TEXT",
        requiredInputs = listOf("scenario", "rešenja", "tačno rešenje", "dobici/cene", "free-text prompt"),
        features = listOf("single choice", "quick swipe categorization", "free text"),
        steps = listOf(
            choiceStep("s4_solution", "Izaberi rešenje", single = true, labels = true),
            mappingStep("s4_balance", "Razdvoji dobitak i prihvaćenu cenu", StepType.CATEGORIZATION, TaskCreationRendererHint.SENIOR_SWIPE, listOf("Šta se dobija", "Prihvaćena cena")),
            freeTextStep("s4_reflection", "Obrazloženje trade-off odluke")
        )
    )

    private fun seniorS5() = template(
        templateId = "senior_s5_prioritization",
        level = LearningLevel.SENIOR,
        questionIdPattern = "S5.x",
        questionType = QuestionType.PRIORITIZATION,
        displayName = "Prioritizacija",
        description = "Poređivanje akcija po prioritetu uz zamke, pa kratko obrazloženje.",
        seedSource = "SeniorPrioritizationSeed.kt / SeniorSeedBuilders.prioritizationQuestion",
        uiRenderer = "OrderedCardsStepContent + FreeTextStepContent",
        evaluationLogic = "evaluateOrderedCards + FREE_TEXT",
        requiredInputs = listOf("scenario", "intervention cards", "correctOrder", "distraktori", "free-text prompt"),
        features = listOf("ordered cards", "distractors", "free text"),
        steps = listOf(
            orderedStep("s5_order", "Poređaj prioritete", traps = true),
            freeTextStep("s5_reflection", "Obrazloženje prioriteta")
        )
    )

    private fun architectA1() = template(
        templateId = "architect_a1_extension",
        level = LearningLevel.ARCHITECT,
        questionIdPattern = "A1.x",
        questionType = QuestionType.ARCHITECTURE_EXTENSION,
        displayName = "Projektovanje proširenja",
        description = "Višekoračno projektovanje proširenja: odluke, redosled implementacije i refleksija.",
        seedSource = "ArchitectExtensionSeed.kt / ArchitectSeedBuilders",
        uiRenderer = "ChoiceStepContent + ArchitectOrderedCardsContent + FreeTextStepContent",
        evaluationLogic = "choice evaluatori + evaluateArchitectOrderedCards + FREE_TEXT",
        requiredInputs = listOf("scenario/constraints", "choice steps", "ordered cards/traps", "free-text prompt", "AI follow-up"),
        features = listOf("multi-step", "architect ordered cards", "distractors", "free text"),
        steps = listOf(
            choiceStep("a1_arch_choice", "Izaberi najpogodniji pravac proširenja", single = true, labels = true),
            orderedStep("a1_order", "Poređaj tok implementacije", traps = true, hint = TaskCreationRendererHint.ORDERING_WITH_TRAPS),
            choiceStep("a1_key_decisions", "Izaberi najvažnije arhitektonske odluke", single = false, labels = true),
            choiceStep("a1_risk", "Označi glavni rizik", single = true, labels = true),
            freeTextStep("a1_reflection", "Obrazloženje proširenja")
        )
    )

    private fun architectA2() = template(
        templateId = "architect_a2_style",
        level = LearningLevel.ARCHITECT,
        questionIdPattern = "A2.x",
        questionType = QuestionType.ARCHITECTURE_STYLE,
        displayName = "Izbor arhitektonskog stila",
        description = "Izbor stila, mapiranje komponenti u zone, rizici i obrazloženje.",
        seedSource = "ArchitectStyleSeed.kt / ArchitectSeedBuilders",
        uiRenderer = "ChoiceStepContent + ArchitectStyleZoneMapping + FreeTextStepContent",
        evaluationLogic = "evaluateSingleChoice + evaluateMapping + evaluateMultiChoice + FREE_TEXT",
        requiredInputs = listOf("scenario", "style options", "zone mapping", "risk options", "free-text prompt"),
        features = listOf("single choice", "architect zone mapping", "multi choice", "free text"),
        steps = listOf(
            choiceStep("a2_style_choice", "Izaberi arhitektonski stil", single = true, labels = true),
            mappingStep("a2_zone_mapping", "Mapiraj komponente u arhitektonske zone", StepType.CATEGORIZATION, TaskCreationRendererHint.ARCHITECT_STYLE_MAPPING),
            choiceStep("a2_risks", "Izaberi rizike ili neprikladnosti", single = false, labels = true),
            freeTextStep("a2_reflection", "Obrazloženje stila")
        )
    )

    private fun architectA3() = template(
        templateId = "architect_a3_review",
        level = LearningLevel.ARCHITECT,
        questionIdPattern = "A3.x",
        questionType = QuestionType.ARCHITECTURE_REVIEW,
        displayName = "Revizija arhitekture",
        description = "Analiza dijagrama: hotspot, razdvajanje uzroka/simptoma, izbor i trozona review tabla.",
        seedSource = "ArchitectReviewSeed.kt / ArchitectSeedBuilders",
        uiRenderer = "Diagram preview + hotspot + architect categorization + FreeTextStepContent",
        evaluationLogic = "evaluateMultiChoice + evaluateMapping + evaluateSingleChoice + FREE_TEXT",
        requiredInputs = listOf("scenario", "diagram reference", "hotspot options", "categories", "3-way review items", "free-text prompt"),
        features = listOf("diagramImageName", "hotspot", "categorization", "three-way review", "free text"),
        steps = listOf(
            choiceStep("a3_hotspot", "Označi problematične delove", type = StepType.HOTSPOT, single = false, labels = true, hint = TaskCreationRendererHint.HOTSPOT),
            mappingStep("a3_cause_symptom", "Razdvoji simptome i uzroke", StepType.CATEGORIZATION, TaskCreationRendererHint.SENIOR_SWIPE, listOf("Simptomi", "Arhitektonski uzroci")),
            choiceStep("a3_decision", "Izaberi najbolju revizijsku odluku", single = true, labels = true),
            mappingStep("a3_review_board", "Razvrstaj efekte revizije", StepType.CATEGORIZATION, TaskCreationRendererHint.ARCHITECT_COMPOSITION_BOARD, listOf("Dobici", "Novi rizici", "Treba projektovati")),
            freeTextStep("a3_reflection", "Obrazloženje revizije")
        ),
        requiresDiagramReference = true
    )

    private fun architectA4() = template(
        templateId = "architect_a4_composition",
        level = LearningLevel.ARCHITECT,
        questionIdPattern = "A4.x",
        questionType = QuestionType.ARCHITECTURE_COMPOSITION,
        displayName = "Sastavljanje arhitekture",
        description = "Izbor komponenti, redosled toka, postavljanje komponenti u zone, odluka i refleksija.",
        seedSource = "ArchitectCompositionSeed.kt / ArchitectSeedBuilders",
        uiRenderer = "ArchitectComponentSelectionContent + ArchitectOrderedCardsContent + ChoiceStepContent + FreeTextStepContent",
        evaluationLogic = "evaluateMultiChoice + evaluateArchitectOrderedCards + evaluateMultiChoice + evaluateSingleChoice + FREE_TEXT",
        requiredInputs = listOf("scenario", "komponente/distraktori", "flow order/traps", "ključna arhitektonska pravila", "decision choice", "free-text prompt"),
        features = listOf("multi choice with distractors", "ordered cards with traps", "multi choice rules", "single choice", "free text"),
        steps = listOf(
            choiceStep("a4_components", "Izaberi potrebne komponente", type = StepType.MULTI_CHOICE, single = false, labels = true, hint = TaskCreationRendererHint.ARCHITECT_COMPONENT_SELECTION).copy(supportsDistractors = true),
            orderedStep("a4_order", "Poređaj tok arhitekture", traps = true, hint = TaskCreationRendererHint.ORDERING_WITH_TRAPS),
            choiceStep("a4_rules", "Izaberi ključna arhitektonska pravila", type = StepType.MULTI_CHOICE, single = false, labels = true),
            choiceStep("a4_decision", "Izaberi odluku za sastav sistema", single = true, labels = true),
            freeTextStep("a4_reflection", "Obrazloženje sastava")
        )
    )

    private fun architectA5() = template(
        templateId = "architect_a5_scaling",
        level = LearningLevel.ARCHITECT,
        questionIdPattern = "A5.x",
        questionType = QuestionType.SCALING_ASSESSMENT,
        displayName = "Procena skaliranja",
        description = "Poređivanje toka, izbor relevantnih tvrdnji i fokusirano mapiranje efekata skaliranja.",
        seedSource = "ArchitectScalingSeed.kt / ArchitectSeedBuilders",
        uiRenderer = "ArchitectOrderedCardsContent + ChoiceStepContent + ArchitectFocusedEffectLineMappingContent",
        evaluationLogic = "evaluateArchitectOrderedCards + evaluateMultiChoice + evaluateMapping + FREE_TEXT",
        requiredInputs = listOf("scenario", "ordered flow/traps", "multi choice", "effect zones/items", "free-text prompt"),
        features = listOf("ordered cards with traps", "multi choice", "focused effect mapping", "free text"),
        steps = listOf(
            orderedStep("a5_order", "Poređaj tok kritičnog procesa", traps = true, hint = TaskCreationRendererHint.ORDERING_WITH_TRAPS),
            choiceStep("a5_concerns", "Izaberi relevantne tvrdnje", single = false, labels = true),
            mappingStep("a5_effects", "Mapiraj odluke na efekte", StepType.CATEGORIZATION, TaskCreationRendererHint.ARCHITECT_FOCUSED_EFFECT_MAPPING),
            freeTextStep("a5_reflection", "Obrazloženje skaliranja")
        )
    )

    private fun architectA6() = template(
        templateId = "architect_a6_compromise",
        level = LearningLevel.ARCHITECT,
        questionIdPattern = "A6.x",
        questionType = QuestionType.ARCHITECTURAL_COMPROMISE,
        displayName = "Arhitektonski kompromis",
        description = "Radni prostor za odbranu odluke, prioritete, signale i mini ADR.",
        seedSource = "ArchitectCompromiseSeed.kt + InitialSeedData.asArchitectType6Workspace()",
        uiRenderer = "ChoiceStepContent + ArchitectDefenseBoardContent + ArchitectPriorityBoardContent + ArchitectSignalBoardContent + FreeTextStepContent",
        evaluationLogic = "evaluateSingleChoice + evaluateArchitectType6DefenseBoard + evaluateMapping + MINI_ADR",
        requiredInputs = listOf("scenario", "decision options", "defense evidence/zones", "priority tracks", "signal zones", "mini ADR rubric"),
        features = listOf("remapped workspace", "defense board", "priority board", "signal board", "mini ADR"),
        steps = listOf(
            choiceStep("a6_decision", "Izaberi kompromis koji možeš braniti", single = true, labels = true),
            mappingStep(
                "a6_defense",
                "Odbrani odluku dokazima",
                StepType.CATEGORIZATION,
                TaskCreationRendererHint.ARCHITECT_DEFENSE_BOARD,
                listOf("Pritisak 1", "Pritisak 2", "Pritisak 3")
            ).copy(supportsDistractors = true),
            mappingStep("a6_priority", "Razvrstaj prioritete", StepType.CATEGORIZATION, TaskCreationRendererHint.ARCHITECT_PRIORITY_BOARD, listOf("Uraditi sada", "Ostaviti za kasnije", "Ne raditi / pogrešan pravac")),
            mappingStep("a6_signals", "Razvrstaj signale za promenu odluke", StepType.CATEGORIZATION, TaskCreationRendererHint.ARCHITECT_SIGNAL_BOARD, listOf("Pravi signal", "Lažni signal", "Signal za oprez")),
            freeTextStep("a6_mini_adr", "Mini ADR", StepType.MINI_ADR, TaskCreationRendererHint.MINI_ADR)
        )
    )

    private fun architectA7() = template(
        templateId = "architect_a7_sketch",
        level = LearningLevel.ARCHITECT,
        questionIdPattern = "A7.x",
        questionType = QuestionType.ARCHITECTURE_SKETCH,
        displayName = "Arhitektonsko skiciranje sistema",
        description = "Open-ended zadatak sa kamerom: korisnik crta sistem na papiru i šalje sliku na AI analizu.",
        seedSource = "ArchitectSketchSeed.kt",
        uiRenderer = "ArchitectSketchQuestionScreen",
        evaluationLogic = "AI image analysis; završavanje posle uspešnog slanja/analize",
        requiredInputs = listOf("opis sistema", "zadatak za korisnika", "checklista za crtež", "interna AI smernica"),
        features = listOf("camera", "image preview", "AI sketch analysis", "checklist", "internal rubric"),
        steps = listOf(
            freeTextStep("a7_sketch_prompt", "Zadatak za skiciranje", StepType.FREE_TEXT, TaskCreationRendererHint.SKETCH_CAMERA)
        ),
        requiresSketchChecklist = true,
        requiresInternalRubric = true
    )

    private fun choiceStep(
        key: String,
        title: String,
        type: StepType = StepType.SINGLE_CHOICE,
        single: Boolean,
        labels: Boolean,
        hint: TaskCreationRendererHint = TaskCreationRendererHint.DEFAULT
    ) = TaskCreationStepBlueprint(
        key = key,
        type = type,
        title = title,
        instruction = "Izaberi najbolji odgovor.",
        purpose = "Opcijski korak sa tačnim odgovorom.",
        rendererHint = hint,
        minOptions = 2,
        requiresSingleCorrectOption = single,
        requiresAtLeastOneCorrectOption = !single,
        defaultCorrectAnswerMode = if (single) TaskCreationCorrectAnswerMode.SINGLE else TaskCreationCorrectAnswerMode.MULTIPLE,
        usesOptionLabels = labels
    )

    private fun orderedStep(
        key: String,
        title: String,
        traps: Boolean,
        hint: TaskCreationRendererHint = TaskCreationRendererHint.SMOOTH_ORDERING
    ) = TaskCreationStepBlueprint(
        key = key,
        type = StepType.ORDERED_CARDS,
        title = title,
        instruction = "Poređaj kartice u tačan redosled.",
        purpose = "Poređivanje kartica po expected correctOrder vrednosti.",
        rendererHint = hint,
        minOptions = 2,
        requiresCorrectOrder = true,
        supportsDistractors = traps
    )

    private fun mappingStep(
        key: String,
        title: String,
        type: StepType,
        hint: TaskCreationRendererHint = TaskCreationRendererHint.DEFAULT,
        zones: List<String> = emptyList()
    ) = TaskCreationStepBlueprint(
        key = key,
        type = type,
        title = title,
        instruction = "Razvrstaj stavke u odgovarajuće zone.",
        purpose = "Mapiranje stavki u zone preko correctZoneId.",
        rendererHint = hint,
        minOptions = 2,
        minZones = zones.size.coerceAtLeast(2),
        defaultZones = zones,
        requiresCorrectZone = true
    )

    private fun codeCompletionStep(
        key: String,
        title: String,
        hint: TaskCreationRendererHint
    ) = TaskCreationStepBlueprint(
        key = key,
        type = StepType.CODE_COMPLETION,
        title = title,
        instruction = "Dopuni prazna mesta u pseudo-kodu.",
        purpose = "Code block sa listom blankova i expected correctValue.",
        rendererHint = hint,
        requiresCodeBlock = true,
        minBlanks = 1
    )

    private fun freeTextStep(
        key: String,
        title: String,
        type: StepType = StepType.FREE_TEXT,
        hint: TaskCreationRendererHint = TaskCreationRendererHint.DEFAULT
    ) = TaskCreationStepBlueprint(
        key = key,
        type = type,
        title = title,
        instruction = "Napiši kratko obrazloženje.",
        purpose = "Otvoreni odgovor koji se čuva i koristi za mentorsku analizu.",
        rendererHint = hint,
        requiresExplanation = false,
        requiresRubricPoints = true,
        allowOptionEditing = false,
        allowZoneEditing = false
    )

    private fun template(
        templateId: String,
        level: LearningLevel,
        questionIdPattern: String,
        questionType: QuestionType,
        displayName: String,
        description: String,
        seedSource: String,
        uiRenderer: String,
        evaluationLogic: String,
        requiredInputs: List<String>,
        features: List<String>,
        steps: List<TaskCreationStepBlueprint>,
        requiresDiagramReference: Boolean = false,
        requiresSketchChecklist: Boolean = false,
        requiresInternalRubric: Boolean = false,
        allowAdditionalSteps: Boolean = false,
        repeatableStepKey: String? = null,
        minStepCount: Int = steps.size
    ): TaskCreationTemplateDefinition {
        return TaskCreationTemplateDefinition(
            templateId = templateId,
            level = level.id,
            questionIdPattern = questionIdPattern,
            taskType = questionType.id,
            displayName = displayName,
            description = description,
            seedSource = seedSource,
            uiRenderer = uiRenderer,
            evaluationLogic = evaluationLogic,
            requiredInputs = requiredInputs,
            features = features,
            steps = steps,
            requiresDiagramReference = requiresDiagramReference,
            requiresSketchChecklist = requiresSketchChecklist,
            requiresInternalRubric = requiresInternalRubric,
            allowAdditionalSteps = allowAdditionalSteps,
            repeatableStepKey = repeatableStepKey,
            minStepCount = minStepCount
        )
    }
}
