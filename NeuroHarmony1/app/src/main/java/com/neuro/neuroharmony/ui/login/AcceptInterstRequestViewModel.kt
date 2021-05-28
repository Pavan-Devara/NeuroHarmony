package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.AcceptIntetrestResponse
import com.neuro.neuroharmony.data.repository.AcceptIterestRequestResponseRepository

class AcceptInterstRequestViewModel: ViewModel() {

    var acceptInterestrequestAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var acceptInterestrequestResponseLiveData = MutableLiveData<AcceptIntetrestResponse>() // live data for getting Login response


    fun acceptInterestrequestvm(action_type: Int,sender_key: String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            acceptInterestrequestAPICallStatus.value = ResourceStatus.loading()
            AcceptIterestRequestResponseRepository.acceptinterstrequestRepo(action_type, sender_key) { isSuccess, response ->

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