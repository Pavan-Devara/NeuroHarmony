package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class AffiliateSource(
    @SerializedName("description")
    val description: String,
    @SerializedName("key")
    val key: String
)