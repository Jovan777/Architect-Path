package com.example.pmuprojekat.data.local.entity


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "step_zones",
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
data class StepZoneEntity(
    @PrimaryKey
    val zoneId: String,

    val stepId: String,

    val title: String,
    val zoneOrder: Int = 0
)