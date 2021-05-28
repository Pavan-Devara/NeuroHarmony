package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.BaselineRequest
import com.neuro.neuroharmony.data.model.BaselineResponse
import com.neuro.neuroharmony.data.model.CompleteMatch.DataFinalMatchRequest
import com.neuro.neuroharmony.data.model.CompleteMatch.FinalMatchRequest
import com.neuro.neuroharmony.data.model.CompleteMatch.FinalMatchResponse
import com.neuro.neuroharmony.data.model.DataBaselineRequest
import com.neuro.neuroharmony.data.remote.NotificationApiService
import com.neuro.neuroharmony.data.remote.TestNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CompleteMatchRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun completematchrepo( user_key: Any?, country_code: Any?,mobile_number: Any?,onResult:  (isSuccess: Boolean, response: FinalMatchResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.completematchapiservice(
                FinalMatchRequest(
                    data = DataFinalMatchRequest(
                        user_key,country_code,mobile_number
                    ),secured = false
                )
            )
                .enqueue(object :
                    Callback<FinalMatchResponse> {

                    override fun onResponse(call: Call<FinalMatchResponse>, response: Response<FinalMatchResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<FinalMatchResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}