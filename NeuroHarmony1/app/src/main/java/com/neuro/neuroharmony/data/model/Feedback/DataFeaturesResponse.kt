package com.neuro.neuroharmony.data.model.Feedback


import com.google.gson.annotations.SerializedName

data class DataFeaturesResponse(
    @SerializedName("feature_id")
    val featureId: String,
    @SerializedName("title")
    val title: String
)