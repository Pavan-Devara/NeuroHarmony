package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.RevokeInterestResponse
import com.neuro.neuroharmony.data.repository.RevokeInterestRepository
import com.neuro.neuroharmony.utils.SingleLiveEvent

class RevokeInterestRequestViewModel: ViewModel() {

    var revokeInterestrequesAPICallStatus = SingleLiveEvent<ResourceStatus>() // livedata for observing login API call status
    var revokeInterestrequesResponseLiveData = SingleLiveEvent<RevokeInterestResponse>() // live data for getting Login response


    fun revokeInterestrequestvm(receiver_key: Int,action_type: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            revokeInterestrequesAPICallStatus.value = ResourceStatus.loading()
            RevokeInterestRepository.revokeinterest(receiver_key,action_type) { isSuccess, response ->

                if (isSuccess) {
                    revokeInterestrequesAPICallStatus.value =
                        ResourceStatus.success("")
                    revokeInterestrequesResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        revokeInterestrequesAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        revokeInterestrequesAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            revokeInterestrequesAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}