package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataLoginResponse(
    @SerializedName("account_status")
    val accountStatus: Int,
    @SerializedName("baseline")
    val baseline: Baseline,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("profile_pic")
    val profilePic: String,
    @SerializedName("user_key")
    val userKey: Int,
    @SerializedName("user_type")
    val userType: Any,
    @SerializedName("username")
    val username: String
)