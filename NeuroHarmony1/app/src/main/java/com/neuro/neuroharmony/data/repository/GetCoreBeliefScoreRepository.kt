package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.GetCoreBeliefScoreResponse
import com.neuro.neuroharmony.data.remote.GetScoreApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetCoreBeliefScoreRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getcorebelirfscoree(onResult:  (isSuccess: Boolean, response: GetCoreBeliefScoreResponse?) -> Unit) {

        scope.launch {
            GetScoreApiService.instance.getcorebeliefscore(

            )
                .enqueue(object :
                    Callback<GetCoreBeliefScoreResponse> {

                    override fun onResponse(call: Call<GetCoreBeliefScoreResponse>, response: Response<GetCoreBeliefScoreResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<GetCoreBeliefScoreResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}