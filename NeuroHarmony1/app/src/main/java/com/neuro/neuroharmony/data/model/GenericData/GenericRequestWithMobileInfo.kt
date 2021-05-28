package com.neuro.neuroharmony.data.model.GenericData


import com.google.gson.annotations.SerializedName


data class GenericRequestWithMobileInfo(
    @SerializedName("data")
    val `data`: Data
)