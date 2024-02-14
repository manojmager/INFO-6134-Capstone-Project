package com.newsportal.info_6134capstoneproject.response

import com.newsportal.info_6134capstoneproject.model.Article

data class LatestHeadlinesResponse(
    val status: String,
    val total_hits: Int,
    val page: Int,
    val total_pages: Int,
    val page_size: Int,
    val articles: List<Article>,
    val user_input_article: UserInputArticle
)

data class UserInputArticle(
    val lang: Any?,
    val not_lang: Any?,
    val countries: List<String>,
    val not_countries: Any?,
    val page: Int,
    val size: Int,
    val sources: Any?,
    val not_sources: List<Any>,
    val topic: String,
    val from: String
)