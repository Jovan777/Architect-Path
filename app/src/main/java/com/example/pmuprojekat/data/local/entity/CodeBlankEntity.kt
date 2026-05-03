package com.example.pmuprojekat.data.local.entity


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "code_blanks",
    foreignKeys = [
        ForeignKey(
            entity = QuestionStepEntity::class,
            parentColumns = ["stepId"],
            childColumns = ["stepId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["stepId"])
    ]
)
data class CodeBlankEntity(
    @PrimaryKey
    val blankId: String,

    val stepId: String,

    val blankOrder: Int,
    val placeholder: String = "__________",
    val correctValue: String
)