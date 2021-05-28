package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.ReportsNew.CoreBeliefReports.CoreBeliefReportsResponse
import com.neuro.neuroharmony.data.remote.ReportsAPIService
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReportsCoreBeliefRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getcorebeliefreportsrepo(onResult:  (isSuccess: Boolean, response: CoreBeliefReportsResponse?) -> Unit) {

        scope.launch {
            ReportsAPIService.instance.getcorebeliefreports(PrefsHelper().getPref("report_type"),
                PrefsHelper().getPref("sessionId_reports"))
                .enqueue(object :
                    Callback<CoreBeliefReportsResponse> {

                    override fun onResponse(call: Call<CoreBeliefReportsResponse>, response: Response<CoreBeliefReportsResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<CoreBeliefReportsResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}