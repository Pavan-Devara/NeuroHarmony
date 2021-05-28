package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class TermsAndPrivacyResponse(
    @SerializedName("data")
    val `data`: DataTermsAndPrivacyResponse,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("secured")
    val secured: Boolean,
    @SerializedName("status")
    val status: Int
)