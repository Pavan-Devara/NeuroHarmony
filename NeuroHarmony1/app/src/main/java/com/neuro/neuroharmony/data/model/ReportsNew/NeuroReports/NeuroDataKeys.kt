package com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports

data class NeuroDataKeys(
    val description: NeuroDescription,
    val overview_footer: String,
    val overview_header: String,
    val combined_overview: String,
    val rating: Int,
    val report_name: String,
    val type_id: Int
)