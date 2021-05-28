package com.neuro.neuroharmony.ui.login.Reports

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

@Suppress("DEPRECATION")
class ReportsViewPager(
    fm: FragmentManager,
    val Report: String?,
    val report_type: Int
) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {

        if (report_type==1) {
            val neuro_or_desire = 1
            val fragment = ReportsFragment(position,neuro_or_desire, Report)
            return fragment
        }
        else if (report_type ==3){
            val fragment = ReportsCoreBeliefFragment(position, Report)
            return fragment
        }else if (report_type ==4){
            val fragment = ReportsDistanceFragment(position, Report)
            return fragment
        }else if (report_type ==5) {
            val fragment = ReportsCoreBeliefDistanceFragment(position, Report)
            return fragment
        }else if(report_type ==2){
            val neuro_or_desire = 2
            val fragment = ReportsFragment(position, neuro_or_desire,Report)
            return fragment
        }
        else{
            val neuro_or_desire = -1
            val fragment = ReportsFragment(position, neuro_or_desire,Report)
            return fragment
        }
    }

    override fun getCount(): Int {
        if (report_type==1) {
            return 5
        }else if (report_type==3){
            return 7
        }else if (report_type==4){
            return 8
        }else if (report_type == 5){
            return 5
        }else if(report_type == 2){
            return 5
        }
        else{
            return 0
        }
    }

}
