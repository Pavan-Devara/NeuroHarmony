package com.neuro.neuroharmony.ui.login.SocialProfile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.Social_profile.PersonalInfo.PersonalInfoResponse
import com.neuro.neuroharmony.data.repository.LifestyleInfoRepository
import com.neuro.neuroharmony.data.repository.PersonalInfoRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class PersonalInfoViewModel:ViewModel() {
    var personalInfoApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var personalInfoResponseLiveData = MutableLiveData<PersonalInfoResponse>() // live data for getting Login response

    fun personalInfoLiveData(partner_key: String?) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            personalInfoApiCallStatus.value = ResourceStatus.loading()
            PersonalInfoRepository.personalInfoRepo(partner_key) { isSuccess, response ->

                if (isSuccess) {
                    personalInfoApiCallStatus.value =
                        ResourceStatus.success("")
                    personalInfoResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        personalInfoApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        personalInfoApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            personalInfoApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }


}