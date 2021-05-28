package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataCoreBeliefFinalTwoQuestionsResponse(
    @SerializedName("groups")
    val groups: List<GroupCoreBeliefFinalTwoQuestionsResponse>,
    @SerializedName("re-test")
    val reTest: Boolean,
    @SerializedName("session_id")
    val sessionId: String
)