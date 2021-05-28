package com.neuro.neuroharmony.data.model.ReportsNew.CoreBeliefReports

data class Description(
    val attention_energy: List<AttentionEnergy>,
    val attention_focus: List<AttentionFocu>,
    val communication_style: List<CommunicationStyle>,
    val `do`: List<Do>,
    val dont: List<Dont>,
    val score_description: List<ScoreDescription>
)