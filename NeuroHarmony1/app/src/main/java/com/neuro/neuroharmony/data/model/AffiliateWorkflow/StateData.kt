package com.neuro.neuroharmony.data.model.AffiliateWorkflow

import com.google.gson.annotations.SerializedName

data class StateData (
    @SerializedName("id")
    val id: Int,
    @SerializedName("state")
    val name: String
)