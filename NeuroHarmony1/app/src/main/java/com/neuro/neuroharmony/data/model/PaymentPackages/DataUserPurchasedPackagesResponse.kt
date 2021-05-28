package com.neuro.neuroharmony.data.model.PaymentPackages

import java.io.Serializable

data class DataUserPurchasedPackagesResponse(
    val available_neuro_tokens: Int,
    val purchase_list: List<Purchase>,
    val transaction_data: List<TransactionData>
): Serializable