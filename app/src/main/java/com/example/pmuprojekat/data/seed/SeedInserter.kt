package com.example.pmuprojekat.data.seed

import androidx.room.withTransaction
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.data.local.PMUDatabase
import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.dao.SeedMetaDao
import com.example.pmuprojekat.data.local.dao.UserDao
import com.example.pmuprojekat.data.local.entity.CodeBlankEntity
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.QuestionStepEntity
import com.example.pmuprojekat.data.local.entity.SeedMetaEntity
import com.example.pmuprojekat.data.local.entity.StepOptionEntity
import com.example.pmuprojekat.data.local.entity.StepZoneEntity
import com.example.pmuprojekat.data.local.entity.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeedInserter @Inject constructor(
    private val database: PMUDatabase,
    private val userDao: UserDao,
    private val questionDao: QuestionDao,
    private val seedMetaDao: SeedMetaDao
) {

    private companion object {
        const val CURRENT_SEED_VERSION = 6
    }

    suspend fun seedIfNeeded() {
        database.withTransaction {
            ensureDefaultUserExists()

            val seedQuestions = InitialSeedData.allQuestions()
            val meta = seedMetaDao.getSeedMeta()

            val shouldReseed =
                meta == null ||
                        meta.version != CURRENT_SEED_VERSION ||
                        meta.questionCount != seedQuestions.size

            if (!shouldReseed) return@withTransaction

            questionDao.clearAllQuestionData()

            insertQuestions(seedQuestions)
            insertSteps(seedQuestions)
            insertZones(seedQuestions)
            insertOptions(seedQuestions)
            insertBlanks(seedQuestions)

            seedMetaDao.upsertSeedMeta(
                SeedMetaEntity(
                    version = CURRENT_SEED_VERSION,
                    questionCount = seedQuestions.size
                )
            )
        }
    }

    private suspend fun ensureDefaultUserExists() {
        val existingUser = userDao.getUserById("local_user")

        if (existingUser == null) {
            userDao.upsertUser(
                UserEntity(
                    userId = "local_user",
                    displayName = "Marko",
                    currentLevel = "beginner",
                    xp = 0,
                    streakDays = 0,
                    completedQuestions = 0
                )
            )
        }
    }

    private suspend fun insertQuestions(seedQuestions: List<SeedQuestion>) {
        questionDao.insertQuestions(
            seedQuestions.map { question ->
                QuestionEntity(
                    questionId = question.questionId,
                    level = question.level,
                    type = question.type,
                    title = question.title,
                    prompt = question.prompt,
                    diagramImageName = question.diagramImageName,
                    aiFollowUp = question.aiFollowUp,
                    wave = question.wave,
                    difficulty = question.difficulty,
                    orderIndex = question.orderIndex,
                    estimatedMinutes = question.estimatedMinutes,
                    isActive = true
                )
            }
        )
    }

    private suspend fun insertSteps(seedQuestions: List<SeedQuestion>) {
        questionDao.insertSteps(
            seedQuestions.flatMap { question ->
                question.steps.mapIndexed { index, step ->
                    val isAutoEvaluated =
                        step.type != StepType.FREE_TEXT.id &&
                                step.type != StepType.MINI_ADR.id

                    QuestionStepEntity(
                        stepId = step.stepId,
                        questionId = question.questionId,
                        stepOrder = index + 1,
                        type = step.type,
                        title = step.title,
                        instruction = step.instruction,
                        requiredCount = step.requiredCount,
                        codeBlock = step.codeBlock,
                        explanation = step.explanation,
                        isRequired = true,
                        isAutoEvaluated = isAutoEvaluated,
                        points = if (isAutoEvaluated) 1 else 0
                    )
                }
            }
        )
    }

    private suspend fun insertZones(seedQuestions: List<SeedQuestion>) {
        questionDao.insertZones(
            seedQuestions.flatMap { question ->
                question.steps.flatMap { step ->
                    step.zones.map { zone ->
                        StepZoneEntity(
                            zoneId = zone.zoneId,
                            stepId = step.stepId,
                            title = zone.title,
                            zoneOrder = zone.zoneOrder
                        )
                    }
                }
            }
        )
    }

    private suspend fun insertOptions(seedQuestions: List<SeedQuestion>) {
        questionDao.insertOptions(
            seedQuestions.flatMap { question ->
                question.steps.flatMap { step ->
                    step.options.map { option ->
                        StepOptionEntity(
                            optionId = option.optionId,
                            stepId = step.stepId,
                            label = option.label,
                            text = option.text,
                            optionOrder = option.optionOrder,
                            isCorrect = option.isCorrect,
                            correctOrder = option.correctOrder,
                            correctZoneId = option.correctZoneId,
                            isDistractor = option.isDistractor,
                            metadata = option.metadata
                        )
                    }
                }
            }
        )
    }

    private suspend fun insertBlanks(seedQuestions: List<SeedQuestion>) {
        questionDao.insertBlanks(
            seedQuestions.flatMap { question ->
                question.steps.flatMap { step ->
                    step.blanks.map { blank ->
                        CodeBlankEntity(
                            blankId = blank.blankId,
                            stepId = step.stepId,
                            blankOrder = blank.blankOrder,
                            placeholder = blank.placeholder,
                            correctValue = blank.correctValue
                        )
                    }
                }
            }
        )
    }
}
