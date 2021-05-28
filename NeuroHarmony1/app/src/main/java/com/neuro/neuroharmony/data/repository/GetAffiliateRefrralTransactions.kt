package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.AffiliateWorkflow.GetAffiliateDetailsResponse
import com.neuro.neuroharmony.data.model.AffiliateWorkflow.GetAffiliateRefferalTransactions
import com.neuro.neuroharmony.data.remote.AffiliateApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetAffiliateRefrralTransactions {



        private val job = Job()
        private val scope = CoroutineScope(Dispatchers.Main + job)

        fun getaffiliatetransactionsrepo(onResult:  (isSuccess: Boolean, response: GetAffiliateRefferalTransactions?) -> Unit) {

            scope.launch {
                AffiliateApiService.instance.getaffliatetransactions().enqueue(object :
                    Callback<GetAffiliateRefferalTransactions> {

                    override fun onResponse(call: Call<GetAffiliateRefferalTransactions>, response: Response<GetAffiliateRefferalTransactions>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<GetAffiliateRefferalTransactions>, t: Throwable) {
                        onResult(false, null)
                    }

                })

            }

        }
}