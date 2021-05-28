package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.CoreBeliefFinalTwoQuestionsRequest
import com.neuro.neuroharmony.data.model.CoreBeliefFinalTwoQuestionsResponse
import com.neuro.neuroharmony.data.model.DataCoreBeliefFinalTwoQuestionsRequest
import com.neuro.neuroharmony.data.remote.TestNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CoreBeliefFinalTwoQuestionsRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun finalcorebeliefapi( session_id: String ,onResult:  (isSuccess: Boolean, response: CoreBeliefFinalTwoQuestionsResponse?) -> Unit) {

        scope.launch {
            TestNeuroHarmonyApiService.instance.cbfinalsubmitanswer(
                CoreBeliefFinalTwoQuestionsRequest(data = DataCoreBeliefFinalTwoQuestionsRequest(session_id),secured = false)
            )
                .enqueue(object :
                    Callback<CoreBeliefFinalTwoQuestionsResponse> {

                    override fun onResponse(call: Call<CoreBeliefFinalTwoQuestionsResponse>, response: Response<CoreBeliefFinalTwoQuestionsResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<CoreBeliefFinalTwoQuestionsResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}