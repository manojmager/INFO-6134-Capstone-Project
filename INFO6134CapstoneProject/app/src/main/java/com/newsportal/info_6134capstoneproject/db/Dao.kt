package com.newsportal.info_6134capstoneproject.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsportal.info_6134capstoneproject.model.DBArticle
import kotlinx.coroutines.flow.Flow
@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(dbArticle: DBArticle)
    @Delete
    suspend fun deleteArticle(dbArticle: DBArticle)
    @Query("SELECT * FROM bookmark_article ORDER BY _id ASC")
    fun getBookedMarkedArticles(): LiveData<List<DBArticle>>
}