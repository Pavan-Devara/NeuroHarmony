package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataSignupResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("otp")
    val otp: Int,
    @SerializedName("user_key")
    val userKey: Int,
    @SerializedName("username")
    val username: String
)