package com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports

import com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports.NeuroDataKeys

data class NeuroReportResponse(
    val `data`: NeuroDataKeys,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)