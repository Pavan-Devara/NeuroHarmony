package com.neuro.neuroharmony.ui.login.SocialProfile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.CasteData.CasteDataResponse
import com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.ReligionData.ReligionDataResponse
import com.neuro.neuroharmony.data.repository.ReligiousDataRepo.CasteDataInfoRepository
import com.neuro.neuroharmony.data.repository.ReligiousDataRepo.ReligionDataInfoRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class ReligionViewModel: ViewModel() {
    var ReligionInfoApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var ReligionInfoResponseLiveData = MutableLiveData<ReligionDataResponse>() // live data for getting Login response

    var CasteInfoApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var CasteInfoResponseLiveData = MutableLiveData<CasteDataResponse>() // live data for getting Login response

    fun ReligionInfoLiveData() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            ReligionInfoApiCallStatus.value = ResourceStatus.loading()
            ReligionDataInfoRepository.religiondataInfoRepo { isSuccess, response ->

                if (isSuccess) {
                    ReligionInfoApiCallStatus.value =
                        ResourceStatus.success("")
                    ReligionInfoResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        ReligionInfoApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        ReligionInfoApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            ReligionInfoApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }
    fun CasteInfoLiveData(idArrayReligion: ArrayList<Int>) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            CasteInfoApiCallStatus.value = ResourceStatus.loading()
            CasteDataInfoRepository.castedataInfoRepo(idArrayReligion) { isSuccess, response ->

                if (isSuccess) {
                    CasteInfoApiCallStatus.value =
                        ResourceStatus.success("")
                    CasteInfoResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        CasteInfoApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        CasteInfoApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            CasteInfoApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }

}