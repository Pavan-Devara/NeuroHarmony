package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class DataGetAffiliateRefferalTransactions(
    @SerializedName("credited_amount")
    val creditedAmount: String,
    @SerializedName("due_amount")
    val dueAmount: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("org_details")
    val orgDetails: OrgDetailsX,
    @SerializedName("profile_pic")
    val profilePic: String,
    @SerializedName("transaction_list")
    val transactionList: List<Transaction>
)