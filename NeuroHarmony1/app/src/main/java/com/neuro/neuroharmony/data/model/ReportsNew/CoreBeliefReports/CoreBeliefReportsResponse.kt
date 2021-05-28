package com.neuro.neuroharmony.data.model.ReportsNew.CoreBeliefReports

data class CoreBeliefReportsResponse(
    val `data`: DataCoreBelief,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)