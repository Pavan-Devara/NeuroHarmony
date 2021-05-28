package com.neuro.neuroharmony.ui.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

@Suppress("DEPRECATION")
class ChatViewPageAdapter (fm: FragmentManager) : FragmentStatePagerAdapter(fm){


    override fun getItem(position: Int): Fragment {
        val fragment = ChatMenuFragmnet(position)

        return fragment
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var position  = position+1
        if (position==1){
            return  ("ACTIVE")
        }
        if(position==2)
        {
            return ("AVAILABLE")
        }
        else{
            return "REQUESTS"
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

}