package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class ResetPasswordRequestOtpRequest(
    @SerializedName("data")
    val `data`: DataResetPasswordRequestOtpRequest,
    @SerializedName("secured")
    val secured: Boolean
)