package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataCoreBeliefScoreResponse(
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("user_score")
    val userScore: UserScore
)