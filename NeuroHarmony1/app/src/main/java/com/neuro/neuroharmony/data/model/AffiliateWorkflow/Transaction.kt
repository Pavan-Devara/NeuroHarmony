package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("payment_amount")
    val paymentAmount: String,
    @SerializedName("payment_status")
    val paymentStatus: Int,
    @SerializedName("payment_credited_date")
    val creditedDate: Any,
    @SerializedName("payment_status_name")
    val paymentStatusName: String,
    @SerializedName("referral_name")
    val referralName: String,
    @SerializedName("gateway_payout")
    val gateway_payout: DataGatewayPayout
)