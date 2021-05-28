package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports.NeuroReportResponse
import com.neuro.neuroharmony.data.remote.ReportsAPIService
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReportsRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getneuroreportsrepo(onResult:  (isSuccess: Boolean, response: NeuroReportResponse?) -> Unit) {

        scope.launch {
            ReportsAPIService.instance.getneuroreports(PrefsHelper().getPref("report_type"),
                PrefsHelper().getPref("sessionId_reports"))
                .enqueue(object :
                    Callback<NeuroReportResponse> {

                    override fun onResponse(call: Call<NeuroReportResponse>, response: Response<NeuroReportResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<NeuroReportResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}