package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class DataSubmitSelectedPackageResponse(
    @SerializedName("package_neuro_tokens")
    val packageNeuroTokens: Int,
    @SerializedName("user_neuro_tokens")
    val userNeuroTokens: Int
)