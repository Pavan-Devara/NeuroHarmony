package com.neuro.neuroharmony.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_neuro_report_matched_user.*

class NeuroReportMatchedUser : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neuro_report_matched_user)
        val neuroReport = intent.getStringExtra("neuro_report")
        val neuro_report = neuroReport

        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"

        back_neuro_match_report2.setOnClickListener {
            super.onBackPressed()
        }

        neuroReportmatcheduser.loadDataWithBaseURL(null,neuro_report,mimeType,utfType, null)
        okay_neuroReport.setOnClickListener {
            super.onBackPressed();

        }


    }
}
