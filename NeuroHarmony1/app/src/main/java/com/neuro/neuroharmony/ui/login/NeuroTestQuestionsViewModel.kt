package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.NeuroTestQuestionsResponse
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoSubmitResponse
import com.neuro.neuroharmony.data.repository.NeuroTestQuestionsRepository

class NeuroTestQuestionsViewModel: ViewModel() {
    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<NeuroTestQuestionsResponse>() // live data for getting Login response


    var resetAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var resetResponseLiveData = MutableLiveData<LifestyleInfoSubmitResponse>() // live data for getting Login response


    /**
     * Signup in API call
     */
    fun neurotestquestionsvm(test_name:String, session_id: String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            NeuroTestQuestionsRepository.neurotestquestions(test_name, session_id) { isSuccess, response ->

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




    fun resettestvm(test_name:String, session_id: String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            resetAPICallStatus.value = ResourceStatus.loading()
            NeuroTestQuestionsRepository.resettest(test_name, session_id) { isSuccess, response ->

                if (isSuccess) {
                    resetAPICallStatus.value =
                        ResourceStatus.success("")
                    resetResponseLiveData.postValue(response)


                } else {
                    if (response?. message== "Sucsess") {
                        resetAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        resetAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            resetAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}