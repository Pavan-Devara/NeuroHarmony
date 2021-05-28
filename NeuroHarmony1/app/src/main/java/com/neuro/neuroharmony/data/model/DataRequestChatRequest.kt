package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataRequestChatRequest(
    @SerializedName("receiver_key")
    val receiverKey: Int
)