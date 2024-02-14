package com.newsportal.info_6134capstoneproject.data
import com.newsportal.info_6134capstoneproject.model.Article
interface DataSource{
    fun retrieveArticles(callback: OperationCallback<Article>)
    fun cancel()
}
