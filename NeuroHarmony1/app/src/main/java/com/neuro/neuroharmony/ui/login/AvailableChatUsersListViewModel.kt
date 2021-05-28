package com.neuro.neuroharmony.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.ListofActiveAvailableUsersChatResponse
import com.neuro.neuroharmony.data.repository.AvailableChatUsersListRepository

class AvailableChatUsersListViewModel: ViewModel() {

    var AvailableChatUsersListAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var AvailableChatUsersListResponseLiveData = MutableLiveData<ListofActiveAvailableUsersChatResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun availablechatusersvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            AvailableChatUsersListAPICallStatus.value = ResourceStatus.loading()
            AvailableChatUsersListRepository.availableuserslistchat() { isSuccess, response ->

                if (isSuccess) {
                    AvailableChatUsersListAPICallStatus.value =
                        ResourceStatus.success("")
                    AvailableChatUsersListResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        AvailableChatUsersListAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        AvailableChatUsersListAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            AvailableChatUsersListAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}