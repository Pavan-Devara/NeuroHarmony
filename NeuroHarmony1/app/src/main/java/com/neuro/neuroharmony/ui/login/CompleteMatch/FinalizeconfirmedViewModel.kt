package com.neuro.neuroharmony.ui.login.CompleteMatch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.CompleteMatch.ResponseSentReceivedRequestFinalizeMatch
import com.neuro.neuroharmony.data.repository.FinalizeConfirmedRequestsRepo
import com.neuro.neuroharmony.data.repository.FinalizeReceivedRequestRepo
import com.neuro.neuroharmony.ui.login.ResourceStatus

class FinalizeconfirmedViewModel: ViewModel() {

    var FinalizeUsersConfirmedRequestAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var FinalizeUsersConfirmedRequestResponseLiveData = MutableLiveData<ResponseSentReceivedRequestFinalizeMatch>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun finalizeuserconfirmedrequestsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            FinalizeUsersConfirmedRequestAPICallStatus.value = ResourceStatus.loading()
            FinalizeConfirmedRequestsRepo.finalizeconfirmedrequestsrepo() { isSuccess, response ->

                if (isSuccess) {
                    FinalizeUsersConfirmedRequestAPICallStatus.value =
                        ResourceStatus.success("")
                    FinalizeUsersConfirmedRequestResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        FinalizeUsersConfirmedRequestAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        FinalizeUsersConfirmedRequestAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            FinalizeUsersConfirmedRequestAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}