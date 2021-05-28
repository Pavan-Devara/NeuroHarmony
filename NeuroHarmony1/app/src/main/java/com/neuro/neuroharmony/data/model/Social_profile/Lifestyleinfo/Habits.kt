package com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo


import com.google.gson.annotations.SerializedName

data class Habits(
    @SerializedName("choice")
    val choice: String,
    @SerializedName("id")
    val id: String
)