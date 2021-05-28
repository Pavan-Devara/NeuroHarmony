package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataAddPartnerResponse(
    @SerializedName("is_invited")
    val isInvited: Boolean,
    @SerializedName("user_key")
    val userKey: Int
)