package com.example.pmuprojekat.data.local.entity


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "questions",
    indices = [
        Index(value = ["level"]),
        Index(value = ["type"]),
        Index(value = ["wave"])
    ]
)
data class QuestionEntity(
    @PrimaryKey
    val questionId: String,

    val level: String,
    val type: String,

    val title: String,
    val prompt: String,

    val aiFollowUp: String? = null,

    val wave: Int? = null,
    val difficulty: String = "easy",

    val orderIndex: Int = 0,
    val estimatedMinutes: Int = 3,

    val isActive: Boolean = true,
    val topic: String? = null,
    val patternName: String? = null
)