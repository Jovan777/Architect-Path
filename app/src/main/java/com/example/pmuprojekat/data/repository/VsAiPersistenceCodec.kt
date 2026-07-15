package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.ai.VsAiEvaluationResult
import com.example.pmuprojekat.ai.VsAiGeneratedChallenge
import com.example.pmuprojekat.ai.VsAiLearnerContext
import com.example.pmuprojekat.ai.VsAiLevelContext
import org.json.JSONArray
import org.json.JSONObject

object VsAiPersistenceCodec {
    fun encodeLevelContext(context: VsAiLevelContext): String {
        return JSONObject()
            .put("levelId", context.levelId)
            .put("displayName", context.displayName)
            .put("difficultyDescription", context.difficultyDescription)
            .put("expectedReasoningDepth", context.expectedReasoningDepth)
            .put("typicalConcepts", context.typicalConcepts.toJsonArray())
            .put("allowedQuestionStyles", context.allowedQuestionStyles.toJsonArray())
            .put("forbiddenQuestionStyles", context.forbiddenQuestionStyles.toJsonArray())
            .put("satisfactionCriteria", context.satisfactionCriteria.toJsonArray())
            .put("maximumRounds", context.maximumRounds)
            .put("minimumExpectedAnswerQuality", context.minimumExpectedAnswerQuality)
            .put("suitableQuestionExamples", context.suitableQuestionExamples.toJsonArray())
            .toString()
    }

    fun encodeLearnerContext(context: VsAiLearnerContext): String {
        return JSONObject()
            .put("selectedLevelId", context.selectedLevelId)
            .put("selectedLevelName", context.selectedLevelName)
            .put("solvedTasks", context.solvedTasks)
            .put("totalTasks", context.totalTasks)
            .put("averageScorePercent", context.averageScorePercent ?: JSONObject.NULL)
            .put("bestScorePercent", context.bestScorePercent ?: JSONObject.NULL)
            .put("weakerTaskTypes", context.weakerTaskTypes.toJsonArray())
            .put("strongerTaskTypes", context.strongerTaskTypes.toJsonArray())
            .put("repeatedMistakePatterns", context.repeatedMistakePatterns.toJsonArray())
            .put("status", context.status.name)
            .put("hasEnoughHistoryForPersonalization", context.hasEnoughHistoryForPersonalization)
            .put("personalizationNote", context.personalizationNote)
            .put(
                "recentResults",
                JSONArray().apply {
                    context.recentResults.forEach { result ->
                        put(
                            JSONObject()
                                .put("questionId", result.questionId)
                                .put("taskType", result.taskType)
                                .put("bestScorePercent", result.bestScorePercent)
                                .put("updatedAt", result.updatedAt)
                        )
                    }
                }
            )
            .toString()
    }

    fun encodeChallenge(challenge: VsAiGeneratedChallenge): String {
        return JSONObject()
            .put("question", challenge.question)
            .put("targetConcept", challenge.targetConcept)
            .put("expectedAnswerGuide", challenge.expectedAnswerGuide)
            .put("difficultyLabel", challenge.difficultyLabel)
            .put("reasonChosen", challenge.reasonChosen)
            .toString()
    }

    fun encodeEvaluation(result: VsAiEvaluationResult): String {
        return JSONObject()
            .put("visibleMessage", result.visibleMessage)
            .put("shouldContinue", result.shouldContinue)
            .put("isSatisfied", result.isSatisfied)
            .put("satisfactionScore", result.satisfactionScore)
            .put("nextQuestion", result.nextQuestion ?: JSONObject.NULL)
            .put("targetConcepts", result.targetConcepts.toJsonArray())
            .put("detectedStrengths", result.detectedStrengths.toJsonArray())
            .put("detectedWeaknesses", result.detectedWeaknesses.toJsonArray())
            .put("reasonForContinuingOrStopping", result.reasonForContinuingOrStopping)
            .toString()
    }

    fun encodeStringList(values: List<String>): String = values.distinct().toJsonArray().toString()

    private fun List<String>.toJsonArray(): JSONArray {
        return JSONArray().apply { forEach(::put) }
    }
}
