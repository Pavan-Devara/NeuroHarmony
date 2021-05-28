package com.neuro.neuroharmony.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.UsersSentRequestsResponse
import com.neuro.neuroharmony.data.repository.UserSentRequestsRepository

class USersSentRequestsViewModel: ViewModel() {

    var UsersSentRequestAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var UsersSentRequestResponseLiveData = MutableLiveData<UsersSentRequestsResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun usersentrequestsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            UsersSentRequestAPICallStatus.value = ResourceStatus.loading()
            UserSentRequestsRepository.userssentrequestsrepo() { isSuccess, response ->

                if (isSuccess) {
                    UsersSentRequestAPICallStatus.value =
                        ResourceStatus.success("")
                    UsersSentRequestResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        UsersSentRequestAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        UsersSentRequestAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            UsersSentRequestAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}