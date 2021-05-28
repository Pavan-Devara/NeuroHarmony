package com.neuro.neuroharmony.utils

import android.widget.Button
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType

object SimpleFunctions: BaseActivity() {

    fun checkifemptyString(s: String?): String? {
        if (s==""){
            return "NA"
        }else{
            return s
        }
    }
}