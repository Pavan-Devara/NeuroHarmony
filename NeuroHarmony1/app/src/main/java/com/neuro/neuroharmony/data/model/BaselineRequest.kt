package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class BaselineRequest(
    @SerializedName("data")
    val `data`: DataBaselineRequest
)