package com.neuro.neuroharmony.data.model.PaymentPackages

import java.io.Serializable

data class UserPurchasedPackagesResponse(
    val `data`: DataUserPurchasedPackagesResponse,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
): Serializable