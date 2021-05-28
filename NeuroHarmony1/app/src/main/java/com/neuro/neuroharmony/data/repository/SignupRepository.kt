package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataSignupRequest
import com.neuro.neuroharmony.data.model.SignupRequest
import com.neuro.neuroharmony.data.model.SignupResponse
import com.neuro.neuroharmony.data.remote.NeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call

object SignupRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun signupUser( authType: Int,clientId: String,email: String,mobileNumber: String,password: String,country_code_picker_sign_up: Int,onResult:  (isSuccess: Boolean, response: SignupResponse?) -> Unit) {

        scope.launch {
            NeuroHarmonyApiService.instance.signupUser(
                SignupRequest(
                    data = DataSignupRequest(
                        authType,
                        clientId,
                        email,
                        mobileNumber,
                        password,
                        country_code_picker_sign_up
                    ),
                    secured = true
                )
            )
                .enqueue(object :
                Callback<SignupResponse> {

                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }

}