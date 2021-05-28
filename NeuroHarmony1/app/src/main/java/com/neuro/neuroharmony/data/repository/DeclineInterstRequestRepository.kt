package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DeclineInterestRequestRequest
import com.neuro.neuroharmony.data.model.DeclinInterestRequestResponse
import com.neuro.neuroharmony.data.model.DataDeclineInterestRequestRequest
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DeclineInterstRequestRepository {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun declineinterestrequestRepo( action_type: Int, sender_key: String,onResult:  (isSuccess: Boolean, response: DeclinInterestRequestResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.declineinterestrequestinterface(
                DeclineInterestRequestRequest(
                    data = DataDeclineInterestRequestRequest(
                        action_type, sender_key
                    )
                )
            )
                .enqueue(object :
                    Callback<DeclinInterestRequestResponse> {

                    override fun onResponse(call: Call<DeclinInterestRequestResponse>, response: Response<DeclinInterestRequestResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<DeclinInterestRequestResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}