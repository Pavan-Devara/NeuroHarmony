package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class GroupSize(
    @SerializedName("count")
    val count: Int,
    @SerializedName("group")
    val group: String,
    @SerializedName("active_users_count")
    val activeUsers: Int,
    @SerializedName("social_filter_count")
    val FilteredUsers: Int
)

