package com.example.pmuprojekat.ai

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.UserQuestionProgressEntity
import com.example.pmuprojekat.data.repository.LearningRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class LearnerContextBuilder @Inject constructor(
    private val learningRepository: LearningRepository
) {
    suspend fun build(levelId: String): VsAiLearnerContext {
        return combine(
            learningRepository.observeQuestionsByLevel(levelId),
            learningRepository.observeLocalUserProgress()
        ) { questions, progress ->
            LearnerProgressSummarizer.summarize(levelId, questions, progress)
        }.first()
    }
}

object LearnerProgressSummarizer {
    fun summarize(
        levelId: String,
        questions: List<QuestionEntity>,
        allProgress: List<UserQuestionProgressEntity>
    ): VsAiLearnerContext {
        val level = LearningLevel.fromId(levelId)
        val questionsById = questions.associateBy(QuestionEntity::questionId)
        val completed = allProgress
            .asSequence()
            .filter { it.status == "completed" }
            .filter { questionsById.containsKey(it.questionId) }
            .sortedByDescending(UserQuestionProgressEntity::updatedAt)
            .toList()

        val scores = completed.map(UserQuestionProgressEntity::bestScorePercent)
        val averageScore = scores.takeIf(List<Int>::isNotEmpty)?.average()?.roundToInt()
        val bestScore = scores.maxOrNull()
        val typeStats = completed
            .groupBy { questionsById.getValue(it.questionId).type }
            .mapValues { (_, values) ->
                values.map(UserQuestionProgressEntity::bestScorePercent).average().roundToInt()
            }

        val strongerTypes = typeStats.entries
            .filter { it.value >= STRONG_TYPE_SCORE }
            .sortedByDescending(Map.Entry<String, Int>::value)
            .take(MAX_TYPE_HINTS)
            .map(Map.Entry<String, Int>::key)

        val weakerTypes = typeStats.entries
            .filter { it.value < WEAK_TYPE_SCORE }
            .sortedBy(Map.Entry<String, Int>::value)
            .take(MAX_TYPE_HINTS)
            .map(Map.Entry<String, Int>::key)

        val completionRatio = if (questions.isEmpty()) {
            0.0
        } else {
            completed.size.toDouble() / questions.size.toDouble()
        }

        val learnerStatus = when {
            completed.isEmpty() -> VsAiLearnerStatus.NEW_TO_LEVEL
            averageScore != null && averageScore >= 85 && completionRatio >= 0.5 ->
                VsAiLearnerStatus.STRONG
            averageScore != null && averageScore >= 70 && completionRatio >= 0.25 ->
                VsAiLearnerStatus.SOLID
            else -> VsAiLearnerStatus.DEVELOPING
        }

        val hasEnoughHistory = completed.size >= MIN_HISTORY_FOR_PERSONALIZATION
        val personalizationNote = if (hasEnoughHistory) {
            "Personalizuj pitanje samo prema navedenim rezultatima i tipovima zadataka. " +
                "Ne zaključuj o konkretnim greškama koje nisu sačuvane."
        } else {
            "Korisnik nema dovoljno istorije za preciznu personalizaciju. Generiši pitanje prema nivou."
        }

        return VsAiLearnerContext(
            selectedLevelId = level.id,
            selectedLevelName = level.displayName,
            solvedTasks = completed.size,
            totalTasks = questions.size,
            averageScorePercent = averageScore,
            bestScorePercent = bestScore,
            recentResults = completed.take(MAX_RECENT_RESULTS).map { progress ->
                val question = questionsById.getValue(progress.questionId)
                VsAiRecentResult(
                    questionId = progress.questionId,
                    taskType = question.type,
                    bestScorePercent = progress.bestScorePercent,
                    updatedAt = progress.updatedAt
                )
            },
            weakerTaskTypes = weakerTypes,
            strongerTaskTypes = strongerTypes,
            repeatedMistakePatterns = emptyList(),
            status = learnerStatus,
            hasEnoughHistoryForPersonalization = hasEnoughHistory,
            personalizationNote = personalizationNote
        )
    }

    private const val MIN_HISTORY_FOR_PERSONALIZATION = 3
    private const val MAX_RECENT_RESULTS = 5
    private const val MAX_TYPE_HINTS = 2
    private const val STRONG_TYPE_SCORE = 85
    private const val WEAK_TYPE_SCORE = 70
}
