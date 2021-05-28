package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.ReligiousInfoSubmitResponse
import com.neuro.neuroharmony.data.remote.FormDataUploadApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.R.attr.data
import okhttp3.MediaType
import okhttp3.RequestBody


object ReligiousInfoSubmitRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun religiousInfoUpload(file: MultipartBody.Part?, religion: Int, caste: Int,  deeply_religious: Int, worship_place_visit: Int, user_key: Int, religion_name: String, caste_name:Any?, sub_caste_text: Any?,worship_place_visit_text:Any?,deeply_religious_text:Any?,  onResult:  (isSuccess: Boolean, response: ReligiousInfoSubmitResponse?) -> Unit){
        val requestReligionName = RequestBody.create(
            MediaType.parse("multipart/form-data"), religion_name
        )
        val requestCasteName = RequestBody.create(
            MediaType.parse("multipart/form-data"), caste_name.toString()
        )
        val requestSubCasteName = RequestBody.create(
            MediaType.parse("multipart/form-data"), sub_caste_text.toString()
        )
        val requestWorshipPlaceVisitText = RequestBody.create(
            MediaType.parse("multipart/form-data"), worship_place_visit_text.toString()
        )
        val requestDeeplyReligiousText = RequestBody.create(
            MediaType.parse("multipart/form-data"), deeply_religious_text.toString()
        )
        ReligiousInfoSubmitRepository.scope.launch {
            FormDataUploadApiService.instance.uploadReligiousInfo(
                file, religion = religion, caste = caste,  deeply_religious = deeply_religious, worship_place_visit = worship_place_visit, user_key = user_key, religion_name = requestReligionName, caste_name = requestCasteName,sub_caste_text =  requestSubCasteName,worship_place_visit_text = requestWorshipPlaceVisitText,deeply_religious_text =requestDeeplyReligiousText
            )
                ?.enqueue(object :
                    Callback<ReligiousInfoSubmitResponse> {

                    override fun onResponse(call: Call<ReligiousInfoSubmitResponse>, response: Response<ReligiousInfoSubmitResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ReligiousInfoSubmitResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}
