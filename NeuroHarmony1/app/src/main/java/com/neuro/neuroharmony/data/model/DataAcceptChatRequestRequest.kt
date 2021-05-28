package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataAcceptChatRequestRequest(
    @SerializedName("action_type")
    val actionType: Int,
    @SerializedName("user_key")
    val userKey: Int
)