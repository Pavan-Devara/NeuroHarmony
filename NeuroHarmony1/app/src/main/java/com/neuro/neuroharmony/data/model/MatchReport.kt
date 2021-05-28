package com.neuro.neuroharmony.data.model

data class MatchReport(
    val matched_user_desire: MatchedUserDesire,
    val matched_user_neuro: MatchedUserNeuro,
    val user_desire_score: UserDesireScore,
    val user_neuro_score: UserNeuroScore
)