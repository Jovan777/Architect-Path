package com.example.pmuprojekat.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seed_meta")
data class SeedMetaEntity(
    @PrimaryKey
    val id: String = "questions_seed",
    val version: Int,
    val questionCount: Int,
    val updatedAt: Long = System.currentTimeMillis()
)