package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.AllSessionOfUserResponse
import com.neuro.neuroharmony.data.repository.AllSessionIdsOfUserRepository

class FixTestAsDefaultViewModel : ViewModel(){
    var fixTestAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var responseLiveData = MutableLiveData<AllSessionOfUserResponse>() // live data for getting Login response

    fun getAllSessionIds() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            fixTestAPICallStatus.value = ResourceStatus.loading()
            AllSessionIdsOfUserRepository.sessionIdsrepo() { isSuccess, response ->

                if (isSuccess) {
                    fixTestAPICallStatus.value =
                        ResourceStatus.success("")
                    responseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        fixTestAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        fixTestAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            fixTestAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}