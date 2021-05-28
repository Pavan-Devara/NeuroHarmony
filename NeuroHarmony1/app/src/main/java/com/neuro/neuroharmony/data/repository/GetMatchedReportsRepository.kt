package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.GetMatchedReportsOfUserResponse
import com.neuro.neuroharmony.data.remote.GetMatchReportsInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetMatchedReportsRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getmatchedreportsrepo(onResult:  (isSuccess: Boolean, response: GetMatchedReportsOfUserResponse?) -> Unit) {

        scope.launch {
            GetMatchReportsInterface.instance.getmatchedreports(

            )
                .enqueue(object :
                    Callback<GetMatchedReportsOfUserResponse> {

                    override fun onResponse(call: Call<GetMatchedReportsOfUserResponse>, response: Response<GetMatchedReportsOfUserResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<GetMatchedReportsOfUserResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}