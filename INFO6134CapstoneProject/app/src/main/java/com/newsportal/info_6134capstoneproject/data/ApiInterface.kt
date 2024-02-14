package com.newsportal.info_6134capstoneproject.data
import com.newsportal.info_6134capstoneproject.response.ArticlesResponse
import com.newsportal.info_6134capstoneproject.response.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
interface ApiInterface {
    @GET("sources")
    fun getSources(
        @Query("topic") topic: String?,
        @Query("lang") language: String?,
        @Query("countries") countries: String?,
        @Header("x-api-key") apiKey: String
    ): Call<SourceResponse>

    @GET("latest_headlines")
    fun getArticles(
        @Query("source") source: String,
        @Query("sortBy") sortBy: String?,
        @Header("x-api-key") apiKey: String
    ): Call<ArticlesResponse>
}