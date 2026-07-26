package com.example.pmuprojekat.core.learning

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningTimeProvider @Inject constructor() {

    fun nowMillis(): Long = System.currentTimeMillis()

    fun localDate(epochMillis: Long = nowMillis()): LocalDate {
        return Instant.ofEpochMilli(epochMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
}
