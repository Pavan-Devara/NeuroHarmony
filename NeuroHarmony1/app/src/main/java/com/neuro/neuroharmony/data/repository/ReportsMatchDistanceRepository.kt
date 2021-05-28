package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.ReportsNew.MatchReports.ReportsMatchedUsers
import com.neuro.neuroharmony.data.remote.ReportsAPIService
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReportsMatchDistanceRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun getmatchdistancereportsrepo(onResult:  (isSuccess: Boolean, response: ReportsMatchedUsers?) -> Unit) {

        scope.launch {
            ReportsAPIService.instance.getmatchdistancereport(
                PrefsHelper().getPref("report_type"),
                PrefsHelper().getPref("partnerKey_reports"))
                .enqueue(object :
                    Callback<ReportsMatchedUsers> {

                    override fun onResponse(call: Call<ReportsMatchedUsers>, response: Response<ReportsMatchedUsers>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ReportsMatchedUsers>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}