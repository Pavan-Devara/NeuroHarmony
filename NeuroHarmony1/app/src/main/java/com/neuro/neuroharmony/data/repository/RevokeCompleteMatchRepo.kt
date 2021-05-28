package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.CompleteMatch.*
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RevokeCompleteMatchRepo {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun revokecompletematchrepo(reciver_key : Int,onResult:  (isSuccess: Boolean, response: RevokeCompleteMatchResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.revokecompletematchapiservice(
                RevokeCompleteMatchRequest(data = DataRevokeCompleteMatchRequest(reciver_key),secured = false)
            ).enqueue(object :
                Callback<RevokeCompleteMatchResponse> {

                override fun onResponse(call: Call<RevokeCompleteMatchResponse>, response: Response<RevokeCompleteMatchResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<RevokeCompleteMatchResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}