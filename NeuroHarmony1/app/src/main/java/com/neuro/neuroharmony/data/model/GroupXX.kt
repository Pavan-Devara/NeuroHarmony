package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class GroupXX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("questions")
    val questions: List<Any>
)