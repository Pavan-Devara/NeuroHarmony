package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("data")
    val `data`: DataLoginRequest,
    @SerializedName("secured")
    val secured: Boolean
)