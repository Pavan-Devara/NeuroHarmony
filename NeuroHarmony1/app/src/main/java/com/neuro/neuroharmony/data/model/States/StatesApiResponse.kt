package com.neuro.neuroharmony.data.model.States

data class StatesApiResponse(
    val `data`: List<DataStatesApiResponse>,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)