package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.manualMatching.ReceivedSentConfirmedManualMatchingResponse
import com.neuro.neuroharmony.data.remote.ManualMatchConfirmedAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ManualMatchConfirmedRepository {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun getreceivedsentconfirmedmanualmatchingrepo(onResult:  (isSuccess: Boolean, response: ReceivedSentConfirmedManualMatchingResponse?) -> Unit) {

        scope.launch {
            ManualMatchConfirmedAPIService.instance.getreceivedsentconfirmedmanualmatching().enqueue(object :
                Callback<ReceivedSentConfirmedManualMatchingResponse> {

                override fun onResponse(call: Call<ReceivedSentConfirmedManualMatchingResponse>, response: Response<ReceivedSentConfirmedManualMatchingResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<ReceivedSentConfirmedManualMatchingResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}