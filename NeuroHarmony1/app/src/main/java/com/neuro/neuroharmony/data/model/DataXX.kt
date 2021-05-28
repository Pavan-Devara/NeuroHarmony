package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataXX(
    @SerializedName("instructions_data")
    val instructionsData: List<InstructionsData>,
    @SerializedName("pending_test")
    val pendingTest: Any,
    @SerializedName("last_qs_time")
    val lastQsTime: Any
)