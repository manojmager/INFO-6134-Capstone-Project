package com.newsportal.info_6134capstoneproject.data

import com.newsportal.info_6134capstoneproject.response.LatestHeadlinesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
interface ApiInterface {
    /**
     * Retrofit service interface defining API endpoints
     */
    @GET("latest_headlines")
    fun getLatestHeadlines(
        @Query("topic") topic: String,
        @Query("countries") countries: String,
        @Query("when") whenParam: String,
        @Header("x-api-key") apiKey: String = Api.API_KEY
    ): Call<LatestHeadlinesResponse>

    @GET("search")
    fun getSearchedArticles(
        @Query("q") query: String,
        @Header("x-api-key") apiKey: String = Api.API_KEY
    ): Call<LatestHeadlinesResponse>
}