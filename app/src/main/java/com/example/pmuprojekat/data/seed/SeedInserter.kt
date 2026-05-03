package com.example.pmuprojekat.data.seed

import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.dao.UserDao
import com.example.pmuprojekat.data.local.entity.CodeBlankEntity
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.QuestionStepEntity
import com.example.pmuprojekat.data.local.entity.StepOptionEntity
import com.example.pmuprojekat.data.local.entity.StepZoneEntity
import com.example.pmuprojekat.data.local.entity.UserEntity

class SeedInserter(
    private val userDao: UserDao,
    private val questionDao: QuestionDao
) {
    suspend fun seedIfNeeded() {
        if (questionDao.countQuestions() > 0) return

        userDao.upsertUser(UserEntity())

        val seedQuestions = InitialSeedData.allQuestions()

        questionDao.insertQuestions(
            seedQuestions.map { question ->
                QuestionEntity(
                    questionId = question.questionId,
                    level = question.level,
                    type = question.type,
                    title = question.title,
                    prompt = question.prompt,
                    aiFollowUp = question.aiFollowUp,
                    wave = question.wave,
                    difficulty = question.difficulty,
                    orderIndex = question.orderIndex,
                    estimatedMinutes = question.estimatedMinutes,
                    isActive = true
                )
            }
        )

        questionDao.insertSteps(
            seedQuestions.flatMap { question ->
                question.steps.mapIndexed { index, step ->
                    QuestionStepEntity(
                        stepId = step.stepId,
                        questionId = question.questionId,
                        stepOrder = index + 1,
                        type = step.type,
                        title = step.title,
                        instruction = step.instruction,
                        requiredCount = step.requiredCount,
                        codeBlock = step.codeBlock,
                        explanation = step.explanation
                    )
                }
            }
        )

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
