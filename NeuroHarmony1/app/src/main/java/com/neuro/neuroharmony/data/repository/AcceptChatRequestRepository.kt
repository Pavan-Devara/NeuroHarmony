package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataAcceptChatRequestRequest
import com.neuro.neuroharmony.data.model.AcceptChatRequestRequest
import com.neuro.neuroharmony.data.model.AcceptChatRequestResponse
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AcceptChatRequestRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun acceptchatrequest( action_type: Int, receiver_key: Int,onResult:  (isSuccess: Boolean, response: AcceptChatRequestResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.acceptchatrequestinterface(
                AcceptChatRequestRequest(
                    data = DataAcceptChatRequestRequest(
                        action_type,receiver_key
                    )
                )
            )
                .enqueue(object :
                    Callback<AcceptChatRequestResponse> {

                    override fun onResponse(call: Call<AcceptChatRequestResponse>, response: Response<AcceptChatRequestResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<AcceptChatRequestResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}