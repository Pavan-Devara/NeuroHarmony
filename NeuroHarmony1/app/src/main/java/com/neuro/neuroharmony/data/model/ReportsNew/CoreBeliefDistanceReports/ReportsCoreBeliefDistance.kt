package com.neuro.neuroharmony.data.model.ReportsNew.CoreBeliefDistanceReports

data class ReportsCoreBeliefDistance(
    val `data`: DataCoreBeliefDistance,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)