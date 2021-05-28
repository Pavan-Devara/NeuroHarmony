package com.neuro.neuroharmony.data.model.PaymentPackages

import java.io.Serializable

data class TransactionData(
    val created_date: String,
    val description: String,
    val neuro_tokens: Int,
    val transaction_type: String
): Serializable