package com.neuro.neuroharmony.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_user_desire_distance_text.*

class UserDesireDistanceText : AppCompatActivity() {
    var DistanceReport = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_desire_distance_text)

        DistanceReport = intent.getStringExtra("distance")

        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"

        distanceReport.loadDataWithBaseURL(null,DistanceReport,mimeType,utfType, null)


        okay_distanceBeliefReport.setOnClickListener {
            val intent1 = Intent(this, HomePage1::class.java)
            startActivity(intent1)
        }
    }
}

