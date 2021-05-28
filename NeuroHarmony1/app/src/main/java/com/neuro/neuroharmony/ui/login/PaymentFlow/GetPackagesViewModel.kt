package com.neuro.neuroharmony.ui.login.PaymentFlow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.PaymentPackages.GetPaymentPackageDetails
import com.neuro.neuroharmony.data.model.PaymentPackages.TransferTokensResponse
import com.neuro.neuroharmony.data.model.PaymentPackages.UserPurchasedPackagesResponse
import com.neuro.neuroharmony.data.repository.GetPaymentPackagesRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class GetPackagesViewModel: ViewModel() {

    var getpackagesAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var getpackagesResponseLiveData = MutableLiveData<GetPaymentPackageDetails>() // live data for getting Login response

    var getuserpackagesAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var getuserpackagesResponseLiveData = MutableLiveData<UserPurchasedPackagesResponse>() // live data for getting Login response

    var transferTokensAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var transferTokensResponseLiveData = MutableLiveData<TransferTokensResponse>() // live data for getting Login response

    /**
     * Signup in API call
     */
    fun getpackagesvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            getpackagesAPICallStatus.value = ResourceStatus.loading()
            GetPaymentPackagesRepository.getpackagesrepo() { isSuccess, response ->

                if (isSuccess) {
                    getpackagesAPICallStatus.value =
                        ResourceStatus.success("")
                    getpackagesResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        getpackagesAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        getpackagesAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            getpackagesAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }




    fun getuserpackagesvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            getuserpackagesAPICallStatus.value = ResourceStatus.loading()
            GetPaymentPackagesRepository.getuserpackagesrepo { isSuccess, response ->

                if (isSuccess) {
                    getuserpackagesAPICallStatus.value =
                        ResourceStatus.success("")
                    getuserpackagesResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        getuserpackagesAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        getuserpackagesAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            getuserpackagesAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }



    fun transfertokensvm(mobile_number:String, neuro_tokens:Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            transferTokensAPICallStatus.value = ResourceStatus.loading()
            GetPaymentPackagesRepository.transfertokensrepo(mobile_number, neuro_tokens) { isSuccess, response ->

                if (isSuccess) {
                    transferTokensAPICallStatus.value =
                        ResourceStatus.success("")
                    transferTokensResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        transferTokensAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        transferTokensAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            transferTokensAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}