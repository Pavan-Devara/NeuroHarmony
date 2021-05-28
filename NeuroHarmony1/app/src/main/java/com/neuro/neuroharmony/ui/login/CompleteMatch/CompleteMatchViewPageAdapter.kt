package com.neuro.neuroharmony.ui.login.CompleteMatch

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.neuro.neuroharmony.ui.login.RequestsFragment

    @Suppress("DEPRECATION")
    class CompleteMatchViewPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            val fragment = RequestsFragment(position)
            return fragment
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            var position = position + 1
            if (position == 1){
                return ("Confirmed")
            }else if (position == 2){
                return ("Received")
            }else
                return ("Sent")
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }

    }
