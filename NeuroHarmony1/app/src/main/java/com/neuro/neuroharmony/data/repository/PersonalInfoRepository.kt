package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoResponse
import com.neuro.neuroharmony.data.model.Social_profile.PersonalInfo.PersonalInfoResponse
import com.neuro.neuroharmony.data.remote.ReligiousInfoApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PersonalInfoRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun personalInfoRepo(partner_key: String?,onResult: (isSuccess: Boolean, response: PersonalInfoResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.personalInfo(partner_key)
                .enqueue(object :
                    Callback<PersonalInfoResponse> {

                    override fun onResponse(
                        call: Call<PersonalInfoResponse>,
                        response: Response<PersonalInfoResponse>
                    ) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<PersonalInfoResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }
    }
}