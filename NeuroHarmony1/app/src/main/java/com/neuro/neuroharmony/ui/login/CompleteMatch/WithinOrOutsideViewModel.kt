package com.neuro.neuroharmony.ui.login.CompleteMatch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.AcceptIntetrestResponse
import com.neuro.neuroharmony.data.model.CompleteMatch.WithinOrOutsideNeuroHarmonyResponse
import com.neuro.neuroharmony.data.repository.AcceptIterestRequestResponseRepository
import com.neuro.neuroharmony.data.repository.WithinOrOutsideNeuroHarmonyRepo
import com.neuro.neuroharmony.ui.login.ResourceStatus

class WithinOrOutsideViewModel: ViewModel() {
    var withinoroutsideneuroharmonyAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var withinoroutsideneuroharmonyResponseLiveData = MutableLiveData<WithinOrOutsideNeuroHarmonyResponse>() // live data for getting Login response


    fun withinoroutsideneuroharmonyvm(do_not_match:Boolean,final_match:Boolean,out_side_partner:Boolean) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            withinoroutsideneuroharmonyAPICallStatus.value = ResourceStatus.loading()
            WithinOrOutsideNeuroHarmonyRepo.WithinOrOutsideNeuroHarmonyrepo(do_not_match, final_match,out_side_partner) { isSuccess, response ->

                if (isSuccess) {
                    withinoroutsideneuroharmonyAPICallStatus.value =
                        ResourceStatus.success("")
                    withinoroutsideneuroharmonyResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        withinoroutsideneuroharmonyAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        withinoroutsideneuroharmonyAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("LoginView","No netwrok")
            withinoroutsideneuroharmonyAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}