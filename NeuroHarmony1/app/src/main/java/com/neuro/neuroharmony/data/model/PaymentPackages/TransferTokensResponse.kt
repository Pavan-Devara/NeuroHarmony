package com.neuro.neuroharmony.data.model.PaymentPackages

data class TransferTokensResponse(
    val `data`: DataTransferTokensResponse,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)