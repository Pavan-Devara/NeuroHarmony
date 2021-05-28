package com.neuro.neuroharmony.data.repository


import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.DataSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifeStyleInfoSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoResponse
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoSubmitResponse
import com.neuro.neuroharmony.data.remote.ReligiousInfoApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LifestyleInfoRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun lifestyleInfoRepo(partner_key: String?, onResult:  (isSuccess: Boolean, response: LifestyleInfoResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.lifestyleInfo(partner_key)
                .enqueue(object :
                    Callback<LifestyleInfoResponse> {

                    override fun onResponse(call: Call<LifestyleInfoResponse>, response: Response<LifestyleInfoResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<LifestyleInfoResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }

    fun lifestyleInfoSubmitRepo(alcohol: String, dietary: String,
                                pet: String, post_working: String, smoke: String, sunglasses: String,
                                onResult:  (isSuccess: Boolean, response: LifestyleInfoSubmitResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.lifestyleSubmitInfo(
                LifeStyleInfoSubmitRequest(DataSubmitRequest(
                    alcohol, dietary, pet, post_working, smoke, sunglasses))
            )
                .enqueue(object :
                    Callback<LifestyleInfoSubmitResponse> {

                    override fun onResponse(
                        call: Call<LifestyleInfoSubmitResponse>,
                        response: Response<LifestyleInfoSubmitResponse>
                    ) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<LifestyleInfoSubmitResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }
    }

}