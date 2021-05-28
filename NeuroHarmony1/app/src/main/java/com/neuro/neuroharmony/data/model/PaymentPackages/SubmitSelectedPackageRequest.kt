package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class SubmitSelectedPackageRequest(
    @SerializedName("data")
    val `data`: DataSubmitSelectedPackageRequest
)