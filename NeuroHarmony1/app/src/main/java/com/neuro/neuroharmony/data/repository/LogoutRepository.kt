package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.UserLogoutResponse
import com.neuro.neuroharmony.data.remote.LoginNeuroHarmonyApiService

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LogoutRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun logoutuser(onResult:  (isSuccess: Boolean, response: UserLogoutResponse?) -> Unit) {

        scope.launch {
            LoginNeuroHarmonyApiService.instance.logoutuser().enqueue(object :
                Callback<UserLogoutResponse> {

                override fun onResponse(call: Call<UserLogoutResponse>, response: Response<UserLogoutResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<UserLogoutResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}