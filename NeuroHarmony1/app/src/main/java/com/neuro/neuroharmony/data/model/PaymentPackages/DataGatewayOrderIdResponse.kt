package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class DataGatewayOrderIdResponse(
    @SerializedName("gateway_order_id")
    val gatewayOrderId: String,
    @SerializedName("purchase_id")
    val purchaseId: String
)