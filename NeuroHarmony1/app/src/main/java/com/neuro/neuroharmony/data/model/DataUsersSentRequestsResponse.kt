package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName

data class DataUsersSentRequestsResponse(
    @SerializedName("age")
    val age: String,
    @SerializedName("caste_name")
    val casteName: Any,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("present_city")
    val presentResidence: String,
    @SerializedName("profile_pic")
    val profilePic: String,
    @SerializedName("religion_name")
    val religionName: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("user_key")
    val userKey: Int,
    @SerializedName("request_status")
    val requestStatus: Int,
    @SerializedName("profession")
    val profession: String,
    @SerializedName("marital_status_name")
    val maritialStatus: String,
    @SerializedName("gender_choice")
    val gender_choice: String

)