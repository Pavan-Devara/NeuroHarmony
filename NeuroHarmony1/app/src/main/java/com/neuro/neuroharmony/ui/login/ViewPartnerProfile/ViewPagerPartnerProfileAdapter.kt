package com.neuro.neuroharmony.ui.login.ViewPartnerProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.neuro.neuroharmony.utils.PrefsHelper

@Suppress("DEPRECATION")
class ViewPagerPartnerProfileAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        var position  = position+1
        if (position==1){
            val fragment = ViewPartnerProfileNeuroFragment()
            return fragment
        }else if (position==2){
            val fragment = ViewPartnerProfileDesireFragment()
            return fragment
        }else if (position==3){
            val fragment = ViewPartnerProfileCoreBeliefFragment()
            return fragment
        }else if (position==4){
            val fragment = ViewPartnerProfilePersonalFragment()
            return fragment
        }else if (position==5){
            val fragment = ViewPartnerProfileReligiousFragment()
            return fragment
        }else if (position==6){
            val fragment = ViewPartnerProfileFamilyFragment()
            return fragment
        }else if (position==7){
            val fragment = ViewPartnerProfileEducationFragment()
            return fragment
        }else if (position==8){
            val fragment = ViewPartnerProfileLifestyleFragment()
            return fragment
        }else{
            val fragment = ViewPartnerProfileNeuroFragment()
            return fragment
        }

    }

    override fun getCount(): Int {
        return 8
    }



    override fun getPageTitle(position: Int): CharSequence? {
        var position  = position+1
        when (position){
            1 -> return ("NEURO")
            2 -> return ("DESIRE")
            3 -> return ("CORE BELIEF")
            4 -> return ("PERSONAL")
            5 -> return ("RELIGIOUS")
            6 -> return ("FAMILY")
            7 -> return ("EDUCATION & PROFESSION")
            8 -> return ("LIFESTYLE")
            else -> return  ("DEFAULT")
        }
    }


    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}