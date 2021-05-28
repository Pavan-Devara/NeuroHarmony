package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.PaymentPackages.CheckoutIdsRequest
import com.neuro.neuroharmony.data.model.PaymentPackages.CheckoutIdsResponse
import com.neuro.neuroharmony.data.model.PaymentPackages.DataCheckoutIdsRequest
import com.neuro.neuroharmony.data.remote.CheckoutIdsApiService
import com.neuro.neuroharmony.data.remote.GetPaymentPackagesApiService
import com.neuro.neuroharmony.data.remote.NeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SubmitSelectedPackageRepository {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun checkoutidsrepo(gateway_order_id : String,gateway_payment_id:String,gateway_signature:String,onResult:  (isSuccess: Boolean, response: CheckoutIdsResponse?) -> Unit) {

        scope.launch {
            CheckoutIdsApiService.instance.checkoutids(CheckoutIdsRequest(data = DataCheckoutIdsRequest(gateway_order_id,gateway_payment_id,gateway_signature),secured = false)).enqueue(object :
                Callback<CheckoutIdsResponse> {

                override fun onResponse(call: Call<CheckoutIdsResponse>, response: Response<CheckoutIdsResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<CheckoutIdsResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}