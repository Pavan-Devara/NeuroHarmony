package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.InitiateMatchRequest
import com.neuro.neuroharmony.data.model.InitiateMatchResponse
import com.neuro.neuroharmony.data.model.DataInitiateMatchRequest
import com.neuro.neuroharmony.data.remote.InitialMatchApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object InitiateMatchRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun initiateneuromatchrepo( user_key: Int ,onResult:  (isSuccess: Boolean, response: InitiateMatchResponse?) -> Unit) {

        scope.launch {
            InitialMatchApiService.instance.initiatematch(
                InitiateMatchRequest(data = DataInitiateMatchRequest(user_key))
            )
                .enqueue(object :
                    Callback<InitiateMatchResponse> {

                    override fun onResponse(call: Call<InitiateMatchResponse>, response: Response<InitiateMatchResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<InitiateMatchResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}