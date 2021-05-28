package com.neuro.neuroharmony.data.model.Feedback


import com.google.gson.annotations.SerializedName

data class SubmitFeedbackRequest(
    @SerializedName("data")
    val `data`: DataSubmitFeedbackRequest
)