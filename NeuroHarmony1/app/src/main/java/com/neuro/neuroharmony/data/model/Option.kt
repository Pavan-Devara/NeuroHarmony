package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class Option(
    @SerializedName("option")
    val option: String,
    @SerializedName("option_label")
    val optionLabel: String,
    @SerializedName("order_number")
    val orderNumber: Int
)