package com.neuro.neuroharmony.data.repository.ReligiousDataRepo

import com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.CasteData.CasteDataResponse
import com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.ReligionData.ReligionDataResponse
import com.neuro.neuroharmony.data.remote.ReligiousInfoApiService
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CasteDataInfoRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun castedataInfoRepo(id_array_religion: ArrayList<Int>, onResult:  (isSuccess: Boolean, response: CasteDataResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.casteDataInfo(
                id_array_religion)
                .enqueue(object :
                    Callback<CasteDataResponse> {

                    override fun onResponse(call: Call<CasteDataResponse>, response: Response<CasteDataResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<CasteDataResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}