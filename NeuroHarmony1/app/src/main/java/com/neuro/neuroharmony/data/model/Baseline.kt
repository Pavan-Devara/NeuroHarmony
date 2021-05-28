package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class Baseline(
    @SerializedName("core_belief")
    val coreBelief: Int,
    @SerializedName("desire")
    val desire: Int,
    @SerializedName("neuro")
    val neuro: Int
)