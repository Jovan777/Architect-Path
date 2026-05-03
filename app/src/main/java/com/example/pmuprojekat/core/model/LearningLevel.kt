package com.example.pmuprojekat.core.model


enum class LearningLevel(
    val id: String,
    val displayName: String
) {
    BEGINNER("beginner", "Početnik"),
    JUNIOR("junior", "Junior"),
    MEDIOR("medior", "Medior"),
    SENIOR("senior", "Senior"),
    ARCHITECT("architect", "Arhitekta");

    companion object {
        fun fromId(id: String): LearningLevel {
            return entries.firstOrNull { it.id == id } ?: BEGINNER
        }
    }
}