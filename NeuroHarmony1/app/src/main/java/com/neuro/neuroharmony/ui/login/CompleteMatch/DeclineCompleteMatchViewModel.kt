package com.neuro.neuroharmony.ui.login.CompleteMatch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.CompleteMatch.AcceptDeclineCompleteMatchResponse
import com.neuro.neuroharmony.data.repository.DeclineCompleteMatchRepo
import com.neuro.neuroharmony.ui.login.ResourceStatus

class DeclineCompleteMatchViewModel: ViewModel() {

    var DeclineCompleteMatchAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var DeclineCompleteMatchResponseLiveData = MutableLiveData<AcceptDeclineCompleteMatchResponse>() // live data for getting Login response


    fun declinecompletematchvm(sender_key: Int,action_type:Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            DeclineCompleteMatchAPICallStatus.value = ResourceStatus.loading()
            DeclineCompleteMatchRepo.declinecompletematchrepo(sender_key,action_type) { isSuccess, response ->

                if (isSuccess) {
                    DeclineCompleteMatchAPICallStatus.value =
                        ResourceStatus.success("")
                    DeclineCompleteMatchResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        DeclineCompleteMatchAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        DeclineCompleteMatchAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            DeclineCompleteMatchAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}