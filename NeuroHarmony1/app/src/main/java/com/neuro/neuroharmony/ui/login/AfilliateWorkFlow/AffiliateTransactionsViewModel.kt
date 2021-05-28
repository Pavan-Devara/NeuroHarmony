package com.neuro.neuroharmony.ui.login.AfilliateWorkFlow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.AffiliateWorkflow.GetAffiliateRefferalTransactions
import com.neuro.neuroharmony.data.repository.GetAffiliateRefrralTransactions
import com.neuro.neuroharmony.ui.login.ResourceStatus

class AffiliateTransactionsViewModel: ViewModel() {
    var getAffiliateTransactionsAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var getAffiliateTransactionsResponseLiveData = MutableLiveData<GetAffiliateRefferalTransactions>() // live data for getting Login response

    /**GetAffiliateDetailsResponse
     * Login in API call
     */
    fun getaffiliatetransactionsvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            getAffiliateTransactionsAPICallStatus.value = ResourceStatus.loading()
            GetAffiliateRefrralTransactions.getaffiliatetransactionsrepo() { isSuccess, response ->

                if (isSuccess) {
                    getAffiliateTransactionsAPICallStatus.value =
                        ResourceStatus.success("")
                    getAffiliateTransactionsResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        getAffiliateTransactionsAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        getAffiliateTransactionsAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            getAffiliateTransactionsAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}