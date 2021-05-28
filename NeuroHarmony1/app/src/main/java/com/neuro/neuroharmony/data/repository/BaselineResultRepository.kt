package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.BaselineResultResponse
import com.neuro.neuroharmony.data.remote.GetTestInstructionsApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BaselineResultRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun baselineresultrepo(partner_key: String?, onResult:  (isSuccess: Boolean, response: BaselineResultResponse?) -> Unit) {

        scope.launch {
            GetTestInstructionsApiService.instance.baselineresult(
                partner_key
                )
                .enqueue(object :
                    Callback<BaselineResultResponse> {

                    override fun onResponse(call: Call<BaselineResultResponse>, response: Response<BaselineResultResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<BaselineResultResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}