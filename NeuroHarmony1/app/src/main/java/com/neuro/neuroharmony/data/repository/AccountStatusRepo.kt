package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.AccountStatusResponse
import com.neuro.neuroharmony.data.remote.LoginNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AccountStatusRepo {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun AccountStatusRepository(onResult:  (isSuccess: Boolean, response: AccountStatusResponse?) -> Unit) {

        scope.launch {
            LoginNeuroHarmonyApiService.instance.accountstatusapiservice(

            )
                .enqueue(object :
                    Callback<AccountStatusResponse> {

                    override fun onResponse(call: Call<AccountStatusResponse>, response: Response<AccountStatusResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<AccountStatusResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}