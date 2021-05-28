package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("data")
    val `data`: DataResetPasswordRequest,
    @SerializedName("secured")
    val secured: Boolean
)