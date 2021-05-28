package com.neuro.neuroharmony.data.model.Feedback


import com.google.gson.annotations.SerializedName

data class DataSubmitFeedbackRequest(

    @SerializedName("feature_id")
    val featureId: String,
    @SerializedName("comment")
    val comment: String
)