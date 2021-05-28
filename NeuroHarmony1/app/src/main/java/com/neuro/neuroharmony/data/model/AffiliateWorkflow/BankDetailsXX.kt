package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class BankDetailsXX(
    @SerializedName("bank_account_number")
    val bankAccountNumber: String,
    @SerializedName("bank_account_type")
    val bankAccountType: Int,
    @SerializedName("bank_account_type_name")
    val bankAccountTypeName: String,
    @SerializedName("bank_ifsc")
    val bankIfsc: String,
    @SerializedName("bank_name")
    val bankName: String
)