package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataProfileInfoResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("user_key")
    val userKey: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("account_status")
    val accountStatus: Int
)