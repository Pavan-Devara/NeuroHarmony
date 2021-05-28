package com.neuro.neuroharmony.data.model.manualMatching


import com.google.gson.annotations.SerializedName

data class DataReceivedSentConfirmedManualMatchingResponse(
    @SerializedName("age")
    val age: String,
    @SerializedName("caste_name")
    val casteName: Any,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("mobile_number")
    val mobileNumber: String,
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
    @SerializedName("is_user_data_updated")
    val is_user_data_updated: Boolean,
    @SerializedName("profession")
    val profession: String,
    @SerializedName("marital_status_name")
    val maritalStatus: String,
    @SerializedName("gender_choice")
    val gender_choice: String
)