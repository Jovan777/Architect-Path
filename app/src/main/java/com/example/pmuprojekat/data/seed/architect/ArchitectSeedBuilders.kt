package com.example.pmuprojekat.data.seed.architect

import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.data.seed.SeedOption
import com.example.pmuprojekat.data.seed.SeedStep
import com.example.pmuprojekat.data.seed.SeedZone

internal data class ArchitectCategorySeed(
    val title: String,
    val items: List<String>
)

internal object ArchitectSeedBuilders {

    fun singleChoiceStep(
        questionId: String,
        stepNumber: Int,
        title: String,
        instruction: String,
        options: List<String>,
        correctAnswer: String
    ): SeedStep {
        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = StepType.SINGLE_CHOICE.id,
            title = title,
            instruction = instruction,
            requiredCount = 1,
            explanation = "Tačan odgovor: $correctAnswer.",
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

    fun multiChoiceStep(
        questionId: String,
        stepNumber: Int,
        title: String,
        instruction: String,
        options: List<String>,
        correctAnswers: List<String>
    ): SeedStep {
        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = StepType.MULTI_CHOICE.id,
            title = title,
            instruction = instruction,
            requiredCount = correctAnswers.size,
            explanation = "Tačno: ${correctAnswers.joinToString()}.",
            options = options.mapIndexed { index, option ->
                SeedOption(
                    optionId = "${questionId}_s${stepNumber}_o${index + 1}",
                    text = option,
                    optionOrder = index + 1,
                    isCorrect = correctAnswers.contains(option)
                )
            }
        )
    }

    fun hotspotStep(
        questionId: String,
        stepNumber: Int,
        title: String,
        instruction: String,
        options: List<String>,
        correctAnswers: List<String>
    ): SeedStep {
        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = StepType.HOTSPOT.id,
            title = title,
            instruction = instruction,
            requiredCount = correctAnswers.size,
            explanation = "Označene tačke predstavljaju glavne arhitektonske slabosti ili problematične tokove.",
            options = options.mapIndexed { index, option ->
                SeedOption(
                    optionId = "${questionId}_s${stepNumber}_o${index + 1}",
                    text = option,
                    optionOrder = index + 1,
                    isCorrect = correctAnswers.contains(option)
                )
            }
        )
    }

    fun orderedCardsStep(
        questionId: String,
        stepNumber: Int,
        title: String,
        instruction: String,
        cards: List<String>,
        correctOrder: List<String>
    ): SeedStep {
        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = StepType.ORDERED_CARDS.id,
            title = title,
            instruction = instruction,
            requiredCount = correctOrder.size,
            explanation = "Tačan redosled: ${correctOrder.joinToString(" → ")}.",
            options = cards.mapIndexed { index, card ->
                SeedOption(
                    optionId = "${questionId}_s${stepNumber}_o${index + 1}",
                    text = card,
                    optionOrder = index + 1,
                    correctOrder = correctOrder.indexOf(card).takeIf { it >= 0 }?.plus(1),
                    isDistractor = !correctOrder.contains(card)
                )
            }
        )
    }

    fun categoryStep(
        questionId: String,
        stepNumber: Int,
        title: String,
        instruction: String,
        categories: List<ArchitectCategorySeed>
    ): SeedStep {
        val zones = categories.mapIndexed { index, category ->
            SeedZone(
                zoneId = "${questionId}_s${stepNumber}_z${index + 1}",
                title = category.title,
                zoneOrder = index + 1
            )
        }
        val zoneIdByTitle = zones.associateBy({ it.title }, { it.zoneId })
        val pairs = categories.flatMap { category -> category.items.map { it to category.title } }
        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = StepType.CATEGORIZATION.id,
            title = title,
            instruction = instruction,
            requiredCount = pairs.size,
            explanation = "Kategorizacija proverava da li korisnik razlikuje odgovornosti, dobitke, rizike i signale za promenu odluke.",
            zones = zones,
            options = pairs.mapIndexed { index, pair ->
                SeedOption(
                    optionId = "${questionId}_s${stepNumber}_o${index + 1}",
                    text = pair.first,
                    optionOrder = index + 1,
                    correctZoneId = zoneIdByTitle.getValue(pair.second)
                )
            }
        )
    }

    fun freeTextStep(
        questionId: String,
        stepNumber: Int,
        title: String,
        instruction: String
    ): SeedStep {
        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = StepType.FREE_TEXT.id,
            title = title,
            instruction = instruction,
            explanation = "Odgovor treba da poveže odluku, ograničenja, rizike i posledice po sistem."
        )
    }

    fun miniAdrStep(
        questionId: String,
        stepNumber: Int,
        title: String,
        instruction: String
    ): SeedStep {
        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = StepType.MINI_ADR.id,
            title = title,
            instruction = instruction,
            explanation = "Mini ADR treba da sadrži odluku, razlog, cenu koju prihvatamo i signal za preispitivanje odluke."
        )
    }
}
