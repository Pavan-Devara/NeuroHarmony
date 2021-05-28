package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.NewMatchedUsersData.GetMatchedUsersResponse
import com.neuro.neuroharmony.data.remote.GetMatchedUsersApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetMatchedUsersRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getmatchedusersrepo(onResult:  (isSuccess: Boolean, response: GetMatchedUsersResponse?) -> Unit) {

            scope.launch {
            GetMatchedUsersApiService.instance.getmatchedusersapi(1,"Group_1", 0)
                .enqueue(object :
                    Callback<GetMatchedUsersResponse> {

                    override fun onResponse(call: Call<GetMatchedUsersResponse>, response: Response<GetMatchedUsersResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<GetMatchedUsersResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }

}