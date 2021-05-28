package com.neuro.neuroharmony.data.model.ReportsNew.DesireReports

data class Data(
    val combined_overview: String,
    val description: Description,
    val overview_footer: String,
    val overview_header: String,
    val rating: Int,
    val report_name: String,
    val type_id: Int
)