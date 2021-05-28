package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.BaselineResponse
import com.neuro.neuroharmony.data.repository.BaselineRepository

class BaseLineViewModel: ViewModel() {

    var acceptInterestrequestAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var acceptInterestrequestResponseLiveData = MutableLiveData<BaselineResponse>() // live data for getting Login response


    fun baselinevm(session_id: String, test_type:Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            acceptInterestrequestAPICallStatus.value = ResourceStatus.loading()
            BaselineRepository.baselineRepo(session_id,test_type) { isSuccess, response ->

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
            acceptInterestrequestAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}