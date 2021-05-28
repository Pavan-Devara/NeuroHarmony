package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataSubmitAnswerRequest(
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("group_submit")
    val groupSubmit: Boolean,
    @SerializedName("qid")
    val qid: Int,
    @SerializedName("selected_option")
    val selectedOption: String,
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("user_key")
    val userKey: String,
    @SerializedName("test_type")
    val testType: Int,
    @SerializedName("group")
    val group: Int


)