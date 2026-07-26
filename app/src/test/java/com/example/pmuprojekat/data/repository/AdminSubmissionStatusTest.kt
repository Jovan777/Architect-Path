package com.example.pmuprojekat.data.repository

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AdminSubmissionStatusTest {
    @Test
    fun promotedSubmission_hasPriorityOverApprovedReviewStatus() {
        assertEquals(
            AdminSubmissionStatus.PROMOTED,
            AdminSubmissionStatus.from(
                reviewStatus = "APPROVED",
                promotedToRemoteTaskId = "admin_task_1"
            )
        )
    }

    @Test
    fun filters_keepApprovedAndPromotedSubmissionsSeparate() {
        assertTrue(
            AdminSubmissionFilter.APPROVED.accepts(
                AdminSubmissionStatus.APPROVED
            )
        )
        assertFalse(
            AdminSubmissionFilter.APPROVED.accepts(
                AdminSubmissionStatus.PROMOTED
            )
        )
        assertTrue(
            AdminSubmissionFilter.PROMOTED.accepts(
                AdminSubmissionStatus.PROMOTED
            )
        )
        assertTrue(
            AdminSubmissionFilter.ALL.accepts(
                AdminSubmissionStatus.REJECTED
            )
        )
    }
}
