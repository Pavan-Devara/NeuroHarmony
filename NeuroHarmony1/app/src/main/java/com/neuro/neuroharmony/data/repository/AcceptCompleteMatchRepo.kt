package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.CompleteMatch.AcceptDeclineCompleteMatchRequest
import com.neuro.neuroharmony.data.model.CompleteMatch.AcceptDeclineCompleteMatchResponse
import com.neuro.neuroharmony.data.model.CompleteMatch.DataAcceptDeclineCompleteMatchRequest
import com.neuro.neuroharmony.data.model.PaymentPackages.CheckoutIdsRequest
import com.neuro.neuroharmony.data.model.PaymentPackages.CheckoutIdsResponse
import com.neuro.neuroharmony.data.model.PaymentPackages.DataCheckoutIdsRequest
import com.neuro.neuroharmony.data.remote.CheckoutIdsApiService
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AcceptCompleteMatchRepo {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun acceptcompletematchrepo(sender_key : Int,action_type:Int,onResult:  (isSuccess: Boolean, response: AcceptDeclineCompleteMatchResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.acceptcompletematchapiservice(
                AcceptDeclineCompleteMatchRequest(data = DataAcceptDeclineCompleteMatchRequest(sender_key,action_type),secured = false)
            ).enqueue(object :
                Callback<AcceptDeclineCompleteMatchResponse> {

                override fun onResponse(call: Call<AcceptDeclineCompleteMatchResponse>, response: Response<AcceptDeclineCompleteMatchResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<AcceptDeclineCompleteMatchResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}