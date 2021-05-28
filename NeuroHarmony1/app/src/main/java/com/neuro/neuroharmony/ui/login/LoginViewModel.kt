package com.neuro.neuroharmony.ui.login

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.LoginResponse
import com.neuro.neuroharmony.data.repository.LoginRepository

class LoginViewModel :ViewModel(){

    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<LoginResponse>() // live data for getting Login response

    /**
     * Signup in API call
     */
    fun login(authType: Int,clientId: String,password: String,username: String,country_code_picker_login: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            LoginRepository.loginUser(authType,clientId,password,username,country_code_picker_login) { isSuccess, response ->

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