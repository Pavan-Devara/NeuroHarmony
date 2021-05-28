package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataManualMatchAddPartnerReques
import com.neuro.neuroharmony.data.model.ManualMatchAddPartnerRequest
import com.neuro.neuroharmony.data.model.AddPartnerResponse
import com.neuro.neuroharmony.data.remote.NotificationApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AddPartnerRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun addpartnerrepo( mobile_number: String,country_code_picker_manual_match: Int,onResult:  (isSuccess: Boolean, response: AddPartnerResponse?) -> Unit) {

        scope.launch {
            NotificationApiService.instance.addpartnerinterface(
                ManualMatchAddPartnerRequest(
                    data = DataManualMatchAddPartnerReques(
                        mobile_number,
                        country_code_picker_manual_match
                    )
                )
            )
                .enqueue(object :
                    Callback<AddPartnerResponse> {

                    override fun onResponse(call: Call<AddPartnerResponse>, response: Response<AddPartnerResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<AddPartnerResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}