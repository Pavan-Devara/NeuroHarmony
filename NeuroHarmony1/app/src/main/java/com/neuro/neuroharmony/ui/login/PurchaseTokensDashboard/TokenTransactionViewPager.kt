package com.neuro.neuroharmony.ui.login.PurchaseTokensDashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.neuro.neuroharmony.utils.PrefsHelper

@Suppress("DEPRECATION")
class TokenTransactionViewPager(
    fm: FragmentManager,
    val bundle: Bundle
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if (position==0){
            val fragment = PurchasePackages(bundle)
            return fragment
        }
        else{
            val fragment = NeuroTokenTransactions(bundle)
            return fragment
        }
    }

    override fun getCount(): Int {

        if (PrefsHelper().getPref<Int>("userType")==2){
            return 1
        }
        else{
            return 2
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        var position  = position+1
        if (position==1){
            return  ("PURCHASE")
        }
        else
        {
            return ("NEURO TOKEN")
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}