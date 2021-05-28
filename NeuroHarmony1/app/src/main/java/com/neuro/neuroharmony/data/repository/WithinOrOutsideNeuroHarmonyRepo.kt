package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.BaselineRequest
import com.neuro.neuroharmony.data.model.BaselineResponse
import com.neuro.neuroharmony.data.model.CompleteMatch.DataWithinOrOutsideNeuroHarmonyRequest
import com.neuro.neuroharmony.data.model.CompleteMatch.WithinOrOutsideNeuroHarmonyRequest
import com.neuro.neuroharmony.data.model.CompleteMatch.WithinOrOutsideNeuroHarmonyResponse
import com.neuro.neuroharmony.data.model.DataBaselineRequest
import com.neuro.neuroharmony.data.remote.GetMatchedUsersApiService
import com.neuro.neuroharmony.data.remote.TestNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WithinOrOutsideNeuroHarmonyRepo {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun WithinOrOutsideNeuroHarmonyrepo( do_not_match:Boolean, final_match:Boolean,out_side_partner:Boolean,onResult:  (isSuccess: Boolean, response: WithinOrOutsideNeuroHarmonyResponse?) -> Unit) {

        scope.launch {
            GetMatchedUsersApiService.instance.WithinOrOutsideNeuroHarmonyApiService(
                WithinOrOutsideNeuroHarmonyRequest(
                    data = DataWithinOrOutsideNeuroHarmonyRequest(
                        do_not_match,final_match,out_side_partner
                    ),secured = false
                )
            )
                .enqueue(object :
                    Callback<WithinOrOutsideNeuroHarmonyResponse> {

                    override fun onResponse(call: Call<WithinOrOutsideNeuroHarmonyResponse>, response: Response<WithinOrOutsideNeuroHarmonyResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<WithinOrOutsideNeuroHarmonyResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}