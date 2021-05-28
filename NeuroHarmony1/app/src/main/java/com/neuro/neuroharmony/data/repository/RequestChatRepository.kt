package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.RequestChatRequest
import com.neuro.neuroharmony.data.model.RequestChatResponse
import com.neuro.neuroharmony.data.model.DataRequestChatRequest
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RequestChatRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun requestchatrequest( receiver_key: Int, onResult:  (isSuccess: Boolean, response: RequestChatResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.requestchatinterface(
                RequestChatRequest(
                    data = DataRequestChatRequest(
                        receiver_key
                    )
                )
            )
                .enqueue(object :
                    Callback<RequestChatResponse> {

                    override fun onResponse(call: Call<RequestChatResponse>, response: Response<RequestChatResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<RequestChatResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}