package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("groups")
    val groups: List<GroupXX>,
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("group_id")
    val groupId: Int,
    @SerializedName("question_id")
    val question_id: Int,
    @SerializedName("question_index_id")
    val questionIndex: Int
)