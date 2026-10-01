package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.DailyStudyRecord
import com.example.data.model.MainsPracticeItem
import com.example.data.model.OneLinerFact
import com.example.data.model.QuizQuestion
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
  @Query("SELECT * FROM quiz_questions WHERE date = :date ORDER BY id ASC")
  fun getQuestionsForDate(date: String): Flow<List<QuizQuestion>>

  @Query("SELECT * FROM quiz_questions ORDER BY date DESC, id ASC")
  fun getAllQuestions(): Flow<List<QuizQuestion>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertQuestions(questions: List<QuizQuestion>)

  @Query("UPDATE quiz_questions SET userSelectedOption = :selectedIndex, isAttempted = 1 WHERE id = :id")
  suspend fun submitAnswer(id: Long, selectedIndex: Int)

  @Query("UPDATE quiz_questions SET userSelectedOption = -1, isAttempted = 0 WHERE date = :date")
  suspend fun resetQuizForDate(date: String)

  @Query("SELECT COUNT(*) FROM quiz_questions")
  suspend fun getQuestionCount(): Int
}

@Dao
interface MainsAndCapsuleDao {
  @Query("SELECT * FROM mains_practice_items WHERE date = :date ORDER BY id ASC")
  fun getMainsForDate(date: String): Flow<List<MainsPracticeItem>>

  @Query("SELECT * FROM mains_practice_items ORDER BY date DESC")
  fun getAllMains(): Flow<List<MainsPracticeItem>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMainsItems(items: List<MainsPracticeItem>)

  @Query("UPDATE mains_practice_items SET userDraftAnswer = :answer, isCompleted = :isCompleted WHERE id = :id")
  suspend fun saveMainsAnswer(id: Long, answer: String, isCompleted: Boolean)

  @Query("SELECT * FROM one_liner_facts WHERE date = :date ORDER BY id ASC")
  fun getOneLinersForDate(date: String): Flow<List<OneLinerFact>>

  @Query("SELECT * FROM one_liner_facts ORDER BY date DESC")
  fun getAllOneLiners(): Flow<List<OneLinerFact>>

  @Query("SELECT * FROM one_liner_facts WHERE isBookmarked = 1 ORDER BY date DESC")
  fun getBookmarkedOneLiners(): Flow<List<OneLinerFact>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOneLiners(facts: List<OneLinerFact>)

  @Query("UPDATE one_liner_facts SET isBookmarked = :bookmarked WHERE id = :id")
  suspend fun toggleBookmark(id: Long, bookmarked: Boolean)

  @Query("SELECT COUNT(*) FROM one_liner_facts")
  suspend fun getOneLinerCount(): Int
}

@Dao
interface StudyRecordDao {
  @Query("SELECT * FROM daily_study_records WHERE date = :date LIMIT 1")
  fun getRecordForDate(date: String): Flow<DailyStudyRecord?>

  @Query("SELECT * FROM daily_study_records ORDER BY date DESC")
  fun getAllRecords(): Flow<List<DailyStudyRecord>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsertRecord(record: DailyStudyRecord)
}
