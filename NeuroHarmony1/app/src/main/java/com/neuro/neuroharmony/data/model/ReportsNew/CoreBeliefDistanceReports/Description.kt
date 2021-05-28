package com.neuro.neuroharmony.data.model.ReportsNew.CoreBeliefDistanceReports

data class Description(
    val partner_data: List<PartnerData>,
    val partner_paragraph: String,
    val positive_aspects: List<PositiveAspect>,
    val potential_challenges: List<PotentialChallenge>,
    val user_data: List<UserData>,
    val user_paragraph: String
)