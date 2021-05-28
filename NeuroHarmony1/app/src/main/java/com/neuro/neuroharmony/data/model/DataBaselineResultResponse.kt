package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataBaselineResultResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("desire_score")
    val desireScore: DesireScoreXX,
    @SerializedName("neuro_report")
    val neuroReport: String,
    @SerializedName("neuro_score")
    val neuroScore: NeuroScoreXX,
    @SerializedName("test_type")
    val testType: Int,
    @SerializedName("user_distance_report")
    val userDistanceReport: String
)