package com.neuro.neuroharmony.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_neuro_view_report.*

class NeuroViewReport : AppCompatActivity() {
    var Description = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neuro_view_report)
        val intent = intent
        Description = intent.getStringExtra("description")
        back_neuro_match_report3.setOnClickListener {
            super.onBackPressed()
        }

    }
}
