package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class DataFinalMatchRequest(
    @SerializedName("receiver_key")
    val receiverKey: Any?,
    @SerializedName("country_code")
    val country_code: Any?,
    @SerializedName("mobile_number")
    val mobile_number: Any?
)