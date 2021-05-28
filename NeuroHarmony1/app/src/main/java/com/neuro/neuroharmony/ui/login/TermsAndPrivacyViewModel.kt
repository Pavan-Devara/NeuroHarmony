package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.GenericData.GenericDataResponse
import com.neuro.neuroharmony.data.model.TermsAndPrivacyResponse
import com.neuro.neuroharmony.data.repository.TermsAndPrivacyRepository

class TermsAndPrivacyViewModel: ViewModel() {
    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<TermsAndPrivacyResponse>() // live data for getting Login response

    var genericdataAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var genericResponseLiveData = MutableLiveData<GenericDataResponse>() // live data for getting Login response

    /**
     * Signup in API call
     */
    fun termsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            TermsAndPrivacyRepository.gettermsrepo() { isSuccess, response ->

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
            Log.e("SingupView","No netwrok")
            loginAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }



    fun genericDataVM(baseBandVersion : String,cpu : String,isRootedOrJailBroken : String,kernelVersion : String,modelName : String,modelNumber : String,osType : Int,osVersion : String, ram : String,securityPatchLevel : String ,serialNumber : String,storageAvailable : String,storageTotal : String,vendorOsVersion : String ) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            genericdataAPICallStatus.value = ResourceStatus.loading()
            TermsAndPrivacyRepository.genericdatarepo(baseBandVersion,cpu,isRootedOrJailBroken,kernelVersion,modelName,modelNumber,osType,osVersion, ram,securityPatchLevel ,serialNumber,storageAvailable,storageTotal,vendorOsVersion) { isSuccess, response ->

                if (isSuccess) {
                    genericdataAPICallStatus.value =
                        ResourceStatus.success("")
                    genericResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        genericdataAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        genericdataAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            genericdataAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}