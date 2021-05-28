package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class DataFinalMatchResponse(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("partner_user_key")
    val partnerUserKey: Int,
    @SerializedName("sender_user_key")
    val senderUserKey: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("updated_date")
    val updatedDate: String
)