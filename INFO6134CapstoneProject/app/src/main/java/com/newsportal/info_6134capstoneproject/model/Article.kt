package com.newsportal.info_6134capstoneproject.model
import java.io.Serializable
data class Article(
    val title: String?,
    val author: String?,
    val published_date: String?,
    val link: String?,
    val media: String?,
    val excerpt: String?,
    val summary: String?,
    val topic: String?,
    val _id: String
): Serializable

/*
data class Article(
    val clean_url: String,
    val rights: String,
    val rank: Int,
    val topic: String,
    val country: String,
    val language: String,
    val authors: String,
    val is_opinion: Boolean,
    val twitter_account: String?,
    val _score: Any?,
): Serializable*/
