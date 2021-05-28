package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.FAQs.FAQResponse
import com.neuro.neuroharmony.data.model.ReligiousInfoResponse
import com.neuro.neuroharmony.data.remote.ReligiousInfoApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReligiousInfoRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun religiousInfoRepo(partner_key: String?, onResult:  (isSuccess: Boolean, response: ReligiousInfoResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.religiousInfo(
                partner_key
            )
                .enqueue(object :
                    Callback<ReligiousInfoResponse> {

                    override fun onResponse(call: Call<ReligiousInfoResponse>, response: Response<ReligiousInfoResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ReligiousInfoResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }




    fun faqrepo(onResult:  (isSuccess: Boolean, response: FAQResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.frequentlyAskedQuestions(

            )
                .enqueue(object :
                    Callback<FAQResponse> {

                    override fun onResponse(call: Call<FAQResponse>, response: Response<FAQResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<FAQResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}