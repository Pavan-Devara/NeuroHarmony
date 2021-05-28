package com.neuro.neuroharmony.data.model.ReportsNew.MatchReports

data class ReportsMatchedUsers(
    val `data`: DataMatchedReports,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)