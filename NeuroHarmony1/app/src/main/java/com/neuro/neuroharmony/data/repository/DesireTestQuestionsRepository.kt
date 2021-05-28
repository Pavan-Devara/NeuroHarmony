package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataTestRequestBody
import com.neuro.neuroharmony.data.model.DesireTestQuestionsResponse
import com.neuro.neuroharmony.data.model.TestsRequestBody
import com.neuro.neuroharmony.data.remote.TestNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DesireTestQuestionsRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun desiretestquestions(test_name:String,session_id: String, onResult:  (isSuccess: Boolean, response: DesireTestQuestionsResponse?) -> Unit) {

        scope.launch {
            TestNeuroHarmonyApiService.instance.desirequestions(
                TestsRequestBody(DataTestRequestBody(test_name, session_id))
            )
                .enqueue(object :
                    Callback<DesireTestQuestionsResponse> {

                    override fun onResponse(call: Call<DesireTestQuestionsResponse>, response: Response<DesireTestQuestionsResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<DesireTestQuestionsResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}