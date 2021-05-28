package com.neuro.neuroharmony.data.repository.ReligiousDataRepo

import com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.ReligionData.ReligionDataResponse
import com.neuro.neuroharmony.data.remote.ReligiousInfoApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReligionDataInfoRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun religiondataInfoRepo(onResult:  (isSuccess: Boolean, response: ReligionDataResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.religionDataInfo()
                .enqueue(object :
                    Callback<ReligionDataResponse> {

                    override fun onResponse(call: Call<ReligionDataResponse>, response: Response<ReligionDataResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ReligionDataResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}