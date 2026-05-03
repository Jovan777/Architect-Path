package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.dao.UserAnswerDao
import com.example.pmuprojekat.data.local.dao.UserDao
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.UserEntity
import com.example.pmuprojekat.data.local.entity.UserQuestionProgressEntity
import com.example.pmuprojekat.data.local.entity.UserStepAnswerEntity
import com.example.pmuprojekat.data.local.relation.QuestionWithSteps
import com.example.pmuprojekat.data.seed.SeedInserter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningRepository @Inject constructor(
    private val userDao: UserDao,
    private val questionDao: QuestionDao,
    private val userAnswerDao: UserAnswerDao,
    private val seedInserter: SeedInserter
) {

    fun observeActiveUser(): Flow<UserEntity?> {
        return userDao.observeActiveUser()
    }

    fun observeAllQuestions(): Flow<List<QuestionEntity>> {
        return questionDao.observeAllQuestions()
    }

    fun observeQuestionsByLevel(level: String): Flow<List<QuestionEntity>> {
        return questionDao.observeQuestionsByLevel(level)
    }

    fun observeQuestionWithSteps(questionId: String): Flow<QuestionWithSteps?> {
        return questionDao.observeQuestionWithSteps(questionId)
    }

    suspend fun initializeDatabaseIfNeeded() {
        seedInserter.seedIfNeeded()
    }

    fun observeLocalUserProgress() =
        userAnswerDao.observeUserProgress(LOCAL_USER_ID)



    suspend fun updateCurrentLevel(level: String) {
        userDao.updateCurrentLevel(
            userId = LOCAL_USER_ID,
            level = level
        )
    }

    suspend fun saveStepAnswer(answer: UserStepAnswerEntity) {
        userAnswerDao.upsertStepAnswer(answer)
    }

    suspend fun completeQuestion(
        questionId: String,
        scorePercent: Int,
        xpReward: Int
    ) {
        val existingProgress = userAnswerDao.getQuestionProgress(
            userId = LOCAL_USER_ID,
            questionId = questionId
        )

        val alreadyCompleted = existingProgress?.status == "completed"

        val updatedProgress = UserQuestionProgressEntity(
            userId = LOCAL_USER_ID,
            questionId = questionId,
            status = "completed",
            attempts = (existingProgress?.attempts ?: 0) + 1,
            bestScorePercent = maxOf(existingProgress?.bestScorePercent ?: 0, scorePercent),
            startedAt = existingProgress?.startedAt ?: System.currentTimeMillis(),
            completedAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )

        userAnswerDao.upsertQuestionProgress(updatedProgress)

        if (!alreadyCompleted) {
            userDao.increaseLearningStats(
                userId = LOCAL_USER_ID,
                completedDelta = 1,
                xpDelta = xpReward
            )
        }
    }

    companion object {
        const val LOCAL_USER_ID = "local_user"
    }
}