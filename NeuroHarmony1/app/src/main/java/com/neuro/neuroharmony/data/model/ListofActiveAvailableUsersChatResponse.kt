package com.neuro.neuroharmony.data.model

data class ListofActiveAvailableUsersChatResponse(
    val `data`: List<DataActiveAvailableUsersChatResponse>,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)