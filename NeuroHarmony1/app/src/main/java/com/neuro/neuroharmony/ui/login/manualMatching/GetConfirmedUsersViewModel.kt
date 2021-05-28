package com.neuro.neuroharmony.ui.login.manualMatching

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.manualMatching.ReceivedSentConfirmedManualMatchingResponse
import com.neuro.neuroharmony.data.repository.ManualMatchConfirmedRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class GetConfirmedUsersViewModel: ViewModel() {


    var getreceivedsentconfirmedmanualmatchingAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var getreceivedsentconfirmedmanualmatchingResponseLiveData = MutableLiveData<ReceivedSentConfirmedManualMatchingResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun getreceivedsentconfirmedmanualmatchingvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            getreceivedsentconfirmedmanualmatchingAPICallStatus.value = ResourceStatus.loading()
            ManualMatchConfirmedRepository.getreceivedsentconfirmedmanualmatchingrepo() { isSuccess, response ->

                if (isSuccess) {
                    getreceivedsentconfirmedmanualmatchingAPICallStatus.value =
                        ResourceStatus.success("")
                    getreceivedsentconfirmedmanualmatchingResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        getreceivedsentconfirmedmanualmatchingAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        getreceivedsentconfirmedmanualmatchingAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            getreceivedsentconfirmedmanualmatchingAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}