package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("data")
    val `data`: DataSignupRequest,
    @SerializedName("secured")
    val secured: Boolean
)