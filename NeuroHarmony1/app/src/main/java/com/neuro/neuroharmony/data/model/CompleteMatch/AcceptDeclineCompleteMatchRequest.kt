package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class AcceptDeclineCompleteMatchRequest(
    @SerializedName("data")
    val `data`: DataAcceptDeclineCompleteMatchRequest,
    @SerializedName("secured")
    val secured: Boolean
)