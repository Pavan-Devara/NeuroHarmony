package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.GetNeuroDesireScoreResponse
import com.neuro.neuroharmony.data.repository.ScoreNeuroDesireRepository

class ScoreNeuroDesireViewModel: ViewModel() {
    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<GetNeuroDesireScoreResponse>() // live data for getting Login response

    /**
     * Signup in API call
     */
    fun getscoreneurodesire() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            ScoreNeuroDesireRepository.getscoreneurodesire() { isSuccess, response ->

                if (isSuccess) {
                    loginAPICallStatus.value =
                        ResourceStatus.success("")
                    loginResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        loginAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        loginAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            loginAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}