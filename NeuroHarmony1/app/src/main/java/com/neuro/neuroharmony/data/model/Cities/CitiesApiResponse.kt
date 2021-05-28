package com.neuro.neuroharmony.data.model.Cities

data class CitiesApiResponse(
    val `data`: List<DataCitiesApiResponse>,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)