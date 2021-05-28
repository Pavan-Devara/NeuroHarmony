package com.neuro.neuroharmony.ui.login.CompleteMatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.manualMatching.PartnerListViewPageAdapter
import kotlinx.android.synthetic.main.activity_finalize_match_menu.*
import kotlinx.android.synthetic.main.activity_partner_list_menu.*

class FinalizeMatchMenu : AppCompatActivity() {

    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_match_menu)

        val intent1 = this.getIntent();
        if (intent1 != null && intent1.getExtras() != null) {
            //val jobID = this.getIntent().getExtras()!!.getInt("JOBID");
            position = intent1.getIntExtra("position", 0)
        };


        val viewPager: ViewPager = findViewById(R.id.list_finalize_match_viewPager)
        val adapter = FinalizeMatchViewPageAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(position)

        val tabLayout = findViewById<TabLayout>(R.id.list_finalize_match_tabs)
        tabLayout.setupWithViewPager(viewPager)

        back_finalize_match_list_menu.setOnClickListener {
            super.onBackPressed();
        }

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                adapter.notifyDataSetChanged()
            }

        })
    }

    data class  UserDetails_Finalize(

        val userdetails: MutableMap<String, Any>
    )
}
