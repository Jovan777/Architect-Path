package com.example.pmuprojekat

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.core.model.TaskFocusPreference
import com.example.pmuprojekat.core.model.TaskFormatPreference
import com.example.pmuprojekat.core.model.TaskMetadata
import com.example.pmuprojekat.core.model.TaskPersonalizer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TaskPersonalizerTest {

    @Test
    fun scoreCombinesFormatAndFocusMatches() {
        val score = TaskPersonalizer.score(
            metadata = TaskMetadata(
                format = TaskFormatPreference.CODE_PSEUDOCODE,
                focus = TaskFocusPreference.REFACTORING
            ),
            preferredFormats = setOf(TaskFormatPreference.CODE_PSEUDOCODE),
            learningFocus = TaskFocusPreference.REFACTORING
        )

        assertEquals(5, score)
    }

    @Test
    fun scoreGivesMediumPriorityForSingleMatch() {
        val formatOnlyScore = TaskPersonalizer.score(
            metadata = TaskMetadata(
                format = TaskFormatPreference.MAPPING_CARDS,
                focus = TaskFocusPreference.DESIGN_PATTERNS
            ),
            preferredFormats = setOf(TaskFormatPreference.MAPPING_CARDS),
            learningFocus = TaskFocusPreference.PRODUCTION_THINKING
        )

        val focusOnlyScore = TaskPersonalizer.score(
            metadata = TaskMetadata(
                format = TaskFormatPreference.QUIZ_QUESTIONS,
                focus = TaskFocusPreference.PRODUCTION_THINKING
            ),
            preferredFormats = setOf(TaskFormatPreference.MAPPING_CARDS),
            learningFocus = TaskFocusPreference.PRODUCTION_THINKING
        )

        assertEquals(2, formatOnlyScore)
        assertEquals(3, focusOnlyScore)
    }

    @Test
    fun parsesMultipleFormatsFromStoredPreference() {
        val formats = TaskPersonalizer.parsePreferredFormats(
            "Kviz pitanja, Code & Pseudocode"
        )

        assertTrue(formats.contains(TaskFormatPreference.QUIZ_QUESTIONS))
        assertTrue(formats.contains(TaskFormatPreference.CODE_PSEUDOCODE))
    }

    @Test
    fun derivesMetadataFromQuestionType() {
        val metadata = TaskPersonalizer.metadataFor(
            questionType = QuestionType.ARCHITECTURE_REVIEW.id,
            level = LearningLevel.ARCHITECT.id
        )

        assertEquals(TaskFormatPreference.ARCHITECTURAL_SCENARIOS, metadata.format)
        assertEquals(TaskFocusPreference.ARCHITECTURAL_DECISION_MAKING, metadata.focus)
    }
}
