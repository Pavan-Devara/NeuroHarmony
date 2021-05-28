package com.neuro.neuroharmony.data.model.CompleteMatch


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("account_status")
    val accountStatus: Int,
    @SerializedName("age")
    val age: String,
    @SerializedName("all_test_taken")
    val allTestTaken: Boolean,
    @SerializedName("caste_name")
    val casteName: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("gender_choice")
    val gender: Any,
    @SerializedName("is_user_data_updated")
    val isUserDataUpdated: Boolean,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("present_city")
    val presentResidence: String,
    @SerializedName("profession")
    val profession: String,
    @SerializedName("profile_pic")
    val profilePic: String,
    @SerializedName("religion_name")
    val religionName: String,
    @SerializedName("marital_status_name")
    val status: Any,
    @SerializedName("user_key")
    val userKey: Int
)