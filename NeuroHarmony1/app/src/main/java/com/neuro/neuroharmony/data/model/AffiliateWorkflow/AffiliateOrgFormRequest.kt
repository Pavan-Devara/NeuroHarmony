package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class AffiliateOrgFormRequest(
    @SerializedName("data")
    val `data`: DataAffiliateOrgFormRequest,
    @SerializedName("secured")
    val secured: Boolean
)