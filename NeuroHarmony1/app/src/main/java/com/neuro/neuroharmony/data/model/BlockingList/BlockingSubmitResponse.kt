package com.neuro.neuroharmony.data.model.BlockingList


import com.google.gson.annotations.SerializedName


data class BlockingSubmitResponse(
    @SerializedName("data")
    val `data`: DataSumbitResponse,
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