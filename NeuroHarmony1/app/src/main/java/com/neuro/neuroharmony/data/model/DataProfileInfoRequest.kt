package com.neuro.neuroharmony.data.model


import com.google.gson.annotations.SerializedName
import com.neuro.neuroharmony.data.model.Cities.DataCitiesApiResponse
import com.neuro.neuroharmony.data.model.CountriesApi.DataCountriesApiResponse
import com.neuro.neuroharmony.data.model.States.DataStatesApiResponse

data class DataProfileInfoRequest(
    @SerializedName("alternative_mobile_number")
    val alternativeMobileNumber: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("height")
    val height: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("mother_tongue")
    val motherTongue: String,
    @SerializedName("nationality")
    val nationality: Int,
    @SerializedName("native_place")
    val nativePlace: String,
    @SerializedName("present_residence")
    val presentResidence: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("fathers_name")
    val fathername: String,
    @SerializedName("alt_country_code")
    val country_code_picker_alt_num: Int,
    val native_country: DataCountriesApiResponse,
    val native_state: DataStatesApiResponse,
    val native_city: DataCitiesApiResponse,
    val present_country: DataCountriesApiResponse,
    val present_state: DataStatesApiResponse,
    val present_city: DataCitiesApiResponse
)