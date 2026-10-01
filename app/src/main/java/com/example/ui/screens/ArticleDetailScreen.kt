package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.TextIncrease
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CurrentAffairArticle
import com.example.ui.components.ExamBadge
import com.example.ui.components.TopicBadge
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.theme.NavyPrimary
import com.example.ui.viewmodel.CurrentAffairsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
  viewModel: CurrentAffairsViewModel,
  modifier: Modifier = Modifier
) {
  BackHandler {
    viewModel.navigateBack()
  }

  val article by viewModel.currentArticle.collectAsState()
  val fontScale by viewModel.fontScale.collectAsState()
  val isAudioPlaying by viewModel.ttsManager.isPlaying.collectAsState()
  val currentPlayingId by viewModel.ttsManager.currentPlayingId.collectAsState()

  if (article == null) {
    Box(
      modifier = modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text("Article not found", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
    return
  }

  val nonNullArticle = article!!
  val isThisPlaying = isAudioPlaying && currentPlayingId == nonNullArticle.id

  var notesText by remember(nonNullArticle.id) { mutableStateOf(nonNullArticle.userNotes) }
  var notesSavedMessage by remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Editorial Brief",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
        },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("detail_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back"
            )
          }
        },
        actions = {
          // Font size cycle (0.9 -> 1.0 -> 1.15)
          IconButton(
            onClick = {
              val nextScale = when {
                fontScale < 1.0f -> 1.0f
                fontScale < 1.1f -> 1.15f
                else -> 0.9f
              }
              viewModel.setFontScale(nextScale)
            },
            modifier = Modifier.testTag("font_size_cycle_btn")
          ) {
            Icon(
              imageVector = Icons.Default.TextIncrease,
              contentDescription = "Adjust font size",
              tint = MaterialTheme.colorScheme.primary
            )
          }

          // Audio listen
          IconButton(
            onClick = { viewModel.playAudioForArticle(nonNullArticle) },
            modifier = Modifier.testTag("detail_audio_btn")
          ) {
            Icon(
              imageVector = if (isThisPlaying) Icons.Default.Stop else Icons.Default.Headphones,
              contentDescription = "Listen Aloud",
              tint = if (isThisPlaying) AmberSecondary else MaterialTheme.colorScheme.primary
            )
          }

          // Bookmark
          IconButton(
            onClick = { viewModel.toggleBookmark(nonNullArticle) },
            modifier = Modifier.testTag("detail_bookmark_btn")
          ) {
            Icon(
              imageVector = if (nonNullArticle.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "Bookmark",
              tint = if (nonNullArticle.isBookmarked) AmberSecondary else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    }
  ) { innerPadding ->
    val scrollState = rememberScrollState()

    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(scrollState)
        .padding(16.dp)
        .testTag("article_detail_content")
    ) {
      // Tags Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          ExamBadge(examFocus = nonNullArticle.examFocus)
          TopicBadge(topicName = nonNullArticle.topicCategory)
        }
        Text(
          text = nonNullArticle.displayDate,
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontWeight = FontWeight.Medium
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Title
      Text(
        text = nonNullArticle.title,
        fontSize = (20 * fontScale).sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (28 * fontScale).sp,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Syllabus Banner
      Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text(
            text = "SYLLABUS MAPPING",
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 0.8.sp
          )
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = nonNullArticle.syllabusTag,
            fontSize = (13 * fontScale).sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Why in news / Context Card
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Why in News? (Context)",
              fontWeight = FontWeight.Bold,
              fontSize = (14 * fontScale).sp,
              color = MaterialTheme.colorScheme.primary
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = nonNullArticle.whyInNews,
            fontSize = (14 * fontScale).sp,
            lineHeight = (22 * fontScale).sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Section: Key Points & Salient Features
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.MenuBook,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Key Points & Detailed Breakdown",
          fontSize = (16 * fontScale).sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = nonNullArticle.keyPoints,
        fontSize = (14 * fontScale).sp,
        lineHeight = (22 * fontScale).sp,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(20.dp))

      // High-Yield Prelims Pointers Box (Gold/Amber styled)
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
          containerColor = AmberSecondary.copy(alpha = 0.1f)
        ),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(AmberSecondary.copy(alpha = 0.5f))
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = AmberSecondary,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Prelims High-Yield Pointers",
              fontSize = (15 * fontScale).sp,
              fontWeight = FontWeight.ExtraBold,
              color = AmberSecondary
            )
          }
          Text(
            text = "High probability factual retention for UPSC, SSC & State PCS",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = nonNullArticle.prelimsPointers,
            fontSize = (14 * fontScale).sp,
            lineHeight = (22 * fontScale).sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Mains Analytical Angle (Teal/Emerald styled)
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
          containerColor = EmeraldTertiary.copy(alpha = 0.08f)
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = null,
              tint = EmeraldTertiary,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Mains Analytical Takeaway & Way Forward",
              fontSize = (15 * fontScale).sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldTertiary
            )
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = nonNullArticle.mainsAngle,
            fontSize = (14 * fontScale).sp,
            lineHeight = (22 * fontScale).sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // SSC One-Liner Punchline
      Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "⚡ SSC / State PCS Factoid: ",
            fontSize = (12 * fontScale).sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
          Text(
            text = nonNullArticle.sscOneLiner,
            fontSize = (13 * fontScale).sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
      HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
      Spacer(modifier = Modifier.height(20.dp))

      // Personal Aspirant Notes Section
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.NoteAdd,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Aspirant Self-Study Notes",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Add custom mnemonics, case laws, or revision pointers to this topic",
        fontSize = 11.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = notesText,
        onValueChange = {
          notesText = it
          notesSavedMessage = false
        },
        placeholder = { Text("E.g., Article 324 vs State Election Commission powers in Art 243K...") },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("aspirant_notes_input"),
        minLines = 3,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MaterialTheme.colorScheme.surface,
          unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (notesSavedMessage) {
          Text(
            text = "✓ Notes saved to database",
            color = EmeraldTertiary,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )
        } else {
          Spacer(modifier = Modifier.width(1.dp))
        }

        Button(
          onClick = {
            viewModel.updateNotes(nonNullArticle.id, notesText)
            notesSavedMessage = true
          },
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
          modifier = Modifier.testTag("save_notes_btn")
        ) {
          Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Save Notes")
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Revision and Read completion checklist
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = { viewModel.toggleRevision(nonNullArticle) },
          modifier = Modifier
            .weight(1f)
            .testTag("toggle_revision_btn")
        ) {
          Icon(
            imageVector = Icons.Default.RateReview,
            contentDescription = null,
            tint = if (nonNullArticle.needsRevision) AmberSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (nonNullArticle.needsRevision) "Needs Revision ★" else "Add to Revision",
            fontSize = 12.sp,
            color = if (nonNullArticle.needsRevision) AmberSecondary else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Button(
          onClick = {
            viewModel.toggleRead(nonNullArticle)
            viewModel.navigateBack()
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = if (nonNullArticle.isRead) MaterialTheme.colorScheme.surfaceVariant else EmeraldTertiary
          ),
          modifier = Modifier
            .weight(1f)
            .testTag("mark_read_btn")
        ) {
          Icon(
            imageVector = if (nonNullArticle.isRead) Icons.Default.CheckCircle else Icons.Default.CheckCircleOutline,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (nonNullArticle.isRead) "Marked Read" else "Mark Complete",
            fontSize = 12.sp,
            color = if (nonNullArticle.isRead) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimary
          )
        }
      }

      Spacer(modifier = Modifier.height(40.dp))
    }
  }
}
