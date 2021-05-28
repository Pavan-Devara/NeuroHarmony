package com.neuro.neuroharmony.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.GetReceivedChatRequestResponse
import com.neuro.neuroharmony.data.repository.GetReceivedChatRequestsRepository

class GetReceivedChatRequestsViewModel: ViewModel() {

    var receivedchatrequesAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var receivedchatrequesResponseLiveData = MutableLiveData<GetReceivedChatRequestResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun getreceivedchatrequestsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            receivedchatrequesAPICallStatus.value = ResourceStatus.loading()
            GetReceivedChatRequestsRepository.receivedchatrequestsrepo() { isSuccess, response ->

                if (isSuccess) {
                    receivedchatrequesAPICallStatus.value =
                        ResourceStatus.success("")
                    receivedchatrequesResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        receivedchatrequesAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        receivedchatrequesAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            receivedchatrequesAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}