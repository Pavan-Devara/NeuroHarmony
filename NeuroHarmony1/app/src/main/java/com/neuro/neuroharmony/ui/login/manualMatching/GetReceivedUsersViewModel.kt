package com.neuro.neuroharmony.ui.login.manualMatching

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.manualMatching.ReceivedSentConfirmedManualMatchingResponse
import com.neuro.neuroharmony.data.repository.ReceivedManualMatchRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class GetReceivedUsersViewModel: ViewModel() {

    var getreceivedmanualmatchingAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var getreceivedmanualmatchingResponseLiveData = MutableLiveData<ReceivedSentConfirmedManualMatchingResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun getreceivedsentconfirmedmanualmatchingvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            getreceivedmanualmatchingAPICallStatus.value = ResourceStatus.loading()
            ReceivedManualMatchRepository.getreceivedsentconfirmedmanualmatchingrepo() { isSuccess, response ->

                if (isSuccess) {
                    getreceivedmanualmatchingAPICallStatus.value =
                        ResourceStatus.success("")
                    getreceivedmanualmatchingResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        getreceivedmanualmatchingAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        getreceivedmanualmatchingAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            getreceivedmanualmatchingAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}