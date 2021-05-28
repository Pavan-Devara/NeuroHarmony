package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class TermsConditions(
    @SerializedName("content")
    val content: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_latest")
    val isLatest: Boolean,
    @SerializedName("version")
    val version: Int
)