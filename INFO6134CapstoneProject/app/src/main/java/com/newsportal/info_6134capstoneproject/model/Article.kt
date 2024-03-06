package com.newsportal.info_6134capstoneproject.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Article(
    val title: String?,
    val author: String?,
    val published_date: String?,
    val link: String?,
    val media: String?,
    val excerpt: String?,
    val summary: String,
    val topic: String?,
): Serializable

//data class Article(
//    val title: String,
//    val author: String,
//    val published_date: String,
//    val link: String,
//    val clean_url: String,
//    val excerpt: String,
//    val summary: String,
//    val rights: String,
//    val rank: Int,
//    val topic: String,
//    val country: String,
//    val language: String,
//    val authors: String,
//    val media: String,
//    val is_opinion: Boolean,
//    val twitter_account: String?,
//    val _score: Any?,
//    val _id: String
//): Serializable