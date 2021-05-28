package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataResetPasswordRequestOtpRequest(
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("country_code")
    val country_code_picker_forgot_pwd: Int
)