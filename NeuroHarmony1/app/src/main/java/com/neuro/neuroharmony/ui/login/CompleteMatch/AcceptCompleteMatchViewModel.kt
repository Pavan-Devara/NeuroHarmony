package com.neuro.neuroharmony.ui.login.CompleteMatch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.CompleteMatch.AcceptDeclineCompleteMatchResponse
import com.neuro.neuroharmony.data.repository.AcceptCompleteMatchRepo
import com.neuro.neuroharmony.ui.login.ResourceStatus

class AcceptCompleteMatchViewModel: ViewModel() {

    var AcceptCompleteMatchAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var AcceptCompleteMatchResponseLiveData = MutableLiveData<AcceptDeclineCompleteMatchResponse>() // live data for getting Login response


    fun acceptcompletematchvm(sender_key: Int,action_type:Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            AcceptCompleteMatchAPICallStatus.value = ResourceStatus.loading()
            AcceptCompleteMatchRepo.acceptcompletematchrepo(sender_key,action_type) { isSuccess, response ->

                if (isSuccess) {
                    AcceptCompleteMatchAPICallStatus.value =
                        ResourceStatus.success("")
                    AcceptCompleteMatchResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        AcceptCompleteMatchAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        AcceptCompleteMatchAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            AcceptCompleteMatchAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}