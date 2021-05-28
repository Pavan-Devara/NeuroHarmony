package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataSuspendChatRequest(
    @SerializedName("user_key")
    val userKey: String,
    @SerializedName("action_type")
    val actionType: Int

)