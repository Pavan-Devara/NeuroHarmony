package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class AffiliateType(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)