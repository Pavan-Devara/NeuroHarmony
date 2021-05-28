package com.neuro.neuroharmony.data.model.NewMatchedUsersData


import com.google.gson.annotations.SerializedName
import com.neuro.neuroharmony.data.model.NewMatchedUsersData.DataGetMatchedUsersResponse

data class GetMatchedUsersResponse(
    @SerializedName("data")
    val `data`: List<DataGetMatchedUsersResponse>,
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