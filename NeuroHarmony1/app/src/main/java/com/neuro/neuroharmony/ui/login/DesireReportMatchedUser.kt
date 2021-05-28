package com.neuro.neuroharmony.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_desire_report_matched_user.*

class DesireReportMatchedUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desire_report_matched_user)
        val desire_report = intent.getStringExtra("desire_report")

        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"


        back_desire_match_report2.setOnClickListener {
            super.onBackPressed()
        }

        disireReport_matched_user.loadDataWithBaseURL(null,desire_report,mimeType,utfType, null)
        okay_desireReport.setOnClickListener {

            super.onBackPressed();
        }
    }
}
