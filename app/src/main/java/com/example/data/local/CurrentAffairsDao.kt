package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.CurrentAffairArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentAffairsDao {
  @Query("SELECT * FROM current_affairs_articles ORDER BY date DESC, id DESC")
  fun getAllArticles(): Flow<List<CurrentAffairArticle>>

  @Query("SELECT * FROM current_affairs_articles WHERE date = :date ORDER BY id ASC")
  fun getArticlesByDate(date: String): Flow<List<CurrentAffairArticle>>

  @Query("SELECT * FROM current_affairs_articles WHERE isBookmarked = 1 ORDER BY date DESC")
  fun getBookmarkedArticles(): Flow<List<CurrentAffairArticle>>

  @Query("SELECT * FROM current_affairs_articles WHERE needsRevision = 1 ORDER BY date DESC")
  fun getRevisionArticles(): Flow<List<CurrentAffairArticle>>

  @Query("SELECT * FROM current_affairs_articles WHERE id = :id LIMIT 1")
  fun getArticleById(id: Long): Flow<CurrentAffairArticle?>

  @Query("""
    SELECT * FROM current_affairs_articles 
    WHERE title LIKE '%' || :query || '%' 
       OR summary LIKE '%' || :query || '%' 
       OR syllabusTag LIKE '%' || :query || '%'
       OR prelimsPointers LIKE '%' || :query || '%'
       OR keyPoints LIKE '%' || :query || '%'
    ORDER BY date DESC
  """)
  fun searchArticles(query: String): Flow<List<CurrentAffairArticle>>

  @Query("SELECT DISTINCT date FROM current_affairs_articles ORDER BY date DESC")
  fun getAvailableDates(): Flow<List<String>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertArticles(articles: List<CurrentAffairArticle>)

  @Update
  suspend fun updateArticle(article: CurrentAffairArticle)

  @Query("UPDATE current_affairs_articles SET isBookmarked = :isBookmarked WHERE id = :id")
  suspend fun updateBookmark(id: Long, isBookmarked: Boolean)

  @Query("UPDATE current_affairs_articles SET isRead = :isRead WHERE id = :id")
  suspend fun updateReadStatus(id: Long, isRead: Boolean)

  @Query("UPDATE current_affairs_articles SET userNotes = :notes WHERE id = :id")
  suspend fun updateNotes(id: Long, notes: String)

  @Query("UPDATE current_affairs_articles SET needsRevision = :needsRevision WHERE id = :id")
  suspend fun updateNeedsRevision(id: Long, needsRevision: Boolean)

  @Query("SELECT COUNT(*) FROM current_affairs_articles")
  suspend fun getArticleCount(): Int

  @Query("SELECT COUNT(*) FROM current_affairs_articles WHERE isRead = 1")
  fun getReadArticlesCount(): Flow<Int>
}
