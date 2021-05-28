package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataAcceptInterestRequest
import com.neuro.neuroharmony.data.model.AcceptInterestRequest
import com.neuro.neuroharmony.data.model.AcceptIntetrestResponse
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AcceptIterestRequestResponseRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun acceptinterstrequestRepo( action_type: Int, sender_key: String,onResult:  (isSuccess: Boolean, response: AcceptIntetrestResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.acceptinterestrequest(
                AcceptInterestRequest(
                    data = DataAcceptInterestRequest(
                        action_type, sender_key
                    )
                )
            )
                .enqueue(object :
                    Callback<AcceptIntetrestResponse> {

                    override fun onResponse(call: Call<AcceptIntetrestResponse>, response: Response<AcceptIntetrestResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<AcceptIntetrestResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}