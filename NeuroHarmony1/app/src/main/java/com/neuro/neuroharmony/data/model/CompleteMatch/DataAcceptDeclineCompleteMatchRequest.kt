package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class DataAcceptDeclineCompleteMatchRequest(
    @SerializedName("action_type")
    val actionType: Int,
    @SerializedName("sender_key")
    val senderKey: Int
)