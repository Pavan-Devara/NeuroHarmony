package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataGetMatchewdUsersRequest(
    @SerializedName("configured_list")
    val configuredList: List<Configured>,
    @SerializedName("user_key")
    val userKey: Int
)