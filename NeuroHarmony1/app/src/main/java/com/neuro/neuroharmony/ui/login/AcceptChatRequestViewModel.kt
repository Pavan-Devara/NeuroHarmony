package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.AcceptChatRequestResponse
import com.neuro.neuroharmony.data.repository.AcceptChatRequestRepository
import com.neuro.neuroharmony.utils.SingleLiveEvent

class AcceptChatRequestViewModel: ViewModel() {

    var acceptInterestrequestAPICallStatus = SingleLiveEvent<ResourceStatus>() // livedata for observing login API call status
    var acceptInterestrequestResponseLiveData = SingleLiveEvent<AcceptChatRequestResponse>() // live data for getting Login response


    fun acceptchatrequestvm(action_type: Int, receiver_key: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            acceptInterestrequestAPICallStatus.value = ResourceStatus.loading()
            AcceptChatRequestRepository.acceptchatrequest(action_type,receiver_key) { isSuccess, response ->

                if (isSuccess) {
                    acceptInterestrequestAPICallStatus.value =
                        ResourceStatus.success("")
                    acceptInterestrequestResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        acceptInterestrequestAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        acceptInterestrequestAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            acceptInterestrequestAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}