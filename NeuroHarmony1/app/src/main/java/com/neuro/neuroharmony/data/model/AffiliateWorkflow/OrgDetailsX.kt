package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class OrgDetailsX(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("gst")
    val gst: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("tan_number")
    val tanNumber: String
)