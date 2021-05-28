package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class DataSubmitSelectedPackageRequest(
    @SerializedName("package_id")
    val packageId: String
)