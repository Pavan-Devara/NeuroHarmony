package com.neuro.neuroharmony.ui.login.PaymentFlow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.PaymentPackages.GatewayOrderIdResponse
import com.neuro.neuroharmony.data.repository.GatewayOrderIdRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class GatewayOrderIdViewModel: ViewModel() {
    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<GatewayOrderIdResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun gatewayidorderid(package_id: String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            GatewayOrderIdRepository.gatewayorderid(package_id) { isSuccess, response ->

                if (isSuccess) {
                    loginAPICallStatus.value =
                        ResourceStatus.success("")
                    loginResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        loginAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        loginAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            loginAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}