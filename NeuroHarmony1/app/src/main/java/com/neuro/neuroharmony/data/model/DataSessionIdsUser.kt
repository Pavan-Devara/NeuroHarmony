package com.neuro.neuroharmony.data.model

import java.io.Serializable

data class DataSessionIdsUser(
    val session_id: String,
    val name: String,
    val created_date: Any
): Serializable