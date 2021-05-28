package com.neuro.neuroharmony.data.model.AffiliateWorkflow

import com.google.gson.annotations.SerializedName

data class DataGatewayPayout (
    @SerializedName("payout_status")
    val payout_status: String,
    @SerializedName("payout_status_name")
    val payout_status_name: String,
    @SerializedName("payout_date")
    val payout_date: Any
)
