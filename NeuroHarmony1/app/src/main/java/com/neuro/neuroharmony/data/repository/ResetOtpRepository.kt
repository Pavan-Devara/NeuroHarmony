package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataResetPasswordRequestOtpRequest
import com.neuro.neuroharmony.data.model.ResetPasswordRequestOtpRequest
import com.neuro.neuroharmony.data.model.ResetPasswordRequestOtpResponse
import com.neuro.neuroharmony.data.remote.LoginNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ResetOtpRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun forgototprequest( mobileNumber: String,country_code_picker_forgot_pwd: Int,onResult:  (isSuccess: Boolean, response: ResetPasswordRequestOtpResponse?) -> Unit) {

        scope.launch {
            LoginNeuroHarmonyApiService.instance.forgototprequest(
                ResetPasswordRequestOtpRequest(
                    data = DataResetPasswordRequestOtpRequest(
                        mobileNumber,
                        country_code_picker_forgot_pwd

                    ),
                    secured = false
                )
            )
                .enqueue(object :
                    Callback<ResetPasswordRequestOtpResponse> {

                    override fun onResponse(call: Call<ResetPasswordRequestOtpResponse>, response: Response<ResetPasswordRequestOtpResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ResetPasswordRequestOtpResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}