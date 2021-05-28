package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.InstructionsResponse
import com.neuro.neuroharmony.data.remote.GetTestInstructionsApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object InstructionsRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun instructions(onResult:  (isSuccess: Boolean, response: InstructionsResponse?) -> Unit) {

        scope.launch {
            GetTestInstructionsApiService.instance.instructions().enqueue(object :
                Callback<InstructionsResponse> {

                override fun onResponse(call: Call<InstructionsResponse>, response: Response<InstructionsResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<InstructionsResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}