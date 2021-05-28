package com.neuro.neuroharmony.data.model.SocialFilter

data class SocialFilterResponse(
    val `data`: DataSocialFilterResponse,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)