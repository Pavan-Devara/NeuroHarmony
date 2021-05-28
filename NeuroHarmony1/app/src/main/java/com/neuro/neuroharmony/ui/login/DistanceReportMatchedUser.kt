package com.neuro.neuroharmony.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_distance_report_matched_user.*

class DistanceReportMatchedUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distance_report_matched_user)
        val distance_report = intent.getStringExtra("distance_report")

        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"

        back_distance_match_report2.setOnClickListener {
            super.onBackPressed()
        }

        distanceReport_matched_user.loadDataWithBaseURL(null,distance_report,mimeType,utfType, null)

        okay_distanceReport.setOnClickListener {

            super.onBackPressed();
        }
    }
}
