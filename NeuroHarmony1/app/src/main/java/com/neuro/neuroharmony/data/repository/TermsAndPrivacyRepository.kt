package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.GenericData.Data
import com.neuro.neuroharmony.data.model.GenericData.DeviceInfo
import com.neuro.neuroharmony.data.model.GenericData.GenericDataResponse
import com.neuro.neuroharmony.data.model.GenericData.GenericRequestWithMobileInfo
import com.neuro.neuroharmony.data.model.TermsAndPrivacyResponse
import com.neuro.neuroharmony.data.remote.FeatureListApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TermsAndPrivacyRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun gettermsrepo(onResult:  (isSuccess: Boolean, response: TermsAndPrivacyResponse?) -> Unit) {

        scope.launch {
            FeatureListApiService.instance.getterms(

            )
                .enqueue(object :
                    Callback<TermsAndPrivacyResponse> {

                    override fun onResponse(call: Call<TermsAndPrivacyResponse>, response: Response<TermsAndPrivacyResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<TermsAndPrivacyResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }


    fun genericdatarepo(baseBandVersion : String,cpu : String,isRootedOrJailBroken : String,kernelVersion : String,modelName : String,modelNumber : String,osType : Int,osVersion : String, ram : String,securityPatchLevel : String ,serialNumber : String,storageAvailable : String,storageTotal : String,vendorOsVersion : String ,onResult:  (isSuccess: Boolean, response: GenericDataResponse?) -> Unit) {

        scope.launch {
            FeatureListApiService.instance.genericdata(
                GenericRequestWithMobileInfo(Data(DeviceInfo(baseBandVersion,cpu,isRootedOrJailBroken,kernelVersion,modelName,modelNumber,osType,osVersion, ram,securityPatchLevel ,serialNumber,storageAvailable,storageTotal,vendorOsVersion)))
            )
                .enqueue(object :
                    Callback<GenericDataResponse> {

                    override fun onResponse(call: Call<GenericDataResponse>, response: Response<GenericDataResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<GenericDataResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }

}