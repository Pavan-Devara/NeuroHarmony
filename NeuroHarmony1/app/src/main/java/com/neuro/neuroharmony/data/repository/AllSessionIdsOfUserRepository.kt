package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.AllSessionOfUserResponse
import com.neuro.neuroharmony.data.remote.ActiveSessionsOfUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AllSessionIdsOfUserRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun sessionIdsrepo(onResult:  (isSuccess: Boolean, response: AllSessionOfUserResponse?) -> Unit) {
        scope.launch {
            ActiveSessionsOfUser.instance.getActiveSessions()
                .enqueue(object :
                    Callback<AllSessionOfUserResponse> {

                    override fun onResponse(call: Call<AllSessionOfUserResponse>, response: Response<AllSessionOfUserResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<AllSessionOfUserResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }
    }
}