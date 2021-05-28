package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class BankDetailsAffiliateOrgFormRequest(
    @SerializedName("bank_account_number")
    val bankAccountNumber: String,
    @SerializedName("bank_account_type")
    val bankAccountType: Int,
    @SerializedName("bank_ifsc")
    val bankIfsc: String,
    @SerializedName("bank_name")
    val bankName: String
)