package com.newsportal.info_6134capstoneproject.data
import com.newsportal.info_6134capstoneproject.response.LatestHeadlinesResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

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

    /**
     * Retrofit service interface defining API endpoints
     */
    interface ApiInterface {
        @GET("latest_headlines")
        fun getLatestHeadlines(
            @Query("topic") topic: String,
            @Query("countries") countries: String,
            @Query("when") whenParam: String,
            @Header("x-api-key") apiKey: String = Api.API_KEY
        ): Call<LatestHeadlinesResponse>
    }
}