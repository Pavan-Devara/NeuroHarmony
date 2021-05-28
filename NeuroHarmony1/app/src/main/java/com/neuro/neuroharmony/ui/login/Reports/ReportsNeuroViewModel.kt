package com.neuro.neuroharmony.ui.login.Reports

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports.NeuroReportResponse
import com.neuro.neuroharmony.data.repository.ReportsRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class ReportsNeuroViewModel: ViewModel() {

    var getreportsAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing Reports API call status
    var getreportsResponseLiveData = MutableLiveData<NeuroReportResponse>() // live data for getting Reports response
    /**
     * Signup in API call
     */
    fun getreportsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            getreportsAPICallStatus.value = ResourceStatus.loading()
            ReportsRepository.getneuroreportsrepo() { isSuccess, response ->

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