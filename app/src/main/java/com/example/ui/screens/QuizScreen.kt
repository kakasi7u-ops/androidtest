package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuizQuestion
import com.example.ui.components.ExamBadge
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.viewmodel.CurrentAffairsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
  viewModel: CurrentAffairsViewModel,
  modifier: Modifier = Modifier
) {
  BackHandler {
    viewModel.navigateBack()
  }

  val questions by viewModel.currentQuizzes.collectAsState()
  var currentIndex by remember { mutableIntStateOf(0) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "Daily Prelims MCQ Drill",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Current Affairs Practice Test",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("quiz_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back"
            )
          }
        },
        actions = {
          IconButton(
            onClick = {
              viewModel.resetQuiz()
              currentIndex = 0
            },
            modifier = Modifier.testTag("quiz_reset_button")
          ) {
            Icon(
              imageVector = Icons.Default.Refresh,
              contentDescription = "Reset test",
              tint = MaterialTheme.colorScheme.primary
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    }
  ) { innerPadding ->
    if (questions.isEmpty()) {
      Box(
        modifier = modifier
          .fillMaxSize()
          .padding(innerPadding),
        contentAlignment = Alignment.Center
      ) {
        Text("No quiz available for this date", color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
      return@Scaffold
    }

    val totalQuestions = questions.size
    val safeIndex = currentIndex.coerceIn(0, totalQuestions - 1)
    val question = questions[safeIndex]
    val attemptedCount = questions.count { it.isAttempted }
    val correctCount = questions.count { it.isAttempted && it.userSelectedOption == it.correctOptionIndex }

    val scrollState = rememberScrollState()

    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(scrollState)
        .padding(16.dp)
        .testTag("quiz_screen_content")
    ) {
      // Progress Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Question ${safeIndex + 1} of $totalQuestions",
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp,
          color = MaterialTheme.colorScheme.primary
        )

        Surface(
          color = MaterialTheme.colorScheme.surfaceVariant,
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = "Score: $correctCount / $attemptedCount",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (correctCount > 0) EmeraldTertiary else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      LinearProgressIndicator(
        progress = { (safeIndex + 1).toFloat() / totalQuestions },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Tags: Exam Target & Subject
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        ExamBadge(examFocus = question.examTarget)
        Surface(
          color = MaterialTheme.colorScheme.secondaryContainer,
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = question.topic,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Question Text Card
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = question.questionText,
          style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.SemiBold,
            lineHeight = 24.sp
          ),
          color = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.padding(16.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Options
      val options = listOf(
        0 to question.optionA,
        1 to question.optionB,
        2 to question.optionC,
        3 to question.optionD
      )

      options.forEach { (index, optionText) ->
        OptionRow(
          optionLabel = when (index) {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            else -> "D"
          },
          optionText = optionText,
          isSelected = question.userSelectedOption == index,
          isAttempted = question.isAttempted,
          isCorrect = question.correctOptionIndex == index,
          onClick = {
            if (!question.isAttempted) {
              viewModel.submitQuizAnswer(question, index)
            }
          }
        )
        Spacer(modifier = Modifier.height(10.dp))
      }

      // Detailed Explanation Section (Appears immediately upon answering)
      AnimatedVisibility(visible = question.isAttempted) {
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (question.userSelectedOption == question.correctOptionIndex) {
              EmeraldTertiary.copy(alpha = 0.1f)
            } else {
              MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
            }
          ),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .testTag("quiz_explanation_card")
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = if (question.userSelectedOption == question.correctOptionIndex) Icons.Default.CheckCircle else Icons.Default.Close,
                contentDescription = null,
                tint = if (question.userSelectedOption == question.correctOptionIndex) EmeraldTertiary else MaterialTheme.colorScheme.error,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (question.userSelectedOption == question.correctOptionIndex) "Correct Answer!" else "Incorrect Choice",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (question.userSelectedOption == question.correctOptionIndex) EmeraldTertiary else MaterialTheme.colorScheme.error
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "Detailed Explanation & Elimination Strategy:",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = question.explanation,
              style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 21.sp),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      // Quiz Completion Summary
      if (attemptedCount == totalQuestions) {
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("quiz_completed_banner")
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.EmojiEvents,
              contentDescription = null,
              tint = AmberSecondary,
              modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Daily Drill Completed! 🎉",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
              )
              Text(
                text = "Accuracy: ${(correctCount.toFloat() / totalQuestions * 100).toInt()}% ($correctCount of $totalQuestions correct)",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Navigation Prev / Next
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedButton(
          onClick = {
            if (currentIndex > 0) currentIndex--
          },
          enabled = currentIndex > 0,
          modifier = Modifier.testTag("quiz_prev_btn")
        ) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Previous")
        }

        Button(
          onClick = {
            if (currentIndex < totalQuestions - 1) currentIndex++
          },
          enabled = currentIndex < totalQuestions - 1,
          modifier = Modifier.testTag("quiz_next_btn")
        ) {
          Text("Next")
          Spacer(modifier = Modifier.width(4.dp))
          Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
        }
      }

      Spacer(modifier = Modifier.height(40.dp))
    }
  }
}

@Composable
fun OptionRow(
  optionLabel: String,
  optionText: String,
  isSelected: Boolean,
  isAttempted: Boolean,
  isCorrect: Boolean,
  onClick: () -> Unit
) {
  val (borderColor, containerColor, labelBgColor, labelTextColor) = when {
    isAttempted && isCorrect -> {
      // Correct Option
      Quadruple(EmeraldTertiary, EmeraldTertiary.copy(alpha = 0.12f), EmeraldTertiary, Color.White)
    }
    isAttempted && isSelected && !isCorrect -> {
      // Wrong Selected Option
      Quadruple(MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f), MaterialTheme.colorScheme.error, Color.White)
    }
    isSelected -> {
      // Just selected before submit
      Quadruple(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f), MaterialTheme.colorScheme.primary, Color.White)
    }
    else -> {
      // Normal option
      Quadruple(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }

  Surface(
    shape = RoundedCornerShape(10.dp),
    color = containerColor,
    modifier = Modifier
      .fillMaxWidth()
      .border(1.5.dp, borderColor, RoundedCornerShape(10.dp))
      .clickable(enabled = !isAttempted) { onClick() }
      .testTag("option_$optionLabel")
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .background(labelBgColor),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = optionLabel,
          color = labelTextColor,
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Text(
        text = optionText,
        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.weight(1f)
      )

      if (isAttempted && isCorrect) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = "Correct",
          tint = EmeraldTertiary,
          modifier = Modifier.size(20.dp)
        )
      } else if (isAttempted && isSelected && !isCorrect) {
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = "Wrong",
          tint = MaterialTheme.colorScheme.error,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
