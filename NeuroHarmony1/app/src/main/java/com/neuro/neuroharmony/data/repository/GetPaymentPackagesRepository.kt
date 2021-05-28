package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.PaymentPackages.*
import com.neuro.neuroharmony.data.remote.GetPaymentPackagesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetPaymentPackagesRepository {



    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getpackagesrepo(onResult:  (isSuccess: Boolean, response: GetPaymentPackageDetails?) -> Unit) {

        scope.launch {
            GetPaymentPackagesApiService.instance.getpaymentpackages(

            )
                .enqueue(object :
                    Callback<GetPaymentPackageDetails> {

                    override fun onResponse(call: Call<GetPaymentPackageDetails>, response: Response<GetPaymentPackageDetails>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<GetPaymentPackageDetails>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }



    fun getuserpackagesrepo(onResult:  (isSuccess: Boolean, response: UserPurchasedPackagesResponse?) -> Unit) {

        scope.launch {
            GetPaymentPackagesApiService.instance.getuserpackages(

            )
                .enqueue(object :
                    Callback<UserPurchasedPackagesResponse> {

                    override fun onResponse(call: Call<UserPurchasedPackagesResponse>, response: Response<UserPurchasedPackagesResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<UserPurchasedPackagesResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }



    fun transfertokensrepo(mobile_number:String, neuro_tokens:Int, onResult:  (isSuccess: Boolean, response: TransferTokensResponse?) -> Unit) {

        scope.launch {
            GetPaymentPackagesApiService.instance.transfertokens(
                TransferTokensRequest(data = DataTransferTokensRequest(mobile_number = mobile_number, neuro_tokens = neuro_tokens), secured = true)
            )
                .enqueue(object :
                    Callback<TransferTokensResponse> {

                    override fun onResponse(call: Call<TransferTokensResponse>, response: Response<TransferTokensResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<TransferTokensResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}