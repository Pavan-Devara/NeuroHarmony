package com.neuro.neuroharmony.ui.login.manualMatching

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.AddPartnerResponse
import com.neuro.neuroharmony.data.repository.AddPartnerRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class AddPartnerViewModel: ViewModel() {

    var addpartnerAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var addpartnerResponseLiveData = MutableLiveData<AddPartnerResponse>() // live data for getting Login response


    fun addpartnervm(mobile_number: String,country_code_picker_manual_match: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            addpartnerAPICallStatus.value = ResourceStatus.loading()
            AddPartnerRepository.addpartnerrepo(mobile_number,country_code_picker_manual_match) { isSuccess, response ->

                if (isSuccess) {
                    addpartnerAPICallStatus.value =
                        ResourceStatus.success("")
                    addpartnerResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        addpartnerAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        addpartnerAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            addpartnerAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}