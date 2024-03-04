package com.newsportal.info_6134capstoneproject.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiClient {
    private var apiInterface: ApiInterface? = null

    /**
     * Function to build and return the Retrofit service interface
     */
    fun build(): ApiInterface {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        // Create and return the service interface
        apiInterface = retrofit.create(ApiInterface::class.java)
        return apiInterface!!
    }

    /**
     * Function to define the interceptor for adding API key to requests
     */
    private fun interceptor(): Interceptor {
        return Interceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("x-api-key", Api.API_KEY) // Add API key to headers
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    }
}