package com.neuro.neuroharmony.data.model.AffiliateWorkflow

import com.google.gson.annotations.SerializedName

data class CountryData (
    @SerializedName("id")
    val id: Int,
    @SerializedName("country")
    val name: String
)