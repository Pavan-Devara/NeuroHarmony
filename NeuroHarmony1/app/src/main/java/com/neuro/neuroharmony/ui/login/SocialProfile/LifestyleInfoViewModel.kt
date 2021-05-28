package com.neuro.neuroharmony.ui.login.SocialProfile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoResponse
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoSubmitResponse
import com.neuro.neuroharmony.data.repository.LifestyleInfoRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class LifestyleInfoViewModel:ViewModel(){
    var lifestyleInfoApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var lifestyleInfoResponseLiveData = MutableLiveData<LifestyleInfoResponse>() // live data for getting Login response

    var lifestyleSubmitInfoApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var lifestyleSubmitInfoResponseLiveData = MutableLiveData<LifestyleInfoSubmitResponse>() // live data for getting Login response

    /**
     * Signup in API call
     */
    fun lifestyleInfoLiveData(partner_key: String?) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            lifestyleInfoApiCallStatus.value = ResourceStatus.loading()
            LifestyleInfoRepository.lifestyleInfoRepo(partner_key) { isSuccess, response ->

                if (isSuccess) {
                    lifestyleInfoApiCallStatus.value =
                        ResourceStatus.success("")
                    lifestyleInfoResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        lifestyleInfoApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        lifestyleInfoApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            lifestyleInfoApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }



    fun lifestyleSubmitInfoLiveData(alcohol: String, dietary: String, pet: String,
                                    post_working: String, smoke: String, sunglasses: String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            lifestyleSubmitInfoApiCallStatus.value = ResourceStatus.loading()
            LifestyleInfoRepository.lifestyleInfoSubmitRepo(alcohol, dietary, pet, post_working,
                smoke, sunglasses) { isSuccess, response ->

                if (isSuccess) {
                    lifestyleSubmitInfoApiCallStatus.value =
                        ResourceStatus.success("")
                    lifestyleSubmitInfoResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        lifestyleSubmitInfoApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        lifestyleSubmitInfoApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            lifestyleSubmitInfoApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }
    
    
}