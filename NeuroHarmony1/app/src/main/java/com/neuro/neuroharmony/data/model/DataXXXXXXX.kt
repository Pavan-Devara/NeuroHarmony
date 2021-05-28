package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataXXXXXXX(
    @SerializedName("items")
    val items: List<ItemXXX>,
    @SerializedName("user_score")
    val userScore: UserScoreXXX
)