package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class ProfilepicRequest(
    @SerializedName("data")
    val `data`: DataProfilepicRequest
)