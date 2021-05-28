package com.neuro.neuroharmony.ui.login.SocialProfile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoResponse
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoSubmitResponse
import com.neuro.neuroharmony.data.repository.FamilyInfoRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class FamilyInfoViewModel: ViewModel() {
    var familyInfoApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var familyInfoResponseLiveData = MutableLiveData<FamilyInfoResponse>() // live data for getting Login response

    var familyInfoSubmitApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var familyInfoSubmitResponseLiveData = MutableLiveData<FamilyInfoSubmitResponse>() // live data for getting Login response


    /**
     * Signup in API call
     */
    fun familyInfoLiveData(partner_key: String?) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            familyInfoApiCallStatus.value = ResourceStatus.loading()
            FamilyInfoRepository.familyInfoRepo(partner_key) { isSuccess, response ->

                if (isSuccess) {
                    familyInfoApiCallStatus.value =
                        ResourceStatus.success("")
                    familyInfoResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        familyInfoApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        familyInfoApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            familyInfoApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }

    fun familyInfoSubmitLiveData(familyIncome: String,familyType: String,familyValue: String,fatherCompany: String,fatherDesignation: String,fatherStatus: String,motherCompany: String,motherDesignation: String,motherStatus: String,siblings: String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            familyInfoSubmitApiCallStatus.value = ResourceStatus.loading()
            FamilyInfoRepository.familyInfoSubmitRepo(familyIncome,familyType,familyValue,fatherCompany,fatherDesignation,fatherStatus,motherCompany,motherDesignation,motherStatus,siblings) { isSuccess, response ->

                if (isSuccess) {
                    familyInfoSubmitApiCallStatus.value =
                        ResourceStatus.success("")
                    familyInfoSubmitResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        familyInfoSubmitApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        familyInfoSubmitApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            familyInfoSubmitApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }


}