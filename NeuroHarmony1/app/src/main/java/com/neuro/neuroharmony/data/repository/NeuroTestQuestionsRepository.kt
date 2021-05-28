package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataTestRequestBody
import com.neuro.neuroharmony.data.model.NeuroTestQuestionsResponse
import com.neuro.neuroharmony.data.model.ResetTest.DataResetTestRequest
import com.neuro.neuroharmony.data.model.ResetTest.ResetTestRequest
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoSubmitResponse
import com.neuro.neuroharmony.data.model.TestsRequestBody
import com.neuro.neuroharmony.data.remote.TestNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NeuroTestQuestionsRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun neurotestquestions(test_name:String,session_id: String, onResult:  (isSuccess: Boolean, response: NeuroTestQuestionsResponse?) -> Unit) {

        scope.launch {
            TestNeuroHarmonyApiService.instance.neurotestquestions(
                TestsRequestBody(DataTestRequestBody(test_name, session_id))
            )
                .enqueue(object :
                    Callback<NeuroTestQuestionsResponse> {

                    override fun onResponse(call: Call<NeuroTestQuestionsResponse>, response: Response<NeuroTestQuestionsResponse>) {
                        if (response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<NeuroTestQuestionsResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }



    fun resettest(test_name:String,session_id: String, onResult:  (isSuccess: Boolean, response: LifestyleInfoSubmitResponse?) -> Unit) {

        scope.launch {
            TestNeuroHarmonyApiService.instance.resettest(
                ResetTestRequest(data = DataResetTestRequest(session_id, test_name), secured = true)
            )
                .enqueue(object :
                    Callback<LifestyleInfoSubmitResponse> {

                    override fun onResponse(call: Call<LifestyleInfoSubmitResponse>, response: Response<LifestyleInfoSubmitResponse>) {
                        if (response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<LifestyleInfoSubmitResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}