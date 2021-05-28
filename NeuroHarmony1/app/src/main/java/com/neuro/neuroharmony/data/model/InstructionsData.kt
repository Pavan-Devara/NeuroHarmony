package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class InstructionsData(
    @SerializedName("instruction")
    val instruction: String,
    @SerializedName("instruction_number")
    val instructionNumber: Int
)