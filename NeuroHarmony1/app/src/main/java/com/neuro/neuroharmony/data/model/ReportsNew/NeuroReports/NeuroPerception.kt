package com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports

data class NeuroPerception(
    val footer: String,
    val key_data: List<NeuroKeyDataPerception>,
    val score_data: List<NeuroScoreDataPerception>
)