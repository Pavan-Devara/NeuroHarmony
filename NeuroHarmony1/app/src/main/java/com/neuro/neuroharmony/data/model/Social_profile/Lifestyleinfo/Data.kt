package com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo


import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("choice")
    val choice: Choice,
    @SerializedName("user_choice")
    val userChoice: UserChoice
)