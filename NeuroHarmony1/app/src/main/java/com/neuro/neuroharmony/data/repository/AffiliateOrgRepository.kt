package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.AffiliateWorkflow.*
import com.neuro.neuroharmony.data.remote.AffiliateOrgApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AffiliateOrgRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun affiliateformorg(
        affiliate_source_key: String,
        affiliate_type:Int,
        bank_account_number:String,
        account_type:Int,
        ifsc:String,
        bank_name:String,
        city:String,
        first_name:String,
        last_name:String,
        middle_name:String,
        nh_agent_name: Any?,
        nh_agent_number: Any?,
        org_state:String,
        org_city:String,
        org_country:String,
        org_gst: String,
        org_name:String,
        org_tan:String,
        pin:String,
        state:String,
        street:String,
        country_id: Int,
        country: String,
        state_id: Int,
        city_id: Int,
        country_org_id: Int,
        state_org_id: Int,
        city_org_id: Int,
        onResult:  (isSuccess: Boolean, response: AffiliateOrgFormResponse?) -> Unit) {

        scope.launch {
            AffiliateOrgApiService.instance.affiliateformOrg(AffiliateOrgFormRequest(data =
            DataAffiliateOrgFormRequest(
                affiliate_source_key,affiliate_type,
                bankDetails = BankDetailsAffiliateOrgFormRequest(bank_account_number,account_type,ifsc,bank_name),
                city = null,
                firstName = first_name,
                lastName = last_name,
                middleName = middle_name,
                nhAgentName = nh_agent_name,
                nhAgentNumber = nh_agent_number,
                orgDetails = OrgDetailsAffiliateOrgFormRequest(
                    org_gst,org_name,org_tan,
                    countryDataOrg = CountryData(country_org_id, org_country),
                    cityDataOrg = CityData(city_org_id, org_city),
                    stateDataOrg = StateData(state_org_id, org_state)),
                pin = pin,
                state = null,
                street = street,
                countryData = CountryData(country_id, country),
                stateData = StateData(state_id, state),
                cityData = CityData(city_id, city)),
                secured = true)).enqueue(object :
                Callback<AffiliateOrgFormResponse> {
                override fun onResponse(call: Call<AffiliateOrgFormResponse>, response: Response<AffiliateOrgFormResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<AffiliateOrgFormResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}