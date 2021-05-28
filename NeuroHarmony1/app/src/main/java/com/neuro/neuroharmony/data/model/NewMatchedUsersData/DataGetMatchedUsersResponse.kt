package com.neuro.neuroharmony.data.model.NewMatchedUsersData


import com.google.gson.annotations.SerializedName

data class DataGetMatchedUsersResponse(
    @SerializedName("groupName")
    val groupName: String,
    @SerializedName("items")
    val items: List<ItemMatchedUsers>,
    val filtered: Int
)