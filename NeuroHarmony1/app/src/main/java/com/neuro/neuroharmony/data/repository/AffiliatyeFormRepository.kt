package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.AffiliateWorkflow.*
import com.neuro.neuroharmony.data.remote.AffiliateApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AffiliatyeFormRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun affiliateform(
        affilaite_sorcekey:String,
        affiliate_type:Int, bankAccountNumber: String,
        bankAccountType: Int,
        bankIfsc: String,
        bankName: String,
        firstName: String,
        lastName: String,
        middleName: String,
        nh_agent_name: Any?,
        nh_agent_number: Any?,
        pan_number:String,
        pin: String,
        street: String,
        country_id: Int,
        country: String,
        state_id: Int,
        state: String,
        city_id: Int,
        city: String,
        onResult:  (isSuccess: Boolean, response: AffiliateFormResponse?) -> Unit) {

        scope.launch {
            AffiliateApiService.instance.affiliateform(AffiliateFormRequest(
                data = DataAffiliateFormRequest(
                    affilaite_sorcekey,
                    affiliate_type,
                bankDetails = BankDetailsX(bankAccountNumber,bankAccountType, bankIfsc, bankName),
                city = null,
                firstName = firstName,
                lastName = lastName,
                middleName = middleName,
                nhAgentName = nh_agent_name,
                nhAgentNumber = nh_agent_number,
                panNumber = pan_number,
                pin = pin,
                state = null,
                street = street,
                countryData = CountryData(country_id, country),
                stateData = StateData(state_id, state),
                cityData = CityData(city_id, city)), secured = false)).enqueue(object :
                Callback<AffiliateFormResponse> {

                override fun onResponse(call: Call<AffiliateFormResponse>, response: Response<AffiliateFormResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<AffiliateFormResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }


}