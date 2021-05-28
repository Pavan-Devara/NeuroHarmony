package com.neuro.neuroharmony.data.model.GenericData

data class GenericDataResponse(
    val `data`: DataGenericDataResponse,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)