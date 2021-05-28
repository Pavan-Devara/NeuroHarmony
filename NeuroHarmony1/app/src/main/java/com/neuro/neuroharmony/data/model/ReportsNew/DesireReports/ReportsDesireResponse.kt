package com.neuro.neuroharmony.data.model.ReportsNew.DesireReports

data class ReportsDesireResponse(
    val `data`: Data,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)