package com.example.pmuprojekat.data.seed

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
            addAll(ArchitectCompromiseSeed.questions)
        }
    }
}
