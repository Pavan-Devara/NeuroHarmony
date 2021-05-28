package com.neuro.neuroharmony.data.model.Feedback


import com.google.gson.annotations.SerializedName

data class FeaturesResponse(
    @SerializedName("data")
    val `data`: List<DataFeaturesResponse>,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("secured")
    val secured: Boolean,
    @SerializedName("status")
    val status: Int,
    @SerializedName("token")
    val token: String
)