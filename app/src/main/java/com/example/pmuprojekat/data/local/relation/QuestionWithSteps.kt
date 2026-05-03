package com.example.pmuprojekat.data.local.relation


import androidx.room.Embedded
import androidx.room.Relation
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.QuestionStepEntity

data class QuestionWithSteps(
    @Embedded
    val question: QuestionEntity,

    @Relation(
        entity = QuestionStepEntity::class,
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val steps: List<StepWithContent> = emptyList()
)