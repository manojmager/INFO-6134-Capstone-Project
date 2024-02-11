package com.newsportal.info_6134capstoneproject.response
import com.google.gson.annotations.SerializedName
import com.newsportal.info_6134capstoneproject.model.Category
import com.newsportal.info_6134capstoneproject.model.Source
data class SourceResponse(
    @SerializedName("status") var status: String?, //ok
    @SerializedName("sources") var sources: List<Source>
)