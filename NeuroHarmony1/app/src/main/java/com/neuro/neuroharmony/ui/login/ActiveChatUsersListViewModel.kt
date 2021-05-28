package com.neuro.neuroharmony.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.ListofActiveAvailableUsersChatResponse
import com.neuro.neuroharmony.data.repository.ActiveChatUsersListRepository

class ActiveChatUsersListViewModel: ViewModel() {

    var ActiveChatUsersListAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var ActiveChatUsersListResponseLiveData = MutableLiveData<ListofActiveAvailableUsersChatResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun activechatlistvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            ActiveChatUsersListAPICallStatus.value = ResourceStatus.loading()
            ActiveChatUsersListRepository.activeuserslistchat() { isSuccess, response ->

                if (isSuccess) {
                    ActiveChatUsersListAPICallStatus.value =
                        ResourceStatus.success("")
                    ActiveChatUsersListResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        ActiveChatUsersListAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        ActiveChatUsersListAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            ActiveChatUsersListAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}