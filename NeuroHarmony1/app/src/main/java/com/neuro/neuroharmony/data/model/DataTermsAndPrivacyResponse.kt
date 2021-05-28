package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataTermsAndPrivacyResponse(
    @SerializedName("privacy_policy")
    val privacyPolicy: PrivacyPolicy,
    @SerializedName("terms_conditions")
    val termsConditions: TermsConditions
)