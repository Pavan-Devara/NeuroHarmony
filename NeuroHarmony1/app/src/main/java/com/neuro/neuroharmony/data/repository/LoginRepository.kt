package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataLoginRequest
import com.neuro.neuroharmony.data.model.LoginRequest
import com.neuro.neuroharmony.data.model.LoginResponse
import com.neuro.neuroharmony.data.remote.LoginNeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun loginUser( authType: Int,clientId: String,password: String,username: String,country_code_picker_login: Int,onResult:  (isSuccess: Boolean, response: LoginResponse?) -> Unit) {

        scope.launch {
            LoginNeuroHarmonyApiService.instance.loginUser(
                LoginRequest(
                    data = DataLoginRequest(
                        authType,
                        clientId,
                        password,
                        username,
                        country_code_picker_login
                    ),
                    secured = true
                )
            )
                .enqueue(object :
                    Callback<LoginResponse> {

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}