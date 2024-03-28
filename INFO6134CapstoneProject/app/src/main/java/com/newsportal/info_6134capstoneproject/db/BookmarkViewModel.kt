package com.newsportal.info_6134capstoneproject.db

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.newsportal.info_6134capstoneproject.data.NotificationHandler
import com.newsportal.info_6134capstoneproject.model.Article
import com.newsportal.info_6134capstoneproject.model.DBArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<DBArticle>>
    private val repository: BookmarkRepository
    private val notificationHandler: NotificationHandler

    init {
        val dao = BookmarkDatabase.getDatabase(application).dao()
        repository = BookmarkRepository(dao)
        readAllData = repository.readAllData
        Log.d(TAG, "read: $readAllData")
        notificationHandler = NotificationHandler(application.applicationContext)
    }

    fun addBookmark(dbArticle: DBArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBookmark(dbArticle)
            val article = Article(
                title = dbArticle.title,
                excerpt = "",
                media = dbArticle.media,
                summary = dbArticle.summary,
                link = dbArticle.link,
                author = "",
                published_date = "",
                topic = "",
                _id = "",
            )
            notificationHandler.sendPushNotification(article)
        }
    }





    fun deleteBookmark(dbArticle: DBArticle){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBookmark(dbArticle)
        }
    }

}