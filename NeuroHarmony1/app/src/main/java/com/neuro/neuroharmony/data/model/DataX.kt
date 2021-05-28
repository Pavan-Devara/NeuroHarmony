package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("desire_score")
    val desireScore: DesireScore,
    @SerializedName("neuro_score")
    val neuroScore: NeuroScore
)