package com.neuro.neuroharmony.ui.login.PaymentFlow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.PaymentPackages.CheckoutIdsResponse
import com.neuro.neuroharmony.data.repository.SubmitSelectedPackageRepository
import com.neuro.neuroharmony.data.repository.UsertypeRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class SubmitSelectedPackageViewModel: ViewModel() {

    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<CheckoutIdsResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun checkoutidsvm(gateway_order_id : String,gateway_payment_id:String,gateway_signature:String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            SubmitSelectedPackageRepository.checkoutidsrepo(gateway_order_id,gateway_payment_id,gateway_signature) { isSuccess, response ->

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