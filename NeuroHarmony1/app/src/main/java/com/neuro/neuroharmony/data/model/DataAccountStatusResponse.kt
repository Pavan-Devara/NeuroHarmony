package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataAccountStatusResponse(
    @SerializedName("account_status")
    val accountStatus: Int
)