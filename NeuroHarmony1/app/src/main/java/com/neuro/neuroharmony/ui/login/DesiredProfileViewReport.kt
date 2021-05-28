package com.neuro.neuroharmony.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_desired_profile_view_report.*

class DesiredProfileViewReport : AppCompatActivity() {
    var Description = ""
    var NeuroReport = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desired_profile_view_report)


        val DistanceReport = intent.getStringExtra("distance")

        val intent = intent
        NeuroReport = intent.getStringExtra("neuro_report")
        Description = intent.getStringExtra("description")




        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"

        desireBeliefReport.loadDataWithBaseURL(null,Description,mimeType,utfType, null)
        okay_desireBeliefReport.setOnClickListener {
            val intent1 = Intent(this, UserDesireDistanceText::class.java)
            intent1.putExtra("distance", DistanceReport)
            startActivity(intent1)
        }


        okay_desireneuroReport.setOnClickListener {
            val intent1 = Intent(this, UserNeuroDesireProfileReport::class.java)
            intent1.putExtra("neuro_report", NeuroReport)
            startActivity(intent1)
        }
    }
}
