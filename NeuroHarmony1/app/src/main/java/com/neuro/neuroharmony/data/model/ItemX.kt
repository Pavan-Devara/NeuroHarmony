package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class ItemX(
    @SerializedName("group_type")
    val groupType: Int,
    @SerializedName("group_type_value")
    val groupTypeValue: String,
    @SerializedName("sub_items")
    val subItems: List<SubItemX>
)