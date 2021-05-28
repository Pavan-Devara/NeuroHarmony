package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class RequestChatRequest(
    @SerializedName("data")
    val `data`: DataRequestChatRequest
)