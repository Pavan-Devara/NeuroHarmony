package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataResetPasswordRequestOtpResponse(
    @SerializedName("otp")
    val otp: Int,
    @SerializedName("otp_expiry_time")
    val otpExpiryTime: Int,
    @SerializedName("otp_retry_limit")
    val otpRetryLimit: Int
)