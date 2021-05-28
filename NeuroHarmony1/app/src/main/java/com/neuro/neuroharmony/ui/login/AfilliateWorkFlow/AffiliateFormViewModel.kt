package com.neuro.neuroharmony.ui.login.AfilliateWorkFlow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.AffiliateWorkflow.AffiliateFormResponse
import com.neuro.neuroharmony.data.repository.AffiliatyeFormRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class AffiliateFormViewModel: ViewModel() {

    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<AffiliateFormResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun affiliateformvm(
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
        city: String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            AffiliatyeFormRepository.affiliateform(
                affilaite_sorcekey,
                affiliate_type, bankAccountNumber,
                bankAccountType,
                bankIfsc,
                bankName,
                firstName,
                lastName,
                middleName,
                nh_agent_name,
                nh_agent_number,
                pan_number,
                pin,
                street,
                country_id,
                country,
                state_id,
                state,
                city_id,
                city) { isSuccess, response ->

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