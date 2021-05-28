package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataSuspendChatRequest
import com.neuro.neuroharmony.data.model.SuspendChatResponse
import com.neuro.neuroharmony.data.model.SuspendChatRequest
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object StartChatRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun startchatrepo( user_key: String, action_type: Int,onResult:  (isSuccess: Boolean, response: SuspendChatResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.startchatinterface(
                SuspendChatRequest(
                    data = DataSuspendChatRequest(
                        user_key,action_type
                    )
                )
            )
                .enqueue(object :
                    Callback<SuspendChatResponse> {

                    override fun onResponse(call: Call<SuspendChatResponse>, response: Response<SuspendChatResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<SuspendChatResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}