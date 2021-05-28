package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class DataErrorCodeAndDescriptionRequest(
    @SerializedName("err_code")
    val errCode: Any,
    @SerializedName("err_res")
    val errRes: String,
    @SerializedName("gateway_order_id")
    val gatewayOrderId: String
)