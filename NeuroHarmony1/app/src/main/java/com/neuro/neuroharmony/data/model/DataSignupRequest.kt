package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataSignupRequest(
    @SerializedName("auth_type")
    val authType: Int,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("country_code")
    val country_code_picker_sign_up: Int

)