package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.AffiliateWorkflow.GetAffiliateDetailsResponse
import com.neuro.neuroharmony.data.model.UsersSentRequestsResponse
import com.neuro.neuroharmony.data.remote.AffiliateApiService
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetAffiliateDetailsRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun getaffiliaterepo(onResult:  (isSuccess: Boolean, response: GetAffiliateDetailsResponse?) -> Unit) {

        scope.launch {
            AffiliateApiService.instance.getaffliatedetails().enqueue(object :
                Callback<GetAffiliateDetailsResponse> {

                override fun onResponse(call: Call<GetAffiliateDetailsResponse>, response: Response<GetAffiliateDetailsResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<GetAffiliateDetailsResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}