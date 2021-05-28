package com.neuro.neuroharmony.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.UsersSentRequestsResponse
import com.neuro.neuroharmony.data.repository.GetReceivedRequestRepository

class GetReceivedRequestViewModel: ViewModel() {

    var ReceivedRequestAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var ReceivedRequestResponseLiveData = MutableLiveData<UsersSentRequestsResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun getrequestrequestsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            ReceivedRequestAPICallStatus.value = ResourceStatus.loading()
            GetReceivedRequestRepository.receivedrequestsrepo() { isSuccess, response ->

                if (isSuccess) {
                    ReceivedRequestAPICallStatus.value =
                        ResourceStatus.success("")
                    ReceivedRequestResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        ReceivedRequestAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        ReceivedRequestAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            ReceivedRequestAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}