package com.neuro.neuroharmony.data.model.BlockingList


import com.google.gson.annotations.SerializedName


data class DataSubmitRequest(
    @SerializedName("blocked_user")
    val blockedUser: Int
)