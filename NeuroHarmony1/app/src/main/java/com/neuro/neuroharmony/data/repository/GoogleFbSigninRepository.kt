package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.*
import com.neuro.neuroharmony.data.remote.GoogleFbSignIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GoogleFbSigninRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun googlefbsigninrepo(auth_type: Int, client_id: String, email: String,onResult:  (isSuccess: Boolean, response: GoogleFbSignInRespone?) -> Unit){

        scope.launch {
            GoogleFbSignIn.instance.googlefbsignin(
                GoogleFbSigninRequest(
                    data = DataGoogleFbSigninRequest(
                        auth_type,
                        client_id,
                        email
                   ),
                    secured = true
                )
            )
                .enqueue(object :
                    Callback<GoogleFbSignInRespone> {

                    override fun onResponse(call: Call<GoogleFbSignInRespone>, response: Response<GoogleFbSignInRespone>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<GoogleFbSignInRespone>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}