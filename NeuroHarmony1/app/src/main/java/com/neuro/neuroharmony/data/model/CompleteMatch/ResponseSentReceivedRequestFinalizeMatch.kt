package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class ResponseSentReceivedRequestFinalizeMatch(
    @SerializedName("data")
    val `data`: List<Data>,
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