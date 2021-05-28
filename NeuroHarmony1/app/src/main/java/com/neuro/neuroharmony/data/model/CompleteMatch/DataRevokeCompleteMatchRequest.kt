package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class DataRevokeCompleteMatchRequest(
    @SerializedName("receiver_key")
    val receiverKey: Int
)