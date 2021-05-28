package com.neuro.neuroharmony.ui.login.Feedback

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.Feedback.SubmitFeedbackResponse
import com.neuro.neuroharmony.data.repository.LoginRepository
import com.neuro.neuroharmony.data.repository.SubmitFeedbackRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class SubmitFeedbackViewModel: ViewModel() {


    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<SubmitFeedbackResponse>() // live data for getting Login response

    /**
     * Signup in API call
     */
    fun submitfeedbackvm(feature_id: String,comment: String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            SubmitFeedbackRepository.submitfeedback(feature_id,comment) { isSuccess, response ->

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