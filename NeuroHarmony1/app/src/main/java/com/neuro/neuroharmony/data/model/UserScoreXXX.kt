package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class UserScoreXXX(
    @SerializedName("group_type")
    val groupType: String,
    @SerializedName("order_number")
    val orderNumber: String
)