package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class Configured(
    @SerializedName("Group_1")
    val group1: Int,
    @SerializedName("Group_2")
    val group2: Int,
    @SerializedName("Group_3")
    val group3: Int,
    @SerializedName("Group_4")
    val group4: Int,
    @SerializedName("Group_5")
    val group5: Int,
    @SerializedName("Group_6")
    val group6: Int,
    @SerializedName("Group_7")
    val group7: Int,
    @SerializedName("Group_8")
    val group8: Int,
    @SerializedName("Group_9")
    val group9: Int,
    @SerializedName("Group_10")
    val group10: Int
)