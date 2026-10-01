package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.viewmodel.CurrentAffairsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainsPracticeScreen(
  viewModel: CurrentAffairsViewModel,
  modifier: Modifier = Modifier
) {
  BackHandler {
    viewModel.navigateBack()
  }

  val mainsList by viewModel.currentMains.collectAsState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "Mains Answer Writing",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Daily Editorial Question & Structure",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("mains_back_button")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    }
  ) { innerPadding ->
    if (mainsList.isEmpty()) {
      Box(
        modifier = modifier
          .fillMaxSize()
          .padding(innerPadding),
        contentAlignment = Alignment.Center
      ) {
        Text("No Mains questions available for this date", color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
      return@Scaffold
    }

    val scrollState = rememberScrollState()

    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(scrollState)
        .padding(16.dp)
        .testTag("mains_screen_content")
    ) {
      mainsList.forEach { mainsItem ->
        var showFramework by remember(mainsItem.id) { mutableStateOf(false) }
        var userDraft by remember(mainsItem.id) { mutableStateOf(mainsItem.userDraftAnswer) }
        var savedStatus by remember { mutableStateOf(false) }

        val wordCount = userDraft.trim().split("\\s+".toRegex()).count { it.isNotEmpty() }

        // Question Container Card
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = mainsItem.paperTag,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onPrimaryContainer,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }

              Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Surface(
                  color = MaterialTheme.colorScheme.surfaceVariant,
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(
                    text = "${mainsItem.marks} Marks",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
                Surface(
                  color = MaterialTheme.colorScheme.surfaceVariant,
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(
                    text = "${mainsItem.wordLimit} Words",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
              text = "Q. ${mainsItem.question}",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                lineHeight = 24.sp
              ),
              color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Toggle Model Framework button
            OutlinedButton(
              onClick = { showFramework = !showFramework },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Icon(
                imageVector = if (showFramework) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (showFramework) "Hide Answer Structure" else "Show Model Answer Framework",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
              )
            }

            // Model Framework Details
            AnimatedVisibility(visible = showFramework) {
              Column(
                modifier = Modifier
                  .padding(top = 14.dp)
                  .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    RoundedCornerShape(8.dp)
                  )
                  .padding(12.dp)
              ) {
                // Intro
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = AmberSecondary,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "1. Introduction (15-20% weightage):",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                }
                Text(
                  text = mainsItem.introApproach,
                  fontSize = 12.sp,
                  lineHeight = 18.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(start = 22.dp, top = 2.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Body
                Text(
                  text = "2. Core Body Arguments:",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = MaterialTheme.colorScheme.onSurface,
                  modifier = Modifier.padding(start = 22.dp)
                )
                Text(
                  text = mainsItem.bodyApproach,
                  fontSize = 12.sp,
                  lineHeight = 18.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(start = 22.dp, top = 2.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Quotes / Committees
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Key Committees, Data & Precedents to Quote:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                  )
                }
                Text(
                  text = mainsItem.quotesAndCommittees,
                  fontSize = 12.sp,
                  lineHeight = 18.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(start = 22.dp, top = 2.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Conclusion
                Text(
                  text = "3. Conclusion & Way Forward:",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = MaterialTheme.colorScheme.onSurface,
                  modifier = Modifier.padding(start = 22.dp)
                )
                Text(
                  text = mainsItem.conclusionApproach,
                  fontSize = 12.sp,
                  lineHeight = 18.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(start = 22.dp, top = 2.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Aspirant Scratchpad / Answer Editor
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Your Answer Draft",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Words: $wordCount / ${mainsItem.wordLimit}",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (wordCount > mainsItem.wordLimit) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
              value = userDraft,
              onValueChange = {
                userDraft = it
                savedStatus = false
              },
              placeholder = { Text("Write your practice answer outline, intro, body points, and conclusion here...") },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("mains_draft_input_${mainsItem.id}"),
              minLines = 4,
              shape = RoundedCornerShape(10.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
              )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              if (savedStatus) {
                Text(
                  text = "✓ Answer draft saved!",
                  color = EmeraldTertiary,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              } else {
                Spacer(modifier = Modifier.width(1.dp))
              }

              Button(
                onClick = {
                  viewModel.saveMainsAnswer(mainsItem, userDraft, completed = true)
                  savedStatus = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.testTag("save_mains_btn_${mainsItem.id}")
              ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Save Practice Answer")
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))
      }

      Spacer(modifier = Modifier.height(40.dp))
    }
  }
}
