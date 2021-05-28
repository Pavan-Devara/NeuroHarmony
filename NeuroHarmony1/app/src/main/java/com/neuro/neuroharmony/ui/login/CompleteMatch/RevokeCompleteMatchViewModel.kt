package com.neuro.neuroharmony.ui.login.CompleteMatch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.CompleteMatch.RevokeCompleteMatchResponse
import com.neuro.neuroharmony.data.repository.RevokeCompleteMatchRepo
import com.neuro.neuroharmony.ui.login.ResourceStatus

class RevokeCompleteMatchViewModel: ViewModel() {

    var RevokeCompleteMatchAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var RevokeCompleteMatchResponseLiveData = MutableLiveData<RevokeCompleteMatchResponse>() // live data for getting Login response


    fun revokecompletematchvm(sender_key: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            RevokeCompleteMatchAPICallStatus.value = ResourceStatus.loading()
            RevokeCompleteMatchRepo.revokecompletematchrepo(sender_key) { isSuccess, response ->

                if (isSuccess) {
                    RevokeCompleteMatchAPICallStatus.value =
                        ResourceStatus.success("")
                    RevokeCompleteMatchResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        RevokeCompleteMatchAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        RevokeCompleteMatchAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            RevokeCompleteMatchAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}