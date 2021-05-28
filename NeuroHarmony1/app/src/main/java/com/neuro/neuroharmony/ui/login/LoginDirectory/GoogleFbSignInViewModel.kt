package com.neuro.neuroharmony.ui.login.LoginDirectory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.GoogleFbSignInRespone
import com.neuro.neuroharmony.data.repository.GoogleFbSigninRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class GoogleFbSignInViewModel: ViewModel() {

    var googlefbloginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var googlefbloginResponseLiveData = MutableLiveData<GoogleFbSignInRespone>() // live data for getting Login response

    /**
     * Signup in API call
     */
    fun googlefbsignin(authType: Int,clientId: String,email : String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            googlefbloginAPICallStatus.value = ResourceStatus.loading()
            GoogleFbSigninRepository.googlefbsigninrepo(authType,clientId,email) { isSuccess, response ->

                if (isSuccess) {
                    googlefbloginAPICallStatus.value =
                        ResourceStatus.success("")
                    googlefbloginResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        googlefbloginAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        googlefbloginAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            googlefbloginAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}