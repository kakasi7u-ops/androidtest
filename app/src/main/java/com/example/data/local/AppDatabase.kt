package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.CurrentAffairArticle
import com.example.data.model.DailyStudyRecord
import com.example.data.model.MainsPracticeItem
import com.example.data.model.OneLinerFact
import com.example.data.model.QuizQuestion

@Database(
  entities = [
    CurrentAffairArticle::class,
    QuizQuestion::class,
    MainsPracticeItem::class,
    OneLinerFact::class,
    DailyStudyRecord::class
  ],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun currentAffairsDao(): CurrentAffairsDao
  abstract fun quizDao(): QuizDao
  abstract fun mainsAndCapsuleDao(): MainsAndCapsuleDao
  abstract fun studyRecordDao(): StudyRecordDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "civil_currents_database"
        ).fallbackToDestructiveMigration().build()
        INSTANCE = instance
        instance
      }
    }
  }
}
