package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.ListofActiveAvailableUsersChatResponse
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AvailableChatUsersListRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun availableuserslistchat(onResult:  (isSuccess: Boolean, response: ListofActiveAvailableUsersChatResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.availablechatlistinterface().enqueue(object :
                Callback<ListofActiveAvailableUsersChatResponse> {

                override fun onResponse(call: Call<ListofActiveAvailableUsersChatResponse>, response: Response<ListofActiveAvailableUsersChatResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<ListofActiveAvailableUsersChatResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })

        }

    }
}