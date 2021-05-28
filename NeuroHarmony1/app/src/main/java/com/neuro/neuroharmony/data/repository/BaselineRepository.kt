package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataBaselineRequest
import com.neuro.neuroharmony.data.model.BaselineResponse
import com.neuro.neuroharmony.data.model.BaselineRequest
import com.neuro.neuroharmony.data.remote.NotificationApiService
import com.neuro.neuroharmony.data.remote.TestNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BaselineRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun baselineRepo( session_id: String, test_type: Int,onResult:  (isSuccess: Boolean, response: BaselineResponse?) -> Unit) {

        scope.launch {
            TestNeuroHarmonyApiService.instance.baselineatest(
                BaselineRequest(
                    data = DataBaselineRequest(
                        session_id,test_type
                    )
                )
            )
                .enqueue(object :
                    Callback<BaselineResponse> {

                    override fun onResponse(call: Call<BaselineResponse>, response: Response<BaselineResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<BaselineResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}