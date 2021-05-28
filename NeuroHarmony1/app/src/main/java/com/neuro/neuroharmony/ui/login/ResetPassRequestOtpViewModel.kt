package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.ResetPasswordRequestOtpResponse
import com.neuro.neuroharmony.data.repository.ResetOtpRepository

class ResetPassRequestOtpViewModel: ViewModel() {

    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<ResetPasswordRequestOtpResponse>()


    fun forgototprequest(mobile: String,country_code_picker_forgot_pwd: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            ResetOtpRepository.forgototprequest(mobile,country_code_picker_forgot_pwd) { isSuccess, response ->

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
            Log.e("ResetOtpView","No netwrok")
            loginAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }

}