package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class RevokeInterestRequest(
    @SerializedName("data")
    val `data`: DataRevokeInterestRequest
)