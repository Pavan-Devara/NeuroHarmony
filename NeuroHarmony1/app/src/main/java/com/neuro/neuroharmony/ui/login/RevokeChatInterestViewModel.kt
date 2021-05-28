package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.RevokeInterestResponse
import com.neuro.neuroharmony.data.repository.RevokeChatInterestRepository

class RevokeChatInterestViewModel: ViewModel() {


    var revokechatinterestrequesAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var revokechatinterestrequesResponseLiveData = MutableLiveData<RevokeInterestResponse>() // live data for getting Login response


    fun revokeInterestrequestvm(receiver_key: Int,action_type: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            revokechatinterestrequesAPICallStatus.value = ResourceStatus.loading()
            RevokeChatInterestRepository.revokechatinterest(receiver_key,action_type) { isSuccess, response ->

                if (isSuccess) {
                    revokechatinterestrequesAPICallStatus.value =
                        ResourceStatus.success("")
                    revokechatinterestrequesResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        revokechatinterestrequesAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        revokechatinterestrequesAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            revokechatinterestrequesAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}