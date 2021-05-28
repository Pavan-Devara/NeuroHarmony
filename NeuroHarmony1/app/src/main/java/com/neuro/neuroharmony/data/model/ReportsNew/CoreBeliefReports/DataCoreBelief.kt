package com.neuro.neuroharmony.data.model.ReportsNew.CoreBeliefReports

data class DataCoreBelief(
    val combined_overview: String,
    val description: Description,
    val icon: String,
    val name: String,
    val overview_footer: String,
    val overview_header: String,
    val rating: Int,
    val report_name: String,
    val type: Int,
    val type_id: Int
)