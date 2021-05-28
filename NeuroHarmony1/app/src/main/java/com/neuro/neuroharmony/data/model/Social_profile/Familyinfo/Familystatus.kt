package com.neuro.neuroharmony.data.model.Social_profile.Familyinfo


import com.google.gson.annotations.SerializedName


data class Familystatus(
    @SerializedName("choice")
    val choice: String,
    @SerializedName("id")
    val id: String
)