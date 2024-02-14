package com.newsportal.info_6134capstoneproject.data

import com.newsportal.info_6134capstoneproject.model.Article
import com.newsportal.info_6134capstoneproject.response.LatestHeadlinesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val apiClient: ApiClient) : DataSource {
    private var call: Call<LatestHeadlinesResponse>? = null
    private val service = apiClient.build()
    override fun retrieveArticles(callback: OperationCallback<Article>) {
        call = service.getLatestHeadlines(topic = "business", countries = "ca", whenParam = "7d" )
        call?.enqueue(object : Callback<LatestHeadlinesResponse> {
            override fun onFailure(call: Call<LatestHeadlinesResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<LatestHeadlinesResponse>, response: Response<LatestHeadlinesResponse>) {
                response.body()?.let {
                    if (response.isSuccessful && it.articles != null) {
                        // Convert the list of source names to a list of Source objects
                        val sourceName = it.articles.map { sourceName ->
                            Article(title = sourceName.title, author = sourceName.author,
                                published_date = sourceName.published_date, link = sourceName.link,
                                excerpt = sourceName.excerpt, topic = sourceName.topic, media = sourceName.media)
                        }
                        callback.onSuccess(sourceName)
                    } else {
                        callback.onError(it.status)
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}
