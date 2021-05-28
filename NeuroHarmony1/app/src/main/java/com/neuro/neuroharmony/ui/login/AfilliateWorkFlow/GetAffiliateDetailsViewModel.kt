package com.neuro.neuroharmony.ui.login.AfilliateWorkFlow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.AffiliateWorkflow.GetAffiliateDetailsResponse
import com.neuro.neuroharmony.data.repository.GetAffiliateDetailsRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class GetAffiliateDetailsViewModel: ViewModel() {

    var getAffiliateDetailsAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var getAffiliateDetailsResponseLiveData = MutableLiveData<GetAffiliateDetailsResponse>() // live data for getting Login response

    /**GetAffiliateDetailsResponse
     * Login in API call
     */
    fun getaffiliatevm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            getAffiliateDetailsAPICallStatus.value = ResourceStatus.loading()
            GetAffiliateDetailsRepository.getaffiliaterepo() { isSuccess, response ->

                if (isSuccess) {
                    getAffiliateDetailsAPICallStatus.value =
                        ResourceStatus.success("")
                    getAffiliateDetailsResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        getAffiliateDetailsAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        getAffiliateDetailsAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            getAffiliateDetailsAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}