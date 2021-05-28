package com.neuro.neuroharmony.data.model.Social_profile.Familyinfo


import com.google.gson.annotations.SerializedName


data class FamilyInfoSubmitResponse(
    @SerializedName("data")
    val `data`: DataSubmitResponse,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("secured")
    val secured: Boolean,
    @SerializedName("status")
    val status: Int,
    @SerializedName("token")
    val token: String
)