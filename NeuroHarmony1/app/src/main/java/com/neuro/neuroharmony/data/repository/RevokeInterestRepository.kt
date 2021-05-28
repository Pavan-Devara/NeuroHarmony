package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataRevokeInterestRequest
import com.neuro.neuroharmony.data.model.RevokeInterestRequest
import com.neuro.neuroharmony.data.model.RevokeInterestResponse
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RevokeInterestRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun revokeinterest( receiver_key: Int, action_type: Int,onResult:  (isSuccess: Boolean, response: RevokeInterestResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.revokeinterestinterface(
                RevokeInterestRequest(
                    data = DataRevokeInterestRequest(
                        receiver_key,action_type
                    )
                )
            )
                .enqueue(object :
                    Callback<RevokeInterestResponse> {

                    override fun onResponse(call: Call<RevokeInterestResponse>, response: Response<RevokeInterestResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<RevokeInterestResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}