package com.neuro.neuroharmony.ui.login.BlockList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.BlockingList.BlockingListResponse
import com.neuro.neuroharmony.data.model.BlockingList.BlockingSubmitResponse
import com.neuro.neuroharmony.data.repository.BlockingListRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class BlockingListViewModel : ViewModel() {
    var blockinglistApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var blockinglistResponseLiveData = MutableLiveData<BlockingListResponse>() // live data for getting Login response

    var blockinglistsubmitApiCallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var blockinglistsubmitResponseLiveData = MutableLiveData<BlockingSubmitResponse>() // live data for getting Login response

    fun blockinglistLiveData() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            blockinglistApiCallStatus.value = ResourceStatus.loading()
            BlockingListRepository.blockListRepo { isSuccess, response ->

                if (isSuccess) {
                    blockinglistApiCallStatus.value =
                        ResourceStatus.success("")
                    blockinglistResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        blockinglistApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        blockinglistApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            blockinglistApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }

    fun BlockingListSubmitLiveData(user_key: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            blockinglistsubmitApiCallStatus.value = ResourceStatus.loading()
            BlockingListRepository.BlockingListSubmitRepo(user_key) { isSuccess, response ->

                if (isSuccess) {
                    blockinglistsubmitApiCallStatus.value =
                        ResourceStatus.success("")
                    blockinglistsubmitResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        blockinglistsubmitApiCallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        blockinglistsubmitApiCallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            blockinglistsubmitApiCallStatus.value = ResourceStatus.nonetwork()
        }

    }

}