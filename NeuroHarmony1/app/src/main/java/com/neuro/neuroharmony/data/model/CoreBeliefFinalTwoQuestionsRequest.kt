package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class CoreBeliefFinalTwoQuestionsRequest(
    @SerializedName("data")
    val `data`: DataCoreBeliefFinalTwoQuestionsRequest,
    @SerializedName("secured")
    val secured: Boolean
)