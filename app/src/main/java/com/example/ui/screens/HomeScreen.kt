package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ArticleCard
import com.example.ui.components.AudioPlayingStrip
import com.example.ui.components.ExamSelectorRow
import com.example.ui.components.TopicSelectorRow
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.theme.NavyPrimary
import com.example.ui.viewmodel.CurrentAffairsViewModel
import com.example.ui.viewmodel.ScreenDestination

@Composable
fun HomeScreen(
  viewModel: CurrentAffairsViewModel,
  modifier: Modifier = Modifier
) {
  val articles by viewModel.filteredArticles.collectAsState()
  val selectedExam by viewModel.selectedExam.collectAsState()
  val selectedTopic by viewModel.selectedTopic.collectAsState()
  val selectedDate by viewModel.selectedDate.collectAsState()
  val availableDates by viewModel.availableDates.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val readCount by viewModel.readCount.collectAsState()
  val isAudioPlaying by viewModel.ttsManager.isPlaying.collectAsState()
  val currentPlayingId by viewModel.ttsManager.currentPlayingId.collectAsState()

  var isSearchExpanded by remember { mutableStateOf(false) }

  val playingArticle = articles.find { it.id == currentPlayingId }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // Top Bar Header
    Surface(
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "CivilCurrents",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
              ),
              color = MaterialTheme.colorScheme.primary
            )
            Text(
              text = "Daily Current Affairs for UPSC, SSC & State PCS",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            // Study Streak Indicator
            Surface(
              color = AmberSecondary.copy(alpha = 0.15f),
              shape = RoundedCornerShape(20.dp),
              modifier = Modifier.padding(end = 4.dp)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.LocalFireDepartment,
                  contentDescription = "Streak",
                  tint = AmberSecondary,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "4 Days",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = AmberSecondary
                )
              }
            }

            // Search toggle icon
            IconButton(
              onClick = {
                isSearchExpanded = !isSearchExpanded
                if (!isSearchExpanded) viewModel.setSearchQuery("")
              },
              modifier = Modifier.testTag("search_toggle_button")
            ) {
              Icon(
                imageVector = if (isSearchExpanded) Icons.Default.Close else Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.primary
              )
            }
          }
        }

        // Search Input (Collapsible)
        if (isSearchExpanded) {
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            placeholder = { Text("Search topics, ministries, schemes, articles...") },
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 6.dp)
              .testTag("search_input_field"),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
              unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            trailingIcon = {
              if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { viewModel.setSearchQuery("") }) {
                  Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                }
              }
            }
          )
        }

        // Date selection chips
        val dateScrollState = rememberScrollState()
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(dateScrollState)
            .padding(horizontal = 16.dp, vertical = 4.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Date",
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )

          availableDates.forEach { dateStr ->
            val isSelected = dateStr == selectedDate
            val label = when (dateStr) {
              "2026-10-01" -> "Today (01 Oct)"
              "2026-09-30" -> "Yesterday (30 Sep)"
              "2026-09-29" -> "29 Sep 2026"
              else -> dateStr
            }

            Surface(
              shape = RoundedCornerShape(16.dp),
              color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier
                .clickable { viewModel.setDate(dateStr) }
                .testTag("date_chip_$dateStr")
            ) {
              Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              )
            }
          }
        }
      }
    }

    // Exam category filter pills
    ExamSelectorRow(
      selectedExam = selectedExam,
      onExamSelected = { viewModel.setExam(it) }
    )

    // Subject/Topic Filter pills
    TopicSelectorRow(
      selectedTopic = selectedTopic,
      onTopicSelected = { viewModel.setTopic(it) }
    )

    // Audio status strip (if active)
    AudioPlayingStrip(
      isPlaying = isAudioPlaying,
      articleTitle = playingArticle?.title,
      onStopClick = { viewModel.stopAudio() }
    )

    // Articles Feed
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .testTag("articles_lazy_column"),
      contentPadding = PaddingValues(bottom = 80.dp)
    ) {
      // Quick Revision & Practice Action Cards
      item {
        DailyPracticeBanner(
          onDailyQuizClick = { viewModel.navigateTo(ScreenDestination.Quiz) },
          onMainsClick = { viewModel.navigateTo(ScreenDestination.MainsPractice) },
          onOneLinersClick = { viewModel.navigateTo(ScreenDestination.OneLiners) }
        )
      }

      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Curated Daily Affairs (${articles.size})",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
          )
          Text(
            text = "Read $readCount",
            fontSize = 12.sp,
            color = EmeraldTertiary,
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      if (articles.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(40.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.outline
              )
              Spacer(modifier = Modifier.height(12.dp))
              Text(
                text = "No articles found",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = "Try switching the exam filter, subject, or clear search.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
              )
            }
          }
        }
      } else {
        items(articles, key = { it.id }) { article ->
          ArticleCard(
            article = article,
            isPlayingAudio = isAudioPlaying && currentPlayingId == article.id,
            onClick = {
              viewModel.navigateTo(ScreenDestination.ArticleDetail(article.id))
            },
            onBookmarkClick = { viewModel.toggleBookmark(article) },
            onReadToggle = { viewModel.toggleRead(article) },
            onAudioClick = { viewModel.playAudioForArticle(article) }
          )
        }
      }
    }
  }
}

@Composable
fun DailyPracticeBanner(
  onDailyQuizClick: () -> Unit,
  onMainsClick: () -> Unit,
  onOneLinersClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    // Daily Prelims Quiz Card
    Card(
      modifier = Modifier
        .weight(1f)
        .clickable { onDailyQuizClick() }
        .testTag("banner_quiz_btn"),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
      )
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.FactCheck,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "Daily MCQs",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "5 Prelims questions",
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )
      }
    }

    // Mains Practice Card
    Card(
      modifier = Modifier
        .weight(1f)
        .clickable { onMainsClick() }
        .testTag("banner_mains_btn"),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer
      )
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.EditNote,
            contentDescription = null,
            tint = AmberSecondary,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "Mains Qs",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Editorial Model Ans",
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
        )
      }
    }

    // Rapid One-Liners Card (SSC & PCS)
    Card(
      modifier = Modifier
        .weight(1f)
        .clickable { onOneLinersClick() }
        .testTag("banner_oneliners_btn"),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer
      )
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Speed,
            contentDescription = null,
            tint = EmeraldTertiary,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "One-Liners",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onTertiaryContainer
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "SSC & PCS facts",
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
        )
      }
    }
  }
}
