package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.ShowInitailNeuroMatchResponse
import com.neuro.neuroharmony.data.remote.GetInitailNeuroMatchApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetInitialNeuroMatchRepository {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getneuromatchrepo(onResult:  (isSuccess: Boolean, response: ShowInitailNeuroMatchResponse?) -> Unit) {

        scope.launch {
            GetInitailNeuroMatchApiService.instance.getinitailmatchresults(

            )
                .enqueue(object :
                    Callback<ShowInitailNeuroMatchResponse> {

                    override fun onResponse(call: Call<ShowInitailNeuroMatchResponse>, response: Response<ShowInitailNeuroMatchResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ShowInitailNeuroMatchResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}