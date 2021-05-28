package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataResetPasswordRequest(
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("password")
    val password: String
)