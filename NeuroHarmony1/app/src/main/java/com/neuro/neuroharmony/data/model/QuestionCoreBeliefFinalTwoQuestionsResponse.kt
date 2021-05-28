package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class QuestionCoreBeliefFinalTwoQuestionsResponse(
    @SerializedName("group")
    val group: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("options")
    val options: List<OptionCoreBeliefFinalTwoQuestionsResponse>,
    @SerializedName("question_a")
    val questionA: String,
    @SerializedName("question_b")
    val questionB: String,
    @SerializedName("test_type")
    val testType: Int
)