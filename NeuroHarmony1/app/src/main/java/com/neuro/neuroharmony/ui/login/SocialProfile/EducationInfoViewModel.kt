package com.neuro.neuroharmony.ui.login.SocialProfile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfessionResponse
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfessionSubmitResponse
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoResponse
import com.neuro.neuroharmony.data.repository.EducationProfessionRepository
import com.neuro.neuroharmony.data.repository.FamilyInfoRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class EducationInfoViewModel:ViewModel() {
    var educationprofApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var educationprofResponseLiveData = MutableLiveData<EducationProfessionResponse>() // live data for getting Login response

    var educationprofsubmitApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var educationprofsubmitResponseLiveData = MutableLiveData<EducationProfessionSubmitResponse>() // live data for getting Login response

    /**
     * Signup in API call
     */
    fun educationProfLiveData(partner_key: String?) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            educationprofApiCallStatus.value = ResourceStatus.loading()
            EducationProfessionRepository.educationprofInfoRepo(partner_key) { isSuccess, response ->

                if (isSuccess) {
                    educationprofApiCallStatus.value =
                        ResourceStatus.success("")
                    educationprofResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        educationprofApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        educationprofApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            educationprofApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }
    fun educationProfSubmitInfoLiveData(employmentStatus: String,employmentStatusDescription: String,presentEmployer: String,presentProfession: String,qualification: String,salaryRange: String,workExperience: String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            educationprofsubmitApiCallStatus.value = ResourceStatus.loading()
            EducationProfessionRepository.EducationProfSubmitRepo(employmentStatus,employmentStatusDescription,presentEmployer,presentProfession,qualification,salaryRange,workExperience) { isSuccess, response ->

                if (isSuccess) {
                    educationprofsubmitApiCallStatus.value =
                        ResourceStatus.success("")
                    educationprofsubmitResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        educationprofsubmitApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        educationprofsubmitApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            educationprofsubmitApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }

}