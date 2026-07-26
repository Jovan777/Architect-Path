package com.example.pmuprojekat.ui.question

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestionSessionModeTest {
    @Test
    fun onlyNormalMode_persistsUserProgress() {
        assertTrue(QuestionSessionMode.NORMAL.persistsUserProgress)
        assertFalse(QuestionSessionMode.ADMIN_PREVIEW.persistsUserProgress)
        assertFalse(QuestionSessionMode.ADMIN_TEST.persistsUserProgress)
    }
}
