package com.newsportal.info_6134capstoneproject.data

import com.newsportal.info_6134capstoneproject.model.Article

interface DataSource{
    fun retrieveArticles(title: String, callback: OperationCallback<Article>) // New method with title parameter
    fun retrieveSearchArticles(que: String, callback: OperationCallback<Article>)
    fun cancel()
}