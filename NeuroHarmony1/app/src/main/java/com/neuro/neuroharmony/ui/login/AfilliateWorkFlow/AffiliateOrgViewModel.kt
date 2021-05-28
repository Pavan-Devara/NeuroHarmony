package com.neuro.neuroharmony.ui.login.AfilliateWorkFlow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.AffiliateWorkflow.AffiliateOrgFormResponse
import com.neuro.neuroharmony.data.repository.AffiliateOrgRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class AffiliateOrgViewModel: ViewModel() {

    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<AffiliateOrgFormResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun affiliateformorgvm(
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
        city_org_id: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            AffiliateOrgRepository.affiliateformorg(affiliate_source_key,affiliate_type,
                bank_account_number,account_type,ifsc,bank_name
            ,city,first_name,last_name,middle_name,nh_agent_name,
                nh_agent_number,org_state,org_city,org_country,
                org_gst,org_name,org_tan,pin,state,street,
                country_id, country, state_id, city_id, country_org_id,
                state_org_id, city_org_id) { isSuccess, response ->

                if (isSuccess) {
                    loginAPICallStatus.value =
                        ResourceStatus.success("")
                    loginResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        loginAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        loginAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            loginAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}