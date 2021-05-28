package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.PaymentPackages.ErrorCodeAndDescriptionRequest
import com.neuro.neuroharmony.data.model.PaymentPackages.ErrorCodeAndErrorDescriptionResponse
import com.neuro.neuroharmony.data.model.PaymentPackages.DataErrorCodeAndDescriptionRequest
import com.neuro.neuroharmony.data.remote.CheckoutIdsApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ErrorCodeAndDescriptionRepo {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun logerrorpaymnetrepo(error_code: Any,err_response : String,gateway_order_id:String,onResult:  (isSuccess: Boolean, response: ErrorCodeAndErrorDescriptionResponse?) -> Unit) {

        scope.launch {
            CheckoutIdsApiService.instance.logerrorpayment(ErrorCodeAndDescriptionRequest(data = DataErrorCodeAndDescriptionRequest(error_code,err_response,gateway_order_id),secured = false)).enqueue(object :
                Callback<ErrorCodeAndErrorDescriptionResponse> {

                override fun onResponse(call: Call<ErrorCodeAndErrorDescriptionResponse>, response: Response<ErrorCodeAndErrorDescriptionResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<ErrorCodeAndErrorDescriptionResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}