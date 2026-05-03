package com.example.pmuprojekat.data.repository

import com.example.pmuprojekat.data.local.dao.QuestionDao
import com.example.pmuprojekat.data.local.dao.UserAnswerDao
import com.example.pmuprojekat.data.local.dao.UserDao
import com.example.pmuprojekat.data.local.entity.QuestionEntity
import com.example.pmuprojekat.data.local.entity.UserEntity
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

    suspend fun updateCurrentLevel(level: String) {
        userDao.updateCurrentLevel(
            userId = "local_user",
            level = level
        )
    }
}