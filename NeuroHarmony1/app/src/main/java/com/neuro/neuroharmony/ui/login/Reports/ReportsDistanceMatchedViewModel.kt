package com.neuro.neuroharmony.ui.login.Reports

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.ReportsNew.MatchReports.ReportsMatchedUsers
import com.neuro.neuroharmony.data.repository.ReportsCoreBeliefRepository
import com.neuro.neuroharmony.data.repository.ReportsMatchDistanceRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class ReportsDistanceMatchedViewModel: ViewModel() {
    
    var getreportsAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing Reports API call status
    var getreportsResponseLiveData = MutableLiveData<ReportsMatchedUsers>() // live data for getting Reports response
    /**
     * Signup in API call
     */
    fun getreportsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            getreportsAPICallStatus.value = ResourceStatus.loading()
            ReportsMatchDistanceRepository.getmatchdistancereportsrepo() { isSuccess, response ->

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