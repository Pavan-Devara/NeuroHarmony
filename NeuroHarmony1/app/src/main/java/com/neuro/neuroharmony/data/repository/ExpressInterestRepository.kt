package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataExpressInterestRequest
import com.neuro.neuroharmony.data.model.ExpressInterestRequest
import com.neuro.neuroharmony.data.model.ExpressInterestResponse
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ExpressInterestRepository {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun expressinterest( receiver_key: String,onResult:  (isSuccess: Boolean, response: ExpressInterestResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.expressinterestinterface(
                ExpressInterestRequest(
                    data = DataExpressInterestRequest(
                        receiver_key
                    )
                )
            )
                .enqueue(object :
                    Callback<ExpressInterestResponse> {

                    override fun onResponse(call: Call<ExpressInterestResponse>, response: Response<ExpressInterestResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ExpressInterestResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}