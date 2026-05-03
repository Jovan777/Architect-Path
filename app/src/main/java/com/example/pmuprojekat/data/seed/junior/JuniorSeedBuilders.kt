package com.example.pmuprojekat.data.seed.junior

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.data.seed.SeedBlank
import com.example.pmuprojekat.data.seed.SeedOption
import com.example.pmuprojekat.data.seed.SeedQuestion
import com.example.pmuprojekat.data.seed.SeedStep
import com.example.pmuprojekat.data.seed.SeedZone

internal data class JuniorChoiceStepSeed(
    val title: String,
    val instruction: String,
    val options: List<String>,
    val correctAnswers: List<String>,
    val explanation: String? = null
)

internal data class JuniorRoleCardSeed(
    val text: String,
    val correctRole: String
)

internal object JuniorSeedBuilders {

    fun codeCompletionQuestion(
        questionId: String,
        title: String,
        prompt: String,
        codeBlock: String,
        blanks: List<String>,
        aiFollowUp: String,
        orderIndex: Int,
        difficulty: String = "medium"
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.JUNIOR.id,
            type = QuestionType.CODE_COMPLETION.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = 1,
            difficulty = difficulty,
            orderIndex = orderIndex,
            estimatedMinutes = 4,
            steps = listOf(
                SeedStep(
                    stepId = "${questionId}_s1",
                    type = StepType.CODE_COMPLETION.id,
                    title = "Dopuna pseudo-koda",
                    instruction = "Popuni prazna mesta redom tako da pseudo-kod odgovara navedenom obrascu.",
                    requiredCount = blanks.size,
                    codeBlock = codeBlock.trimIndent(),
                    explanation = "Očekivane dopune redom: ${blanks.joinToString()}.",
                    blanks = blanks.mapIndexed { index, value ->
                        SeedBlank(
                            blankId = "${questionId}_s1_b${index + 1}",
                            blankOrder = index + 1,
                            correctValue = value
                        )
                    }
                )
            )
        )
    }

    fun roleMappingQuestion(
        questionId: String,
        title: String,
        prompt: String,
        roles: List<String>,
        cards: List<JuniorRoleCardSeed>,
        aiFollowUp: String,
        orderIndex: Int,
        difficulty: String = "medium"
    ): SeedQuestion {
        val zoneIdByRole = roles.mapIndexed { index, role -> role to "${questionId}_s1_z${index + 1}" }.toMap()

        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.JUNIOR.id,
            type = QuestionType.ROLE_MAPPING.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = 1,
            difficulty = difficulty,
            orderIndex = orderIndex,
            estimatedMinutes = 4,
            steps = listOf(
                SeedStep(
                    stepId = "${questionId}_s1",
                    type = StepType.ROLE_MAPPING.id,
                    title = "Mapiranje uloga",
                    instruction = "Prevuci svaku karticu klase u odgovarajuću ulogu obrasca.",
                    requiredCount = cards.size,
                    explanation = "Tačno mapiranje prati standardne uloge ovog obrasca.",
                    zones = roles.mapIndexed { index, role ->
                        SeedZone(
                            zoneId = zoneIdByRole.getValue(role),
                            title = role,
                            zoneOrder = index + 1
                        )
                    },
                    options = cards.mapIndexed { index, card ->
                        SeedOption(
                            optionId = "${questionId}_s1_o${index + 1}",
                            text = card.text,
                            optionOrder = index + 1,
                            correctZoneId = zoneIdByRole.getValue(card.correctRole)
                        )
                    }
                )
            )
        )
    }

    fun reasoningQuestion(
        questionId: String,
        title: String,
        prompt: String,
        steps: List<JuniorChoiceStepSeed>,
        aiFollowUp: String,
        orderIndex: Int,
        difficulty: String = "medium"
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.JUNIOR.id,
            type = QuestionType.REASONING.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = 1,
            difficulty = difficulty,
            orderIndex = orderIndex,
            estimatedMinutes = 5,
            steps = steps.mapIndexed { index, step ->
                choiceStep(
                    questionId = questionId,
                    stepNumber = index + 1,
                    step = step
                )
            }
        )
    }

    fun refactoringQuestion(
        questionId: String,
        title: String,
        prompt: String,
        codeBlock: String,
        steps: List<JuniorChoiceStepSeed>,
        aiFollowUp: String,
        orderIndex: Int,
        difficulty: String = "medium"
    ): SeedQuestion {
        val generatedSteps = steps.mapIndexed { index, step ->
            choiceStep(
                questionId = questionId,
                stepNumber = index + 1,
                step = step,
                codeBlock = if (index == 0) codeBlock.trimIndent() else null
            )
        }

        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.JUNIOR.id,
            type = QuestionType.REFACTORING.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = 1,
            difficulty = difficulty,
            orderIndex = orderIndex,
            estimatedMinutes = 6,
            steps = generatedSteps + SeedStep(
                stepId = "${questionId}_s${generatedSteps.size + 1}",
                type = StepType.FREE_TEXT.id,
                title = "Kratko obrazloženje",
                instruction = "U jednoj ili dve rečenice objasni zašto je izabrani obrazac bolje rešenje.",
                explanation = "Odgovor treba da pokaže razumevanje smanjenja sprege, čitljivosti ili proširivosti, u zavisnosti od zadatka."
            )
        )
    }

    fun errorDetectionQuestion(
        questionId: String,
        title: String,
        prompt: String,
        codeBlock: String,
        options: List<String>,
        correctAnswers: List<String>,
        explanation: String,
        aiFollowUp: String,
        orderIndex: Int,
        difficulty: String = "medium"
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.JUNIOR.id,
            type = QuestionType.ERROR_DETECTION.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = 1,
            difficulty = difficulty,
            orderIndex = orderIndex,
            estimatedMinutes = 4,
            steps = listOf(
                SeedStep(
                    stepId = "${questionId}_s1",
                    type = StepType.HOTSPOT.id,
                    title = "Označi problematičan deo",
                    instruction = "Izaberi liniju, deo koda ili opis koji predstavlja grešku u primeni obrasca.",
                    requiredCount = correctAnswers.size,
                    codeBlock = codeBlock.trimIndent(),
                    explanation = explanation,
                    options = options.mapIndexed { index, option ->
                        SeedOption(
                            optionId = "${questionId}_s1_o${index + 1}",
                            text = option,
                            optionOrder = index + 1,
                            isCorrect = correctAnswers.contains(option)
                        )
                    }
                )
            )
        )
    }

    private fun choiceStep(
        questionId: String,
        stepNumber: Int,
        step: JuniorChoiceStepSeed,
        codeBlock: String? = null
    ): SeedStep {
        val type = if (step.correctAnswers.size == 1) StepType.SINGLE_CHOICE.id else StepType.MULTI_CHOICE.id

        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = type,
            title = step.title,
            instruction = step.instruction,
            requiredCount = step.correctAnswers.size,
            codeBlock = codeBlock,
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
