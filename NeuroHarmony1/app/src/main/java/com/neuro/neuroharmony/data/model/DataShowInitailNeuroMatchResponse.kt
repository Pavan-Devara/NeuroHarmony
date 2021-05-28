package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataShowInitailNeuroMatchResponse(
    @SerializedName("group_size")
    val groupSize: List<GroupSize>,
    @SerializedName("total_matched_user_count")
    val totalMatchedUserCount: Int,
    val social_filter_active: Boolean,
    val match_status: String
)