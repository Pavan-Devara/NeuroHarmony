package com.neuro.neuroharmony.ui.login.PaymentFlow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.PaymentPackages.ErrorCodeAndErrorDescriptionResponse
import com.neuro.neuroharmony.data.repository.ErrorCodeAndDescriptionRepo
import com.neuro.neuroharmony.ui.login.ResourceStatus

class ErrorCodeAndDescriptionViewModel: ViewModel() {

    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<ErrorCodeAndErrorDescriptionResponse>() // live data for getting Login response

    /**
     * Login in API call
     */
    fun logerrorpaymentvm(error_code: Any,err_response : String,gateway_order_id:String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            ErrorCodeAndDescriptionRepo.logerrorpaymnetrepo(error_code,err_response,gateway_order_id) { isSuccess, response ->

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
