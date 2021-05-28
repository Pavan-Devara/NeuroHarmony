package com.neuro.neuroharmony.data.model

data class ReligiousInfoResponse(
    val `data`: DataReligiousInfo,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)