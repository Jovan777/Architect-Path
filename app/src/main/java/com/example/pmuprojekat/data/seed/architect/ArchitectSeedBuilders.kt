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
        val actualOptions = if (questionId.startsWith("A4.") && stepNumber == 1 && options.isEmpty()) {
            architectureCompositionComponentOptions(questionId, correctAnswers)
        } else {
            options
        }

        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = StepType.MULTI_CHOICE.id,
            title = title,
            instruction = instruction,
            requiredCount = correctAnswers.size,
            explanation = "Tačno: ${correctAnswers.joinToString()}.",
            options = actualOptions.mapIndexed { index, option ->
                val isCorrect = correctAnswers.contains(option)
                SeedOption(
                    optionId = "${questionId}_s${stepNumber}_o${index + 1}",
                    text = option,
                    optionOrder = index + 1,
                    isCorrect = isCorrect,
                    isDistractor = questionId.startsWith("A4.") && stepNumber == 1 && !isCorrect
                )
            }
        )
    }

    private fun architectureCompositionComponentOptions(
        questionId: String,
        correctAnswers: List<String>
    ): List<String> {
        val distractors = when (questionId) {
            "A4.1" -> listOf("Notification Service", "OCR/Receipt Processing Service")
            "A4.2" -> listOf("Notification Service", "AI Architecture Assistant", "Template Marketplace Service")
            "A4.3" -> listOf("Energy Analytics Store", "Predictive Optimization Engine", "Billing/Cost Allocation Service")
            "A4.4" -> listOf("Sales Analytics Store", "Kitchen Optimization Engine", "Direct Delivery-to-Kitchen Command")
            "A4.5" -> listOf("Route Optimization Engine", "Public Tracking Cache", "Billing Service")
            else -> listOf("AI Automation Service", "Direct Database Access Adapter")
        }

        return (correctAnswers + distractors).distinct()
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
        val cleanedCards = cards.map { cleanArchitectCardText(it) }
        val cleanedCorrectOrder = correctOrder.map { cleanArchitectCardText(it) }

        return SeedStep(
            stepId = "${questionId}_s$stepNumber",
            type = StepType.ORDERED_CARDS.id,
            title = title,
            instruction = instruction,
            requiredCount = cleanedCorrectOrder.size,
            explanation = "Tačan redosled: ${correctOrder.joinToString(" → ")}.",
            options = cleanedCards.mapIndexed { index, card ->
                SeedOption(
                    optionId = "${questionId}_s${stepNumber}_o${index + 1}",
                    text = card,
                    optionOrder = index + 1,
                    correctOrder = cleanedCorrectOrder.indexOf(card).takeIf { it >= 0 }?.plus(1),
                    isDistractor = !cleanedCorrectOrder.contains(card)
                )
            }
        )
    }

    private fun compositionBoardStep(
        questionId: String,
        stepNumber: Int,
        cards: List<String>,
        correctCards: List<String>
    ): SeedStep {
        val correctSet = correctCards.toSet()
        val intake = correctCards.take(2)
        val core = correctCards.drop(2).dropLast(2).ifEmpty { correctCards.drop(2) }
        val asyncRead = correctCards.takeLast(2).filterNot { core.contains(it) || intake.contains(it) }
        val traps = cards.filterNot { correctSet.contains(it) }

        val categories = buildList {
            add(
                ArchitectCategorySeed(
                    title = "Ulaz i pristup sistemu",
                    items = intake
                )
            )
            add(
                ArchitectCategorySeed(
                    title = "Domenska obrada i izvor istine",
                    items = core
                )
            )
            add(
                ArchitectCategorySeed(
                    title = "Asinhroni prikaz, status i citanje",
                    items = asyncRead
                )
            )
            if (traps.isNotEmpty()) {
                add(
                    ArchitectCategorySeed(
                        title = "Ne pripada glavnoj strukturi",
                        items = traps
                    )
                )
            }
        }.filter { it.items.isNotEmpty() }

        return categoryStep(
            questionId = questionId,
            stepNumber = stepNumber,
            title = "Sastavi arhitektonsku strukturu",
            instruction = "Rasporedi komponente i tokove u zone sistema. Zamke prebaci u zonu koja ne pripada glavnoj strukturi.",
            categories = categories
        )
    }

    private fun cleanArchitectCardText(text: String): String {
        return text
            .replace(Regex("\\s+"), " ")
            .substringBefore("Tačan redosled:")
            .substringBefore("Tačan raspored:")
            .substringBefore("Tačan izbor i redosled:")
            .substringBefore("Ta")
            .substringBefore("________________________________________")
            .trim()
    }

    fun categoryStep(
        questionId: String,
        stepNumber: Int,
        title: String,
        instruction: String,
        categories: List<ArchitectCategorySeed>
    ): SeedStep {
        val normalizedCategories = normalizeArchitectReviewCategories(
            questionId = questionId,
            stepNumber = stepNumber,
            categories = categories
        )

        val zones = normalizedCategories.mapIndexed { index, category ->
            SeedZone(
                zoneId = "${questionId}_s${stepNumber}_z${index + 1}",
                title = category.title,
                zoneOrder = index + 1
            )
        }
        val zoneIdByTitle = zones.associateBy({ it.title }, { it.zoneId })
        val pairs = normalizedCategories.flatMap { category -> category.items.map { it to category.title } }
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

    private fun normalizeArchitectReviewCategories(
        questionId: String,
        stepNumber: Int,
        categories: List<ArchitectCategorySeed>
    ): List<ArchitectCategorySeed> {
        if (!questionId.startsWith("A3.") || stepNumber != 4 || categories.size != 2) {
            return categories
        }

        val gainItems = categories.firstOrNull {
            it.title.contains("Dobici", ignoreCase = true)
        }?.items ?: categories.first().items

        val remainingItems = categories
            .filterNot { it.items == gainItems }
            .flatMap { it.items }

        val designItems = remainingItems.filter { item ->
            item.contains("potrebno", ignoreCase = true) ||
                    item.contains("mora", ignoreCase = true) ||
                    item.contains("pravila", ignoreCase = true) ||
                    item.contains("kontrolu", ignoreCase = true) ||
                    item.contains("invalidaciju", ignoreCase = true) ||
                    item.contains("projektovati", ignoreCase = true)
        }

        val riskItems = remainingItems - designItems.toSet()

        return listOf(
            ArchitectCategorySeed(
                title = "Dobici",
                items = gainItems
            ),
            ArchitectCategorySeed(
                title = "Novi rizici",
                items = riskItems.ifEmpty { remainingItems.take(1) }
            ),
            ArchitectCategorySeed(
                title = "Stvari koje treba projektovati",
                items = designItems.ifEmpty { remainingItems.drop(1) }
            )
        ).filter { it.items.isNotEmpty() }
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
