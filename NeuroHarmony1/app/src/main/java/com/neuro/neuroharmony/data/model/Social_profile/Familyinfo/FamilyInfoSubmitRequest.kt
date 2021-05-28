package com.neuro.neuroharmony.data.model.Social_profile.Familyinfo


import com.google.gson.annotations.SerializedName


data class FamilyInfoSubmitRequest(
    @SerializedName("data")
    val `data`: DataSubmitRequest
)