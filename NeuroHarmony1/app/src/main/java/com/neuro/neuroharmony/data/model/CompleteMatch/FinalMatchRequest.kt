package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class FinalMatchRequest(
    @SerializedName("data")
    val `data`: DataFinalMatchRequest,
    @SerializedName("secured")
    val secured: Boolean
)