package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataInitiateMatchRequest(
    @SerializedName("user_key")
    val userKey: Int
)