package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_questions")
data class QuizQuestion(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val date: String,
  val examTarget: String, // "UPSC Prelims", "SSC CGL", "State PCS"
  val topic: String, // "Polity", "Economy", "Environment", etc.
  val questionText: String,
  val optionA: String,
  val optionB: String,
  val optionC: String,
  val optionD: String,
  val correctOptionIndex: Int, // 0 for A, 1 for B, 2 for C, 3 for D
  val explanation: String,
  val userSelectedOption: Int = -1, // -1 means unattempted
  val isAttempted: Boolean = false
)
