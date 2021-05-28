package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataXXX(
    @SerializedName("desire_score")
    val desireScore: DesireScoreX,
    @SerializedName("neuro_score")
    val neuroScore: NeuroScoreX,
    @SerializedName("description")
    val Description: String,
    @SerializedName("user_distance_report")
    val user_distance_report: String,
    @SerializedName("neuro_report")
    val neuro_report: String




)