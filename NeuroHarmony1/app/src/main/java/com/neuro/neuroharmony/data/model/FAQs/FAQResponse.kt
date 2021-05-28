package com.neuro.neuroharmony.data.model.FAQs

data class FAQResponse(
    val `data`: List<Any>,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)