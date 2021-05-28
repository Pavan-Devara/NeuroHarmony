package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.GetReceivedChatRequestResponse
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object GetReceivedChatRequestsRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun receivedchatrequestsrepo(onResult:  (isSuccess: Boolean, response: GetReceivedChatRequestResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.getreceivedchatrequestsinterface().enqueue(object :
                Callback<GetReceivedChatRequestResponse> {

                override fun onResponse(call: Call<GetReceivedChatRequestResponse>, response: Response<GetReceivedChatRequestResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<GetReceivedChatRequestResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }


}