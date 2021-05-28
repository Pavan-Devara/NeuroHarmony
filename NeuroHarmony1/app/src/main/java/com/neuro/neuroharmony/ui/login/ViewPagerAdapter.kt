package com.neuro.neuroharmony.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.neuro.neuroharmony.utils.PrefsHelper

@Suppress("DEPRECATION")
class ViewPagerAdapter(fm: FragmentManager, jsonArray: String?) : FragmentStatePagerAdapter(fm) {
    private val array = jsonArray
    override fun getItem(position: Int): Fragment {
        val fragment = NeuroMatchedUsersNewFragment(position, array)
        val bundle = Bundle()
        //bundle.putInt("position", position)
        //bundle.putString("jsonArray", array)
        //fragment.arguments = bundle

        return fragment
    }

    override fun getCount(): Int {
        return 10
    }



    override fun getPageTitle(position: Int): CharSequence? {
        var position  = position+1
        return  ("Group "+position)
    }


    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}