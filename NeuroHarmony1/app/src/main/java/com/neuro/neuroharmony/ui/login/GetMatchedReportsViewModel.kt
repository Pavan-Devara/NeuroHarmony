package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.GetMatchedReportsOfUserResponse
import com.neuro.neuroharmony.data.repository.GetMatchedReportsRepository

class GetMatchedReportsViewModel: ViewModel() {

    var getreportsAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var getreportsResponseLiveData = MutableLiveData<GetMatchedReportsOfUserResponse>() // live data for getting Login response
    /**
     * Signup in API call
     */
    fun getreportsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            getreportsAPICallStatus.value = ResourceStatus.loading()
            GetMatchedReportsRepository.getmatchedreportsrepo() { isSuccess, response ->

                if (isSuccess) {
                    getreportsAPICallStatus.value =
                        ResourceStatus.success("")
                    getreportsResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        getreportsAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        getreportsAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            getreportsAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}