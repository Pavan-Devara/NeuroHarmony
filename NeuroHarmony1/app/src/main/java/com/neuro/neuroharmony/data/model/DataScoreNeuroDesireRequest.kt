package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataScoreNeuroDesireRequest(
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("test_type")
    val testType: Int
)