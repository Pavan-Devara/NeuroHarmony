package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.manualMatching.AcceptDeclineRevokeDelinkManualMatchingRequest
import com.neuro.neuroharmony.data.model.manualMatching.Data
import com.neuro.neuroharmony.data.model.manualMatching.AcceptDeclineRevokeDelinkManualMatchingResponse
import com.neuro.neuroharmony.data.remote.AcceptDeclineManualManualMatching
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AcceptDeclineRevokeDelinkManualMatchingRepository {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun acceptdeclinemanualmathcing( user_key: Int,onResult:  (isSuccess: Boolean, response: AcceptDeclineRevokeDelinkManualMatchingResponse?) -> Unit) {

        scope.launch {
            AcceptDeclineManualManualMatching.instance.AcceptDeclineManualManualMatching(
                AcceptDeclineRevokeDelinkManualMatchingRequest(
                    data = Data(
                        user_key
                    )
                )
            )
                .enqueue(object :
                    Callback<AcceptDeclineRevokeDelinkManualMatchingResponse> {

                    override fun onResponse(call: Call<AcceptDeclineRevokeDelinkManualMatchingResponse>, response: Response<AcceptDeclineRevokeDelinkManualMatchingResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<AcceptDeclineRevokeDelinkManualMatchingResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}