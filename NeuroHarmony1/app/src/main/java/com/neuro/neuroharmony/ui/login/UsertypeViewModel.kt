package com.neuro.neuroharmony.ui.login


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.UserTypeResponse
import com.neuro.neuroharmony.data.repository.UsertypeRepository

class UsertypeViewModel : ViewModel() {

    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<UserTypeResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun usertype(userType: Int,referral_code:String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            UsertypeRepository.usertypeUser(userType,referral_code) { isSuccess, response ->

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
            loginAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}