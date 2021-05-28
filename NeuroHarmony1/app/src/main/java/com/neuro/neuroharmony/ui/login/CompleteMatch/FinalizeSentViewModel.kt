package com.neuro.neuroharmony.ui.login.CompleteMatch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.CompleteMatch.ResponseSentReceivedRequestFinalizeMatch
import com.neuro.neuroharmony.data.model.UsersSentRequestsResponse
import com.neuro.neuroharmony.data.repository.FinalizeSentRequestsRepo
import com.neuro.neuroharmony.data.repository.UserSentRequestsRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class FinalizeSentViewModel: ViewModel() {

    var FinalizeUsersSentRequestAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var FinalizeUsersSentRequestResponseLiveData = MutableLiveData<ResponseSentReceivedRequestFinalizeMatch>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun usersentrequestsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            FinalizeUsersSentRequestAPICallStatus.value = ResourceStatus.loading()
            FinalizeSentRequestsRepo.finalizesentrequestsrepo() { isSuccess, response ->

                if (isSuccess) {
                    FinalizeUsersSentRequestAPICallStatus.value =
                        ResourceStatus.success("")
                    FinalizeUsersSentRequestResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        FinalizeUsersSentRequestAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        FinalizeUsersSentRequestAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            FinalizeUsersSentRequestAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}