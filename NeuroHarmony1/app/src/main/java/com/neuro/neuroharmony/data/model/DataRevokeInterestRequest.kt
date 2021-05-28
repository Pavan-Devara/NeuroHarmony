package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataRevokeInterestRequest(
    @SerializedName("receiver_key")
    val receiverKey: Int,
    @SerializedName("action_type")
    val actionType: Int
)