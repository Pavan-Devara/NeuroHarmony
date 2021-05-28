package com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports

data class NeuroFunctionalApproach(
    val footer: String,
    val key_data: List<NeuroKeyDataFunctional>,
    val score_data: List<NeuroScoreDataFunctional>
)