package com.neuro.neuroharmony.data.model

data class ReligiousInfoSubmitResponse(
    val `data`: DataReligiousInfoSubmitResponse,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)