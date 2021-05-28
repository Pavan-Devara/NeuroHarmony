package com.neuro.neuroharmony.data.model

import java.io.Serializable

data class AllSessionOfUserResponse(
    val `data`: List<DataSessionIdsUser>,
    val isSuccess: Boolean,
    val message: String,
    val secured: Boolean,
    val status: Int,
    val token: String
) : Serializable