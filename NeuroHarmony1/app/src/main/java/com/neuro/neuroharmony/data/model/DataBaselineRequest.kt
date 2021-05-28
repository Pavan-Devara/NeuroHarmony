package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataBaselineRequest(
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("test_type")
    val testType: Int
)