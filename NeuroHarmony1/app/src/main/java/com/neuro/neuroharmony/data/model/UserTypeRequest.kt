package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class UserTypeRequest(
    @SerializedName("data")
    val `data`: DataUserTypeRequest,
    @SerializedName("secured")
    val secured: Boolean
)