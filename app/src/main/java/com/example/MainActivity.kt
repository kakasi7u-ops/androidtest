package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.FactCheck
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.ArticleDetailScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MainsPracticeScreen
import com.example.ui.screens.OneLinersScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.SavedAndStatsScreen
import com.example.ui.theme.CivilCurrentsTheme
import com.example.ui.viewmodel.CurrentAffairsViewModel
import com.example.ui.viewmodel.ScreenDestination

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      CivilCurrentsTheme {
        CivilCurrentsApp()
      }
    }
  }
}

@Composable
fun CivilCurrentsApp(
  viewModel: CurrentAffairsViewModel = viewModel()
) {
  val currentScreen by viewModel.currentScreen.collectAsState()

  val showBottomBar = currentScreen !is ScreenDestination.ArticleDetail

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
      if (showBottomBar) {
        NavigationBar(
          containerColor = MaterialTheme.colorScheme.surface,
          tonalElevation = 6.dp,
          modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("main_bottom_nav")
        ) {
          NavigationBarItem(
            selected = currentScreen is ScreenDestination.Home,
            onClick = { viewModel.navigateTo(ScreenDestination.Home) },
            icon = {
              Icon(
                imageVector = if (currentScreen is ScreenDestination.Home) Icons.Default.Newspaper else Icons.Outlined.Newspaper,
                contentDescription = "Daily News",
                modifier = Modifier.size(22.dp)
              )
            },
            label = { Text("Daily Feed", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
              indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_feed")
          )

          NavigationBarItem(
            selected = currentScreen is ScreenDestination.Quiz,
            onClick = { viewModel.navigateTo(ScreenDestination.Quiz) },
            icon = {
              Icon(
                imageVector = if (currentScreen is ScreenDestination.Quiz) Icons.Default.FactCheck else Icons.Outlined.FactCheck,
                contentDescription = "MCQs",
                modifier = Modifier.size(22.dp)
              )
            },
            label = { Text("Prelims MCQ", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
              indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_quiz")
          )

          NavigationBarItem(
            selected = currentScreen is ScreenDestination.MainsPractice,
            onClick = { viewModel.navigateTo(ScreenDestination.MainsPractice) },
            icon = {
              Icon(
                imageVector = if (currentScreen is ScreenDestination.MainsPractice) Icons.Default.EditNote else Icons.Outlined.EditNote,
                contentDescription = "Mains",
                modifier = Modifier.size(22.dp)
              )
            },
            label = { Text("Mains Qs", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
              indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_mains")
          )

          NavigationBarItem(
            selected = currentScreen is ScreenDestination.OneLiners,
            onClick = { viewModel.navigateTo(ScreenDestination.OneLiners) },
            icon = {
              Icon(
                imageVector = if (currentScreen is ScreenDestination.OneLiners) Icons.Default.Speed else Icons.Outlined.Speed,
                contentDescription = "One-Liners",
                modifier = Modifier.size(22.dp)
              )
            },
            label = { Text("One-Liners", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
              indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_oneliners")
          )

          NavigationBarItem(
            selected = currentScreen is ScreenDestination.SavedAndStats,
            onClick = { viewModel.navigateTo(ScreenDestination.SavedAndStats) },
            icon = {
              Icon(
                imageVector = if (currentScreen is ScreenDestination.SavedAndStats) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = "Vault",
                modifier = Modifier.size(22.dp)
              )
            },
            label = { Text("Vault", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
              indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_vault")
          )
        }
      }
    }
  ) { innerPadding ->
    Crossfade(
      targetState = currentScreen,
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      label = "screen_crossfade"
    ) { screen ->
      when (screen) {
        is ScreenDestination.Home -> HomeScreen(viewModel = viewModel)
        is ScreenDestination.ArticleDetail -> ArticleDetailScreen(viewModel = viewModel)
        is ScreenDestination.Quiz -> QuizScreen(viewModel = viewModel)
        is ScreenDestination.MainsPractice -> MainsPracticeScreen(viewModel = viewModel)
        is ScreenDestination.OneLiners -> OneLinersScreen(viewModel = viewModel)
        is ScreenDestination.SavedAndStats -> SavedAndStatsScreen(viewModel = viewModel)
      }
    }
  }
}
