package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class DataAffiliateOrgFormResponse(
    @SerializedName("affiliate_source_des")
    val affiliateSourceDes: String,
    @SerializedName("affiliate_source_key")
    val affiliateSourceKey: String,
    @SerializedName("affiliate_type")
    val affiliateType: Int,
    @SerializedName("affiliate_type_name")
    val affiliateTypeName: String,
    @SerializedName("bank_details")
    val bankDetails: BankDetailsAffiliateOrgFormResponse,
    @SerializedName("city")
    val city: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("nh_agent_name")
    val nhAgentName: String,
    @SerializedName("nh_agent_number")
    val nhAgentNumber: String,
    @SerializedName("org_details")
    val orgDetails: OrgDetailsAffiliateOrgFormResponse,
    @SerializedName("pan_number")
    val panNumber: Any,
    @SerializedName("pin")
    val pin: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("country_data")
    val countryData: CountryData,
    @SerializedName("state_data")
    val stateData: StateData,
    @SerializedName("city_data")
    val cityData: CityData
)