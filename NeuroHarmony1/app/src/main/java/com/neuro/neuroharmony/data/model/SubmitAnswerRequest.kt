package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class SubmitAnswerRequest(
    @SerializedName("data")
    val `data`: DataSubmitAnswerRequest,
    @SerializedName("secured")
    val secured: Boolean
)