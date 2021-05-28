package com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo

data class LifestyleInfoSubmitResponse(
    val `data`: DataSubmitResponse,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)