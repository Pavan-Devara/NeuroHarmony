package com.neuro.neuroharmony.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_user_neuro_desire_profile_report.*

class UserNeuroDesireProfileReport : AppCompatActivity() {

    var NeuroReport = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_neuro_desire_profile_report)

        NeuroReport = intent.getStringExtra("neuro_report")

        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"

        user_neuroReport.loadDataWithBaseURL(null,NeuroReport,mimeType,utfType, null)

        okay_user_neuro_desire_button.setOnClickListener {
            super.onBackPressed();
        }
    }
}
