package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class AffiliateFormRequest(
    @SerializedName("data")
    val `data`: DataAffiliateFormRequest,
    @SerializedName("secured")
    val secured: Boolean
)