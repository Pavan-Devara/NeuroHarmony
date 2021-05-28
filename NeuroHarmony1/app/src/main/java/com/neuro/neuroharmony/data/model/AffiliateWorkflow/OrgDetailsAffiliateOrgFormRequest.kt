package com.neuro.neuroharmony.data.model.AffiliateWorkflow


import com.google.gson.annotations.SerializedName

data class OrgDetailsAffiliateOrgFormRequest(
    @SerializedName("gst")
    val gst: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("tan_number")
    val tanNumber: String,
    @SerializedName("country_data")
    val countryDataOrg: CountryData,
    @SerializedName("state_data")
    val stateDataOrg: StateData,
    @SerializedName("city_data")
    val cityDataOrg: CityData
)