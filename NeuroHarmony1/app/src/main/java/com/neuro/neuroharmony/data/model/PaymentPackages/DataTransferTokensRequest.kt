package com.neuro.neuroharmony.data.model.PaymentPackages

data class DataTransferTokensRequest(
    val mobile_number: String,
    val neuro_tokens: Int
)