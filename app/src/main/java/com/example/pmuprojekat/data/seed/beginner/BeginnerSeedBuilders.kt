package com.example.pmuprojekat.data.seed.beginner

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.data.seed.SeedOption
import com.example.pmuprojekat.data.seed.SeedQuestion
import com.example.pmuprojekat.data.seed.SeedStep

internal data class ChoiceStepSeed(
    val title: String,
    val instruction: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String? = null
)

internal data class VisualOptionSeed(
    val label: String,
    val text: String,
    val isCorrect: Boolean
)

internal object BeginnerSeedBuilders {
    fun patternRecognitionQuestion(
        questionId: String,
        title: String,
        prompt: String,
        options: List<String>,
        correctAnswer: String,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int,
        difficulty: String = "easy"
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.BEGINNER.id,
            type = QuestionType.PATTERN_RECOGNITION.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = difficulty,
            orderIndex = orderIndex,
            estimatedMinutes = 2,
            steps = listOf(
                choiceStep(
                    questionId = questionId,
                    stepNumber = 1,
                    title = "Izbor obrasca",
                    instruction = "Izaberi obrazac projektovanja koji najbolje odgovara opisu.",
                    options = options,
                    correctAnswer = correctAnswer
                )
            )
        )
    }

    fun visualMappingQuestion(
        questionId: String,
        targetPattern: String,
        options: List<VisualOptionSeed>,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int,
        difficulty: String = "easy"
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.BEGINNER.id,
            type = QuestionType.VISUAL_MAPPING.id,
            title = "Visual Mapping — $targetPattern",
            prompt = "Izaberi UML/vizuelni opis koji najbolje odgovara obrascu $targetPattern.",
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = difficulty,
            orderIndex = orderIndex,
            estimatedMinutes = 3,
            steps = listOf(
                SeedStep(
                    stepId = "${questionId}_s1",
                    type = StepType.VISUAL_MAPPING.id,
                    title = "Izbor vizuelnog prikaza",
                    instruction = "Uporedi ponuđene UML opise i izaberi onaj koji prikazuje obrazac $targetPattern.",
                    requiredCount = 1,
                    explanation = "Tačan prikaz je onaj koji odgovara obrascu $targetPattern.",
                    options = options.mapIndexed { index, option ->
                        SeedOption(
                            optionId = "${questionId}_s1_o${index + 1}",
                            label = option.label,
                            text = option.text,
                            optionOrder = index + 1,
                            isCorrect = option.isCorrect,
                            isDistractor = !option.isCorrect
                        )
                    }
                )
            )
        )
    }

    fun multiStepUnderstandingQuestion(
        questionId: String,
        patternName: String,
        prompt: String,
        steps: List<ChoiceStepSeed>,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int,
        difficulty: String = "easy"
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.BEGINNER.id,
            type = QuestionType.MULTI_STEP_UNDERSTANDING.id,
            title = "Višekoračno razumevanje — $patternName",
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = difficulty,
            orderIndex = orderIndex,
            estimatedMinutes = 4,
            steps = steps.mapIndexed { index, step ->
                choiceStep(
                    questionId = questionId,
                    stepNumber = index + 1,
                    title = step.title,
                    instruction = step.instruction,
                    options = step.options,
                    correctAnswer = step.correctAnswer,
                    explanation = step.explanation
                )
            }
        )
    }

    fun patternComparisonQuestion(
        questionId: String,
        title: String,
        scenario: String,
        steps: List<ChoiceStepSeed>,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int,
        difficulty: String = "easy"
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.BEGINNER.id,
            type = QuestionType.PATTERN_COMPARISON.id,
            title = title,
            prompt = scenario,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = difficulty,
            orderIndex = orderIndex,
            estimatedMinutes = 4,
            steps = steps.mapIndexed { index, step ->
                choiceStep(
                    questionId = questionId,
                    stepNumber = index + 1,
                    title = step.title,
                    instruction = step.instruction,
                    options = step.options,
                    correctAnswer = step.correctAnswer,
                    explanation = step.explanation
                )
            }
        )
    }

    private fun choiceStep(
        questionId: String,
        stepNumber: Int,
        title: String,
        instruction: String,
        options: List<String>,
        correctAnswer: String,
        explanation: String? = null
    ): SeedStep {
        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = StepType.SINGLE_CHOICE.id,
            title = title,
            instruction = instruction,
            requiredCount = 1,
            explanation = explanation ?: "Tačan odgovor je: $correctAnswer.",
            options = options.mapIndexed { index, option ->
                SeedOption(
                    optionId = "${questionId}_s${stepNumber}_o${index + 1}",
                    text = option,
                    optionOrder = index + 1,
                    isCorrect = option == correctAnswer
                )
            }
        )
    }
}
