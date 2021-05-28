package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class DataAffiliateFormRequest(
    @SerializedName("affiliate_source_key")
    val affiliateSourceKey: String,
    @SerializedName("affiliate_type")
    val affiliateType: Int,
    @SerializedName("bank_details")
    val bankDetails: BankDetailsX,
    @SerializedName("city")
    val city: Any?,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("nh_agent_name")
    val nhAgentName: Any?,
    @SerializedName("nh_agent_number")
    val nhAgentNumber: Any?,
    @SerializedName("pan_number")
    val panNumber: String,
    @SerializedName("pin")
    val pin: String,
    @SerializedName("state")
    val state: Any?,
    @SerializedName("street")
    val street: String,
    @SerializedName("country_data")
    val countryData: CountryData,
    @SerializedName("state_data")
    val stateData: StateData,
    @SerializedName("city_data")
    val cityData: CityData
)