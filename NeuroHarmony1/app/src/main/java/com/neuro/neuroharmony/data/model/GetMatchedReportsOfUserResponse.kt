package com.neuro.neuroharmony.data.model

data class GetMatchedReportsOfUserResponse(
    val `data`: DataGetMatchedReportsOfUser,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
)