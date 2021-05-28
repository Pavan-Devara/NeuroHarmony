package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class RevokeCompleteMatchRequest(
    @SerializedName("data")
    val `data`: DataRevokeCompleteMatchRequest,
    @SerializedName("secured")
    val secured: Boolean
)