package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class DataGatewayPackageIdRequest(
    @SerializedName("package_id")
    val packageId: String
)