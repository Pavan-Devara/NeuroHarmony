package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class ProfileInfoResponse(
    @SerializedName("data")
    val `data`: DataProfileInfoResponse,
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