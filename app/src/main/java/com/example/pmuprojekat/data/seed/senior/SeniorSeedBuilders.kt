package com.example.pmuprojekat.data.seed.senior

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.data.seed.SeedOption
import com.example.pmuprojekat.data.seed.SeedQuestion
import com.example.pmuprojekat.data.seed.SeedStep
import com.example.pmuprojekat.data.seed.SeedZone

internal data class SeniorChoiceStepSeed(
    val title: String,
    val instruction: String,
    val options: List<String>,
    val correctAnswers: List<String>
)

internal data class SeniorCategorySeed(
    val title: String,
    val items: List<String>
)

internal object SeniorSeedBuilders {

    fun diagnosisQuestion(
        questionId: String,
        title: String,
        prompt: String,
        symptomCards: List<String>,
        symptoms: List<String>,
        causes: List<String>,
        componentOptions: List<String>,
        correctComponent: String,
        consequenceOptions: List<String>,
        correctConsequences: List<String>,
        interventionOptions: List<String>,
        correctIntervention: String,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int
    ): SeedQuestion {
        val categoryStepNumber = 1
        val zones = listOf(
            SeedZone("${questionId}_s${categoryStepNumber}_z1", "Simptomi", 1),
            SeedZone("${questionId}_s${categoryStepNumber}_z2", "Mogući uzroci", 2)
        )
        val options = symptomCards.mapIndexed { index, card ->
            val zone = when {
                symptoms.contains(card) -> zones[0].zoneId
                causes.contains(card) -> zones[1].zoneId
                else -> null
            }
            SeedOption(
                optionId = "${questionId}_s${categoryStepNumber}_o${index + 1}",
                text = card,
                optionOrder = index + 1,
                correctZoneId = zone
            )
        }

        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.SENIOR.id,
            type = QuestionType.PRODUCTION_DIAGNOSIS.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = "hard",
            orderIndex = orderIndex,
            estimatedMinutes = 8,
            steps = listOf(
                SeedStep(
                    stepId = "${questionId}_s1",
                    type = StepType.CATEGORIZATION.id,
                    title = "Razdvoji simptome i moguće uzroke",
                    instruction = "Prevuci svaku karticu u odgovarajuću zonu.",
                    requiredCount = symptomCards.size,
                    explanation = "Simptomi opisuju vidljive posledice, a uzroci objašnjavaju mehanizam nastanka problema.",
                    zones = zones,
                    options = options
                ),
                singleChoiceStep(questionId, 2, "Problematična komponenta ili tok", "Izaberi deo sistema koji je najverovatnije centralni izvor problema.", componentOptions, correctComponent),
                multiChoiceStep(questionId, 3, "Najrelevantnije posledice", "Izaberi posledice koje najbolje prate opisani kvar.", consequenceOptions, correctConsequences),
                singleChoiceStep(questionId, 4, "Prvi korak istrage / početne intervencije", "Izaberi najrazumniji prvi korak istrage ili stabilizacije.", interventionOptions, correctIntervention),
                freeTextStep(questionId, 5, "Kratko obrazloženje", "Objasni zašto je izabrani tok ili komponenta centralni problem u incidentu.")
            )
        )
    }

    fun optimizationQuestion(
        questionId: String,
        title: String,
        prompt: String,
        strategyOptions: List<String>,
        correctStrategy: String,
        gains: List<String>,
        risks: List<String>,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.SENIOR.id,
            type = QuestionType.OPTIMIZATION_STRATEGY.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = "hard",
            orderIndex = orderIndex,
            estimatedMinutes = 7,
            steps = listOf(
                singleChoiceStep(questionId, 1, "Izbor strategije optimizacije", "Izaberi strategiju koja najbolje rešava dominantno usko grlo.", strategyOptions, correctStrategy),
                categoryStep(questionId, 2, "Rasporedi posledice optimizacije", "Razdvoji šta se dobija od mogućih rizika i cena.", listOf(
                    SeniorCategorySeed("Šta se dobija", gains),
                    SeniorCategorySeed("Šta se gubi / rizici", risks)
                )),
                freeTextStep(questionId, 3, "Kratko obrazloženje", "Objasni zašto izabrana optimizacija bolje pogađa uzrok problema od ostalih alternativa.")
            )
        )
    }

    fun incidentQuestion(
        questionId: String,
        title: String,
        prompt: String,
        interventionOptions: List<String>,
        correctIntervention: String,
        monitorOptions: List<String>,
        correctMonitorOptions: List<String>,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.SENIOR.id,
            type = QuestionType.INCIDENT_ANALYSIS.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = "hard",
            orderIndex = orderIndex,
            estimatedMinutes = 7,
            steps = listOf(
                singleChoiceStep(questionId, 1, "Najprioritetnija intervencija", "Izaberi intervenciju koja prvo smanjuje poslovni ili bezbednosni rizik incidenta.", interventionOptions, correctIntervention),
                multiChoiceStep(questionId, 2, "Metrike za praćenje posle intervencije", "Izaberi metrike ili signale koje treba odmah pratiti.", monitorOptions, correctMonitorOptions),
                freeTextStep(questionId, 3, "Kratko obrazloženje", "Objasni zašto je ova intervencija prioritetnija od šire optimizacije ili kozmetičke izmene.")
            )
        )
    }

    fun tradeOffQuestion(
        questionId: String,
        title: String,
        prompt: String,
        solutionOptions: List<String>,
        correctSolution: String,
        gains: List<String>,
        costs: List<String>,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.SENIOR.id,
            type = QuestionType.TRADE_OFF.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = "hard",
            orderIndex = orderIndex,
            estimatedMinutes = 8,
            steps = listOf(
                singleChoiceStep(questionId, 1, "Izaberi najprikladniji kompromis", "Izaberi rešenje koje najbolje čuva poslovnu granicu i prihvatljiv nivo složenosti.", solutionOptions, correctSolution),
                categoryStep(questionId, 2, "Popuni trade-off", "Rasporedi dobiti i cenu izabranog rešenja.", listOf(
                    SeniorCategorySeed("Šta se dobija", gains),
                    SeniorCategorySeed("Šta se gubi / prihvata kao cena", costs)
                )),
                freeTextStep(questionId, 3, "Završno obrazloženje", "Objasni zašto najbolji izbor nije samo najbrži ili najjednostavniji, već najadekvatniji za dati sistem.")
            )
        )
    }

    fun prioritizationQuestion(
        questionId: String,
        title: String,
        prompt: String,
        interventionCards: List<String>,
        correctOrder: List<String>,
        aiFollowUp: String,
        wave: Int,
        orderIndex: Int
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.SENIOR.id,
            type = QuestionType.PRIORITIZATION.id,
            title = title,
            prompt = prompt,
            aiFollowUp = aiFollowUp,
            wave = wave,
            difficulty = "hard",
            orderIndex = orderIndex,
            estimatedMinutes = 7,
            steps = listOf(
                SeedStep(
                    stepId = "${questionId}_s1",
                    type = StepType.ORDERED_CARDS.id,
                    title = "Odredi prioritetni redosled intervencija",
                    instruction = "Poređaj intervencije od najhitnije do najmanje prioritetne u datom incidentu.",
                    requiredCount = correctOrder.size,
                    explanation = "Tačan redosled: ${correctOrder.joinToString(" → ")}.",
                    options = interventionCards.mapIndexed { index, card ->
                        SeedOption(
                            optionId = "${questionId}_s1_o${index + 1}",
                            text = card,
                            optionOrder = index + 1,
                            correctOrder = correctOrder.indexOf(card).takeIf { it >= 0 }?.plus(1),
                            isDistractor = !correctOrder.contains(card)
                        )
                    }
                ),
                freeTextStep(questionId, 2, "Kratko obrazloženje", "Objasni zašto je prvi korak prioritetan i zašto neke privlačne intervencije dolaze kasnije.")
            )
        )
    }

    private fun singleChoiceStep(
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

    private fun multiChoiceStep(
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

    private fun categoryStep(
        questionId: String,
        stepNumber: Int,
        title: String,
        instruction: String,
        categories: List<SeniorCategorySeed>
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
            explanation = "Kategorije pomažu da se jasno razlikuju dobici od cena, rizika i posledica.",
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

    private fun freeTextStep(
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
            explanation = "Odgovor treba da poveže simptome, uzrok, ograničenja i posledice odluke."
        )
    }
}
