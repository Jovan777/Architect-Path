package com.example.pmuprojekat.data.local.relation


import androidx.room.Embedded
import androidx.room.Relation
import com.example.pmuprojekat.data.local.entity.CodeBlankEntity
import com.example.pmuprojekat.data.local.entity.QuestionStepEntity
import com.example.pmuprojekat.data.local.entity.StepOptionEntity
import com.example.pmuprojekat.data.local.entity.StepZoneEntity

data class StepWithContent(
    @Embedded
    val step: QuestionStepEntity,

    @Relation(
        parentColumn = "stepId",
        entityColumn = "stepId"
    )
    val options: List<StepOptionEntity> = emptyList(),

    @Relation(
        parentColumn = "stepId",
        entityColumn = "stepId"
    )
    val zones: List<StepZoneEntity> = emptyList(),

    @Relation(
        parentColumn = "stepId",
        entityColumn = "stepId"
    )
    val blanks: List<CodeBlankEntity> = emptyList()
)