package com.neuro.neuroharmony.data.model.Social_profile.PersonalInfo


import com.google.gson.annotations.SerializedName
import com.neuro.neuroharmony.data.model.Cities.DataCitiesApiResponse
import com.neuro.neuroharmony.data.model.CountriesApi.CountriesApiResponse
import com.neuro.neuroharmony.data.model.CountriesApi.DataCountriesApiResponse
import com.neuro.neuroharmony.data.model.States.DataStatesApiResponse


data class UserChoice(
    @SerializedName("account_status")
    val accountStatus: Int,
    @SerializedName("activity_status")
    val activityStatus: Int,
    @SerializedName("alt_country_code")
    val altCountryCode: Int,
    @SerializedName("alternative_mobile_number")
    val alternativeMobileNumber: String,
    @SerializedName("country_code")
    val countryCode: Int,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fathers_name")
    val fathersName: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("height")
    val height: Any,
    @SerializedName("id")
    val id: Any,
    @SerializedName("is_invited")
    val isInvited: Boolean,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("modified_date")
    val modifiedDate: String,
    @SerializedName("mother_tongue")
    val motherTongue: String,
    @SerializedName("nationality")
    val nationality: Int,
    @SerializedName("native_place")
    val nativePlace: String,
    @SerializedName("present_residence")
    val presentResidence: String,
    @SerializedName("profile_pic")
    val profilePic: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_key")
    val userKey: Int,
    @SerializedName("user_type")
    val userType: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("weight")
    val weight: Any,
    val native_country: DataCountriesApiResponse,
    val native_state: DataStatesApiResponse,
    val native_city: DataCitiesApiResponse,
    val present_country: DataCountriesApiResponse,
    val present_state: DataStatesApiResponse,
    val present_city: DataCitiesApiResponse
)