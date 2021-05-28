package com.neuro.neuroharmony.data.repository
import com.neuro.neuroharmony.data.model.UsersSentRequestsResponse

import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserSentRequestsRepository {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun userssentrequestsrepo(onResult:  (isSuccess: Boolean, response: UsersSentRequestsResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.usersentrequests().enqueue(object :
                Callback<UsersSentRequestsResponse> {

                override fun onResponse(call: Call<UsersSentRequestsResponse>, response: Response<UsersSentRequestsResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<UsersSentRequestsResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}