package com.neuro.neuroharmony.data.model.BlockingList


import com.google.gson.annotations.SerializedName


data class BlockingSubmitRequest(
    @SerializedName("data")
    val `data`: DataSubmitRequest
)