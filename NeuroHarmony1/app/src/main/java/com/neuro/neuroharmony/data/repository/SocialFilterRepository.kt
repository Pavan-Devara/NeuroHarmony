package com.neuro.neuroharmony.data.repository
import com.neuro.neuroharmony.data.model.SocialFilter.*
import com.neuro.neuroharmony.data.remote.ReportsAPIService
import com.neuro.neuroharmony.data.remote.SocialFilterApiService
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SocialFilterRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun socialfilteruserchoicesrepo(onResult: (isSuccess: Boolean, response: SocialFilterResponse?) -> Unit) {

        scope.launch {
            SocialFilterApiService.instance.socialfilterchoices()
                .enqueue(object :
                    Callback<SocialFilterResponse> {

                    override fun onResponse(
                        call: Call<SocialFilterResponse>,
                        response: Response<SocialFilterResponse>
                    ) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<SocialFilterResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }
    }



    fun socialfiltersubmitchoicesrepo(caste: Any,
                                      max_age: Any?, max_height: Any?,
                                      min_age: Any?, min_height: Any?,
                                      profession: Any,
                                      religion: Any, socialFilterActive: Boolean,
                                      onResult: (isSuccess: Boolean, response: SocialFilterSumitResponse?) -> Unit) {

        scope.launch {
            SocialFilterApiService.instance.socialfiltersubmit(SocialFilterSubmitRequest(
                data = DataSocialFilterSubmitRequest(
                    caste, max_age, max_height,min_age,
                    min_height, profession, religion, socialFilterActive), secured = true))
                .enqueue(object :
                    Callback<SocialFilterSumitResponse> {

                    override fun onResponse(
                        call: Call<SocialFilterSumitResponse>,
                        response: Response<SocialFilterSumitResponse>
                    ) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<SocialFilterSumitResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }
    }
}