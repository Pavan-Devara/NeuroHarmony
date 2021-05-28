package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class GroupCoreBeliefFinalTwoQuestionsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("questions")
    val questions: List<QuestionCoreBeliefFinalTwoQuestionsResponse>
)