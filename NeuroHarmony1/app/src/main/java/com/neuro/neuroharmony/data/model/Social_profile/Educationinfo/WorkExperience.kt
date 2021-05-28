package com.neuro.neuroharmony.data.model.Social_profile.Educationinfo


import com.google.gson.annotations.SerializedName


data class WorkExperience(
    @SerializedName("choice")
    val choice: String,
    @SerializedName("id")
    val id: String
)