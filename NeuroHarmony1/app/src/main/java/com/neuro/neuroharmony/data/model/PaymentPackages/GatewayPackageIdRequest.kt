package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class GatewayPackageIdRequest(
    @SerializedName("data")
    val `data`: DataGatewayPackageIdRequest
)