package com.neuro.neuroharmony.ui.login

import android.content.Context
import androidx.fragment.app.Fragment
import com.neuro.neuroharmony.interfaces.FragmentActions

class BaseFragment: Fragment()  {

    var fragmentActions: FragmentActions? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentActions = context as? FragmentActions
        if (fragmentActions == null) {
//            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentActions = null
    }

}
