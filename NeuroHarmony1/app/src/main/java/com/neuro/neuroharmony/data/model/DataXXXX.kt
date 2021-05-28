package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataXXXX(
    @SerializedName("items")
    val items: List<ItemX>,
    @SerializedName("user_score")
    val userScore: UserScoreX
)