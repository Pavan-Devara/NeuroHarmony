package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.ReportsNew.CoreBeliefDistanceReports.ReportsCoreBeliefDistance
import com.neuro.neuroharmony.data.remote.ReportsAPIService
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReportsCoreBeliefDistanceRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getcorebeliefdistancereport(onResult:  (isSuccess: Boolean, response: ReportsCoreBeliefDistance?) -> Unit) {

        scope.launch {
            ReportsAPIService.instance.getcorebeliefdistancereport(
                PrefsHelper().getPref("report_type"),
                PrefsHelper().getPref("partnerKey_reports"))
                .enqueue(object :
                    Callback<ReportsCoreBeliefDistance> {

                    override fun onResponse(call: Call<ReportsCoreBeliefDistance>, response: Response<ReportsCoreBeliefDistance>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ReportsCoreBeliefDistance>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}