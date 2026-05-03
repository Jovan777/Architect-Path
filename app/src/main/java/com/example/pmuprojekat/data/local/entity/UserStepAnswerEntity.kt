package com.example.pmuprojekat.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "user_step_answers",
    primaryKeys = ["userId", "questionId", "stepId"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["questionId"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = QuestionStepEntity::class,
            parentColumns = ["stepId"],
            childColumns = ["stepId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["questionId"]),
        Index(value = ["stepId"])
    ]
)
data class UserStepAnswerEntity(
    val userId: String,
    val questionId: String,
    val stepId: String,

    val selectedOptionIds: List<String> = emptyList(),
    val orderedOptionIds: List<String> = emptyList(),

    /**
     * Za role_mapping i categorization:
     * key = optionId
     * value = zoneId
     */
    val mappedZoneByOptionId: Map<String, String> = emptyMap(),

    /**
     * Za code_completion:
     * key = blankId
     * value = korisnikov odgovor
     */
    val blankAnswersByBlankId: Map<String, String> = emptyMap(),

    val freeTextAnswer: String? = null,

    val isCorrect: Boolean = false,
    val answeredAt: Long = System.currentTimeMillis()
)