package com.newsportal.info_6134capstoneproject.db

import androidx.lifecycle.LiveData
import com.newsportal.info_6134capstoneproject.model.DBArticle

class BookmarkRepository (private val dao: Dao) {

    val readAllData: LiveData<List<DBArticle>> = dao.getBookedMarkedArticles()

    suspend fun addBookmark(dbArticle: DBArticle){
        dao.insertArticle(dbArticle)
    }

    suspend fun deleteBookmark(dbArticle: DBArticle){
        dao.deleteArticle(dbArticle)
    }

}