package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DownloadPdfResponse
import com.neuro.neuroharmony.data.remote.DownloadPdfApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DownloadPdfRepository {


    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun downloadpdf(onResult:  (isSuccess: Boolean, response: DownloadPdfResponse?) -> Unit) {

        scope.launch {
            DownloadPdfApiService.instance.downloadpdf(

            )
                .enqueue(object :
                    Callback<DownloadPdfResponse> {

                    override fun onResponse(call: Call<DownloadPdfResponse>, response: Response<DownloadPdfResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<DownloadPdfResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}