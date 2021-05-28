package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataDeclineInterestRequestRequest(
    @SerializedName("action_type")
    val actionType: Int,
    @SerializedName("sender_key")
    val senderKey: String
)