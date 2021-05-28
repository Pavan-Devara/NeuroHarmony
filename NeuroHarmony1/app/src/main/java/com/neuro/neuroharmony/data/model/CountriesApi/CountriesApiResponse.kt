package com.neuro.neuroharmony.data.model.CountriesApi

data class CountriesApiResponse(
    val `data`: List<DataCountriesApiResponse>,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)