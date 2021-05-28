package com.neuro.neuroharmony.ui.login.manualMatching

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.manualMatching.AcceptDeclineRevokeDelinkManualMatchingResponse
import com.neuro.neuroharmony.data.repository.AcceptDeclineRevokeDelinkManualMatchingRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.utils.SingleLiveEvent

class AcceptDeclineDelinkViewModel: ViewModel() {

    var acceptdeclinemanualmathcingAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var acceptdeclinemanualmathcingResponseLiveData = SingleLiveEvent<AcceptDeclineRevokeDelinkManualMatchingResponse>() // live data for getting Login response


    fun acceptdelinmanualmatchvm(user_key: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            acceptdeclinemanualmathcingAPICallStatus.value = ResourceStatus.loading()
            AcceptDeclineRevokeDelinkManualMatchingRepository.acceptdeclinemanualmathcing(user_key) { isSuccess, response ->

                if (isSuccess) {
                    acceptdeclinemanualmathcingAPICallStatus.value =
                        ResourceStatus.success("")
                    acceptdeclinemanualmathcingResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        acceptdeclinemanualmathcingAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        acceptdeclinemanualmathcingAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            acceptdeclinemanualmathcingAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}