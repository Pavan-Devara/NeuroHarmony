package com.neuro.neuroharmony.ui.login.SocialPreferences

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.SocialFilter.SocialFilterResponse
import com.neuro.neuroharmony.data.model.SocialFilter.SocialFilterSumitResponse
import com.neuro.neuroharmony.data.repository.SocialFilterRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class SocialFiltersViewModel: ViewModel() {

    var socialFilterAPICallStatus = MutableLiveData<ResourceStatus>()
    var socialFilterResponseLiveData = MutableLiveData<SocialFilterResponse>()

    var socialFilterSubmitAPICallStatus = MutableLiveData<ResourceStatus>()
    var socialFilterSubmitResponseLiveData = MutableLiveData<SocialFilterSumitResponse>()
    /**
     *
     */
    fun socialfiltervm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            socialFilterAPICallStatus.value = ResourceStatus.loading()
            SocialFilterRepository.socialfilteruserchoicesrepo { isSuccess, response ->

                if (isSuccess) {
                    socialFilterAPICallStatus.value =
                        ResourceStatus.success("")
                    socialFilterResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        socialFilterAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        socialFilterAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            socialFilterAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }


    fun socialfiltersubmitvm(
        caste: Any,
        max_age: Any?, max_height: Any?,
        min_age: Any?, min_height: Any?,
        profession: Any,
        religion: Any,
        socialFilterActive: Boolean
    ) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            socialFilterSubmitAPICallStatus.value = ResourceStatus.loading()
            SocialFilterRepository.socialfiltersubmitchoicesrepo(caste, max_age, max_height,min_age,
                min_height, profession, religion, socialFilterActive) { isSuccess, response ->

                if (isSuccess) {
                    socialFilterSubmitAPICallStatus.value =
                        ResourceStatus.success("")
                    socialFilterSubmitResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        socialFilterSubmitAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        socialFilterSubmitAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            socialFilterSubmitAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}