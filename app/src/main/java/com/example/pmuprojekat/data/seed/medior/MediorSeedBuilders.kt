package com.example.pmuprojekat.data.seed.medior

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.data.seed.SeedOption
import com.example.pmuprojekat.data.seed.SeedQuestion
import com.example.pmuprojekat.data.seed.SeedStep
import com.example.pmuprojekat.data.seed.SeedZone

internal data class MediorChoiceStepSeed(
    val title: String,
    val instruction: String,
    val options: List<String>,
    val correctAnswers: List<String>,
    val explanation: String? = null
)

internal data class MediorMappingItemSeed(
    val text: String,
    val correctZone: String
)

internal data class MediorCategorySeed(
    val title: String,
    val items: List<String>
)

internal object MediorSeedBuilders {

    fun sequenceQuestion(
        questionId: String,
        title: String,
        prompt: String,
        cards: List<String>,
        correctOrder: List<String>,
        alternatives: List<String>,
        correctAlternative: String,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.MEDIOR.id,
            type = QuestionType.SEQUENCE_LOGIC.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = "medium",
            orderIndex = orderIndex,
            estimatedMinutes = 6,
            steps = listOf(
                SeedStep(
                    stepId = "${questionId}_s1",
                    type = StepType.ORDERED_CARDS.id,
                    title = "Poređaj tok izvršavanja",
                    instruction = "Prevuci kartice u redosled koji najbolje opisuje tok sistema.",
                    requiredCount = correctOrder.size,
                    explanation = "Tačan redosled: ${correctOrder.joinToString(" → ")}.",
                    options = cards.mapIndexed { index, card ->
                        SeedOption(
                            optionId = "${questionId}_s1_o${index + 1}",
                            text = card,
                            optionOrder = index + 1,
                            correctOrder = correctOrder.indexOf(card).takeIf { it >= 0 }?.plus(1),
                            isDistractor = !correctOrder.contains(card)
                        )
                    }
                ),
                SeedStep(
                    stepId = "${questionId}_s2",
                    type = StepType.SINGLE_CHOICE.id,
                    title = "Izbor pristupa",
                    instruction = "Izaberi pristup koji bolje odgovara dodatnom ograničenju sistema.",
                    requiredCount = 1,
                    explanation = "Tačan odgovor: $correctAlternative.",
                    options = alternatives.mapIndexed { index, option ->
                        SeedOption(
                            optionId = "${questionId}_s2_o${index + 1}",
                            text = option,
                            optionOrder = index + 1,
                            isCorrect = option == correctAlternative
                        )
                    }
                )
            )
        )
    }

    fun codeDecisionQuestion(
        questionId: String,
        title: String,
        prompt: String,
        codeBlock: String,
        solutionCards: List<Pair<String, String>>,
        correctLetter: String,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.MEDIOR.id,
            type = QuestionType.CODE_COMPLETION.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = "medium",
            orderIndex = orderIndex,
            estimatedMinutes = 7,
            steps = listOf(
                SeedStep(
                    stepId = "${questionId}_s1",
                    type = StepType.SINGLE_CHOICE.id,
                    title = "Izaberi dopunu dizajna",
                    instruction = "Izaberi karticu rešenja koja najbolje dopunjava postojeći pseudo-kod u kontekstu zahteva sistema.",
                    requiredCount = 1,
                    codeBlock = codeBlock.ifBlank { null },
                    explanation = "Tačan odgovor: $correctLetter.",
                    options = solutionCards.mapIndexed { index, pair ->
                        SeedOption(
                            optionId = "${questionId}_s1_o${index + 1}",
                            label = pair.first,
                            text = pair.second,
                            optionOrder = index + 1,
                            isCorrect = pair.first == correctLetter
                        )
                    }
                )
            )
        )
    }

    fun mappingQuestion(
        questionId: String,
        title: String,
        prompt: String,
        zones: List<String>,
        items: List<MediorMappingItemSeed>,
        multiStep: MediorChoiceStepSeed?,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int
    ): SeedQuestion {
        val zoneIdByTitle = zones.distinct().mapIndexed { index, zone -> zone to "${questionId}_s1_z${index + 1}" }.toMap()
        val mappingStep = SeedStep(
            stepId = "${questionId}_s1",
            type = StepType.ROLE_MAPPING.id,
            title = "Poveži zahtev sa obrascem",
            instruction = "Prevuci svaku sistemsku potrebu u najpogodniji obrazac projektovanja.",
            requiredCount = items.size,
            explanation = "Tačno povezivanje prati najvažniji problem koji se rešava u svakoj situaciji.",
            zones = zoneIdByTitle.entries.mapIndexed { index, entry ->
                SeedZone(
                    zoneId = entry.value,
                    title = entry.key,
                    zoneOrder = index + 1
                )
            },
            options = items.mapIndexed { index, item ->
                SeedOption(
                    optionId = "${questionId}_s1_o${index + 1}",
                    text = item.text,
                    optionOrder = index + 1,
                    correctZoneId = zoneIdByTitle.getValue(item.correctZone)
                )
            }
        )

        val steps = if (multiStep != null) {
            listOf(mappingStep, choiceStep(questionId, 2, multiStep))
        } else {
            listOf(mappingStep)
        }

        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.MEDIOR.id,
            type = QuestionType.SYSTEM_REQUIREMENT_MAPPING.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = "medium",
            orderIndex = orderIndex,
            estimatedMinutes = 7,
            steps = steps
        )
    }

    fun constraintQuestion(
        questionId: String,
        title: String,
        prompt: String,
        alternatives: List<String>,
        correctAlternative: String,
        choiceSteps: List<MediorChoiceStepSeed>,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int
    ): SeedQuestion {
        val first = SeedStep(
            stepId = "${questionId}_s1",
            type = StepType.SINGLE_CHOICE.id,
            title = "Izbor pristupa",
            instruction = "Izaberi pristup koji najbolje pokriva zahteve i ograničenja sistema.",
            requiredCount = 1,
            explanation = "Tačan odgovor: $correctAlternative.",
            options = alternatives.mapIndexed { index, option ->
                SeedOption(
                    optionId = "${questionId}_s1_o${index + 1}",
                    text = option,
                    optionOrder = index + 1,
                    isCorrect = option == correctAlternative
                )
            }
        )
        val generated = choiceSteps.mapIndexed { index, step -> choiceStep(questionId, index + 2, step) }
        val free = SeedStep(
            stepId = "${questionId}_s${generated.size + 2}",
            type = StepType.FREE_TEXT.id,
            title = "Kratko obrazloženje",
            instruction = "U jednoj ili dve rečenice objasni zašto je izabrani pristup bolji u datim ograničenjima.",
            explanation = "Odgovor treba da poveže izbor obrasca sa zahtevima i ograničenjima sistema."
        )
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.MEDIOR.id,
            type = QuestionType.CONSTRAINT_DECISION.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = "medium",
            orderIndex = orderIndex,
            estimatedMinutes = 7,
            steps = listOf(first) + generated + free
        )
    }

    fun consequenceQuestion(
        questionId: String,
        title: String,
        prompt: String,
        consequenceStep: MediorChoiceStepSeed?,
        categories: List<MediorCategorySeed>,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int
    ): SeedQuestion {
        val steps = mutableListOf<SeedStep>()
        if (consequenceStep != null) {
            steps.add(choiceStep(questionId, 1, consequenceStep))
        }
        if (categories.isNotEmpty()) {
            val stepNumber = steps.size + 1
            val zones = categories.mapIndexed { index, category ->
                SeedZone(
                    zoneId = "${questionId}_s${stepNumber}_z${index + 1}",
                    title = category.title,
                    zoneOrder = index + 1
                )
            }
            val zoneIdByTitle = zones.associateBy({ it.title }, { it.zoneId })
            val options = categories.flatMap { category -> category.items.map { it to category.title } }
            steps.add(
                SeedStep(
                    stepId = "${questionId}_s$stepNumber",
                    type = StepType.CATEGORIZATION.id,
                    title = "Rasporedi posledice po vrsti efekta",
                    instruction = "Prevuci svaku posledicu u odgovarajuću kategoriju efekta.",
                    requiredCount = options.size,
                    explanation = "Kategorije pomažu da se razlikuju dobici od cena, rizika i operativnih posledica.",
                    zones = zones,
                    options = options.mapIndexed { index, pair ->
                        SeedOption(
                            optionId = "${questionId}_s${stepNumber}_o${index + 1}",
                            text = pair.first,
                            optionOrder = index + 1,
                            correctZoneId = zoneIdByTitle.getValue(pair.second)
                        )
                    }
                )
            )
        }
        steps.add(
            SeedStep(
                stepId = "${questionId}_s${steps.size + 1}",
                type = StepType.FREE_TEXT.id,
                title = "Kratko obrazloženje",
                instruction = "Ukratko objasni ukupan efekat odluke na proširivost, održavanje ili operativnu složenost.",
                explanation = "Dobar odgovor navodi i dobitak i cenu uvedenog rešenja."
            )
        )

        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.MEDIOR.id,
            type = QuestionType.CONSEQUENCE_ANALYSIS.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = "medium",
            orderIndex = orderIndex,
            estimatedMinutes = 7,
            steps = steps
        )
    }

    private fun choiceStep(
        questionId: String,
        stepNumber: Int,
        step: MediorChoiceStepSeed
    ): SeedStep {
        val type = if (step.correctAnswers.size == 1) StepType.SINGLE_CHOICE.id else StepType.MULTI_CHOICE.id
        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = type,
            title = step.title,
            instruction = step.instruction,
            requiredCount = step.correctAnswers.size,
            explanation = step.explanation ?: "Tačno: ${step.correctAnswers.joinToString()}.",
            options = step.options.mapIndexed { index, option ->
                SeedOption(
                    optionId = "${questionId}_s${stepNumber}_o${index + 1}",
                    text = option,
                    optionOrder = index + 1,
                    isCorrect = step.correctAnswers.contains(option)
                )
            }
        )
    }
}
