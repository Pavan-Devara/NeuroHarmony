package com.neuro.neuroharmony.data.repository


import com.neuro.neuroharmony.data.model.DataUserTypeRequest
import com.neuro.neuroharmony.data.model.UserTypeRequest
import com.neuro.neuroharmony.data.model.UserTypeResponse
import com.neuro.neuroharmony.data.remote.NeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call

object UsertypeRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun usertypeUser(userType:Int,referral_code: String,onResult:  (isSuccess: Boolean, response: UserTypeResponse?) -> Unit) {

        scope.launch {
            NeuroHarmonyApiService.instance.usertypeuser(UserTypeRequest(data = DataUserTypeRequest(userType,referral_code),secured = equals(Boolean))).enqueue(object :
                    Callback<UserTypeResponse> {

                    override fun onResponse(call: Call<UserTypeResponse>, response: Response<UserTypeResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<UserTypeResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}