package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.SuspendChatResponse
import com.neuro.neuroharmony.data.repository.SuspendChatRepository
import com.neuro.neuroharmony.utils.SingleLiveEvent

class SuspendChatViewModel: ViewModel() {
    var suspendchatAPICallStatus = SingleLiveEvent<ResourceStatus>() // livedata for observing login API call status
    var suspendchatResponseLiveData = SingleLiveEvent<SuspendChatResponse>() // live data for getting Login response


    fun suspendchatvm(user_key: String,action_type: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            suspendchatAPICallStatus.value = ResourceStatus.loading()
            SuspendChatRepository.suspendchatrepo(user_key,action_type) { isSuccess, response ->

                if (isSuccess) {
                    suspendchatAPICallStatus.value =
                        ResourceStatus.success("")
                    suspendchatResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        suspendchatAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        suspendchatAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            suspendchatAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}