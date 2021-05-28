package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.ExpressInterestResponse
import com.neuro.neuroharmony.data.repository.ExpressInterestRepository
import com.neuro.neuroharmony.utils.SingleLiveEvent

class ExpressInterestViewModel : ViewModel(){

    var loginAPICallStatus = SingleLiveEvent<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = SingleLiveEvent<ExpressInterestResponse>() // live data for getting Login response

    /**
     * Signup in API call
     */
    fun expressInterestvm(receiver_key: String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            ExpressInterestRepository.expressinterest(receiver_key) { isSuccess, response ->

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
            Log.e("LoginView","No netwrok")
            loginAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}