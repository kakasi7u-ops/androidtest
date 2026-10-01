package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.model.CurrentAffairArticle
import com.example.data.model.DailyStudyRecord
import com.example.data.model.MainsPracticeItem
import com.example.data.model.OneLinerFact
import com.example.data.model.QuizQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class CurrentAffairsRepository(private val database: AppDatabase) {

  private val articleDao = database.currentAffairsDao()
  private val quizDao = database.quizDao()
  private val mainsAndCapsuleDao = database.mainsAndCapsuleDao()
  private val studyRecordDao = database.studyRecordDao()

  suspend fun ensureDatabaseSeeded() = withContext(Dispatchers.IO) {
    if (articleDao.getArticleCount() == 0) {
      articleDao.insertArticles(SeedDataProvider.getInitialArticles())
    }
    if (quizDao.getQuestionCount() == 0) {
      quizDao.insertQuestions(SeedDataProvider.getInitialQuizzes())
    }
    if (mainsAndCapsuleDao.getOneLinerCount() == 0) {
      mainsAndCapsuleDao.insertOneLiners(SeedDataProvider.getInitialOneLiners())
      mainsAndCapsuleDao.insertMainsItems(SeedDataProvider.getInitialMains())
    }
  }

  // Articles
  fun getAllArticles(): Flow<List<CurrentAffairArticle>> = articleDao.getAllArticles()

  fun getArticlesByDate(date: String): Flow<List<CurrentAffairArticle>> =
    articleDao.getArticlesByDate(date)

  fun getBookmarkedArticles(): Flow<List<CurrentAffairArticle>> =
    articleDao.getBookmarkedArticles()

  fun getRevisionArticles(): Flow<List<CurrentAffairArticle>> =
    articleDao.getRevisionArticles()

  fun getArticleById(id: Long): Flow<CurrentAffairArticle?> =
    articleDao.getArticleById(id)

  fun searchArticles(query: String): Flow<List<CurrentAffairArticle>> =
    articleDao.searchArticles(query)

  fun getAvailableDates(): Flow<List<String>> = articleDao.getAvailableDates()

  fun getReadArticlesCount(): Flow<Int> = articleDao.getReadArticlesCount()

  suspend fun toggleBookmark(id: Long, currentStatus: Boolean) = withContext(Dispatchers.IO) {
    articleDao.updateBookmark(id, !currentStatus)
  }

  suspend fun toggleReadStatus(id: Long, currentStatus: Boolean, date: String) =
    withContext(Dispatchers.IO) {
      val newStatus = !currentStatus
      articleDao.updateReadStatus(id, newStatus)
      if (newStatus) {
        val record = studyRecordDao.getRecordForDate(date).firstOrNull() ?: DailyStudyRecord(date = date)
        studyRecordDao.upsertRecord(record.copy(articlesReadCount = record.articlesReadCount + 1))
      }
    }

  suspend fun updateNotes(id: Long, notes: String) = withContext(Dispatchers.IO) {
    articleDao.updateNotes(id, notes)
  }

  suspend fun toggleNeedsRevision(id: Long, currentStatus: Boolean) =
    withContext(Dispatchers.IO) {
      articleDao.updateNeedsRevision(id, !currentStatus)
    }

  // Quizzes
  fun getQuestionsForDate(date: String): Flow<List<QuizQuestion>> =
    quizDao.getQuestionsForDate(date)

  fun getAllQuestions(): Flow<List<QuizQuestion>> = quizDao.getAllQuestions()

  suspend fun submitQuizAnswer(id: Long, selectedIndex: Int, isCorrect: Boolean, date: String) =
    withContext(Dispatchers.IO) {
      quizDao.submitAnswer(id, selectedIndex)
      val record = studyRecordDao.getRecordForDate(date).firstOrNull() ?: DailyStudyRecord(date = date)
      val newAttempted = record.quizAttemptedCount + 1
      val newCorrect = if (isCorrect) record.quizCorrectCount + 1 else record.quizCorrectCount
      studyRecordDao.upsertRecord(
        record.copy(quizAttemptedCount = newAttempted, quizCorrectCount = newCorrect)
      )
    }

  suspend fun resetQuizForDate(date: String) = withContext(Dispatchers.IO) {
    quizDao.resetQuizForDate(date)
  }

  // Mains & One-Liners
  fun getMainsForDate(date: String): Flow<List<MainsPracticeItem>> =
    mainsAndCapsuleDao.getMainsForDate(date)

  fun getAllMains(): Flow<List<MainsPracticeItem>> =
    mainsAndCapsuleDao.getAllMains()

  suspend fun saveMainsAnswer(id: Long, draftAnswer: String, isCompleted: Boolean) =
    withContext(Dispatchers.IO) {
      mainsAndCapsuleDao.saveMainsAnswer(id, draftAnswer, isCompleted)
    }

  fun getOneLinersForDate(date: String): Flow<List<OneLinerFact>> =
    mainsAndCapsuleDao.getOneLinersForDate(date)

  fun getAllOneLiners(): Flow<List<OneLinerFact>> =
    mainsAndCapsuleDao.getAllOneLiners()

  fun getBookmarkedOneLiners(): Flow<List<OneLinerFact>> =
    mainsAndCapsuleDao.getBookmarkedOneLiners()

  suspend fun toggleOneLinerBookmark(id: Long, currentStatus: Boolean) =
    withContext(Dispatchers.IO) {
      mainsAndCapsuleDao.toggleBookmark(id, !currentStatus)
    }

  // Study Records
  fun getAllStudyRecords(): Flow<List<DailyStudyRecord>> = studyRecordDao.getAllRecords()
}
