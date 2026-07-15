package com.example.pmuprojekat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pmuprojekat.data.local.converters.RoomConverters
import com.example.pmuprojekat.data.local.dao.AiChatDao
import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.dao.SeedMetaDao
import com.example.pmuprojekat.data.local.dao.UserAnswerDao
import com.example.pmuprojekat.data.local.dao.UserDao
import com.example.pmuprojekat.data.local.dao.UserTaskSubmissionDao
import com.example.pmuprojekat.data.local.dao.VsAiDao
import com.example.pmuprojekat.data.local.entity.AiChatConversationEntity
import com.example.pmuprojekat.data.local.entity.AiChatMessageEntity
import com.example.pmuprojekat.data.local.entity.CodeBlankEntity
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.QuestionStepEntity
import com.example.pmuprojekat.data.local.entity.SeedMetaEntity
import com.example.pmuprojekat.data.local.entity.StepOptionEntity
import com.example.pmuprojekat.data.local.entity.StepZoneEntity
import com.example.pmuprojekat.data.local.entity.UserEntity
import com.example.pmuprojekat.data.local.entity.UserQuestionProgressEntity
import com.example.pmuprojekat.data.local.entity.UserStepAnswerEntity
import com.example.pmuprojekat.data.local.entity.UserTaskSubmissionEntity
import com.example.pmuprojekat.data.local.entity.VsAiAttemptEntity
import com.example.pmuprojekat.data.local.entity.VsAiMessageEntity

@Database(
    entities = [
        UserEntity::class,
        QuestionEntity::class,
        QuestionStepEntity::class,
        StepOptionEntity::class,
        StepZoneEntity::class,
        CodeBlankEntity::class,
        UserQuestionProgressEntity::class,
        UserStepAnswerEntity::class,
        UserTaskSubmissionEntity::class,
        AiChatConversationEntity::class,
        AiChatMessageEntity::class,
        VsAiAttemptEntity::class,
        VsAiMessageEntity::class,
        SeedMetaEntity::class
    ],
    version = 9,
    exportSchema = true
)
@TypeConverters(RoomConverters::class)
abstract class PMUDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun questionDao(): QuestionDao
    abstract fun userAnswerDao(): UserAnswerDao
    abstract fun seedMetaDao(): SeedMetaDao
    abstract fun userTaskSubmissionDao(): UserTaskSubmissionDao
    abstract fun aiChatDao(): AiChatDao
    abstract fun vsAiDao(): VsAiDao
}
