package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.SuspendChatResponse
import com.neuro.neuroharmony.data.repository.StartChatRepository
import com.neuro.neuroharmony.utils.SingleLiveEvent
import io.reactivex.Single

class StartChatViewModel:ViewModel() {

    var StartChatAPICallStatus = SingleLiveEvent<ResourceStatus>() // livedata for observing login API call status
    var StartChatResponseLiveData = SingleLiveEvent<SuspendChatResponse>() // live data for getting Login response


    fun startchatvm(user_key: String,action_type: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            StartChatAPICallStatus.value = ResourceStatus.loading()
            StartChatRepository.startchatrepo(user_key,action_type) { isSuccess, response ->

                if (isSuccess) {
                    StartChatAPICallStatus.value =
                        ResourceStatus.success("")
                    StartChatResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        StartChatAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        StartChatAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            StartChatAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}