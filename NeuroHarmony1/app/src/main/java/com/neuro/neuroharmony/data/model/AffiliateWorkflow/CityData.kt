package com.neuro.neuroharmony.data.model.AffiliateWorkflow

import com.google.gson.annotations.SerializedName

data class CityData (
    @SerializedName("id")
    val id: Int,
    @SerializedName("city")
    val name: String
)