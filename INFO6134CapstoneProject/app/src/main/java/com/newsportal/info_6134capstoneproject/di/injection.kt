package com.newsportal.info_6134capstoneproject.di

import com.newsportal.info_6134capstoneproject.data.ApiClient
import com.newsportal.info_6134capstoneproject.data.DataSource
import com.newsportal.info_6134capstoneproject.data.RemoteDataSource
import com.newsportal.info_6134capstoneproject.repository.Repository
import com.newsportal.info_6134capstoneproject.ui.fragments.tabcontentfragment.ViewModelFactory
object Injection {

    private var ds: DataSource? = null
    private var repo: Repository? = null
    private var viewModelFactory: ViewModelFactory? = null

    private fun createDataSource(): DataSource {
        val dataSource = RemoteDataSource(ApiClient)
        ds = dataSource
        return dataSource
    }

    private fun createRepository(): Repository {
        val repository = Repository(provideDataSource())
        repo = repository
        return repository
    }

    private fun createFactory(): ViewModelFactory {
        val factory = ViewModelFactory(providerRepository())
        viewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = ds ?: createDataSource()
    private fun providerRepository() = repo ?: createRepository()

    fun provideViewModelFactory() = viewModelFactory ?: createFactory()

    fun destroy() {
        ds = null
        repo = null
        viewModelFactory = null
    }
}