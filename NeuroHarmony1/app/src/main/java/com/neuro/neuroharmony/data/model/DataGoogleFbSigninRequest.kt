package com.neuro.neuroharmony.data.model

data class DataGoogleFbSigninRequest(
    val auth_type: Int,
    val client_id: String,
    val email: String
)