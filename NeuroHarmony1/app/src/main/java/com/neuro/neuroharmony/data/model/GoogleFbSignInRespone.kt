package com.neuro.neuroharmony.data.model

data class GoogleFbSignInRespone(
    val `data`: DataGoogleFbSignInResponse,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)