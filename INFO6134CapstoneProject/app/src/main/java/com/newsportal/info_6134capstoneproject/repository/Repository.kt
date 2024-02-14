package com.newsportal.info_6134capstoneproject.repository

import com.newsportal.info_6134capstoneproject.data.DataSource
import com.newsportal.info_6134capstoneproject.data.OperationCallback
import com.newsportal.info_6134capstoneproject.model.Article
class Repository(private val dataSource: DataSource) {
    fun fetchArticles(callback: OperationCallback<Article>) {
        dataSource.retrieveArticles(callback)
    }
    fun cancel() {
        dataSource.cancel()
    }
}