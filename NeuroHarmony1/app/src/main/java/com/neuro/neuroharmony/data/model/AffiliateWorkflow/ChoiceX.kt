package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class ChoiceX(
    @SerializedName("account_types")
    val accountTypes: List<AccountTypeX>,
    @SerializedName("affiliate_sources")
    val affiliateSources: List<AffiliateSource>,
    @SerializedName("affiliate_types")
    val affiliateTypes: List<AffiliateType>
)