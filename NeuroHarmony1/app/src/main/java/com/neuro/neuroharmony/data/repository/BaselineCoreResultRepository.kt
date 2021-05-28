package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.BaselineCoreResult
import com.neuro.neuroharmony.data.remote.GetCoreResultBaseline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BaselineCoreResultRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun baselineresultcorerepo(partner_key: String?, onResult:  (isSuccess: Boolean, response: BaselineCoreResult?) -> Unit) {

        scope.launch {
            GetCoreResultBaseline.instance.baselineresultcore(
                partner_key
            )
                .enqueue(object :
                    Callback<BaselineCoreResult> {

                    override fun onResponse(call: Call<BaselineCoreResult>, response: Response<BaselineCoreResult>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<BaselineCoreResult>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}