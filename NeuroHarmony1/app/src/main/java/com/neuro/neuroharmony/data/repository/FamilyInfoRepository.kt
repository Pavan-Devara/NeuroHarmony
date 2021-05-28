package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.DataSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoResponse
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoSubmitResponse
import com.neuro.neuroharmony.data.remote.ReligiousInfoApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FamilyInfoRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun familyInfoRepo(partner_key: String?, onResult:  (isSuccess: Boolean, response: FamilyInfoResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.familyInfo(partner_key)
                .enqueue(object :
                    Callback<FamilyInfoResponse> {

                    override fun onResponse(call: Call<FamilyInfoResponse>, response: Response<FamilyInfoResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<FamilyInfoResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }

    fun familyInfoSubmitRepo(familyIncome: String,familyType: String,familyValue: String,fatherCompany: String,fatherDesignation: String,fatherStatus: String,motherCompany: String,motherDesignation: String,motherStatus: String,siblings: String, onResult:  (isSuccess: Boolean, response: FamilyInfoSubmitResponse?) -> Unit){
        scope.launch {
            ReligiousInfoApiService.instance.familyInfoSubmit(FamilyInfoSubmitRequest(
                DataSubmitRequest(familyIncome,familyType,familyValue,fatherCompany,fatherDesignation,fatherStatus,motherCompany,motherDesignation,motherStatus,siblings)
            ))
                .enqueue(object :
                    Callback<FamilyInfoSubmitResponse> {

                    override fun onResponse(call: Call<FamilyInfoSubmitResponse>, response: Response<FamilyInfoSubmitResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<FamilyInfoSubmitResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }
    }


}