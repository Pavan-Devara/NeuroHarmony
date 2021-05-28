package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class SuspendChatRequest(
    @SerializedName("data")
    val `data`: DataSuspendChatRequest
)