package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataInitiateMatchResponse(
    @SerializedName("match_status")
    val matchStatus: String
)