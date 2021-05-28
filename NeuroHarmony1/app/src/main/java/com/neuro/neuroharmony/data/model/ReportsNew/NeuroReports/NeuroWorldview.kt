package com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports

import com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports.NeuroKeyDataWorldview
import com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports.NeuroScoreDataWorldview

data class NeuroWorldview(
    val footer: String,
    val key_data: List<NeuroKeyDataWorldview>,
    val score_data: List<NeuroScoreDataWorldview>
)