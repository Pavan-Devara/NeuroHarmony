package com.neuro.neuroharmony.data.model.ReportsNew.MatchReports

data class DataMatchedReports(
    val description: Description,
    val name: String,
    val overview_footer: String,
    val overview_header: String,
    val rating: Int,
    val type_id: Int
)