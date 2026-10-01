package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mains_practice_items")
data class MainsPracticeItem(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val date: String,
  val question: String,
  val paperTag: String, // e.g. "GS Paper-II / Polity & Governance"
  val wordLimit: Int = 250,
  val marks: Int = 15,
  val introApproach: String,
  val bodyApproach: String,
  val conclusionApproach: String,
  val quotesAndCommittees: String,
  val userDraftAnswer: String = "",
  val isCompleted: Boolean = false
)

@Entity(tableName = "one_liner_facts")
data class OneLinerFact(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val date: String,
  val category: String, // "Appointments", "Summits", "Defence", "Sports", "Indexes", "Schemes"
  val title: String,
  val factDetails: String,
  val examScope: String = "SSC / State PCS / RRB",
  val isBookmarked: Boolean = false
)

@Entity(tableName = "daily_study_records")
data class DailyStudyRecord(
  @PrimaryKey
  val date: String, // "YYYY-MM-DD"
  val articlesReadCount: Int = 0,
  val quizAttemptedCount: Int = 0,
  val quizCorrectCount: Int = 0
)
