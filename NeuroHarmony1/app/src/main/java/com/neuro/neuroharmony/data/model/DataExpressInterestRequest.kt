package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataExpressInterestRequest(
    @SerializedName("receiver_key")
    val receiverKey: String
)