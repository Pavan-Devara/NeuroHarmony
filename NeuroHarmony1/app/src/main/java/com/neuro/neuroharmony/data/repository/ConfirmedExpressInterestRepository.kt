package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.ConfirmedExpressInterestResponse
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ConfirmedExpressInterestRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun confirmedexpressinterestrepo(onResult:  (isSuccess: Boolean, response: ConfirmedExpressInterestResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.confirmedexpressinterstinterface().enqueue(object :
                Callback<ConfirmedExpressInterestResponse> {

                override fun onResponse(call: Call<ConfirmedExpressInterestResponse>, response: Response<ConfirmedExpressInterestResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<ConfirmedExpressInterestResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}