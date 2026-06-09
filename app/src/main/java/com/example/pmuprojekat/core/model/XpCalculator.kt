package com.example.pmuprojekat.core.model

import kotlin.math.roundToInt

object XpCalculator {
    private const val MAX_XP_PER_QUESTION = 5

    fun xpForScore(scorePercent: Int): Int {
        return ((scorePercent.coerceIn(0, 100) / 100f) * MAX_XP_PER_QUESTION)
            .roundToInt()
            .coerceIn(0, MAX_XP_PER_QUESTION)
    }

    fun maxXpForQuestion(): Int = MAX_XP_PER_QUESTION

    fun maxXpForLevel(questionCount: Int): Int {
        return questionCount.coerceAtLeast(0) * MAX_XP_PER_QUESTION
    }

    fun progressPercent(earnedXp: Int, maxXp: Int): Int {
        if (maxXp <= 0) return 0
        return ((earnedXp.coerceAtLeast(0).toFloat() / maxXp.toFloat()) * 100)
            .roundToInt()
            .coerceIn(0, 100)
    }
}

data class LevelXpProgress(
    val levelId: String,
    val earnedXp: Int,
    val maxXp: Int,
    val progressPercent: Int
)
