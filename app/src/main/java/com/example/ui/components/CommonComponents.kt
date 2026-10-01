package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CurrentAffairArticle
import com.example.data.model.ExamCategory
import com.example.data.model.TopicCategory
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SscBlue
import com.example.ui.theme.SscBlueBg
import com.example.ui.theme.StatePcsGreen
import com.example.ui.theme.StatePcsGreenBg
import com.example.ui.theme.UpscGold
import com.example.ui.theme.UpscGoldBg

@Composable
fun ExamSelectorRow(
  selectedExam: ExamCategory,
  onExamSelected: (ExamCategory) -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()
  Row(
    modifier = modifier
      .fillMaxWidth()
      .horizontalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 6.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    ExamCategory.values().forEach { exam ->
      val isSelected = exam == selectedExam
      FilterChip(
        selected = isSelected,
        onClick = { onExamSelected(exam) },
        label = {
          Text(
            text = exam.displayName,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 13.sp
          )
        },
        colors = FilterChipDefaults.filterChipColors(
          selectedContainerColor = MaterialTheme.colorScheme.primary,
          selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier.testTag("exam_chip_${exam.name.lowercase()}")
      )
    }
  }
}

@Composable
fun TopicSelectorRow(
  selectedTopic: TopicCategory?,
  onTopicSelected: (TopicCategory?) -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()
  Row(
    modifier = modifier
      .fillMaxWidth()
      .horizontalScroll(scrollState)
      .padding(horizontal = 16.dp, vertical = 4.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    AssistChip(
      onClick = { onTopicSelected(null) },
      label = { Text("All Subjects", fontSize = 12.sp) },
      colors = AssistChipDefaults.assistChipColors(
        containerColor = if (selectedTopic == null) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant
      ),
      modifier = Modifier.testTag("topic_chip_all")
    )
    TopicCategory.values().forEach { topic ->
      val isSelected = topic == selectedTopic
      AssistChip(
        onClick = { onTopicSelected(topic) },
        label = { Text(topic.displayName, fontSize = 12.sp) },
        colors = AssistChipDefaults.assistChipColors(
          containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant,
          labelColor = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.testTag("topic_chip_${topic.name.lowercase()}")
      )
    }
  }
}

@Composable
fun ExamBadge(examFocus: String, modifier: Modifier = Modifier) {
  val (bgColor, textColor) = when {
    examFocus.contains("UPSC", ignoreCase = true) -> UpscGoldBg to UpscGold
    examFocus.contains("SSC", ignoreCase = true) -> SscBlueBg to SscBlue
    examFocus.contains("PCS", ignoreCase = true) -> StatePcsGreenBg to StatePcsGreen
    else -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
  }

  Surface(
    color = bgColor,
    shape = RoundedCornerShape(6.dp),
    modifier = modifier
  ) {
    Text(
      text = examFocus,
      color = textColor,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
    )
  }
}

@Composable
fun TopicBadge(topicName: String, modifier: Modifier = Modifier) {
  Surface(
    color = MaterialTheme.colorScheme.surfaceVariant,
    shape = RoundedCornerShape(6.dp),
    modifier = modifier
  ) {
    Text(
      text = topicName.replace("_", " "),
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      fontSize = 10.sp,
      fontWeight = FontWeight.Medium,
      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
    )
  }
}

@Composable
fun ArticleCard(
  article: CurrentAffairArticle,
  isPlayingAudio: Boolean,
  onClick: () -> Unit,
  onBookmarkClick: () -> Unit,
  onReadToggle: () -> Unit,
  onAudioClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 6.dp)
      .clickable { onClick() }
      .testTag("article_card_${article.id}"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (article.isRead) MaterialTheme.colorScheme.surface.copy(alpha = 0.9f) else MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = if (article.isRead) 1.dp else 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Top row: Topic, Exam Badge & Read Time
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          ExamBadge(examFocus = article.examFocus)
          TopicBadge(topicName = article.topicCategory)
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Schedule,
            contentDescription = "Read time",
            modifier = Modifier.size(13.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Text(
            text = "${article.readTimeMinutes} min",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Title
      Text(
        text = article.title,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          lineHeight = 22.sp
        ),
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Syllabus Tag pill
      Surface(
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "🎯 ${article.syllabusTag}",
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.Medium,
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Summary
      Text(
        text = article.summary,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Bottom bar actions
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // SSC / Quick Punchline indicator
        Text(
          text = "Tap for Prelims & Mains Notes →",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.primary
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
          // Audio Listen button
          IconButton(
            onClick = onAudioClick,
            modifier = Modifier.testTag("audio_btn_${article.id}")
          ) {
            Icon(
              imageVector = if (isPlayingAudio) Icons.Default.Stop else Icons.Default.Headphones,
              contentDescription = if (isPlayingAudio) "Stop audio" else "Listen to article",
              tint = if (isPlayingAudio) AmberSecondary else MaterialTheme.colorScheme.primary
            )
          }

          // Bookmark button
          IconButton(
            onClick = onBookmarkClick,
            modifier = Modifier.testTag("bookmark_btn_${article.id}")
          ) {
            Icon(
              imageVector = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "Bookmark",
              tint = if (article.isBookmarked) AmberSecondary else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          // Mark Read button
          IconButton(
            onClick = onReadToggle,
            modifier = Modifier.testTag("read_toggle_${article.id}")
          ) {
            Icon(
              imageVector = if (article.isRead) Icons.Default.CheckCircle else Icons.Outlined.CheckCircleOutline,
              contentDescription = "Toggle Read Status",
              tint = if (article.isRead) EmeraldTertiary else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }
  }
}

@Composable
fun AudioPlayingStrip(
  isPlaying: Boolean,
  articleTitle: String?,
  onStopClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  AnimatedVisibility(visible = isPlaying && articleTitle != null) {
    Surface(
      color = MaterialTheme.colorScheme.primaryContainer,
      tonalElevation = 6.dp,
      shape = RoundedCornerShape(12.dp),
      modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp)
        .testTag("audio_playing_strip")
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .size(10.dp)
              .clip(CircleShape)
              .background(AmberSecondary)
          )
          Column {
            Text(
              text = "Reading Aloud (Audio Summary)",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
              text = articleTitle ?: "",
              fontSize = 12.sp,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onPrimaryContainer
            )
          }
        }

        IconButton(onClick = onStopClick) {
          Icon(
            imageVector = Icons.Default.Stop,
            contentDescription = "Stop",
            tint = AmberSecondary
          )
        }
      }
    }
  }
}
