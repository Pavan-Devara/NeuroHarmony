package com.neuro.neuroharmony.data.model.Social_profile.Familyinfo


import com.google.gson.annotations.SerializedName


data class FamilyInfoResponse(
    @SerializedName("data")
    val `data`: Data,
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