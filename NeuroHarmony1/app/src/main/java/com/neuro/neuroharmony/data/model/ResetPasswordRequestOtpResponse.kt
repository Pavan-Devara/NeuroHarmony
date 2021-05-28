package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class ResetPasswordRequestOtpResponse(
    @SerializedName("data")
    val `data`: DataResetPasswordRequestOtpResponse,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("secured")
    val secured: Boolean,
    @SerializedName("status")
    val status: Int,
    @SerializedName("token")
    val token: String
)