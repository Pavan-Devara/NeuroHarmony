package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.AccountStatusResponse
import com.neuro.neuroharmony.data.repository.AccountStatusRepo
import com.neuro.neuroharmony.utils.SingleLiveEvent

class AccountStatusViewModel: ViewModel() {

    var AccountStatusAPICallStatus = SingleLiveEvent<ResourceStatus>() // livedata for observing login API call status
    var AccountStatusResponseLiveData = SingleLiveEvent<AccountStatusResponse>() // live data for getting Login response


    fun accountstatusvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            AccountStatusAPICallStatus.value = ResourceStatus.loading()
            AccountStatusRepo.AccountStatusRepository() { isSuccess, response ->

                if (isSuccess) {
                    AccountStatusAPICallStatus.value =
                        ResourceStatus.success("")
                    AccountStatusResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        AccountStatusAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        AccountStatusAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            AccountStatusAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}