package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataResetPasswordRequest
import com.neuro.neuroharmony.data.model.ResetPasswordRequest
import com.neuro.neuroharmony.data.model.ResetPasswordResponse
import com.neuro.neuroharmony.data.remote.LoginNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ResetPasswordRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun resetpassword( mobileNumber:String,password: String,onResult:  (isSuccess: Boolean, response: ResetPasswordResponse?) -> Unit) {

        scope.launch {
            LoginNeuroHarmonyApiService.instance.resetpassword(
                ResetPasswordRequest(
                    data = DataResetPasswordRequest(
                        mobileNumber,password

                    ),
                    secured = false
                )
            )
                .enqueue(object :
                    Callback<ResetPasswordResponse> {

                    override fun onResponse(call: Call<ResetPasswordResponse>, response: Response<ResetPasswordResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}