package com.neuro.neuroharmony.ui.login.Reports

import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.DesireGraphResult
import com.neuro.neuroharmony.ui.login.NeuroGraphResult
import kotlinx.android.synthetic.main.activity_reports.*
import kotlinx.android.synthetic.main.fragment_reports.*

class Reports : BaseActivity() {

    var report = ""
    companion object{
        val NEURO = 1
        val DESIRE = 2
        val COREBELIEF = 3
        val DISTANCE = 4
        val COREBELIEFDISTANCE = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)
        val reports = intent.getStringExtra("neuro_report")



        val intent = intent

        if (intent.getIntExtra("response_type", 0)== NEURO) {
            report = intent.getStringExtra("neuro_report")
        }else if (intent.getIntExtra("response_type", 0)== DESIRE) {
            report = intent.getStringExtra("neuro_report")
            }
        else if (intent.getIntExtra("response_type", 0)== COREBELIEF){
            report = intent.getStringExtra("corebelief_report")
        }else if (intent.getIntExtra("response_type", 0)== DISTANCE){
            report = intent.getStringExtra("distance_report")
        }else if (intent.getIntExtra("response_type", 0)== COREBELIEFDISTANCE){
            report = intent.getStringExtra("corebelief_distance_report")
        }



        val viewPager: ViewPager = findViewById(R.id.reports_viewpager)
        val adapter = ReportsViewPager(supportFragmentManager, report, intent.getIntExtra("response_type", 0))
        viewPager.adapter = adapter
        

        val tabLayout = findViewById<TabLayout>(R.id.reports_tab_layout)
        tabLayout.setupWithViewPager(viewPager)
        back_report_six.setOnClickListener {
            onBackPressed()
        }
    }

    data class DataKeys(val specs: String)
}
