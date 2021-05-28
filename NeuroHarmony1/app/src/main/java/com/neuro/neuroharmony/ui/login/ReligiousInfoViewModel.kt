package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.FAQs.FAQResponse
import com.neuro.neuroharmony.data.model.ReligiousInfoResponse
import com.neuro.neuroharmony.data.repository.ReligiousInfoRepository

class ReligiousInfoViewModel: ViewModel() {

    var religiousInfoApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var religiousInfoResponseLiveData = MutableLiveData<ReligiousInfoResponse>() // live data for getting Login response
    var faqResponseLiveData = MutableLiveData<FAQResponse>()
    var faqAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    /**
     * Signup in API call
     */
    fun religiosInfoLiveData(partner_key: String?) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            religiousInfoApiCallStatus.value = ResourceStatus.loading()
            ReligiousInfoRepository.religiousInfoRepo(partner_key) {isSuccess, response ->

                if (isSuccess) {
                    religiousInfoApiCallStatus.value =
                        ResourceStatus.success("")
                    religiousInfoResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        religiousInfoApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        religiousInfoApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            religiousInfoApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }



    fun faqLiveData() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            faqAPICallStatus.value = ResourceStatus.loading()
            ReligiousInfoRepository.faqrepo {isSuccess, response ->

                if (isSuccess) {
                    faqAPICallStatus.value =
                        ResourceStatus.success("")
                    faqResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        faqAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        faqAPICallStatus.value =
                            ResourceStatus.error("")
                    }
                }
            }

        } else {
            Log.e("SingupView","No netwrok")
            faqAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }

}