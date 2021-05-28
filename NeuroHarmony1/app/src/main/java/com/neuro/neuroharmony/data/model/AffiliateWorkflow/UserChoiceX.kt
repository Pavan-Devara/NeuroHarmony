package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName
import com.hbb20.CountryCodePicker

data class UserChoiceX(
    @SerializedName("affiliate_source_des")
    val affiliateSourceDes: String,
    @SerializedName("affiliate_source_key")
    val affiliateSourceKey: String,
    @SerializedName("affiliate_type")
    val affiliateType: Int,
    @SerializedName("affiliate_type_name")
    val affiliateTypeName: String,
    @SerializedName("bank_details")
    val bankDetails: BankDetailsXX,
    @SerializedName("city")
    val city: String,
    @SerializedName("profile_pic")
    val ProfilePic: String,
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
    val orgDetails: OrgDetails,
    @SerializedName("country_data")
    val countryData: CountryData,
    @SerializedName("state_data")
    val stateData: StateData,
    @SerializedName("city_data")
    val cityData: CityData,
    @SerializedName("pan_number")
    val panNumber: Any,
    @SerializedName("pin")
    val pin: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("gateway_contact_id")
    val gateway_contact_id: Any,
    @SerializedName("gateway_fund_account_id")
    val gateway_fund_account_id: Any
)