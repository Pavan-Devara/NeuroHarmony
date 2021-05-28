package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("choice")
    val choice: ChoiceX,
    @SerializedName("user_choice")
    val userChoice: UserChoiceX
)