package com.example.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class TtsManager(context: Context) : TextToSpeech.OnInitListener {

  private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
  private var isInitialized = false

  private val _isPlaying = MutableStateFlow(false)
  val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

  private val _currentPlayingId = MutableStateFlow<Long?>(null)
  val currentPlayingId: StateFlow<Long?> = _currentPlayingId.asStateFlow()

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      val result = tts?.setLanguage(Locale.ENGLISH)
      if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
        isInitialized = true
        tts?.setSpeechRate(0.95f)
        tts?.setPitch(1.0f)
        setupUtteranceListener()
      }
    }
  }

  private fun setupUtteranceListener() {
    tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
      override fun onStart(utteranceId: String?) {
        _isPlaying.value = true
      }

      override fun onDone(utteranceId: String?) {
        _isPlaying.value = false
        _currentPlayingId.value = null
      }

      @Deprecated("Deprecated in Java")
      override fun onError(utteranceId: String?) {
        _isPlaying.value = false
        _currentPlayingId.value = null
      }
    })
  }

  fun speakArticle(articleId: Long, title: String, summary: String, pointers: String) {
    if (!isInitialized) return

    if (_isPlaying.value && _currentPlayingId.value == articleId) {
      stop()
      return
    }

    stop()
    _currentPlayingId.value = articleId
    _isPlaying.value = true

    val cleanPointers = pointers.replace("•", ". ").replace("\n", ". ")
    val speechText = "$title. Summary: $summary. Key Prelims Takeaways: $cleanPointers"

    tts?.speak(speechText, TextToSpeech.QUEUE_FLUSH, null, "article_$articleId")
  }

  fun stop() {
    tts?.stop()
    _isPlaying.value = false
    _currentPlayingId.value = null
  }

  fun shutdown() {
    tts?.stop()
    tts?.shutdown()
    tts = null
    isInitialized = false
    _isPlaying.value = false
    _currentPlayingId.value = null
  }
}
