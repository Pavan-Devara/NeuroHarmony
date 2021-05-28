package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class SubItemXXX(
    @SerializedName("central_position")
    val centralPosition: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order_number")
    val orderNumber: Int,
    @SerializedName("type")
    val type: Int
)