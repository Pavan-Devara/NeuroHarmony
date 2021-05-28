package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.*
import com.neuro.neuroharmony.data.remote.TestNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CorebeliefFirstTwoSetRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun corebeliefapi(test_name:String,session_id: String, onResult:  (isSuccess: Boolean, response: CorebeliefFirstTwoSetsResponse?) -> Unit) {

        scope.launch {
            TestNeuroHarmonyApiService.instance.corebeliefapiinterface(TestsRequestBody(DataTestRequestBody(test_name, session_id)))
                .enqueue(object :
                    Callback<CorebeliefFirstTwoSetsResponse> {

                    override fun onResponse(call: Call<CorebeliefFirstTwoSetsResponse>, response: Response<CorebeliefFirstTwoSetsResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<CorebeliefFirstTwoSetsResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}