package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_affairs_articles")
data class CurrentAffairArticle(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val title: String,
  val date: String, // "YYYY-MM-DD" e.g. "2026-10-01"
  val displayDate: String, // e.g. "01 Oct 2026"
  val topicCategory: String, // e.g. "POLITY", "ECONOMY"
  val examFocus: String, // e.g. "UPSC & State PCS", "SSC & UPSC", "ALL"
  val syllabusTag: String, // e.g. "GS Paper-II: Electoral Reforms & Representation of People Act"
  val summary: String,
  val whyInNews: String,
  val keyPoints: String, // Pipe or newline delimited key points
  val prelimsPointers: String, // High yield facts (Articles, Bodies, Headquarters, Reports)
  val mainsAngle: String, // Analytical depth: Significance, challenges, way forward
  val sscOneLiner: String, // Fast punchline fact for SSC CGL / CHSL / State PCS
  val readTimeMinutes: Int = 3,
  val isBookmarked: Boolean = false,
  val isRead: Boolean = false,
  val userNotes: String = "",
  val needsRevision: Boolean = false,
  val importanceTag: String = "HIGH" // "CRITICAL", "HIGH", "MEDIUM"
)
