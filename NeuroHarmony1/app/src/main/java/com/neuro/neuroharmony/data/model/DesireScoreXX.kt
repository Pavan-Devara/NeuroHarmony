package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DesireScoreXX(
    @SerializedName("c_score")
    val cScore: String,
    @SerializedName("i_score")
    val iScore: String,
    @SerializedName("p_score")
    val pScore: String,
    @SerializedName("test_type")
    val testType: String
)