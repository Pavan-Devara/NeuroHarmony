package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.GetNeuroDesireScoreResponse
import com.neuro.neuroharmony.data.remote.GetScoreApiService

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ScoreNeuroDesireRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getscoreneurodesire(onResult:  (isSuccess: Boolean, response: GetNeuroDesireScoreResponse?) -> Unit) {

        scope.launch {
            GetScoreApiService.instance.getneurodesirescore(

            )
                .enqueue(object :
                    Callback<GetNeuroDesireScoreResponse> {

                    override fun onResponse(call: Call<GetNeuroDesireScoreResponse>, response: Response<GetNeuroDesireScoreResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<GetNeuroDesireScoreResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}