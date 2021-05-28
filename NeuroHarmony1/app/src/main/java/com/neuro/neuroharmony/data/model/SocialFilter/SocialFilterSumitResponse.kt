package com.neuro.neuroharmony.data.model.SocialFilter

data class SocialFilterSumitResponse(
    val `data`: DataSocialFilterSubmitResponse,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)