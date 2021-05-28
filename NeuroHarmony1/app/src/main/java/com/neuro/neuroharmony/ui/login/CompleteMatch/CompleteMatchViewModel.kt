package com.neuro.neuroharmony.ui.login.CompleteMatch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.CompleteMatch.FinalMatchResponse
import com.neuro.neuroharmony.data.model.ResetPasswordRequestOtpResponse
import com.neuro.neuroharmony.data.repository.CompleteMatchRepository
import com.neuro.neuroharmony.data.repository.ResetOtpRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class CompleteMatchViewModel: ViewModel() {

    var completematchAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var completematchResponseLiveData = MutableLiveData<FinalMatchResponse>()


    fun completematchvm(user_key: Any?,country_code: Any?,mobile_number:Any?) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            completematchAPICallStatus.value = ResourceStatus.loading()
            CompleteMatchRepository.completematchrepo(user_key,country_code,mobile_number) { isSuccess, response ->

                if (isSuccess) {
                    completematchAPICallStatus.value =
                        ResourceStatus.success("")
                    completematchResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        completematchAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        completematchAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("ResetOtpView","No netwrok")
            completematchAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}