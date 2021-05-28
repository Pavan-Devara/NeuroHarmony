package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class DataCheckoutIdsRequest(
    @SerializedName("gateway_order_id")
    val gatewayOrderId: String,
    @SerializedName("gateway_payment_id")
    val gatewayPaymentId: String,
    @SerializedName("gateway_signature")
    val gatewaySignature: String
)