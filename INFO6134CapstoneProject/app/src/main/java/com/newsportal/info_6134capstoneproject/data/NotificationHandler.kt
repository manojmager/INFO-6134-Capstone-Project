package com.newsportal.info_6134capstoneproject.data

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.newsportal.info_6134capstoneproject.model.Article
import com.newsportal.info_6134capstoneproject.response.NotificationHelper
import com.newsportal.info_6134capstoneproject.ui.activities.ArticleDetailsActivity

class NotificationHandler(private val context: Context) {
    fun sendPushNotification(article: Article) {
        saveArticleToSharedPreferences(article)
        val title = article.title ?: ""
        val message = article.excerpt ?: ""
        val imageUrl = article.media
        val summary = article.summary
        val url = article.link

        val intent = Intent(context, ArticleDetailsActivity::class.java).apply {
            putExtra("url", url)
            putExtra("media", imageUrl)
            putExtra("title", title)
            putExtra("summary", summary)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (imageUrl != null) {
            NotificationHelper.showNotification(context, title, message, imageUrl, pendingIntent)
        }
    }

    private fun saveArticleToSharedPreferences(article: Article) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val articlesString = sharedPreferences.getString("articles", null)
        val articlesType = object : TypeToken<List<Article>>() {}.type
        val articles: MutableList<Article> = if (articlesString != null) {
            gson.fromJson(articlesString, articlesType)
        } else {
            mutableListOf()
        }

        articles.add(article)
        val updatedArticlesString = gson.toJson(articles)
        sharedPreferences.edit().putString("articles", updatedArticlesString).apply()
    }
}

