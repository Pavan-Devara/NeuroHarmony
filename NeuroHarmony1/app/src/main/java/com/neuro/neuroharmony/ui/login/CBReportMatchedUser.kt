package com.neuro.neuroharmony.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_cbreport_matched_user.*

class CBReportMatchedUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cbreport_matched_user)
        val core_belief_report = intent.getStringExtra("core_belief_report")



        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"

        back_core_match_report2.setOnClickListener {
            super.onBackPressed()
        }

        cbreportReport_matched_user.loadDataWithBaseURL(null,core_belief_report,mimeType,utfType, null)

        okay_cbReport.setOnClickListener {

            super.onBackPressed();
        }
    }
}
