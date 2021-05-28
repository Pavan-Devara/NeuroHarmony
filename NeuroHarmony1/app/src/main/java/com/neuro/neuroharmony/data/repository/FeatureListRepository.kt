package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.Feedback.FeaturesResponse
import com.neuro.neuroharmony.data.remote.FeatureListApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FeatureListRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getfeaturelistrepo(onResult:  (isSuccess: Boolean, response: FeaturesResponse?) -> Unit) {

        scope.launch {
            FeatureListApiService.instance.getfeaturelist(

            )
                .enqueue(object :
                    Callback<FeaturesResponse> {

                    override fun onResponse(call: Call<FeaturesResponse>, response: Response<FeaturesResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<FeaturesResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}