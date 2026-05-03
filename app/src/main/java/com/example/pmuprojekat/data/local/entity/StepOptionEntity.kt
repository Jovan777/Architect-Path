package com.example.pmuprojekat.data.local.entity


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "step_options",
    foreignKeys = [
        ForeignKey(
            entity = QuestionStepEntity::class,
            parentColumns = ["stepId"],
            childColumns = ["stepId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["stepId"]),
        Index(value = ["correctZoneId"])
    ]
)
data class StepOptionEntity(
    @PrimaryKey
    val optionId: String,

    val stepId: String,

    val label: String? = null,
    val text: String,

    val optionOrder: Int = 0,

    /**
     * Za single_choice i multi_choice.
     */
    val isCorrect: Boolean = false,

    /**
     * Za ordered_cards.
     */
    val correctOrder: Int? = null,

    /**
     * Za categorization i role_mapping.
     */
    val correctZoneId: String? = null,

    /**
     * Za distraktore kod ordered/categorization zadataka.
     */
    val isDistractor: Boolean = false,

    /**
     * Kasnije može da čuva imageKey, umlKey, codeLineStart, codeLineEnd itd.
     */
    val metadata: String? = null
)