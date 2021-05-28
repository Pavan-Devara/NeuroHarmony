package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.UploadPhotoResponse
import com.neuro.neuroharmony.data.remote.UploadFilesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UploadProfilePhotoRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun profilePicUpload(file: MultipartBody.Part, description: RequestBody, onResult:  (isSuccess: Boolean, response: UploadPhotoResponse?) -> Unit){

        scope.launch {
            UploadFilesApiService.instance.upload(
                file,
                description
            )
                ?.enqueue(object :
                    Callback<UploadPhotoResponse> {

                    override fun onResponse(call: Call<UploadPhotoResponse>, response: Response<UploadPhotoResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<UploadPhotoResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}