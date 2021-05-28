package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.DataSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfessionResponse
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfessionSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfessionSubmitResponse
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoResponse
import com.neuro.neuroharmony.data.remote.ReligiousInfoApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object EducationProfessionRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun educationprofInfoRepo(partner_key: String?, onResult:  (isSuccess: Boolean, response: EducationProfessionResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.educationprofession(partner_key)
                .enqueue(object :
                    Callback<EducationProfessionResponse> {

                    override fun onResponse(call: Call<EducationProfessionResponse>, response: Response<EducationProfessionResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<EducationProfessionResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }

    fun EducationProfSubmitRepo(employmentStatus: String,employmentStatusDescription: String, presentEmployer: String,presentProfession: String,qualification: String,salaryRange: String,workExperience: String,onResult:  (isSuccess: Boolean, response: EducationProfessionSubmitResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.EducationProfSubmit(EducationProfessionSubmitRequest(
                DataSubmitRequest(employmentStatus,employmentStatusDescription, presentEmployer,presentProfession,qualification,salaryRange,workExperience)
            ))
                .enqueue(object :
                    Callback<EducationProfessionSubmitResponse> {

                    override fun onResponse(call: Call<EducationProfessionSubmitResponse>, response: Response<EducationProfessionSubmitResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<EducationProfessionSubmitResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}