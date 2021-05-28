package com.neuro.neuroharmony.data.model.Social_profile.PersonalInfo


import com.google.gson.annotations.SerializedName


data class Statu(
    @SerializedName("choice")
    val choice: String,
    @SerializedName("id")
    val id: Int
)