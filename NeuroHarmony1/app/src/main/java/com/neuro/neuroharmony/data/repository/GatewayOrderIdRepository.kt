package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.PaymentPackages.DataGatewayPackageIdRequest
import com.neuro.neuroharmony.data.model.PaymentPackages.GatewayPackageIdRequest
import com.neuro.neuroharmony.data.model.PaymentPackages.GatewayOrderIdResponse
import com.neuro.neuroharmony.data.remote.GetPaymentPackagesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GatewayOrderIdRepository {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun gatewayorderid(package_id : String,onResult:  (isSuccess: Boolean, response: GatewayOrderIdResponse?) -> Unit) {

        scope.launch {
            GetPaymentPackagesApiService.instance.getgatewayid(GatewayPackageIdRequest(data = DataGatewayPackageIdRequest(package_id))).enqueue(object :
                Callback<GatewayOrderIdResponse> {

                override fun onResponse(call: Call<GatewayOrderIdResponse>, response: Response<GatewayOrderIdResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<GatewayOrderIdResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}