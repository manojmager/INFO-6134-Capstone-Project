package com.newsportal.info_6134capstoneproject.repository

import com.newsportal.info_6134capstoneproject.data.DataSource
import com.newsportal.info_6134capstoneproject.data.OperationCallback
import com.newsportal.info_6134capstoneproject.model.Article

class Repository(private val dataSource: DataSource) {
    fun fetchArticles(title: String, callback: OperationCallback<Article>) {
        dataSource.retrieveArticles(title, callback)
    }
    fun fetchSearchedArticles(que: String, callback: OperationCallback<Article>) {
        dataSource.retrieveSearchArticles(que, callback)
    }
    fun cancel() {
        dataSource.cancel()
    }
}