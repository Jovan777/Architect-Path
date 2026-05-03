package com.example.pmuprojekat.data.local.entity


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "user_question_progress",
    primaryKeys = ["userId", "questionId"],
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
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["questionId"])
    ]
)
data class UserQuestionProgressEntity(
    val userId: String,
    val questionId: String,

    val status: String = "not_started",

    val attempts: Int = 0,
    val bestScorePercent: Int = 0,

    val startedAt: Long? = null,
    val completedAt: Long? = null,
    val updatedAt: Long = System.currentTimeMillis()
)