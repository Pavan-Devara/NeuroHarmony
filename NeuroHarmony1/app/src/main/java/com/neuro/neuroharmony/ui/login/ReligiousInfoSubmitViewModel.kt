package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.ReligiousInfoSubmitResponse
import com.neuro.neuroharmony.data.repository.ReligiousInfoSubmitRepository
import okhttp3.MultipartBody

class ReligiousInfoSubmitViewModel: ViewModel() {

    var religiousInfoSubmitApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing religiou Info Submit call status
    var religiousInfoSubmitResponseLiveData = MutableLiveData<ReligiousInfoSubmitResponse>() // live data for getting religious Info Submit response

    fun religiosInfoSubmitLiveData(
        file: MultipartBody.Part?,
        religion: Int,
        caste: Int,
        deeply_religious: Int,
        worship_place_visit: Int,
        user_key: Int,
        religion_name: String,
        caste_name: Any?,
        sub_caste_text: Any?,
        worship_place_visit_text: Any?,
        deeply_religious_text: Any?
    ) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            religiousInfoSubmitApiCallStatus.value = ResourceStatus.loading()
            ReligiousInfoSubmitRepository.religiousInfoUpload(file = file, religion = religion,  caste = caste, deeply_religious = deeply_religious, worship_place_visit = worship_place_visit, user_key = user_key, religion_name =  religion_name,caste_name =  caste_name,sub_caste_text =  sub_caste_text,worship_place_visit_text = worship_place_visit_text,deeply_religious_text = deeply_religious_text) { isSuccess, response ->

                if (isSuccess) {
                    religiousInfoSubmitApiCallStatus.value =
                        ResourceStatus.success("")
                    religiousInfoSubmitResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        religiousInfoSubmitApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        religiousInfoSubmitApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            religiousInfoSubmitApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }

}