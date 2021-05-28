package com.neuro.neuroharmony.data.model.Social_profile.PersonalInfo


import com.google.gson.annotations.SerializedName


data class Choice(
    @SerializedName("account_status")
    val accountStatus: List<AccountStatu>,
    @SerializedName("activity_status")
    val activityStatus: List<ActivityStatu>,
    @SerializedName("alt_country_code")
    val altCountryCode: Any,
    @SerializedName("alternative_mobile_number")
    val alternativeMobileNumber: Any,
    @SerializedName("country_code")
    val countryCode: Any,
    @SerializedName("created_date")
    val createdDate: Any,
    @SerializedName("dob")
    val dob: Any,
    @SerializedName("email")
    val email: Any,
    @SerializedName("fathers_name")
    val fathersName: Any,
    @SerializedName("first_name")
    val firstName: Any,
    @SerializedName("gender")
    val gender: List<Gender>,
    @SerializedName("height")
    val height: Any,
    @SerializedName("id")
    val id: Any,
    @SerializedName("is_invited")
    val isInvited: Any,
    @SerializedName("last_name")
    val lastName: Any,
    @SerializedName("middle_name")
    val middleName: Any,
    @SerializedName("mobile_number")
    val mobileNumber: Any,
    @SerializedName("modified_date")
    val modifiedDate: Any,
    @SerializedName("mother_tongue")
    val motherTongue: Any,
    @SerializedName("nationality")
    val nationality: List<Statu>,
    @SerializedName("native_place")
    val nativePlace: Any,
    @SerializedName("present_residence")
    val presentResidence: Any,
    @SerializedName("profile_pic")
    val profilePic: Any,
    @SerializedName("status")
    val status: List<Statu>,
    @SerializedName("user_id")
    val userId: Any,
    @SerializedName("user_key")
    val userKey: Any,
    @SerializedName("user_type")
    val userType: List<UserType>,
    @SerializedName("username")
    val username: Any,
    @SerializedName("weight")
    val weight: Any
)