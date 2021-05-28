package com.neuro.neuroharmony.ui.login.CompleteMatch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.CompleteMatch.ResponseSentReceivedRequestFinalizeMatch
import com.neuro.neuroharmony.data.repository.FinalizeReceivedRequestRepo
import com.neuro.neuroharmony.data.repository.FinalizeSentRequestsRepo
import com.neuro.neuroharmony.ui.login.ResourceStatus

class FinalizeReceivedViewModel: ViewModel() {

    var FinalizeUsersReceivedRequestAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var FinalizeUsersReceivedRequestResponseLiveData = MutableLiveData<ResponseSentReceivedRequestFinalizeMatch>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun finalizeuserreceivedrequestsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            FinalizeUsersReceivedRequestAPICallStatus.value = ResourceStatus.loading()
            FinalizeReceivedRequestRepo.finalizereceivedrequestsrepo() { isSuccess, response ->

                if (isSuccess) {
                    FinalizeUsersReceivedRequestAPICallStatus.value =
                        ResourceStatus.success("")
                    FinalizeUsersReceivedRequestResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        FinalizeUsersReceivedRequestAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        FinalizeUsersReceivedRequestAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            FinalizeUsersReceivedRequestAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}