package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.CurrentAffairArticle
import com.example.data.model.DailyStudyRecord
import com.example.data.model.ExamCategory
import com.example.data.model.MainsPracticeItem
import com.example.data.model.OneLinerFact
import com.example.data.model.QuizQuestion
import com.example.data.model.TopicCategory
import com.example.data.repository.CurrentAffairsRepository
import com.example.util.TtsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ScreenDestination {
  object Home : ScreenDestination()
  data class ArticleDetail(val articleId: Long) : ScreenDestination()
  object Quiz : ScreenDestination()
  object MainsPractice : ScreenDestination()
  object OneLiners : ScreenDestination()
  object SavedAndStats : ScreenDestination()
}

class CurrentAffairsViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: CurrentAffairsRepository
  val ttsManager: TtsManager = TtsManager(application)

  private val _currentScreen = MutableStateFlow<ScreenDestination>(ScreenDestination.Home)
  val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

  private val _screenHistory = mutableListOf<ScreenDestination>()

  private val _selectedExam = MutableStateFlow(ExamCategory.ALL)
  val selectedExam: StateFlow<ExamCategory> = _selectedExam.asStateFlow()

  private val _selectedTopic = MutableStateFlow<TopicCategory?>(null)
  val selectedTopic: StateFlow<TopicCategory?> = _selectedTopic.asStateFlow()

  private val _selectedDate = MutableStateFlow("2026-10-01")
  val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _fontScale = MutableStateFlow(1.0f)
  val fontScale: StateFlow<Float> = _fontScale.asStateFlow()

  // Selected article for detail screen
  private val _selectedArticleId = MutableStateFlow<Long?>(null)
  val selectedArticleId: StateFlow<Long?> = _selectedArticleId.asStateFlow()

  init {
    val db = AppDatabase.getDatabase(application)
    repository = CurrentAffairsRepository(db)

    viewModelScope.launch {
      repository.ensureDatabaseSeeded()
    }
  }

  val availableDates: StateFlow<List<String>> = repository.getAvailableDates()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf("2026-10-01", "2026-09-30", "2026-09-29"))

  val allArticles: StateFlow<List<CurrentAffairArticle>> = repository.getAllArticles()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val bookmarkedArticles: StateFlow<List<CurrentAffairArticle>> = repository.getBookmarkedArticles()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val revisionArticles: StateFlow<List<CurrentAffairArticle>> = repository.getRevisionArticles()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val readCount: StateFlow<Int> = repository.getReadArticlesCount()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  val studyRecords: StateFlow<List<DailyStudyRecord>> = repository.getAllStudyRecords()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Filtered articles for the Home Screen
  val filteredArticles: StateFlow<List<CurrentAffairArticle>> = combine(
    allArticles,
    _selectedExam,
    _selectedTopic,
    _selectedDate,
    _searchQuery
  ) { list, exam, topic, date, query ->
    list.filter { article ->
      val matchesDate = if (query.isNotBlank()) true else (article.date == date)
      val matchesExam = when (exam) {
        ExamCategory.ALL -> true
        ExamCategory.UPSC -> article.examFocus.contains("UPSC", ignoreCase = true) || article.examFocus.contains("ALL", ignoreCase = true)
        ExamCategory.SSC -> article.examFocus.contains("SSC", ignoreCase = true) || article.examFocus.contains("ALL", ignoreCase = true)
        ExamCategory.STATE_PCS -> article.examFocus.contains("PCS", ignoreCase = true) || article.examFocus.contains("ALL", ignoreCase = true)
      }
      val matchesTopic = topic == null || article.topicCategory == topic.name
      val matchesQuery = query.isBlank() || (
        article.title.contains(query, ignoreCase = true) ||
        article.summary.contains(query, ignoreCase = true) ||
        article.syllabusTag.contains(query, ignoreCase = true) ||
        article.prelimsPointers.contains(query, ignoreCase = true) ||
        article.keyPoints.contains(query, ignoreCase = true)
      )

      matchesDate && matchesExam && matchesTopic && matchesQuery
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Current selected article object
  val currentArticle: StateFlow<CurrentAffairArticle?> = combine(
    allArticles,
    _selectedArticleId
  ) { list, id ->
    if (id == null) null else list.find { it.id == id }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

  // Quiz questions for selected date
  val currentQuizzes: StateFlow<List<QuizQuestion>> = combine(
    _selectedDate,
    repository.getAllQuestions()
  ) { date, allQuestions ->
    val forDate = allQuestions.filter { it.date == date }
    if (forDate.isNotEmpty()) forDate else allQuestions.take(5)
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Mains practice items for selected date
  val currentMains: StateFlow<List<MainsPracticeItem>> = combine(
    _selectedDate,
    repository.getAllMains()
  ) { date, allMains ->
    val forDate = allMains.filter { it.date == date }
    if (forDate.isNotEmpty()) forDate else allMains.take(2)
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // One Liners
  val currentOneLiners: StateFlow<List<OneLinerFact>> = combine(
    _selectedDate,
    repository.getAllOneLiners()
  ) { date, allFacts ->
    val forDate = allFacts.filter { it.date == date }
    if (forDate.isNotEmpty()) forDate else allFacts
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Navigation
  fun navigateTo(destination: ScreenDestination) {
    if (_currentScreen.value != destination) {
      _screenHistory.add(_currentScreen.value)
      _currentScreen.value = destination
      if (destination is ScreenDestination.ArticleDetail) {
        _selectedArticleId.value = destination.articleId
      }
    }
  }

  fun navigateBack(): Boolean {
    if (_screenHistory.isNotEmpty()) {
      _currentScreen.value = _screenHistory.removeAt(_screenHistory.size - 1)
      return true
    }
    if (_currentScreen.value !is ScreenDestination.Home) {
      _currentScreen.value = ScreenDestination.Home
      return true
    }
    return false
  }

  // Filter setters
  fun setExam(exam: ExamCategory) {
    _selectedExam.value = exam
  }

  fun setTopic(topic: TopicCategory?) {
    _selectedTopic.value = topic
  }

  fun setDate(date: String) {
    _selectedDate.value = date
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun setFontScale(scale: Float) {
    _fontScale.value = scale
  }

  // Actions on Articles
  fun toggleBookmark(article: CurrentAffairArticle) {
    viewModelScope.launch {
      repository.toggleBookmark(article.id, article.isBookmarked)
    }
  }

  fun toggleRead(article: CurrentAffairArticle) {
    viewModelScope.launch {
      repository.toggleReadStatus(article.id, article.isRead, article.date)
    }
  }

  fun updateNotes(articleId: Long, notes: String) {
    viewModelScope.launch {
      repository.updateNotes(articleId, notes)
    }
  }

  fun toggleRevision(article: CurrentAffairArticle) {
    viewModelScope.launch {
      repository.toggleNeedsRevision(article.id, article.needsRevision)
    }
  }

  // Quiz submission
  fun submitQuizAnswer(question: QuizQuestion, selectedIndex: Int) {
    viewModelScope.launch {
      val isCorrect = (selectedIndex == question.correctOptionIndex)
      repository.submitQuizAnswer(question.id, selectedIndex, isCorrect, question.date)
    }
  }

  fun resetQuiz() {
    viewModelScope.launch {
      repository.resetQuizForDate(_selectedDate.value)
    }
  }

  // Mains Draft save
  fun saveMainsAnswer(mainsItem: MainsPracticeItem, answerText: String, completed: Boolean) {
    viewModelScope.launch {
      repository.saveMainsAnswer(mainsItem.id, answerText, completed)
    }
  }

  // One Liner Bookmark
  fun toggleOneLinerBookmark(fact: OneLinerFact) {
    viewModelScope.launch {
      repository.toggleOneLinerBookmark(fact.id, fact.isBookmarked)
    }
  }

  // TTS Audio
  fun playAudioForArticle(article: CurrentAffairArticle) {
    ttsManager.speakArticle(
      articleId = article.id,
      title = article.title,
      summary = article.summary,
      pointers = article.prelimsPointers
    )
  }

  fun stopAudio() {
    ttsManager.stop()
  }

  override fun onCleared() {
    super.onCleared()
    ttsManager.shutdown()
  }
}
