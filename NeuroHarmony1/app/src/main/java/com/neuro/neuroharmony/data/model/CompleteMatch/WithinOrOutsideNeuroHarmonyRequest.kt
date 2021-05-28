package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class WithinOrOutsideNeuroHarmonyRequest(
    @SerializedName("data")
    val `data`: DataWithinOrOutsideNeuroHarmonyRequest,
    @SerializedName("secured")
    val secured: Boolean
)