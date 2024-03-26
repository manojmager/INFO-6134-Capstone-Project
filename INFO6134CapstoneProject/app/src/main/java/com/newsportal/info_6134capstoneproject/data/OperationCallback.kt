package com.newsportal.info_6134capstoneproject.data
interface OperationCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}

