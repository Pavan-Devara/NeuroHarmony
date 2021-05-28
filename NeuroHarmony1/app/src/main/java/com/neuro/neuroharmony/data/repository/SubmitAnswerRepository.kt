package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataSubmitAnswerRequest
import com.neuro.neuroharmony.data.model.SubmitAnswerRequest
import com.neuro.neuroharmony.data.model.SubmitAnswerResponse
import com.neuro.neuroharmony.data.remote.TestNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SubmitAnswerRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun submitanswer(end_time: String, group_submit: Boolean,
                     qid: Int,
                     selected_option:String,
                     session_id:String,
                     start_time: String,
                     user_key: String,
                     testType: Int,
                     group: Int,
                     onResult:  (isSuccess: Boolean, response: SubmitAnswerResponse?) -> Unit) {

        scope.launch {
            TestNeuroHarmonyApiService.instance.submitanswer(
                SubmitAnswerRequest(data = DataSubmitAnswerRequest(end_time,group_submit,qid,selected_option,session_id,start_time,user_key,testType,group),secured = true)
            )
                .enqueue(object :
                    Callback<SubmitAnswerResponse> {

                    override fun onResponse(call: Call<SubmitAnswerResponse>, response: Response<SubmitAnswerResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<SubmitAnswerResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}