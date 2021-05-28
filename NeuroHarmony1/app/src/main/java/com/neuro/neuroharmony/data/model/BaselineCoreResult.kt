package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class BaselineCoreResult(
    @SerializedName("data")
    val `data`: DataXXXXXXX,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("secured")
    val secured: Boolean,
    @SerializedName("status")
    val status: Int,
    @SerializedName("token")
    val token: String
)