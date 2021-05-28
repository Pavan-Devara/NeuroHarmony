package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataLoginRequest(
    @SerializedName("auth_type")
    val authType: Int,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("country_code")
    val country_code_picker_login: Int
)