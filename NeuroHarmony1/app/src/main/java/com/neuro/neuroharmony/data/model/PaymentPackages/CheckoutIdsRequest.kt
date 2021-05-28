package com.neuro.neuroharmony.data.model.PaymentPackages


import com.google.gson.annotations.SerializedName

data class CheckoutIdsRequest(
    @SerializedName("data")
    val `data`: DataCheckoutIdsRequest,
    @SerializedName("secured")
    val secured: Boolean
)