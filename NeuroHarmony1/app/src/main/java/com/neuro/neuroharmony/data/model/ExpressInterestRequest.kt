package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class ExpressInterestRequest(
    @SerializedName("data")
    val `data`: DataExpressInterestRequest
)