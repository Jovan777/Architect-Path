package com.example.pmuprojekat.data.local.entity


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question_steps",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["questionId"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["questionId"]),
        Index(value = ["type"])
    ]
)
data class QuestionStepEntity(
    @PrimaryKey
    val stepId: String,

    val questionId: String,

    val stepOrder: Int,
    val type: String,

    val title: String,
    val instruction: String,

    val requiredCount: Int? = null,
    val codeBlock: String? = null,

    val explanation: String? = null,

    val isRequired: Boolean = true,
    val isAutoEvaluated: Boolean = true,
    val points: Int = 1
)