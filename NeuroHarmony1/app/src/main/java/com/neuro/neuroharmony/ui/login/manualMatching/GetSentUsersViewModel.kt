package com.neuro.neuroharmony.ui.login.manualMatching

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.manualMatching.ReceivedSentConfirmedManualMatchingResponse
import com.neuro.neuroharmony.data.repository.SentManualMatchingRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class GetSentUsersViewModel: ViewModel() {

    var getsentmanualmatchingAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var getsentmanualmatchingResponseLiveData = MutableLiveData<ReceivedSentConfirmedManualMatchingResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun getreceivedsentconfirmedmanualmatchingvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            getsentmanualmatchingAPICallStatus.value = ResourceStatus.loading()
            SentManualMatchingRepository.getreceivedsentconfirmedmanualmatchingrepo() { isSuccess, response ->

                if (isSuccess) {
                    getsentmanualmatchingAPICallStatus.value =
                        ResourceStatus.success("")
                    getsentmanualmatchingResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        getsentmanualmatchingAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        getsentmanualmatchingAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            getsentmanualmatchingAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}