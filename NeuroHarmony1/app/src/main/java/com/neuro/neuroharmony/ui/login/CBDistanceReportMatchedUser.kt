package com.neuro.neuroharmony.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_cbdistance_report_matched_user.*

class CBDistanceReportMatchedUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cbdistance_report_matched_user)
        val core_belief_distance_report = intent.getStringExtra("core_belief_distance_report")


        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"

        cbdistancereportReport_matched_user.loadDataWithBaseURL(null,core_belief_distance_report,mimeType,utfType, null)

        okay_cbdistnaceReport.setOnClickListener {

            super.onBackPressed();
        }
    }
}
