package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class ErrorCodeAndDescriptionRequest(
    @SerializedName("data")
    val `data`: DataErrorCodeAndDescriptionRequest,
    @SerializedName("secured")
    val secured: Boolean
)