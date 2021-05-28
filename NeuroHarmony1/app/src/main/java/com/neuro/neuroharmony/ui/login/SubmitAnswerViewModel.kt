package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.SubmitAnswerResponse
import com.neuro.neuroharmony.data.repository.SubmitAnswerRepository

class SubmitAnswerViewModel: ViewModel() {
    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<SubmitAnswerResponse>() // live data for getting Login response

    /**
     * Signup in API call
     */

    fun submitanswer(
        end_time: String, group_submit: Boolean,
        qid: Int,
        selected_option:String,
        session_id:String,
        start_time: String,
        user_key: String,
        testType:Int,
        group: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            SubmitAnswerRepository.submitanswer(end_time,group_submit,qid,selected_option,session_id,start_time,user_key,testType,group) { isSuccess, response ->

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