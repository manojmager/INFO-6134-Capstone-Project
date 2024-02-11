package com.newsportal.info_6134capstoneproject.response
import com.google.gson.annotations.SerializedName
import com.newsportal.info_6134capstoneproject.model.Article
data class ArticlesResponse(
    @SerializedName("status") var status: String?, //ok
    @SerializedName("source") var source: String?, //abc-news-au
    @SerializedName("sortBy") var sortBy: String?, //top
    @SerializedName("articles") var articles: List<Article>?
)