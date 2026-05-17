package com.example.pmuprojekat.data.seed

import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.data.seed.beginner.BeginnerWave1Seed
import com.example.pmuprojekat.data.seed.beginner.BeginnerWave2Seed
import com.example.pmuprojekat.data.seed.beginner.BeginnerWave3Seed
import com.example.pmuprojekat.data.seed.junior.JuniorCodeCompletionSeed
import com.example.pmuprojekat.data.seed.junior.JuniorErrorDetectionSeed
import com.example.pmuprojekat.data.seed.junior.JuniorReasoningSeed
import com.example.pmuprojekat.data.seed.junior.JuniorRefactoringSeed
import com.example.pmuprojekat.data.seed.junior.JuniorRoleMappingSeed
import com.example.pmuprojekat.data.seed.medior.MediorConsequenceAnalysisSeed
import com.example.pmuprojekat.data.seed.medior.MediorConstraintDecisionSeed
import com.example.pmuprojekat.data.seed.medior.MediorRequirementMappingSeed
import com.example.pmuprojekat.data.seed.medior.MediorSequenceLogicSeed
import com.example.pmuprojekat.data.seed.medior.MediorSystemCodeSeed
import com.example.pmuprojekat.data.seed.senior.SeniorIncidentAnalysisSeed
import com.example.pmuprojekat.data.seed.senior.SeniorOptimizationStrategySeed
import com.example.pmuprojekat.data.seed.senior.SeniorPrioritizationSeed
import com.example.pmuprojekat.data.seed.senior.SeniorProductionDiagnosisSeed
import com.example.pmuprojekat.data.seed.senior.SeniorTradeOffSeed

import com.example.pmuprojekat.data.seed.architect.ArchitectCompromiseSeed
import com.example.pmuprojekat.data.seed.architect.ArchitectCompositionSeed
import com.example.pmuprojekat.data.seed.architect.ArchitectExtensionSeed
import com.example.pmuprojekat.data.seed.architect.ArchitectReviewSeed
import com.example.pmuprojekat.data.seed.architect.ArchitectScalingSeed
import com.example.pmuprojekat.data.seed.architect.ArchitectStyleSeed

object InitialSeedData {
    fun allQuestions(): List<SeedQuestion> {
        return buildList {
            addAll(BeginnerWave1Seed.questions)
            addAll(BeginnerWave2Seed.questions)
            addAll(BeginnerWave3Seed.questions)

            addAll(JuniorCodeCompletionSeed.questions)
            addAll(JuniorRoleMappingSeed.questions)
            addAll(JuniorReasoningSeed.questions)
            addAll(JuniorRefactoringSeed.questions)
            addAll(JuniorErrorDetectionSeed.questions)

            addAll(MediorSequenceLogicSeed.questions)
            addAll(MediorSystemCodeSeed.questions)
            addAll(MediorRequirementMappingSeed.questions)
            addAll(MediorConstraintDecisionSeed.questions)
            addAll(MediorConsequenceAnalysisSeed.questions)

            addAll(SeniorProductionDiagnosisSeed.questions)
            addAll(SeniorOptimizationStrategySeed.questions)
            addAll(SeniorIncidentAnalysisSeed.questions)
            addAll(SeniorTradeOffSeed.questions)
            addAll(SeniorPrioritizationSeed.questions)

            addAll(ArchitectExtensionSeed.questions)
            addAll(ArchitectStyleSeed.questions)
            addAll(ArchitectReviewSeed.questions)
            addAll(ArchitectCompositionSeed.questions)
            addAll(ArchitectScalingSeed.questions)
            addAll(ArchitectCompromiseSeed.questions.map { it.asArchitectType6Workspace() })
        }
    }

    private fun SeedQuestion.asArchitectType6Workspace(): SeedQuestion {
        if (!questionId.startsWith("A6.")) return this

        val pressureSteps = steps.filter { step ->
            step.type == StepType.SINGLE_CHOICE.id &&
                    (step.stepId.endsWith("_s2") || step.stepId.endsWith("_s3") || step.stepId.endsWith("_s4"))
        }
        val priorityStep = steps.firstOrNull { it.stepId.endsWith("_s5") }
        val signalStep = steps.firstOrNull { it.stepId.endsWith("_s6") }
        val adrStep = steps.firstOrNull { it.stepId.endsWith("_s7") }

        if (pressureSteps.size != 3 || priorityStep == null || signalStep == null || adrStep == null) {
            return this
        }

        return copy(
            steps = buildList {
                add(steps.first())
                add(buildDefenseBoardStep(questionId, pressureSteps))
                add(remapCategoryStep(priorityStep, questionId, 3, priorityZoneTitles))
                add(remapCategoryStep(signalStep, questionId, 4, signalZoneTitles))
                add(
                    adrStep.copy(
                        stepId = "${questionId}_s5",
                        title = "Mini ADR zapis odluke"
                    )
                )
            }
        )
    }

    private val priorityZoneTitles = listOf(
        "Do now",
        "Leave for later",
        "Do not do / wrong direction"
    )

    private val signalZoneTitles = listOf(
        "Real signal for changing the architectural decision",
        "False signal",
        "Dangerous signal that requires caution, but not necessarily an architecture change"
    )

    private fun buildDefenseBoardStep(
        questionId: String,
        pressureSteps: List<SeedStep>
    ): SeedStep {
        val zones = pressureSteps.mapIndexed { index, pressure ->
            SeedZone(
                zoneId = "${questionId}_s2_z${index + 1}",
                title = pressure.title.cleanSeedLabel(),
                zoneOrder = index + 1
            )
        }

        val options = pressureSteps.flatMapIndexed { pressureIndex, pressure ->
            val zoneId = zones[pressureIndex].zoneId
            pressure.options.mapIndexed { optionIndex, option ->
                val correct = option.isCorrect
                SeedOption(
                    optionId = "${questionId}_s2_o${pressureIndex + 1}_${optionIndex + 1}",
                    text = option.text.cleanAnswerPrefix(),
                    optionOrder = pressureIndex * 10 + optionIndex + 1,
                    correctZoneId = if (correct) zoneId else null,
                    isDistractor = !correct
                )
            }
        }

        return SeedStep(
            stepId = "${questionId}_s2",
            type = StepType.CATEGORIZATION.id,
            title = "Architectural Defense Board",
            instruction = "Assign the strongest defense to each pressure challenging the architectural decision.",
            requiredCount = pressureSteps.size,
            explanation = "A strong defense answers each pressure without abandoning the core architectural decision.",
            zones = zones,
            options = options
        )
    }

    private fun remapCategoryStep(
        step: SeedStep,
        questionId: String,
        newStepNumber: Int,
        targetZoneTitles: List<String>
    ): SeedStep {
        val oldZones = step.zones.sortedBy { it.zoneOrder }
        val zoneMapping = oldZones.mapIndexed { index, oldZone ->
            oldZone.zoneId to "${questionId}_s${newStepNumber}_z${index + 1}"
        }.toMap()

        val newZones = oldZones.mapIndexed { index, _ ->
            SeedZone(
                zoneId = "${questionId}_s${newStepNumber}_z${index + 1}",
                title = targetZoneTitles.getOrElse(index) { oldZones[index].title },
                zoneOrder = index + 1
            )
        }

        return step.copy(
            stepId = "${questionId}_s$newStepNumber",
            title = when (newStepNumber) {
                3 -> "Architectural Priority Board"
                4 -> "Signal Analysis Board"
                else -> step.title
            },
            instruction = when (newStepNumber) {
                3 -> "Place each decision on the architectural priority board."
                4 -> "Classify each signal as evidence, noise, or caution."
                else -> step.instruction
            },
            zones = newZones,
            options = step.options.mapIndexed { index, option ->
                option.copy(
                    optionId = "${questionId}_s${newStepNumber}_o${index + 1}",
                    correctZoneId = option.correctZoneId?.let { zoneMapping[it] }
                )
            }
        )
    }

    private fun String.cleanSeedLabel(): String {
        return lines()
            .joinToString(" ") { it.trim() }
            .replace("â€”", "-")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    private fun String.cleanAnswerPrefix(): String {
        return cleanSeedLabel().replace(Regex("^[A-E]\\.\\s*"), "")
    }
}
