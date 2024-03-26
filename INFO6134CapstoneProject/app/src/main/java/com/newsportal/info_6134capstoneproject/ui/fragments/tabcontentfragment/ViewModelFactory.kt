package com.newsportal.info_6134capstoneproject.ui.fragments.tabcontentfragment

import TabContentViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newsportal.info_6134capstoneproject.repository.Repository
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TabContentViewModel(repository) as T
    }
}