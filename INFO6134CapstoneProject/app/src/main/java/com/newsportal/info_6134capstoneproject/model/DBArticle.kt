package com.newsportal.info_6134capstoneproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "bookmark_article")
data class DBArticle(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val title: String?,
    val author: String?,
    val published_date: String?,
    val link: String?,
    val media: String?,
    val excerpt: String?,
    val summary: String?,
    val topic: String?,
)