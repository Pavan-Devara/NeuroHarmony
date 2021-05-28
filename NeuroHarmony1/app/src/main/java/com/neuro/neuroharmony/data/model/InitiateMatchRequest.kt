package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class InitiateMatchRequest(
    @SerializedName("data")
    val `data`: DataInitiateMatchRequest
)