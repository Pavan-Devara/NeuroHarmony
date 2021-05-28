package com.neuro.neuroharmony.data.model.manualMatching


import com.google.gson.annotations.SerializedName

data class AcceptDeclineRevokeDelinkManualMatchingRequest(
    @SerializedName("data")
    val `data`: Data
)