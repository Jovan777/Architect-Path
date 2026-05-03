package com.example.pmuprojekat.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val userId: String = "local_user",

    val displayName: String = "Marko",
    val currentLevel: String = "beginner",

    val xp: Int = 0,
    val streakDays: Int = 0,
    val completedQuestions: Int = 0,

    val createdAt: Long = System.currentTimeMillis(),
    val lastActiveAt: Long = System.currentTimeMillis(),

    val isActive: Boolean = true
)