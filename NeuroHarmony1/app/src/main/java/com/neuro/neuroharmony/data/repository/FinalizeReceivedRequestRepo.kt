package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.CompleteMatch.ResponseSentReceivedRequestFinalizeMatch
import com.neuro.neuroharmony.data.model.UsersSentRequestsResponse
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FinalizeReceivedRequestRepo {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun finalizereceivedrequestsrepo(onResult:  (isSuccess: Boolean, response: ResponseSentReceivedRequestFinalizeMatch?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.finalizereceivedusers().enqueue(object :
                Callback<ResponseSentReceivedRequestFinalizeMatch> {

                override fun onResponse(
                    call: Call<ResponseSentReceivedRequestFinalizeMatch>,
                    response: Response<ResponseSentReceivedRequestFinalizeMatch>
                ) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<ResponseSentReceivedRequestFinalizeMatch>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }
    }
}